package micro.com.microblog;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import micro.com.microblog.adapter.BaseListAdapter;
import micro.com.microblog.mvc.IBaseListUIView;
import micro.com.microblog.mvc.presenter.BaseListPresenter;
import micro.com.microblog.mvc.view.CustomRecyclerView;
import micro.com.microblog.mvc.view.FooterView;
import micro.com.microblog.mvc.view.LoadingListener;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.widget.PublicHeadLayout;

/**
 * Created by guoli on 2016/9/28.
 * <p>
 * 用户上拉 更新数据
 * 下拉 加载更多
 */
public abstract class BaseListActivity<V, T extends BaseListPresenter<IBaseListUIView<V>>>
        extends BaseMultiLayerRequestActivity
        implements IBaseListUIView<V>,
        SwipeRefreshLayout.OnRefreshListener, LoadingListener {

    /**
     * 默认最小获取数据条数
     */
    private static final int MINI_PAGE_SIZE = 10;

    PublicHeadLayout title;
    SwipeRefreshLayout swipe_refresh_layout;
    CustomRecyclerView recycler_view;

    /**
     * 当前listPresenter
     */
    T mListPresenter;

    BaseListAdapter<V> mBaseListAdapter;

    /**
     * 当前第n页
     */
    private int mCurrentPage = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected int getContentLayoutId() {
        return R.layout.base_list_activity_layout;
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
    protected void initViewsAndData() {
        initViews();
        initHeadLayout(title);
        super.initViewsAndData();

        mListPresenter = getListPresenter();
        mListPresenter.attachView(this);

        mBaseListAdapter = getBaseListAdapter();
        mBaseListAdapter.setFooterView(new FooterView(this));

        recycler_view.setAdapter(mBaseListAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setLoadingListener(this);

        getTheFirstRequest();
    }

    @Override
    protected void getTheFirstRequest() {
        mCurrentPage = 1;
        recycler_view.startRequest();
        mListPresenter.getToRequest(true, null, mCurrentPage, mCurrentPage);
        refreshFinished(true);
    }

    private void initViews() {
        title = (PublicHeadLayout) findViewById(R.id.head_title);
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recycler_view = (CustomRecyclerView) findViewById(R.id.recycler_view);
    }

    protected abstract void initHeadLayout(PublicHeadLayout title);

    protected abstract BaseListAdapter<V> getBaseListAdapter();

    protected abstract T getListPresenter();

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        recycler_view.startRequest();
        mListPresenter.getToRequest(true, null, mCurrentPage, mCurrentPage);
    }

    @Override
    public void loadingMoreData() {
        mCurrentPage++;
        recycler_view.startRequest();
        mListPresenter.getToRequest(false, null, mCurrentPage, mCurrentPage);
    }

    @Override
    public void onFinishLoad(List<V> mDatas, boolean isRefresh) {

        refreshFinished(false);
        recycler_view.finishRequest();

        if (isRefresh) {
            if (ComUtils.objectIsNull(mDatas)) {
                LogUtils.d("loading error");
                recycler_view.setHasNoData();
                showErrorPage();
            } else if (ComUtils.CollectionIsNull(mDatas)) {
                LogUtils.d("loading empty");
                recycler_view.setHasNoData();
                showEmptyPage();
            } else {
                if (mDatas.size() < MINI_PAGE_SIZE) {
                    changeRecyclerState(true, mDatas, FooterView.State.NO_DATA);
                } else {
                    changeRecyclerState(true, mDatas, FooterView.State.LOADING);
                }
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

    public void onLoadError(boolean isFirstTime) {
        if(isFirstTime) {
            showErrorPage();
        }else {
            recycler_view.finishRequest();
            changeRecyclerState(false, null, FooterView.State.ERROR);
        }
    }


    private void refreshFinished(final boolean isFinish) {
        swipe_refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_layout.setRefreshing(isFinish);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BaseList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://micro.com.microblog/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BaseList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://micro.com.microblog/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
