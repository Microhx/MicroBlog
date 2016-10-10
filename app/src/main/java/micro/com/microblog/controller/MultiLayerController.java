package micro.com.microblog.controller;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by guoli on 2016/8/24.
 * 多层页面控制器
 */
public class MultiLayerController implements MultiLayerViews {
    /*初始化所需要的View 默认显示的view*/
    private View mView;
    /**
     * 当前显示的view
     */
    private View mCurrentView;
    /**
     * 当前view的索引
     */
    private int mCurrentIndex;
    /**
     * 当前所有view所共用的父类Group
     */
    private ViewGroup mViewParent;
    /**
     * ViewGroup的layout参数
     */
    private ViewGroup.LayoutParams mLayoutParams;

    public MultiLayerController(View mView) {
        this.mView = mView;
    }

    @Override
    public View getCurrentView() {
        return mCurrentView;
    }

    @Override
    public void changeCurrentView(View targetView) {
        if (null == mViewParent) {
            initViews();
        }

        if (mViewParent.getChildAt(mCurrentIndex) != targetView) {
            if (null != targetView.getParent()) {
                ViewGroup parent = (ViewGroup) targetView.getParent();
                parent.removeView(targetView);
            }

            mViewParent.removeViewAt(mCurrentIndex);
            mViewParent.addView(targetView, mCurrentIndex, mLayoutParams);
        }
    }

    private void initViews() {
        mLayoutParams = mView.getLayoutParams();
        if (null != mView.getParent()) {
            mViewParent = (ViewGroup) mView.getParent();
        } else {
            mViewParent = (ViewGroup) mView.getRootView().findViewById(android.R.id.content);
        }
        
        int _totalSize = mViewParent.getChildCount();
        for (int i = 0; i < _totalSize; i++) {
            if (mView == mViewParent.getChildAt(i)) {
                mCurrentIndex = i;
                break;
            }
        }

        mCurrentView = mView;
    }

    @Override
    public void restoreView() {
        changeCurrentView(mView);
    }
}
