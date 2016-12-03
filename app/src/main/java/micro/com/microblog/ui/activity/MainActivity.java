package micro.com.microblog.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import micro.com.microblog.R;
import micro.com.microblog.base.activity.NewBaseActivity;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.TabEntity;
import micro.com.microblog.fragment.NewCSDNFragment;
import micro.com.microblog.fragment.NewInfoQFragment;
import micro.com.microblog.fragment.NewITeyeFragment;
import micro.com.microblog.fragment.NewJccFragment;
import micro.com.microblog.fragment.NewOSChinaFragment;
import micro.com.microblog.utils.Config;
import micro.com.microblog.utils.Constance;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.ToUtils;
import rx.functions.Action1;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 13:40
 */
public class MainActivity extends NewBaseActivity {

    @Bind(R.id.layout_content)
    FrameLayout layout_content;

    @Bind(R.id.drawerlayout)
    DrawerLayout drawerlayout;

    @Bind(R.id.common_table_layout)
    CommonTabLayout commonTableLayout;

    @Bind(R.id.navigation_header)
    NavigationView navigation_header;

    NewInfoQFragment mInfoQFragment;
    NewITeyeFragment mITeyeFragment;
    NewCSDNFragment mCSDNFragment;
    NewJccFragment mJccFragment;
    NewOSChinaFragment mOSChinaFragment;

    private int mTableHeight;

    @Override
    protected void initOtherElements() {
        //关闭侧滑删除
        setSwipeBackEnable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initNavigationHeader();

        initFragment(savedInstanceState);

        initMeasureTableLayout();

        initRxManager();
    }

    private void initMeasureTableLayout() {
        commonTableLayout.measure(0, 0);
        mTableHeight = commonTableLayout.getMeasuredHeight();
        LogUtils.d("tableHeight:" + mTableHeight);
    }

    private void initRxManager() {
        mRxManager.on(Config.MENU_SHOW_HIDE, new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                startAnimation(aBoolean);
            }
        });
    }


    private void initNavigationHeader() {
        navigation_header.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showTheMenuItem(item);
                return true;
            }
        });
    }

    private void showTheMenuItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_chat:
                enterActivity(UserSearchResultActivity.class);
                break;

            case R.id.item_collection:
                enterRecordActivity(Blog.COLLECT_TYPE);
                break;

            case R.id.item_record:
                enterRecordActivity(Blog.READ_TYPE);
                break;

            case R.id.item_update:
                ToUtils.toast("努力开发中,敬请期待哦....");
                break;

            case R.id.item_about_author:
                enterActivity(AboutAuthorActivity.class);
                break;
        }
        //关闭菜单
        drawerlayout.closeDrawers();
    }

    private void enterRecordActivity(int type) {
        Intent _intent = new Intent(this, UserCollectionActivity.class);
        _intent.putExtra("type", type);
        enterActivity(_intent);
    }


    private void initFragment(Bundle state) {
        int currentPosition = 0;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (null != state) {
            mInfoQFragment = (NewInfoQFragment) getSupportFragmentManager().findFragmentByTag("InfoQFragment");
            mITeyeFragment = (NewITeyeFragment) getSupportFragmentManager().findFragmentByTag("ITeyeFragment");
            mCSDNFragment = (NewCSDNFragment) getSupportFragmentManager().findFragmentByTag("CSDNFragment");
            mJccFragment = (NewJccFragment) getSupportFragmentManager().findFragmentByTag("JccFragment");
            mOSChinaFragment = (NewOSChinaFragment) getSupportFragmentManager().findFragmentByTag("OSChinaFragment");
            currentPosition = state.getInt("save_postion", 0);
        } else {
            transaction.add(R.id.layout_content, mInfoQFragment = new NewInfoQFragment(), "InfoQFragment");
            transaction.add(R.id.layout_content, mITeyeFragment = new NewITeyeFragment(), "ITeyeFragment");
            transaction.add(R.id.layout_content, mCSDNFragment = new NewCSDNFragment(), "CSDNFragment");
            transaction.add(R.id.layout_content, mJccFragment = new NewJccFragment(), "JccFragment");
            transaction.add(R.id.layout_content, mOSChinaFragment = new NewOSChinaFragment(), "OSChinaFragment");
        }

        transaction.commit();
        switchTo(currentPosition);

    }

    private void switchTo(int currentPosition) {
        mRxManager.post(Constance.RX_CHANGE_TOOLBAR_TITLE, currentPosition);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (currentPosition) {
            case 0:
                transaction.show(mInfoQFragment);
                transaction.hide(mITeyeFragment);
                transaction.hide(mCSDNFragment);
                transaction.hide(mJccFragment);
                transaction.hide(mOSChinaFragment);
                break;

            case 1:
                transaction.hide(mInfoQFragment);
                transaction.show(mITeyeFragment);
                transaction.hide(mCSDNFragment);
                transaction.hide(mJccFragment);
                transaction.hide(mOSChinaFragment);

                break;

            case 2:
                transaction.hide(mInfoQFragment);
                transaction.hide(mITeyeFragment);
                transaction.show(mCSDNFragment);
                transaction.hide(mJccFragment);
                transaction.hide(mOSChinaFragment);

                break;

            case 3:

                transaction.hide(mInfoQFragment);
                transaction.hide(mITeyeFragment);
                transaction.hide(mCSDNFragment);
                transaction.show(mJccFragment);
                transaction.hide(mOSChinaFragment);

                break;

            case 4:
                transaction.hide(mInfoQFragment);
                transaction.hide(mITeyeFragment);
                transaction.hide(mCSDNFragment);
                transaction.hide(mJccFragment);
                transaction.show(mOSChinaFragment);
                break;
        }

        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void initViews() {
        ArrayList<CustomTabEntity> entityList = new ArrayList<>();
        TabEntity entity;
        String[] titles = getResources().getStringArray(R.array.main_title);
        for (int i = 0; i < titles.length; i++) {
            entity = new TabEntity(R.drawable.d_shayan, R.drawable.d_aini, titles[i]);
            entityList.add(entity);
        }
        commonTableLayout.setTabData(entityList);

        commonTableLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected View getTargetView() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main3;
    }

    private void startAnimation(Boolean showOrHide) {
        final ViewGroup.LayoutParams layoutParams = commonTableLayout.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if (!showOrHide) {
            valueAnimator = ValueAnimator.ofInt(mTableHeight, 0);
            alpha = ObjectAnimator.ofFloat(commonTableLayout, "alpha", 1, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(0, mTableHeight);
            alpha = ObjectAnimator.ofFloat(commonTableLayout, "alpha", 0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                LogUtils.d("layoutParams.height:" + layoutParams.height);
                commonTableLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator, alpha);
        animatorSet.start();
    }

    private boolean mShouldExist = false;

    @Override
    public void onBackPressed() {
        if (!mShouldExist) {
            mShouldExist = true;
            ToUtils.toast("再按一次退出应用");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mShouldExist = false;
                }
            }, 1500);
            return;
        }
        finish();
    }
}

