package micro.com.microblog.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import micro.com.microblog.R;
import micro.com.microblog.adapter.BaseListAdapter;
import micro.com.microblog.entity.EventBean;
import micro.com.microblog.mvc.IBaseListUIView;
import micro.com.microblog.mvc.presenter.BaseListPresenter;
import micro.com.microblog.mvc.view.CustomRecyclerView;
import micro.com.microblog.mvc.view.FooterView;
import micro.com.microblog.mvc.view.LoadingListener;
import micro.com.microblog.parser.IBlogParser;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.LogUtils;

/**
 * Created by guoli on 2016/8/24.
 * <p/>
 * 带有List列表的Fragment
 */
public abstract class BaseListFragment<V, T extends BaseListPresenter<IBaseListUIView<V>>>
        extends BaseMultiLayerFragment
        implements IBaseListUIView<V>,
        SwipeRefreshLayout.OnRefreshListener, LoadingListener {

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;

    @Bind(R.id.recycler_view)
    CustomRecyclerView recycler_view;

    /**
     * 当前listPresenter
     */
    T mListPresenter;

    BaseListAdapter<V> mBaseListAdapter;

    IBlogParser mBlogParser;

    /**
     * 当前第n页
     */
    private int mCurrentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.base_list_fragment_layout;
    }

    @Override
    protected View getTargetView() {
        return swipe_refresh_layout;
    }

    /**
     * 当前List大小
     */
    private int mCurrentPageSize;

    @Override
    protected void initViewAndEvent() {
        super.initViewAndEvent();

        mListPresenter = getListPresenter();
        mListPresenter.attachView(this);

        mBaseListAdapter = getBaseListAdapter();
        mBaseListAdapter.setFooterView(new FooterView(getContext()));

        recycler_view.setAdapter(mBaseListAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view.setLoadingListener(this);

        //初始化blog
        mBlogParser = getBlogParser();

        swipe_refresh_layout.setOnRefreshListener(this);
    }

    protected abstract BaseListAdapter<V> getBaseListAdapter();

    protected abstract T getListPresenter();

    protected abstract IBlogParser getBlogParser();

    /*用户第一次请求*/
    @Override
    protected void onUserFirstTimeRequest() {
        if (ComUtils.objectIsNull(mListPresenter)) {
            LogUtils.d("mListPresenter is null");
            return;
        }

        mCurrentPage = 1;
        recycler_view.startRequest();
        mListPresenter.getToRequest(true, mBlogParser, mCurrentPage);
        refreshFinished(false);
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        recycler_view.startRequest();
        mListPresenter.getToRequest(true, mBlogParser, mCurrentPage);
    }

    @Override
    public void loadingMoreData() {
        mCurrentPage++;
        recycler_view.startRequest();
        mListPresenter.getToRequest(false, mBlogParser, mCurrentPage);
    }

    @Override
    public void onFinishLoad(List<V> mDatas, boolean isRefresh) {
        refreshFinished(true);
        recycler_view.finishRequest();

        if (isRefresh) {
            if (ComUtils.objectIsNull(mDatas)) {
                LogUtils.d("loading error");
                recycler_view.setHasNoData();
                showError();
            } else if (ComUtils.CollectionIsNull(mDatas)) {
                LogUtils.d("loading empty");
                recycler_view.setHasNoData();
                showEmpty();
            } else {
                changeRecyclerState(true, mDatas, FooterView.State.LOADING);
            }
        } else { //加载更多
            if (ComUtils.objectIsNull(mDatas) || ComUtils.CollectionIsNull(mDatas)) {
                LogUtils.d("load more data returns null");
                changeRecyclerState(false, mDatas, FooterView.State.NO_DATA);
                recycler_view.setHasNoData();
                return;
            } else {
                FooterView.State state = FooterView.State.LOADING;
                if (mCurrentPageSize > mDatas.size()) {
                    recycler_view.setHasNoData();
                    state = FooterView.State.NO_DATA;
                }
                mCurrentPageSize = mDatas.size();
                changeRecyclerState(false, mDatas, state);
            }
        }
    }

    @Override
    public void onLoadError(boolean isFirstTime) {
        if(isFirstTime) {
            showError();
        }else {
            recycler_view.finishRequest();
            changeRecyclerState(false, null, FooterView.State.ERROR);
        }
    }

    private void refreshFinished(final boolean isFinish) {
        swipe_refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_layout.setRefreshing(!isFinish);
            }
        });
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    private void changeRecyclerState(boolean isFresh, List<V> mDatas, FooterView.State state) {
        mBaseListAdapter.updateData(isFresh, mDatas, state);
    }

}
