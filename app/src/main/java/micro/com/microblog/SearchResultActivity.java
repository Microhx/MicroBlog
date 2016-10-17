package micro.com.microblog;

import android.app.Dialog;
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

import butterknife.OnClick;
import micro.com.microblog.adapter.CSDNAdapter;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.mvc.ISearchListUIView;
import micro.com.microblog.mvc.presenter.UserSearchPresenter;
import micro.com.microblog.mvc.view.FooterView;
import micro.com.microblog.utils.AnimationUtils;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.DialogUtils;
import micro.com.microblog.utils.SPUtils;
import micro.com.microblog.widget.CustomerRecyclerView;
import micro.com.microblog.widget.SearchItemView;

/**
 * Created by guoli on 2016/10/8.
 */
public class SearchResultActivity extends BaseMultiLayerRequestActivity implements ISearchListUIView {
    CustomerRecyclerView recyclerView;
    EditText etInput;
    ImageView ivSearchType ;
    LinearLayout layoutOrder ;

    SearchItemView itemAll;
    SearchItemView itemCSDN;
    SearchItemView itemJcc ;
    SearchItemView itemOSChina;
    SearchItemView itemItEye;
    SearchItemView itemInfoQ ;

    UserSearchPresenter mSearchPresenter;
    private CSDNAdapter mCSDNAdapter;

    private boolean ivDownFlag = true ;
    /**
     * 当前搜索
     */
    String mCurrentWords = "";

    /**
     * 当前搜索类型
     * 0 为所有
     * 1 为CSDN
     * 2 为JCC
     * 3 为OSChina
     * 4 为ITEYE
     * 5 INFOQ
     */
    int mCurrentSearchType = 0 ;

    @Override
    protected void initViewsAndData() {
        setViewAndData();
        super.initViewsAndData();

        mSearchPresenter = new UserSearchPresenter();
        mSearchPresenter.attachView(this);

        mCSDNAdapter = new CSDNAdapter();
        recyclerView.setAdapter(mCSDNAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCurrentSearchType = SPUtils.getInt(SPUtils.BLOG_SEARCH_TYPE,0) ;
        setSearchItemLayout(mCurrentSearchType);
    }

    private void setViewAndData() {
        recyclerView = (CustomerRecyclerView) findViewById(R.id.custom_view);
        etInput = (EditText) findViewById(R.id.search_edit_text);
        ivSearchType = (ImageView) findViewById(R.id.iv_search_type);
        layoutOrder = (LinearLayout) findViewById(R.id.ll_search_order);

        itemAll = (SearchItemView) findViewById(R.id.item_all);
        itemCSDN = (SearchItemView) findViewById(R.id.item_csdn);
        itemOSChina = (SearchItemView) findViewById(R.id.item_oschina);
        itemJcc = (SearchItemView) findViewById(R.id.item_jcc);
        itemItEye = (SearchItemView) findViewById(R.id.item_iteye);
        itemInfoQ = (SearchItemView) findViewById(R.id.item_infoq);

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

    @OnClick(R.id.iv_search_type)
    public void searchType(View v) {
        ivDownFlag = !ivDownFlag ;
        AnimationUtils.rotate(ivSearchType,ivDownFlag);
        AnimationUtils.alpha(layoutOrder,!ivDownFlag);
    }

    @OnClick({R.id.item_all,R.id.item_csdn ,
            R.id.item_jcc,R.id.item_oschina,
            R.id.item_iteye,R.id.item_infoq})
    public void chooseSearchType(View v) {
        int searchType ;

        switch (v.getId()) {
            default:
            case R.id.item_all:
                searchType = 0 ;
                break;

            case R.id.item_csdn :
                searchType = 1 ;
                break;

            case R.id.item_jcc:
                searchType = 2 ;
                break;

            case R.id.item_oschina:
                searchType = 3 ;
                break;

            case R.id.item_iteye:
                searchType = 4 ;
                break;

            case R.id.item_infoq:
                searchType = 5 ;
                break;
        }

        setSearchItemLayout(searchType);
        searchType(ivSearchType) ;
        toSearchKeyWords(searchType);
    }

    private void setSearchItemLayout(int searchType) {
        itemAll.setChooseVisibility(searchType==0);
        itemCSDN.setChooseVisibility(searchType==1);
        itemJcc.setChooseVisibility(searchType==2);
        itemOSChina.setChooseVisibility(searchType==3);
        itemItEye.setChooseVisibility(searchType==4);
        itemInfoQ.setChooseVisibility(searchType==5);
    }

    @OnClick(R.id.tv_search)
    public void onSearch(View v) {
        toSearchKeyWords(mCurrentSearchType);
    }

    private void toSearchKeyWords(int type) {
        String keyWords = etInput.getText().toString().trim();
        if (TextUtils.isEmpty(keyWords) || (keyWords.equals(mCurrentWords) && type == mCurrentSearchType)) return;
        mCurrentSearchType = type ;
        mCurrentWords = keyWords;
        clearAdapterData();

        ComUtils.hideKeyBroad(etInput);

        showDialog();

        mSearchPresenter.startToSearch(mCurrentWords,mCurrentSearchType);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtils.saveInt(SPUtils.BLOG_SEARCH_TYPE,mCurrentSearchType);
    }
}
