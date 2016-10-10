package micro.com.microblog.emotion;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by guoli on 2016/6/16.
 * 可以左右滑动的ViewPager
 */
public class NotScrollViewPager extends ViewPager {

    public NotScrollViewPager(Context context) {
        this(context,null);
    }

    public NotScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNotScroll(boolean noScroll) {
        this.noScroll = noScroll ;
    }

    private boolean noScroll = true ;

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }
}
