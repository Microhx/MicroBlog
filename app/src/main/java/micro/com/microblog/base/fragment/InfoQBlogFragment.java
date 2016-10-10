package micro.com.microblog.base.fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import micro.com.microblog.adapter.ArticleType;
import micro.com.microblog.adapter.BaseListAdapter;
import micro.com.microblog.adapter.CSDNAdapter;
import micro.com.microblog.base.BaseListFragment;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.EventBean;
import micro.com.microblog.mvc.presenter.InfoQPresenter;
import micro.com.microblog.parser.IBlogParser;
import micro.com.microblog.parser.InfoQParser;

/**
 * Created by guoli on 2016/9/13.
 */
public class InfoQBlogFragment extends BaseListFragment<Blog, InfoQPresenter> {

    private CSDNAdapter mCSDNAdapter;

    @Override
    protected BaseListAdapter<Blog> getBaseListAdapter() {
        mCSDNAdapter = new CSDNAdapter();
        return mCSDNAdapter;
    }

    @Override
    protected InfoQPresenter getListPresenter() {
        return new InfoQPresenter();
    }

    @Override
    protected IBlogParser getBlogParser() {
        return InfoQParser.getInstance();
    }

    @Subscribe
    public void dealWithEvent(EventBean bean) {
        if (bean == null || bean.type != ArticleType.INFOQ) return;
        mCSDNAdapter.setSingleDataChange(bean.position, bean.isCollect, bean.isRead);
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
