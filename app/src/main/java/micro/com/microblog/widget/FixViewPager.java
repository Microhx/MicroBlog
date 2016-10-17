package micro.com.microblog.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by guoli on 2016/10/14.
 */
public class FixViewPager extends android.support.v4.view.ViewPager {

    public FixViewPager(Context context) {
        super(context);
    }

    public FixViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return false;
    }
}
