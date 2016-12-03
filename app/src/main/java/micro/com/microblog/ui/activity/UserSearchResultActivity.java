package micro.com.microblog.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import micro.com.microblog.R;
import micro.com.microblog.adapter.SearchResultAdapter;
import micro.com.microblog.base.view.BaseRequestView;
import micro.com.microblog.base.activity.NewBaseActivity;
import micro.com.microblog.base.model.SearchBlogModeImpl;
import micro.com.microblog.base.presenter.SearchPresenter;
import micro.com.microblog.utils.AnimationUtils;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.DialogUtils;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.ToUtils;
import micro.com.microblog.widget.SearchItemView;
import micro.com.microblog.widget.recyclerview.IRecyclerView;
import micro.com.microblog.widget.recyclerview.widget.LoadMoreFooterView;

/**
 * Created by guoli on 2016/10/8.
 */
public class UserSearchResultActivity extends NewBaseActivity<SearchPresenter, SearchBlogModeImpl> implements BaseRequestView {

    @Bind(R.id.search_edit_text)
    EditText etInput;

    @Bind(R.id.iv_search_type)
    ImageView ivSearchType;

    @Bind(R.id.ll_search_order)
    LinearLayout layoutOrder;

    @Bind(R.id.item_all)
    SearchItemView itemAll;

    @Bind(R.id.item_csdn)
    SearchItemView itemCSDN;

    @Bind(R.id.item_jcc)
    SearchItemView itemJcc;

    @Bind(R.id.item_oschina)
    SearchItemView itemOSChina;

    @Bind(R.id.item_iteye)
    SearchItemView itemItEye;

    @Bind(R.id.item_infoq)
    SearchItemView itemInfoQ;

    @Bind(R.id.custom_view)
    IRecyclerView mIRecyclerView;

    SearchResultAdapter mSearchResultAdapter;

    private String mCurrentWords = "";
    private int mCurrentSearchType = 0;
    private int mCurrentPageSize = 1;


    @Override
    protected void initViews() {
        mSearchResultAdapter = new SearchResultAdapter(this);
        mIRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mIRecyclerView.setAdapter(mSearchResultAdapter);
        //隐藏加载更多标签
        mIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);

        //1. editText 属性设置 imeOptions="actionSearch"
        //2. 设置监听事件
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    onSearch(etInput);
                    return true;
                }
                return false;
            }
        });
    }

    private void onSearch(EditText etInput) {
        if (!TextUtils.isEmpty(etInput.getText().toString().trim())) {
            toSearchKeyWords();
        }
    }

    @OnClick({R.id.item_all, R.id.item_csdn,
            R.id.item_jcc, R.id.item_oschina,
            R.id.item_iteye, R.id.item_infoq,
            R.id.tv_search})
    public void chooseSearchType(View v) {

        switch (v.getId()) {
            default:
            case R.id.item_all:
                mCurrentSearchType = 0;
                showSearchItem();
                break;

            case R.id.item_csdn:
                mCurrentSearchType = 1;
                showSearchItem();
                break;

            case R.id.item_jcc:
                mCurrentSearchType = 2;
                showSearchItem();
                break;

            case R.id.item_oschina:
                mCurrentSearchType = 3;
                showSearchItem();
                break;

            case R.id.item_iteye:
                mCurrentSearchType = 4;
                showSearchItem();
                break;

            case R.id.item_infoq:
                ToUtils.toastLong("接口目前失效,缺少sst,这是我遇到的比较的缺德的搜索检索了,将会在下个版本补充!");

                //mCurrentSearchType = 5;
                //showSearchItem();

                break;

            case R.id.tv_search:
                toSearchKeyWords();
                break;
        }
    }

    private void showSearchItem() {
        setSearchItemLayout(mCurrentSearchType);
        searchType(ivSearchType);

        toSearchKeyWords();
    }

    private int tempSearchType = -1;

    private void toSearchKeyWords() {
        String keyWords = etInput.getText().toString().trim();

        if (TextUtils.isEmpty(keyWords) || (keyWords.equals(mCurrentWords) && (tempSearchType == mCurrentSearchType)))
            return;

        mCurrentWords = keyWords;
        tempSearchType = mCurrentSearchType;

        clearAdapterData();

        ComUtils.hideKeyBroad(etInput);
        DialogUtils.showDialog(this);
        LogUtils.d("current search type : " + mCurrentSearchType);
        mBasePresenter.toSearchKeyWords(mCurrentPageSize, true, keyWords, mCurrentSearchType);
    }

    private void clearAdapterData() {
        mSearchResultAdapter.clear();
        mSearchResultAdapter.notifyDataSetChanged();
    }


    private boolean ivDownFlag = true;

    @OnClick(R.id.iv_search_type)
    public void searchType(View v) {
        ivDownFlag = !ivDownFlag;
        AnimationUtils.rotate(ivSearchType, ivDownFlag);
        AnimationUtils.alpha(layoutOrder, !ivDownFlag);
    }

    private void setSearchItemLayout(int searchType) {
        itemAll.setChooseVisibility(searchType == 0);
        itemCSDN.setChooseVisibility(searchType == 1);
        itemJcc.setChooseVisibility(searchType == 2);
        itemOSChina.setChooseVisibility(searchType == 3);
        itemItEye.setChooseVisibility(searchType == 4);
        itemInfoQ.setChooseVisibility(searchType == 5);
    }

    @Override
    protected void initPresenter() {
        mBasePresenter.setVM(mBaseModel, this);

    }

    @Override
    protected View getTargetView() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void returnBlogListData(List blogList, boolean isFirstTime, boolean isRefresh) {
        LogUtils.d("the blogList: " + blogList);
        addBlogList(blogList, isFirstTime);
    }

    private synchronized void addBlogList(List blogList, boolean isFirstTime) {
        if (isFirstTime) {
            DialogUtils.hideDialog();
        }

        if (!ComUtils.CollectionIsNull(blogList)) {
            int from = mSearchResultAdapter.getItemCount();
            mSearchResultAdapter.addAll(blogList);
            mSearchResultAdapter.notifyItemInserted(from);

            LogUtils.d("from : " + from);
        }
    }


    @Override
    public void srollToTop() {

    }

    @Override
    public void showContentEmpty() {

    }

    @Override
    public void showContentError() {

    }

    @Override
    public void showContentLoading() {

    }

    @Override
    public void restoreContentView() {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {
        DialogUtils.hideDialog();
        ToUtils.toast(msg);
    }
}
