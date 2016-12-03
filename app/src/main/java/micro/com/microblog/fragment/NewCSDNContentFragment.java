package micro.com.microblog.fragment;

import micro.com.microblog.base.fragment.NewListRequestFragment;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.adapter.NewCSDNAdapter;
import micro.com.microblog.base.model.CSDNImplModel;
import micro.com.microblog.base.presenter.CSDNPresenter;
import micro.com.microblog.widget.recyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 15:43
 */
public class NewCSDNContentFragment extends NewListRequestFragment<CSDNPresenter, CSDNImplModel, Blog> {

    @Override
    protected CommonRecycleViewAdapter<Blog> getCommonRecyclerAdapter() {
        return new NewCSDNAdapter(getContext());
    }
}
