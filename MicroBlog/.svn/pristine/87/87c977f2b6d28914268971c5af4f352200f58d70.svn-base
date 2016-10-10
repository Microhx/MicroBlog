package micro.com.microblog.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import micro.com.microblog.base.BaseEmotionFragment;

/**
 * Created by guoli on 2016/9/26.
 */
public class EmotionPageAdapter extends FragmentPagerAdapter {

    private int mCount;

    public EmotionPageAdapter(FragmentManager fm, int mCount) {
        super(fm);
        this.mCount = mCount;
    }

    @Override
    public Fragment getItem(int position) {
        BaseEmotionFragment emotionFragment = new BaseEmotionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("_index", position);
        emotionFragment.setArguments(bundle);
        return emotionFragment;
    }

    @Override
    public int getCount() {
        return mCount;
    }
}
