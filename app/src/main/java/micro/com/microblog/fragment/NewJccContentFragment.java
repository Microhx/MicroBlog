package micro.com.microblog.fragment;

import micro.com.microblog.base.fragment.NewListRequestFragment;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.adapter.NewJccAdapter;
import micro.com.microblog.base.model.JccModel;
import micro.com.microblog.base.presenter.JccPresenter;
import micro.com.microblog.widget.recyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 15:48
 */
public class NewJccContentFragment extends NewListRequestFragment<JccPresenter, JccModel, Blog> {
    @Override
    protected CommonRecycleViewAdapter<Blog> getCommonRecyclerAdapter() {
        return new NewJccAdapter(getContext());
    }
}
