package micro.com.microblog.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import micro.com.microblog.manager.RxManager;
import micro.com.microblog.utils.Config;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/22 - 0:05
 * interface :
 */
public class FABBehavior extends FloatingActionButton.Behavior {

    RxManager mRxManager ;

    public FABBehavior(Context context, AttributeSet attrs) {
        super();
        if(null == mRxManager) {
            mRxManager = new RxManager();
        }
    }


    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();
            mRxManager.post(Config.MENU_SHOW_HIDE,false);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
            mRxManager.post(Config.MENU_SHOW_HIDE,true);
        }
    }

}
