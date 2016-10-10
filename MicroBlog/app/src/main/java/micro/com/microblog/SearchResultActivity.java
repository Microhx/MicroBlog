package micro.com.microblog;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import butterknife.OnClick;
import micro.com.microblog.adapter.CSDNAdapter;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.mvc.ISearchListUIView;
import micro.com.microblog.mvc.presenter.UserSearchPresenter;
import micro.com.microblog.mvc.view.FooterView;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.DialogUtils;
import micro.com.microblog.widget.CustomerRecyclerView;

/**
 * Created by guoli on 2016/10/8.
 */
public class SearchResultActivity extends BaseMultiLayerRequestActivity implements ISearchListUIView {
    CustomerRecyclerView recyclerView;
    EditText etInput;

    UserSearchPresenter mSearchPresenter;

    private CSDNAdapter mCSDNAdapter;

    /**
     * 当前搜索
     */
    String mCurrentWords = "";

    @Override
    protected void initViewsAndData() {
        setViewAndData();
        super.initViewsAndData();

        mSearchPresenter = new UserSearchPresenter();
        mSearchPresenter.attachView(this);

        mCSDNAdapter = new CSDNAdapter();
        recyclerView.setAdapter(mCSDNAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setViewAndData() {
        recyclerView = (CustomerRecyclerView) findViewById(R.id.custom_view);
        etInput = (EditText) findViewById(R.id.search_edit_text);
    }

    @OnClick(R.id.tv_search)
    public void onSearch(View v) {
        String keyWords = etInput.getText().toString().trim();
        if (TextUtils.isEmpty(keyWords) || keyWords.equals(mCurrentWords)) return;
        mCurrentWords = keyWords;

        clearAdapterData();

        ComUtils.hideKeyBroad(etInput);

        showDialog();

        mSearchPresenter.startToSearch(mCurrentWords);
    }

    private void clearAdapterData() {
        mCSDNAdapter.clearAllData();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected View getTargetView() {
        return recyclerView;
    }

    @Override
    public void addSearchData(List<Blog> blogList) {
        hideDialog();
        mCSDNAdapter.updateData(false, blogList, FooterView.State.NO_DATA);
    }

    Dialog mMessageDialog;

    @Override
    public void showDialog() {
        if (mMessageDialog == null) {
            mMessageDialog = DialogUtils.getDialog(this, "搜索中，请稍候...");
        }
        mMessageDialog.show();
    }

    @Override
    public void hideDialog() {
        if (null != mMessageDialog) {
            mMessageDialog.dismiss();
        }
    }

}
