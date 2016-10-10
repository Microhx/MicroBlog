package micro.com.microblog;

import android.view.View;

import butterknife.OnClick;
import micro.com.microblog.adapter.BaseListAdapter;
import micro.com.microblog.adapter.CSDNAdapter;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.mvc.presenter.UserCollectionPresenter;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.UIUtils;
import micro.com.microblog.widget.PublicHeadLayout;

/**
 * Created by guoli on 2016/9/28.
 */
public class UserCollectionActivity extends BaseListActivity<Blog,UserCollectionPresenter> {

    private CSDNAdapter mCSDNAdapter ;

    @Override
    protected void initHeadLayout(PublicHeadLayout title) {
        title.setTitle(UIUtils.getString(R.string.user_collection));
        title.setRightMsg(R.string.clear_all);

    }
    @OnClick(R.id.iv_back)
    public void onCall(View view) {
        finish();
    }

    @OnClick(R.id.tv_msg)
    public void onClearAllMsg(View view) {
        if(mCSDNAdapter.getItemCount() == 0) return;

        //show the empty page
        showEmptyPage();

        //delete the all message
        DBDataUtils.deleteAllUserCollection();
    }

    @Override
    protected BaseListAdapter<Blog> getBaseListAdapter() {
        mCSDNAdapter = new CSDNAdapter() ;
        return mCSDNAdapter;
    }

    @Override
    protected UserCollectionPresenter getListPresenter() {
        return new UserCollectionPresenter();
    }
}
