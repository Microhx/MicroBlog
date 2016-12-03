package micro.com.microblog.ui.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Stack;

import butterknife.Bind;
import butterknife.OnClick;
import micro.com.microblog.base.activity.BaseMultiLayerRequestActivity;
import micro.com.microblog.R;
import micro.com.microblog.entity.ArticleType;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.EventBean;
import micro.com.microblog.entity.HtmlContent;
import micro.com.microblog.manager.RxManager;
import micro.com.microblog.manager.ShareManager;
import micro.com.microblog.mvc.presenter.DetailContentPresenter;
import micro.com.microblog.parser.IBlogParser;
import micro.com.microblog.parser.ParserFactory;
import micro.com.microblog.utils.Config;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.ToUtils;
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

    @Bind(R.id.title)
    PublicHeadLayout title;
    @Bind(R.id.tencent_webview)
    WebView tencent_webview;
    @Bind((R.id.myProgressBar))
    ContentLoadingProgressBar mCustomProgressBar;

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
     * 文章所在Data中的位置
     */
    private int mArticlePosition;

    /**
     * 经过处理的网页内容
     */
    private HtmlContent mWebContent;
    private Stack<String> mUrlContainer;
    private DetailContentPresenter mPresenter;

    private RxManager mRxManager;

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
        super.initViewsAndData();
        initViews();

        mRxManager = new RxManager();
        hasCollection = DBDataUtils.userHasCollection(mCurrentBlog.getTitle());
    }

    private void initViews() {
        title.setTitle(mCurrentBlog.title);
        title.setRightMsg("操作");

        mUrlContainer = new Stack<>();
        mUrlContainer.add(mCurrentBlog.link);

        tencent_webview.setWebChromeClient(new TencentWebViewChromeClient());
        tencent_webview.setWebViewClient(new TencentWebViewClient());
        tencent_webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        WebSettings webSetting = tencent_webview.getSettings();
        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setAppCacheEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //设置自动加载图片
        webSetting.setLoadsImagesAutomatically(true);
        //拦截图片的加载 网页加载完成后再去除拦截
        //webSetting.setBlockNetworkImage(true);
        //支持缩放
        webSetting.setSupportZoom(true);
        //webview读取设置的viewport 即pc版网页
        webSetting.setUseWideViewPort(true);
        //适应屏幕大小
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSetting.setJavaScriptEnabled(true);
        webSetting.setAllowFileAccess(true);
        tencent_webview.addJavascriptInterface(new ShowPhotoBean(), "photo");

        if (mCurrentBlog.articleType != ArticleType.CSDN) {
            mPresenter = new DetailContentPresenter();
            mPresenter.attachView(this);
            mPresenter.loadData(mCurrentBlog.link, mCurrentBlog.articleType);
        } else {
            tencent_webview.loadUrl(mCurrentBlog.link);
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
                setBtn3Text("复制链接").
                setOperationListener(this).
                showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @OnClick(R.id.iv_back)
    public void ivBack(View view) {
        finish();
    }

    @Override
    public void getDataSuccess(String msg) {
        mWebContent = mBlogParser.getBlogContent(mCurrentBlog.articleInnerType, msg);

        showTheTargetPage();
        tencent_webview.loadDataWithBaseURL(null, mWebContent.mContent, "text/html", "utf-8", null);
    }

    @Override
    public void onItem1Click() {
        if (hasCollection) {
            DBDataUtils.deleteUserCollection(mCurrentBlog);
            ToUtils.toast("取消收藏成功");
        } else {
            mCurrentBlog.setType(Blog.COLLECT_TYPE);
            mCurrentBlog.setType(0);
            DBDataUtils.addUserCollection(mCurrentBlog);
            ToUtils.toast("收藏成功");
        }
        hasCollection = !hasCollection;
        sendRxMessage(true);
    }

    /**
     * share the article
     */
    @Override
    public void onItem2Click() {
        //ToUtils.toast("share");
    }

    @Override
    public void onItem3Click() {
        ClipboardManager myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("MicroBlog", mCurrentBlog.link);
        myClipboard.setPrimaryClip(clipData);
        ToUtils.toast("已经复制到剪切板");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //分享回调
        ShareManager.getInstance().getTencentInstance().onActivityResult(requestCode, requestCode, data);
    }

    @Override
    public void onBackPressed() {
        if (null != mOperationWindow && mOperationWindow.isShowing()) {
            mOperationWindow.dismiss();
            return;
        }

        //网页深层次访问
        if (mUrlContainer.size() > 1) {
            String url = mUrlContainer.pop();
            if (mUrlContainer.size() == 1) {
                if (mCurrentBlog.articleType != ArticleType.CSDN) {
                    tencent_webview.loadDataWithBaseURL(null, mWebContent.mContent, "text/html", "utf-8", null);
                } else {
                    tencent_webview.loadUrl(mUrlContainer.peek());
                }
            } else {
                tencent_webview.loadUrl(url);
            }
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (null != mCurrentBlog) {
            DBDataUtils.addUserReadBlog(mCurrentBlog);
            sendRxMessage(false);
        }
    }

    private void sendRxMessage(boolean isCollect) {
        EventBean bean = new EventBean();
        bean.type = mCurrentBlog.articleType;
        bean.msg = "";
        bean.isCollect = hasCollection;
        bean.isRead = true;
        bean.position = mArticlePosition;

        if (isCollect) {
            mRxManager.post(Config.RX_ARTICLE_HAS_COLLECTED, bean);
        } else {
            mRxManager.post(Config.RX_ARTICLE_HAS_READ, bean);
        }
    }

    @Override
    protected void onDestroy() {
        if (null != tencent_webview) {
            tencent_webview = null;
        }

        if (null != mPresenter) {
            mPresenter.onDetach();
        }

        //此时取消收藏注册
        mRxManager.unRegister(Config.RX_ARTICLE_HAS_COLLECTED);
        super.onDestroy();
    }

    /**
     * ==============微信回调======================
     **/
    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {

    }

    //用于JS与webView交互的内部类
    //点击图片，可以看到全屏
    private final class ShowPhotoBean {

        @JavascriptInterface
        public void showImg(String msg) {
            int getPosition = mWebContent.checkLocation(msg);
            ShowArticleImgActivity.startThisActivity(ArticleDetailActivity.this, mWebContent.photoList, getPosition);
        }
    }

    private class TencentWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            LogUtils.d("loadingUrl : " + url);
            if (!TextUtils.isEmpty(url) && (url.endsWith(".jpg")
                    || url.endsWith(".png")
                    || url.endsWith(".gif")
                    || url.endsWith(".webp")))
                return true;

            mUrlContainer.add(url);
            return super.shouldOverrideUrlLoading(webView, url);
        }
    }

    private class TencentWebViewChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int newProgress) {
            if (null != mCustomProgressBar) {
                if (newProgress == 100) {
                    mCustomProgressBar.setVisibility(View.GONE);
                } else {
                    if (mCustomProgressBar.getVisibility() == View.GONE)
                        mCustomProgressBar.setVisibility(View.VISIBLE);
                    mCustomProgressBar.setProgress(newProgress % 100);
                }
            }
            super.onProgressChanged(webView, newProgress);
        }
    }

}
