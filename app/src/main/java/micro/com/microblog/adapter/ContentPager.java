package micro.com.microblog.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by guoli on 2016/9/27.
 */
public class ContentPager extends PagerAdapter {

    private List<View> mContainer ;

    public ContentPager(List<View> container) {
        this.mContainer = container ;
    }

    @Override
    public int getCount() {
        return mContainer == null ? 0 : mContainer.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View targetView = mContainer.get(position);
        container.addView(targetView);
        return targetView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View targetView = mContainer.get(position);
        container.removeView(targetView);
    }

}
