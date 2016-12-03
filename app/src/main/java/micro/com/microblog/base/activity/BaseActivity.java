package micro.com.microblog.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by guoli on 2016/8/23.
 */
public abstract class BaseActivity extends SwipeBackActivity {

    private static BaseActivity mTopActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initStatueBar();
        beforeContentView();

        super.onCreate(savedInstanceState);
        mTopActivity = this ;
        MicroApplication.addActivity(this);

        setContentView(getContentLayoutId());
        ButterKnife.bind(this);

        initIntent(getIntent()) ;
        initViewsAndData();
    }

    protected void beforeContentView() {
        initTheme();
    }

    /**
     * 设置主题
     */
    private void initTheme() {
    }

    protected void initIntent(Intent intent) {}

    protected abstract void initViewsAndData();

    protected abstract int getContentLayoutId() ;

    public static BaseActivity getTheTopActivity() {
        return mTopActivity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);

        MicroApplication.removeActivity(this);
    }

    /**
     * 是否为全屏
     *
     * @return
     */
    protected boolean activityIsFullScreen() {
        return false;
    }

    protected void enterActivity(Intent intent) {
        startActivity(intent);
    }

    protected void enterActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this,activity));
    }

    /*    private void initStatueBar() {
        Window window = getWindow();
        if (activityIsFullScreen()) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  //保持全屏
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //屏幕高亮
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //设置透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //状态栏字体设置为深色
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT);// SDK21
            }
        }
    }*/



}
