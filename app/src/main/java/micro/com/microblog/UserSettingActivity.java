package micro.com.microblog;

import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;
import micro.com.microblog.controller.ChangeModeController;
import micro.com.microblog.entity.NotifyBean;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.widget.PublicHeadLayout;
import micro.com.microblog.widget.SettingItem;

/**
 * Created by guoli on 2016/10/12.
 * <p>
 * 日间/夜间 模式
 * 设置网络字体 网络字体
 * 加载网络图片
 */
public class UserSettingActivity extends BaseActivity {

    @Bind(R.id.title)
    PublicHeadLayout title;

    @Bind(R.id.item_mode)
    SettingItem item_mode;

    @Override
    protected void beforeContentView() {
        super.beforeContentView();

    }

    @Override
    protected void initViewsAndData() {}


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_user_setting;
    }

    /**
     * 日夜间模式切换
     *
     * @param v
     */
    @OnClick(R.id.item_mode)
    public void onCallMode(View v) {
        System.out.println("----enter the call mode------");
        //ChangeModeController.toggleThemeSetting(this);
        EventBus.getDefault().post(new NotifyBean());
    }

}
