package micro.com.microblog.fragment;

import micro.com.microblog.base.fragment.NewListRequestFragment;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.adapter.NewITEyeAdapter;
import micro.com.microblog.base.model.ITEyeImplModel;
import micro.com.microblog.base.presenter.ITEyePresenter;
import micro.com.microblog.widget.recyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 15:35
 */
public class NewITeyeContentFragment extends NewListRequestFragment<ITEyePresenter, ITEyeImplModel, Blog> {

    @Override
    protected CommonRecycleViewAdapter<Blog> getCommonRecyclerAdapter() {
        return new NewITEyeAdapter(getContext());


    }
}
