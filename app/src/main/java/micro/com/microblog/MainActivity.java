package micro.com.microblog;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.OnClick;
import micro.com.microblog.adapter.MainTabAdapter;
import micro.com.microblog.controller.ChangeModeController;
import micro.com.microblog.utils.ToUtils;

public class MainActivity extends BaseActivity {

    ImageView iv_search;
    TabLayout tab_layout;
    ViewPager id_viewpager;
    DrawerLayout drawerlayout;
    NavigationView navigation_header;

    MainTabAdapter mTabAdapter;

    @Override
    protected void initViewsAndData() {
        initViews();

        initNavigationView();

        initViewPager();

        //关闭侧滑删除
        setSwipeBackEnable(false);
    }

    private void initViews() {
        iv_search = (ImageView) findViewById(R.id.iv_search);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        id_viewpager = (ViewPager) findViewById(R.id.id_viewpager);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigation_header = (NavigationView) findViewById(R.id.navigation_header);
    }

    private void initNavigationView() {
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
                enterActivity(UserChattingActivity.class);
                break;

            case R.id.item_collection:
                enterActivity(UserCollectionActivity.class);
                break;

            case R.id.item_record:
                enterActivity(UserRecorderActivity.class);
                break;

            case R.id.item_update:
                ToUtils.toast("努力开发中,敬请期待哦....");
                break;

            case R.id.item_about_author:
                enterActivity(AboutAuthorActivity.class);
                break;

            case R.id.item_setting :
                enterActivity(UserSettingActivity.class);
                break;
        }
        //关闭菜单
        drawerlayout.closeDrawers();
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main2;
    }

    private void initViewPager() {
        mTabAdapter = new MainTabAdapter(getSupportFragmentManager());
        id_viewpager.setAdapter(mTabAdapter);
        id_viewpager.setOffscreenPageLimit(3);

        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_layout.setupWithViewPager(id_viewpager);
    }

    @OnClick(R.id.iv_search)
    public void onSearch(View view) {
        enterActivity(SearchResultActivity.class);

    }

}
