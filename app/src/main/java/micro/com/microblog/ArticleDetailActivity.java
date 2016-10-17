package micro.com.microblog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;

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
import micro.com.microblog.adapter.ArticleType;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.EventBean;
import micro.com.microblog.entity.HtmlContent;
import micro.com.microblog.manager.ShareManager;
import micro.com.microblog.mvc.presenter.DetailContentPresenter;
import micro.com.microblog.parser.IBlogParser;
import micro.com.microblog.parser.ParserFactory;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.FileUtils;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.ToUtils;
import micro.com.microblog.widget.MarkdownView;
import micro.com.microblog.widget.OperationWindow;
import micro.com.microblog.widget.PublicHeadLayout;

/**
 * Created by guoli on 2016/9/16.
 */
@SuppressLint("SetJavaScriptEnabled")
public class ArticleDetailActivity extends BaseMultiLayerRequestActivity implements
        OperationWindow.OperationClickListener,
        IWXAPIEventHandler {

    public static final String PASS_ARTICLE = "_article";

    PublicHeadLayout title;
    WebView tencent_webview;

    MarkdownView markdownView;
    /**
     * 解析文章类
     */
    IBlogParser mBlogParser;

    Blog mCurrentBlog;

    /**
     * 用户是否收藏
     */
    boolean hasCollection;

    private Stack<String> mUrlContainer;

    private DetailContentPresenter mPresenter;

    /**
     * 文章所在Data中的位置
     */
    private int mArticlePosition;

    /**
     * 经过处理的网页内容
     */
    private HtmlContent mWebContent;

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
            mArticlePosition = intent.getIntExtra("position", -1);
        }

        if (null == mCurrentBlog) {
            LogUtils.d("blog is null");
            showErrorPage();
            return;
        }

        mBlogParser = ParserFactory.getParserInstance(mCurrentBlog.articleType);
    }

    @Override
    protected void initViewsAndData() {
        title = (PublicHeadLayout) findViewById(R.id.title);
        tencent_webview = (WebView) findViewById(R.id.tencent_webview);
        markdownView = (MarkdownView) findViewById(R.id.markdownView);

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
        mUrlContainer.add(mCurrentBlog.link);

        if (mCurrentBlog.articleType == ArticleType.OSCHINA) {
            markdownView.setVisibility(View.VISIBLE);
            tencent_webview.setVisibility(View.GONE);
        } else {
            markdownView.setVisibility(View.GONE);
            tencent_webview.setVisibility(View.VISIBLE);

            tencent_webview.setWebViewClient(new TencentWebViewClient());
            tencent_webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            WebSettings webSetting = tencent_webview.getSettings();
            webSetting.setDefaultTextEncodingName("UTF-8");
            webSetting.setAppCacheEnabled(true);
            webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            webSetting.setLoadsImagesAutomatically(Build.VERSION.SDK_INT >= 19);
            //拦截图片的加载 网页加载完成后再去除拦截
            //webSetting.setBlockNetworkImage(true);
            //支持缩放
            webSetting.setSupportZoom(true);
            //webview读取设置的viewport 即pc版网页
            webSetting.setUseWideViewPort(true);
            //适应屏幕大小
            webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

            webSetting.setJavaScriptEnabled(true);
            webSetting.setAllowFileAccess(true);
            tencent_webview.addJavascriptInterface(new ShowPhotoBean(),"photo");
        }

    }

    private OperationWindow mOperationWindow;

    @OnClick(R.id.tv_msg)
    public void onOperation(View view) {
        if (null == mOperationWindow) {
            mOperationWindow = new OperationWindow(this, mCurrentBlog);
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

        System.out.println("---the content-->>- " + mWebContent);

        if (mCurrentBlog.articleType == ArticleType.OSCHINA) {
            markdownView.loadMarkdown(mWebContent.mContent,"file:///android_asset/markdown_css_themes/classic.css");
        } else {
            tencent_webview.loadDataWithBaseURL(null, mWebContent.mContent, "text/html", "utf-8", null);
        }
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
        ShareManager.getInstance().getTencentInstance().onActivityResult(requestCode, requestCode, data);
    }

    private class TencentWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            LogUtils.d("loadingUrl : " + url);
            mUrlContainer.add(url);

            return super.shouldOverrideUrlLoading(webView, url);
        }
    }

    @Override
    public void onBackPressed() {
        if (null != mOperationWindow && mOperationWindow.isShowing()) {
            mOperationWindow.dismiss();
            return;
        }

        //网页深层次访问
        if (mUrlContainer.size() > 1) {
            mUrlContainer.pop();
            if (mUrlContainer.size() == 1) {
                tencent_webview.loadDataWithBaseURL(null, mWebContent.mContent, "text/html", "utf-8", null);
            }
            String url = mUrlContainer.peek();
            tencent_webview.loadUrl(url);
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (null != mCurrentBlog) {
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
        EventBean bean = new EventBean();
        bean.type = mCurrentBlog.articleType;
        bean.msg = "";
        bean.isCollect = hasCollection;
        bean.isRead = true;
        bean.position = mArticlePosition;

        EventBus.getDefault().post(bean);
    }

    /**
     * ==============微信回调======================
     **/
    @Override
    public void onReq(BaseReq baseReq) {}

    @Override
    public void onResp(BaseResp baseResp) {

    }


    //用于JS与webView交互的内部类
    //点击图片，可以看到全屏
    private final class ShowPhotoBean {

        @JavascriptInterface
        public void showImg(String msg) {
            int getPosition = mWebContent.checkLocation(msg) ;
            ShowArticleImgActivity.startThisActivity(ArticleDetailActivity.this,mWebContent.photoList,getPosition);
        }
    }
}
