package micro.com.microblog;

import android.view.View;

import butterknife.OnClick;
import micro.com.microblog.adapter.BaseListAdapter;
import micro.com.microblog.adapter.CSDNAdapter;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.mvc.presenter.UserCollectionPresenter;
import micro.com.microblog.mvc.presenter.UserRecorderPresenter;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.widget.PublicHeadLayout;

/**
 * Created by guoli on 2016/10/6.
 */
public class UserRecorderActivity extends BaseListActivity<Blog,UserRecorderPresenter> {

    private CSDNAdapter mCSDNAdapter ;

    @Override
    protected void initHeadLayout(PublicHeadLayout title) {
        title.setTitle(R.string.str_record);
        title.setRightMsg(R.string.clear_all);
    }

    @OnClick(R.id.iv_back)
    public void onCall(View v) {
        this.finish();
    }

    @OnClick(R.id.tv_msg)
    public void onDeleteAll(View v) {
        if(mCSDNAdapter.getItemCount() == 0) return;
        //show the empty layer page ;
        showEmptyPage();

        //delete the real data
        DBDataUtils.deleteAllUserRead() ;
    }

    @Override
    protected BaseListAdapter<Blog> getBaseListAdapter() {
        mCSDNAdapter = new CSDNAdapter() ;
        return mCSDNAdapter;
    }

    @Override
    protected UserRecorderPresenter getListPresenter() {
        return new UserRecorderPresenter();
    }
}
