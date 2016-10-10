package micro.com.microblog.base.fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import micro.com.microblog.adapter.ArticleType;
import micro.com.microblog.adapter.BaseListAdapter;
import micro.com.microblog.adapter.CSDNAdapter;
import micro.com.microblog.base.BaseListFragment;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.EventBean;
import micro.com.microblog.mvc.presenter.OSChinaPresenter;
import micro.com.microblog.parser.IBlogParser;
import micro.com.microblog.parser.OSChinaParser;

/**
 * Created by guoli on 2016/9/14.
 */
public class OSChinaFragment extends BaseListFragment<Blog,OSChinaPresenter> {

    private CSDNAdapter mCSDNAdapter ;

    @Override
    protected BaseListAdapter<Blog> getBaseListAdapter() {
        mCSDNAdapter = new CSDNAdapter() ;
        return mCSDNAdapter;
    }

    @Override
    protected OSChinaPresenter getListPresenter() {
        return new OSChinaPresenter();
    }

    @Override
    protected IBlogParser getBlogParser() {
        return OSChinaParser.getInstance();
    }

    @Subscribe
    public void dealWithEvent(EventBean bean) {
        if (bean == null || bean.type != ArticleType.OSCHINA) return;
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
