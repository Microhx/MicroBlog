package micro.com.microblog.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import micro.com.microblog.base.fragment.CSDNBlogFragment;
import micro.com.microblog.base.fragment.ITeyeBlogFragment;
import micro.com.microblog.base.fragment.InfoQBlogFragment;
import micro.com.microblog.base.fragment.JccFragment;
import micro.com.microblog.base.fragment.OSChinaFragment;
import micro.com.microblog.utils.Constance;

/**
 * Created by guoli on 2016/8/28.
 */
public class MainTabAdapter extends FragmentPagerAdapter {

    public MainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment baseFragment = null;
        switch (position) {
            case 0:  //INFOQ
                baseFragment = new InfoQBlogFragment();
                break;

            case 1: //ITEYE
                baseFragment = new ITeyeBlogFragment();
                break;

            case 2: //CSDN
                baseFragment = new CSDNBlogFragment();
                break;

            case 3: //泡网
                baseFragment = new JccFragment();
                break;

            case 4: //OSCHINA
                baseFragment = new OSChinaFragment() ;
                break;
        }

        return baseFragment;
    }

    @Override
    public int getCount() {
        return Constance.TAB_TITLE.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Constance.TAB_TITLE[position];
    }
}
