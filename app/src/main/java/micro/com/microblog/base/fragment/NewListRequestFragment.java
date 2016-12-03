package micro.com.microblog.base.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import micro.com.microblog.R;
import micro.com.microblog.base.view.BaseRequestView;
import micro.com.microblog.base.model.BaseModel;
import micro.com.microblog.base.presenter.BasePresenter;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.ToUtils;
import micro.com.microblog.widget.recyclerview.IRecyclerView;
import micro.com.microblog.widget.recyclerview.OnLoadMoreListener;
import micro.com.microblog.widget.recyclerview.OnRefreshListener;
import micro.com.microblog.widget.recyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import micro.com.microblog.widget.recyclerview.widget.LoadMoreFooterView;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/6 - 12:24
 */
public abstract class NewListRequestFragment<T extends BasePresenter, E extends BaseModel<D>, D>
        extends NewBaseFragment<T, E>
        implements OnRefreshListener,
        OnLoadMoreListener,
        BaseRequestView<D> {

    private String mArticleType;

    @Bind(R.id.i_recyclerview)
    IRecyclerView i_recyclerview;

    CommonRecycleViewAdapter<D> mNewInfoQAdapter;

    private int mCurrentPage = 1;
    private int mPageSize = 0;

    @Override
    protected void initOnCreateEvent(Bundle arguments) {
        if (null != arguments) {
            mArticleType = ComUtils.getInfoQArticleType(arguments.getString("article"), arguments.getString("_title"));
        }
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.layout_list_request;
    }

    @Override
    protected void initViews() {
        showContentLoading();

        i_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mNewInfoQAdapter = getCommonRecyclerAdapter();
        i_recyclerview.setAdapter(mNewInfoQAdapter);
        i_recyclerview.setOnRefreshListener(this);
        i_recyclerview.setOnLoadMoreListener(this);

    }

    protected abstract CommonRecycleViewAdapter<D> getCommonRecyclerAdapter();

    @Override
    protected void initPresenter() {
        mBasePresenter.setVM(mBaseModel, this);
    }

    @Override
    protected void onUserFirstTimeVisible() {
        mBasePresenter.loadingRequest(mCurrentPage, true, true, mArticleType);
    }

    @Override
    public void returnBlogListData(List<D> blogList, boolean isFirstTime, boolean isRefresh) {
        if (isRefresh) {
            i_recyclerview.setRefreshing(false);
        }

        if (ComUtils.CollectionIsNull(blogList)) {
            if (isFirstTime) {
                showContentError();
            } else {
                i_recyclerview.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
            }
        } else {
            if (isFirstTime) {
                mNewInfoQAdapter.replaceAll(blogList);
            } else {
                if (blogList.size() >= mPageSize) {
                    mPageSize = blogList.size();
                    i_recyclerview.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
                } else {
                    i_recyclerview.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                    i_recyclerview.setLoadMoreEnabled(false);
                }
                mNewInfoQAdapter.addAll(blogList);
            }
        }
    }

    @Override
    public void srollToTop() {
        i_recyclerview.smoothScrollToPosition(0);
    }

    @Override
    protected View getTargetLoadingView() {
        return i_recyclerview;
    }

    @Override
    public void showLoading(String title) {
    }

    @Override
    public void stopLoading() {
    }

    @Override
    public void showErrorTip(String msg) {
        ToUtils.toast(msg);
        i_recyclerview.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
    }

    @Override
    public void onRefresh() {
        i_recyclerview.setRefreshing(true);
        mCurrentPage = 1;
        mBasePresenter.loadingRequest(mCurrentPage, false, true, mArticleType);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        //发起请求
        i_recyclerview.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mCurrentPage++;
        mBasePresenter.loadingRequest(mCurrentPage, false, false, mArticleType);
    }

}
