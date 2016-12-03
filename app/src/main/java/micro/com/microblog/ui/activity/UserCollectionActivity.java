package micro.com.microblog.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import micro.com.microblog.R;
import micro.com.microblog.adapter.SearchResultAdapter;
import micro.com.microblog.base.view.BaseRequestView;
import micro.com.microblog.base.activity.NewBaseActivity;
import micro.com.microblog.base.model.CollectAndRecordModeImpl;
import micro.com.microblog.base.presenter.CollectAndRecordPresenter;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.Config;
import micro.com.microblog.widget.PublicHeadLayout;
import micro.com.microblog.widget.recyclerview.IRecyclerView;
import micro.com.microblog.widget.recyclerview.OnLoadMoreListener;
import micro.com.microblog.widget.recyclerview.widget.LoadMoreFooterView;

/**
 * author : micro_hx
 * desc : 用户收藏
 * email: javainstalling@163.com
 * date : 2016/11/30 - 11:38
 * interface :
 */
public class UserCollectionActivity extends
        NewBaseActivity<CollectAndRecordPresenter, CollectAndRecordModeImpl>
        implements BaseRequestView, OnLoadMoreListener {

    @Bind(R.id.title)
    PublicHeadLayout mTitle;

    @Bind(R.id.recyclerView)
    IRecyclerView mRecyclerView;

    SearchResultAdapter mSearchResultAdapter;

    private int mCurrentPage = 0;

    private int mEnterType;

    @Override
    protected void initIntent(Intent intent) {
        if (null != intent) {
            mEnterType = intent.getIntExtra("type", Blog.COLLECT_TYPE);
        }
    }

    @Override
    protected void initViews() {
        mTitle.setTitle(mEnterType == Blog.COLLECT_TYPE ? "收藏" : "阅读记录");

        mSearchResultAdapter = new SearchResultAdapter(this, mEnterType);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSearchResultAdapter);
        //隐藏加载更多标签
        mRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
        mRecyclerView.setOnLoadMoreListener(this);

        //获取数据
        showContentLoading();
        mBasePresenter.toGetCollectAndRecordList(mEnterType, mCurrentPage, "");
    }

    @Override
    protected void initPresenter() {
        mBasePresenter.setVM(mBaseModel, this);
    }

    @Override
    protected View getTargetView() {
        return mRecyclerView;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_collect_and_record;
    }

    @Override
    public void returnBlogListData(List blogList, boolean isFirstTime, boolean isRefresh) {
        if (isFirstTime) {
            if (!ComUtils.CollectionIsNull(blogList)) {
                mSearchResultAdapter.replaceAll(blogList);
                restoreContentView();
            } else {
                showContentEmpty();
            }
        } else {
            if (!ComUtils.CollectionIsNull(blogList)) {
                mSearchResultAdapter.addAll(blogList);
                if (blogList.size() >= Config.DB_PAGE_COUNT) {
                    mRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
                } else {
                    mRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                    mRecyclerView.setLoadMoreEnabled(false);
                }
            }
        }
    }

    @Override
    public void srollToTop() {
    }


    @Override
    public void showLoading(String title) {
    }

    @Override
    public void stopLoading() {
    }

    @Override
    public void showErrorTip(String msg) {
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        mCurrentPage++;
        mBasePresenter.toGetCollectAndRecordList(mEnterType, mCurrentPage, "");
    }

    @OnClick(R.id.iv_back)
    public void onCall(View v) {
        finish();
    }
}
