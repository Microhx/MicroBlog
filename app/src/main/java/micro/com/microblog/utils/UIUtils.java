package micro.com.microblog.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;
import android.view.View;

import micro.com.microblog.base.activity.BaseActivity;
import micro.com.microblog.base.activity.MicroApplication;

/**
 * Created by guoli on 2016/9/2.
 */
public class UIUtils {

    public static Context getAppContext() {
        return MicroApplication.getAppContext();
    }

    public static void startActivity(Intent intent) {
        BaseActivity ac = BaseActivity.getTheTopActivity();
        if (null != ac) {
            ac.startActivity(intent);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getAppContext().startActivity(intent);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     * @param ctx
     * @return
     */
    public static int getScreenWidth(Context ctx) {
        Resources resources = ctx.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context ctx) {
        Resources resources = ctx.getResources() ;
        DisplayMetrics displayMetrics = resources.getDisplayMetrics() ;
        return displayMetrics.heightPixels ;
    }


    public static String getString(int intValue) {
        return UIUtils.getAppContext().getString(intValue);
    }

    public static int getColor(int color) {
        return UIUtils.getAppContext().getResources().getColor(color) ;
    }

    public static void setTabScrollLayout(android.support.design.widget.TabLayout layout) {
        int tabLayoutWidth = calculateTabWith(layout) ;
        int screenWidth = getScreenWidth(UIUtils.getAppContext()) ;
        if(tabLayoutWidth > screenWidth) {
            layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else {
            layout.setTabMode(TabLayout.MODE_FIXED);
        }
    }

    private static int calculateTabWith(TabLayout layout) {
        int totalWidth  = 0 ;
        int count = layout.getTabCount() ;
        for(int i = 0 ; i < count ; i++) {
            View childView = layout.getChildAt(i);
            if(null == childView) continue;

            //通知父View进行测量
            childView.measure(0,0);
            totalWidth += childView.getMeasuredWidth() ;
        }
        return totalWidth ;
    }

}
