package micro.com.microblog.base.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.tencent.smtt.sdk.QbSdk;

import java.util.Stack;

import micro.com.microblog.utils.LogUtils;

/**
 * Created by guoli on 2016/8/23.
 */
public class MicroApplication extends Application {
    private static Context mContext;

    public static Handler mHandler;

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

        mHandler = new Handler(Looper.getMainLooper());
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


}
