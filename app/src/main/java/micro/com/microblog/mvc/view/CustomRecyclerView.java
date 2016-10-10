package micro.com.microblog.mvc.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import micro.com.microblog.adapter.BaseListAdapter;

/**
 * Created by guoli on 2016/8/27.
 */
public class CustomRecyclerView extends RecyclerView  {

    /**加载数据完成*/
    private boolean loadDataFinish = true ;

    /**是否存在更多数据*/
    private boolean hasMoreData = true ;

    private BaseListAdapter mBaseAdapter ;

    private LoadingListener mLoadingListener ;

    public CustomRecyclerView(Context context) {
        this(context,null);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initDatas();
    }

    protected void initDatas() {}

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.mBaseAdapter = (BaseListAdapter) adapter;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if(dy > 0 && loadDataFinish  && hasMoreData) {
            LayoutManager layoutManager = getLayoutManager() ;
            int lastVisibleItemPosition ;
            if(layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager)layoutManager).findLastVisibleItemPosition();
            }else if(layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
                int[] into = new int[manager.getSpanCount()] ;
                manager.findFirstVisibleItemPositions(into);
                lastVisibleItemPosition = last(into) ;
            }else {
                lastVisibleItemPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
            }

            if(layoutManager.getChildCount() > 0 &&
                    lastVisibleItemPosition >= layoutManager.getItemCount()-1){
                if(null != mLoadingListener){
                    mLoadingListener.loadingMoreData();
                }

            }
        }
    }

    private int last(int[] lastPositions) {
        int last = lastPositions[0];
        for (int value : lastPositions) {
            if (value > last) {
                last = value;
            }
        }
        return last;

    }

    public void setLoadingListener(LoadingListener mLoadingListener) {
        this.mLoadingListener = mLoadingListener;
    }

    public void setHasNoData() {
        hasMoreData = false ;
    }

    public void finishRequest() {
        loadDataFinish = true ;
    }

    public void startRequest(){
        loadDataFinish = false ;
    }
}
