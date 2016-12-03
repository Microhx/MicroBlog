package micro.com.microblog.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Arrays;
import java.util.List;

import micro.com.microblog.base.fragment.NewBaseFragment;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 14:52
 */
public class NewBaseFragmentAdapter extends FragmentPagerAdapter {

    private List<String> mTitle;
    private List<NewBaseFragment> mFragmentList;


    public NewBaseFragmentAdapter(FragmentManager fm, List<NewBaseFragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    public NewBaseFragmentAdapter(FragmentManager fm, List<NewBaseFragment> fragmentList, List<String> titleList) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTitle = titleList;
    }

    public NewBaseFragmentAdapter(FragmentManager fm, List<NewBaseFragment> fragmentList, String[] arrayList) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTitle = Arrays.asList(arrayList);
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList == null ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null == mTitle ? null : mTitle.get(position);
    }
}
