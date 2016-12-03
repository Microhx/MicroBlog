package micro.com.microblog.fragment;

import micro.com.microblog.base.fragment.NewListRequestFragment;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.adapter.OSChinaAdapter;
import micro.com.microblog.base.model.OSCModel;
import micro.com.microblog.base.presenter.OSCPresenter;
import micro.com.microblog.widget.recyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 15:52
 */
public class NewOSChinaContentFragment extends NewListRequestFragment<OSCPresenter, OSCModel, Blog> {

    @Override
    protected CommonRecycleViewAdapter<Blog> getCommonRecyclerAdapter() {
        return new OSChinaAdapter(getContext());
    }
}
