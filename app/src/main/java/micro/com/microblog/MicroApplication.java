package micro.com.microblog;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.tencent.smtt.sdk.QbSdk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Stack;

import micro.com.microblog.controller.ChangeModeController;
import micro.com.microblog.entity.NotifyBean;
import micro.com.microblog.utils.LogUtils;

/**
 * Created by guoli on 2016/8/23.
 */
public class MicroApplication extends Application {
    private static Context mContext;

    /**
     * activity栈
     */
    private static Stack<Activity> mActivityStack = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        /**
         * 初始化X5内核
         */
        initX5Code();

        /**
         * eventBus注册
         */
        EventBus.getDefault().register(this);
    }

    public static Context getAppContext() {
        return mContext;
    }

    private void initX5Code() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                LogUtils.d("viewInitFinished..." + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                LogUtils.d("core init finished");
            }
        };

        QbSdk.initX5Environment(getApplicationContext(), QbSdk.WebviewInitType.FIRSTUSE_AND_PRELOAD, cb);

    }

    public static void addActivity(Activity atx) {
        mActivityStack.add(atx);
    }

    public static void removeActivity(Activity atx) {
        mActivityStack.remove(atx);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeBackgroundCall(NotifyBean bean) {
        if (!mActivityStack.isEmpty()) {
            ChangeModeController.toggleThemeSetting(mActivityStack);
        }
    }

}
