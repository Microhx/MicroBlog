package micro.com.microblog.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import micro.com.microblog.R;

/**
 * Created by guoli on 2016/9/22.
 * <p/>
 * 带有下拉头的RecyclerView
 */
public class RecyclerViewWithHead extends LinearLayout implements CustomerRecyclerView.LocationChangeListener {

    CustomerRecyclerView mRecyclerView;

    LinearLayout head_layout;

    LinearLayout.LayoutParams mLayoutParams ;

    OnDataLoadListener mDataLoadListener ;

    /**
     * 显示最大高度
     */
    private static final int MAX_HEIGHT = 300 ;

    public RecyclerViewWithHead(Context context) {
        this(context, null);
    }

    public RecyclerViewWithHead(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewWithHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initViews(context);

        initListener() ;
    }

    private void initViews(Context context) {
        View.inflate(context, R.layout.head_with_recyclerview, this);
        mRecyclerView = (CustomerRecyclerView) findViewById(R.id.recycler_view);
        head_layout = (LinearLayout) findViewById(R.id.head_layout);

        mLayoutParams = (LayoutParams) head_layout.getLayoutParams();

    }

    public CustomerRecyclerView getRecyclerView() {
        return  mRecyclerView ;
    }


    private void initListener() {
        mRecyclerView.setChangeListener(this);
    }

    @Override
    public void onHeightChange(float dx) {
        mLayoutParams.height = (int) dx;
        head_layout.setLayoutParams(mLayoutParams);
    }

    @Override
    public void onChangeFinish(boolean shouldShow) {
        if(shouldShow) {
            mLayoutParams.height = MAX_HEIGHT;
            if(null != mDataLoadListener) {
                mDataLoadListener.toLoadData();
            }
        }else{
            mLayoutParams.height = 0;
        }
        head_layout.setLayoutParams(mLayoutParams);
    }

    @Override
    public boolean headLayoutIsShow() {
        return mLayoutParams.height > 0 ;
    }

    public void loadDataSuccess() {
        onChangeFinish(false);
        mRecyclerView.setLoadDataSuccess(true);
    }

    public void setDataLoadListener(OnDataLoadListener listener) {
        mDataLoadListener = listener ;
    }

    /**
     * 加载更多数据
     */
    public interface OnDataLoadListener {

        void toLoadData() ;

    }


}
