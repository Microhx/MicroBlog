package micro.com.microblog;

import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.View;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;

import java.util.Stack;

import butterknife.Bind;
import butterknife.OnClick;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.EventBean;
import micro.com.microblog.manager.ShareManager;
import micro.com.microblog.mvc.presenter.DetailContentPresenter;
import micro.com.microblog.parser.IBlogParser;
import micro.com.microblog.parser.ParserFactory;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.FileUtils;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.ToUtils;
import micro.com.microblog.widget.OperationWindow;
import micro.com.microblog.widget.PublicHeadLayout;

/**
 * Created by guoli on 2016/9/16.
 */
public class ArticleDetailActivity extends BaseMultiLayerRequestActivity implements
        OperationWindow.OperationClickListener,
        IWXAPIEventHandler {

    public static final String PASS_ARTICLE = "_article";

    PublicHeadLayout title;
    WebView tencent_webview;

    /**
     * 解析文章类
     */
    IBlogParser mBlogParser;

    Blog mCurrentBlog;

    /**
     * 用户是否收藏
     */
    boolean hasCollection;

    /**
     * 当前所访问的地址
     */
    private String mCurrentUrl;

    private Stack<String> mUrlContainer;

    private DetailContentPresenter mPresenter;

    /**
     * 文章所在Data中的位置
     */
    private int mArticlePosition ;


    /**
     * 经过处理的网页内容
     */
    private String mWebContent;

    @Override
    protected int getContentLayoutId() {
        return R.layout.article_detail_activity;
    }

    @Override
    protected View getTargetView() {
        return tencent_webview;
    }

    @Override
    protected void initIntent(Intent intent) {
        if (null != intent) {
            mCurrentBlog = (Blog) intent.getSerializableExtra(PASS_ARTICLE);
            mArticlePosition = intent.getIntExtra("position",-1);
        }

        if (null != mCurrentBlog) {
            mBlogParser = ParserFactory.getParserInstance(mCurrentBlog.articleType);
        }
    }

    @Override
    protected void initViewsAndData() {
        title = (PublicHeadLayout) findViewById(R.id.title);
        tencent_webview = (WebView) findViewById(R.id.tencent_webview);
        super.initViewsAndData();

        initViews();
        initData();
        initCollectionSetting();
    }

    private void initCollectionSetting() {
        //这里要使用RxJava

        hasCollection = DBDataUtils.userHasCollection(mCurrentBlog.getTitle());
    }

    private void initData() {
        title.setTitle(mCurrentBlog.title);
        title.setRightMsg("操作");

        mPresenter = new DetailContentPresenter();
        mPresenter.attachView(this);
        mPresenter.loadData(mCurrentBlog.link, mCurrentBlog.articleType);
    }

    private void initViews() {
        mUrlContainer = new Stack<>();

        tencent_webview.setWebViewClient(new TencentWebViewClient());
        tencent_webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        WebSettings webSetting = tencent_webview.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setAppCacheEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSetting.setLoadsImagesAutomatically(Build.VERSION.SDK_INT >= 19);
        //拦截图片的加载 网页加载完成后再去除拦截
//        webSetting.setBlockNetworkImage(true);
        //支持缩放
        webSetting.setSupportZoom(true);
        //webview读取设置的viewport 即pc版网页
        webSetting.setUseWideViewPort(true);
        //适应屏幕大小
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    }

    private OperationWindow mOperationWindow ;

    @OnClick(R.id.tv_msg)
    public void onOperation(View view) {
        if(null == mOperationWindow) {
            mOperationWindow = new OperationWindow(this,mCurrentBlog);
        }

        mOperationWindow.
                setBtn1Text(hasCollection ? "取消收藏" : "收\u3000\u3000藏").
                setBtn2Text("分享").
                setOperationListener(this).
                showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @OnClick(R.id.iv_back)
    public void ivBack(View view) {
        finish();
    }

    @Override
    public void getDataSuccess(String msg) {
        mWebContent = mBlogParser.getBlogContent(0, msg);

        showTheTargetPage();
        tencent_webview.loadDataWithBaseURL(null, mWebContent, "text/html", "utf-8", null);
    }

    /**
     * Collection the article
     */
    @Override
    public void onItem1Click() {
        if (hasCollection) {
            DBDataUtils.deleteUserCollection(mCurrentBlog);
            ToUtils.toast("取消收藏成功");
        } else {
            mCurrentBlog.setType(Blog.COLLECT_TYPE);
            DBDataUtils.addUserCollection(mCurrentBlog);
            ToUtils.toast("收藏成功");
        }
        hasCollection = !hasCollection;
    }

    /**
     * share the article
     */
    @Override
    public void onItem2Click() {
        //ToUtils.toast("share");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //分享回调
        ShareManager.getInstance().getTencentInstance().onActivityResult(requestCode,requestCode,data);
    }

    private class TencentWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            LogUtils.d("loadingUrl : " + url);

            return super.shouldOverrideUrlLoading(webView, url);
        }
    }

    @Override
    public void onBackPressed() {
        if(null != mOperationWindow && mOperationWindow.isShowing()) {
            mOperationWindow.dismiss();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(null != mCurrentBlog) {
            mCurrentBlog.setType(Blog.READ_TYPE);
            DBDataUtils.addUserReadBlog(mCurrentBlog);
        }
    }

    @Override
    protected void onDestroy() {
        if (null != tencent_webview) {
            tencent_webview = null;
        }

        mPresenter.onDetach();
        sendMessage();

        super.onDestroy();
    }

    private void sendMessage() {
        EventBean bean = new EventBean() ;
        bean.type = mCurrentBlog.articleType ;
        bean.msg = "" ;
        bean.isCollect = hasCollection ;
        bean.isRead = true ;
        bean.position = mArticlePosition ;

        EventBus.getDefault().post(bean);
    }

    /**==============微信回调======================**/
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

    }

}
