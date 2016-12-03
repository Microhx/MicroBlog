package micro.com.microblog.base.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import micro.com.microblog.R;
import micro.com.microblog.base.adapter.NewBaseFragmentAdapter;
import micro.com.microblog.utils.Config;
import micro.com.microblog.utils.Constance;
import micro.com.microblog.utils.UIUtils;
import rx.functions.Action1;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 15:28
 */
public abstract class NewContainerFragment extends NewBaseFragment {

    @Bind(R.id.parent_layout)
    CoordinatorLayout parent_layout;

    @Bind(R.id.tab_layout)
    TabLayout tab_layout;

    @Bind(R.id.view_pager)
    ViewPager view_pager;

    @Bind(R.id.v_toolbar)
    Toolbar v_toolbar;


    /**
     * 当前FragmentAdapter
     */
    NewBaseFragmentAdapter mBaseFragmentAdapter;

    @Override
    protected int getResLayoutId() {
        return R.layout.layout_infoq;
    }

    @Override
    protected void initViews() {
        List<NewBaseFragment> fragmentList = new ArrayList<>();
        String[] titles = getResources().getStringArray(getStringArrayId());

        NewBaseFragment baseFragment;
        Bundle bundle;
        for (int i = 0; i < titles.length; i++) {
            baseFragment = getImplFragment();
            bundle = new Bundle();
            bundle.putString("article", getFragmentTag());
            bundle.putString("_title", titles[i]);
            baseFragment.setArguments(bundle);
            fragmentList.add(baseFragment);
        }

        mBaseFragmentAdapter = new NewBaseFragmentAdapter(getChildFragmentManager(), fragmentList, titles);
        view_pager.setAdapter(mBaseFragmentAdapter);
        view_pager.setOffscreenPageLimit(titles.length);

        tab_layout.setupWithViewPager(view_pager);
        UIUtils.setTabScrollLayout(tab_layout);

        setTitleShowing();
    }

    @Override
    protected View getTargetLoadingView() {
        return parent_layout;
    }

    private void setTitleShowing() {
        mRxManager.on(Constance.RX_CHANGE_TOOLBAR_TITLE, new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                String[] arrays = getResources().getStringArray(R.array.toolbar_title);
                v_toolbar.setTitle(arrays[integer % arrays.length]);
            }
        });
    }

    protected abstract int getStringArrayId();

    protected abstract String getFragmentTag();

    protected abstract NewBaseFragment getImplFragment();

    @OnClick(R.id.fab)
    public void onCall(View v) {
        mRxManager.post(Config.SCROLL_TO_TOP , "");
    }

}
