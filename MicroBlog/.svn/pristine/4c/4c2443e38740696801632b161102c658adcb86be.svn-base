package micro.com.microblog.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by guoli on 2016/9/22.
 */
public class CustomerRecyclerView extends RecyclerView {

    /**
     * 是否滑动到顶部
     */
    private boolean isOnTheTop;

    private boolean isMoveUp;

    /**
     * 滑动的y轴距离
     */
    private int mMoveY;

    /**
     * 可以滑动的最大高度
     */
    private static final int MAX_HEIGHT = 300;

    /**
     * 当前的滑动的高度
     */
    private int mCurrentHeight;

    private LocationChangeListener mChangeListener;

    /**
     * 加载数据是否成功
     */
    private boolean loadDataSuccess = true;


    public CustomerRecyclerView(Context context) {
        this(context, null);
    }

    public CustomerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initViews(context);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    private void initViews(Context context) {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(-1)) {
                    onScrollToTop();
                } else if (!recyclerView.canScrollVertically(1)) {
                    onScrollToBottom();
                } else if (dy < 0) {
                    onScrollUp();
                } else if (dy > 0) {
                    onScrollDown();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void onScrollDown() {
        isMoveUp = false;
        isOnTheTop = false;
    }

    private void onScrollUp() {
        isMoveUp = true;
        isOnTheTop = false;
    }

    private void onScrollToBottom() {
        isOnTheTop = false;
    }

    private void onScrollToTop() {
        isOnTheTop = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMoveY = (int) e.getRawY();
                mCurrentHeight = 0;
                break;

            case MotionEvent.ACTION_MOVE:
                System.out.println("---the isOnTheTop-----" + isOnTheTop + "---" + isMoveUp);

                if (isOnTheTop) {
                    int _tempY = (int) e.getRawY();
                    int changeY = _tempY - mMoveY;

                    if (null != mChangeListener && changeY > 0) {
                        //滑动到第一个位置
                        smoothScrollToPosition(0);
                        //累计高度 模拟尼阻
                        mCurrentHeight += changeY * 50.f / (mCurrentHeight + 50);
                        if (mCurrentHeight < 0) {
                            mCurrentHeight = 0;
                        }

                        if (mCurrentHeight > MAX_HEIGHT) {
                            mCurrentHeight = MAX_HEIGHT;
                        }

                        mChangeListener.onHeightChange(mCurrentHeight);
                    }

                    mMoveY = _tempY;

                    /**
                     * 在滑动的过程中 再次上滑将会不响应
                     */
                    if (isMoveUp) {
                        return false;
                    }
                } else {
                    //头布局在显示，此时还向上滑动时，应该禁止
                    if (null != mChangeListener && mChangeListener.headLayoutIsShow() && !isMoveUp) {
                        return false;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if (null != mChangeListener && isOnTheTop && loadDataSuccess) {
                    boolean showShow = mCurrentHeight > MAX_HEIGHT / 2;
                    if (showShow) {
                        loadDataSuccess = false;
                    }
                    mChangeListener.onChangeFinish(showShow);
                } else if (!isOnTheTop) {  //不是在顶部，此时消失
                    if (null != mChangeListener)
                        mChangeListener.onHeightChange(0);
                }

                isOnTheTop = false;
                isMoveUp = false;
                mCurrentHeight = 0;

                break;
        }
        return super.onTouchEvent(e);
    }

    public void setChangeListener(LocationChangeListener mChangeListener) {
        this.mChangeListener = mChangeListener;
    }

    public void setLoadDataSuccess(boolean dataSuccess) {
        this.loadDataSuccess = dataSuccess;
    }


    /**
     * Recycler滑动监听
     */
    public interface LocationChangeListener {
        void onHeightChange(float dx);

        void onChangeFinish(boolean isClose);

        boolean headLayoutIsShow();
    }
}
