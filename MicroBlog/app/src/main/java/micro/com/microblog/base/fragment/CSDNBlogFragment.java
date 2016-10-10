package micro.com.microblog.base.fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import micro.com.microblog.adapter.ArticleType;
import micro.com.microblog.adapter.BaseListAdapter;
import micro.com.microblog.adapter.CSDNAdapter;
import micro.com.microblog.base.BaseListFragment;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.EventBean;
import micro.com.microblog.mvc.presenter.CSDNBlogPresenter;
import micro.com.microblog.parser.CSDNParser;
import micro.com.microblog.parser.IBlogParser;

/**
 * Created by guoli on 2016/8/28.
 */
public class CSDNBlogFragment extends BaseListFragment<Blog, CSDNBlogPresenter> {

    private CSDNAdapter mCSDNAdapter;

    @Override
    protected BaseListAdapter<Blog> getBaseListAdapter() {
        mCSDNAdapter = new CSDNAdapter();
        return mCSDNAdapter;
    }

    @Override
    protected CSDNBlogPresenter getListPresenter() {
        return new CSDNBlogPresenter();
    }

    @Override
    protected IBlogParser getBlogParser() {
        return CSDNParser.getInstance();
    }

    @Subscribe
    public void dealWithEvent(EventBean bean) {
        if (bean == null || bean.type != ArticleType.CSDN) return;
        mCSDNAdapter.setSingleDataChange(bean.position , bean.isCollect , bean.isRead) ;
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }


}
