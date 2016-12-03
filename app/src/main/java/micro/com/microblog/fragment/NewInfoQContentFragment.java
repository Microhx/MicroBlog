package micro.com.microblog.fragment;

import micro.com.microblog.base.fragment.NewListRequestFragment;
import micro.com.microblog.base.presenter.BaseRequestPresenterImpl;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.adapter.NewInfoQAdapter;
import micro.com.microblog.base.model.InfoQImplModel;
import micro.com.microblog.widget.recyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

/**
 * author : micro_hx
 * desc :  InfoQ中详细的Fragment
 * <p>
 * email: javainstalling@163.com
 * date : 2016/10/28 - 15:00
 */
public class NewInfoQContentFragment extends NewListRequestFragment<BaseRequestPresenterImpl, InfoQImplModel, Blog> {

    @Override
    protected CommonRecycleViewAdapter<Blog> getCommonRecyclerAdapter() {
        return new NewInfoQAdapter(getContext());
    }
}
