package micro.com.microblog.manager;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.connect.dataprovider.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import micro.com.microblog.entity.Blog;
import micro.com.microblog.utils.Config;
import micro.com.microblog.utils.UIUtils;

/**
 * Created by guoli on 2016/9/28.
 * <p/>
 * 分享管理器
 */
public class ShareManager {

    /**
     * 腾讯分享
     */
    private static Tencent mTencent;

    /**
     * 微信分享
     */
    private IWXAPI mWeixinApi;


    private ShareManager() {
    }

    private static ShareManager mInstance;

    public synchronized static ShareManager getInstance() {
        if (null == mInstance) {
            synchronized (ShareManager.class) {
                if (null == mInstance) {
                    mInstance = new ShareManager();
                }
            }
        }
        return mInstance;
    }

    private synchronized void initTencent() {
        if (null == mTencent) {
            mTencent = Tencent.createInstance(Config.TENCENT_APP_ID, UIUtils.getAppContext());
        }
    }

    private synchronized void initWeixinApi() {
        if (null == mWeixinApi) {
            /**
             * 通过WXAPIFactory工厂，获取IWXAPI实例
             */
            mWeixinApi = WXAPIFactory.createWXAPI(UIUtils.getAppContext(), Config.WEIXIN_APP_ID, true);

            //将应用的appId注册到微信
            boolean hasRegister = mWeixinApi.registerApp(Config.WEIXIN_APP_ID);

            System.out.println("----hasRegister--------" + hasRegister);
        }
    }


    public Tencent getTencentInstance() {
        initTencent();
        return mTencent;
    }

    /**
     * 分享到QQ
     */
    public void shareToQQ(Activity activity, Blog blog) {
        if (null == blog) return;
        initTencent();

        Bundle bundle = new Bundle();
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, blog.link);
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_ SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, blog.getTitle());
        //分享的消息摘要，最长50个字
        String msg = blog.getDescription();
        if (msg.length() > 50) {
            msg = msg.substring(0, 50);
        }
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, msg);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "MicroBlog");
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        mTencent.shareToQQ(activity, bundle, new BaseUiListener());
    }

    public void shareToWeixin(Activity activity, Blog blog) {
        initWeixinApi();

        //初始化一个WXWebpageObject对象，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = blog.getLink();

        //用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题，描述
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = blog.getTitle();
        msg.description = blog.getDescription();
        //还有图片

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段用于唯一标识请求
        req.transaction = buildTransaction("Micro_Blog");
        req.message = msg;
        /**
         * WXSceneSession:发送到聊天界面
         * WXSceneTimeline：发送到朋友圈界面
         */
        req.scene = SendMessageToWX.Req.WXSceneTimeline /*: SendMessageToWX.Req.WXSceneSession*/;

        mWeixinApi.sendReq(req);
    }

    /**
     * 获取微信实例
     *
     * @return
     */
    public IWXAPI getWeixinInstance() {
        initWeixinApi();
        return mWeixinApi;
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 分享需要的
     */
    public static class BaseUiListener implements IUiListener {

        @Override
        public void onError(UiError e) {
            System.out.println("share error : " + e.errorDetail);
        }

        @Override
        public void onCancel() {
            System.out.println("share cancel");
        }

        @Override
        public void onComplete(Object response) {
            System.out.println("share finish...");
        }
    }


}
