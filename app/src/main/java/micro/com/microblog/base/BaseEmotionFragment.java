package micro.com.microblog.base;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import micro.com.microblog.R;
import micro.com.microblog.adapter.ContentPager;
import micro.com.microblog.adapter.EmotionAdapter;
import micro.com.microblog.emotion.EmojiIndicatorView;
import micro.com.microblog.utils.GlobalItemClickListenerUtils;
import micro.com.microblog.utils.UIUtils;

/**
 * Created by guoli on 2016/9/26.
 */
public class BaseEmotionFragment extends BaseFragment {

    private int mIndex;

    @Bind(R.id.inner_viewpager)
    ViewPager mInnerViewPager ;

    @Bind(R.id.indicator_view)
    EmojiIndicatorView indicatorView ;

    ContentPager mContentPager ;

    @Override
    protected int getLayoutId() {
        return R.layout.emotion_layout;
    }

    @Override
    protected void initViewAndEvent() {
        List<View> container = new ArrayList<>() ;
        for(int i = 0 ; i < 3 ; i++) {
            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.emotion_content_layout,null,false);
            GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
            gridView.setAdapter(new EmotionAdapter(i, UIUtils.getScreenWidth(getActivity())/7));
            gridView.setOnItemClickListener(GlobalItemClickListenerUtils.getInstance().getItemClickListener());
            container.add(rootView) ;
        }

        indicatorView.initIndicator(container.size());
        mContentPager = new ContentPager(container) ;
        mInnerViewPager.setAdapter(mContentPager);

        mInnerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                indicatorView.changePointBackground(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    /**
     *
     *
     contentPagerAdapter = new EmotionContentAdapter(getFragmentManager(),5) ;
     indicatorView.initIndicator(5);
     mInnerViewPager.setAdapter(contentPagerAdapter);

     mInnerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
    indicatorView.changePointBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
    });

     *
     *
     */
}
