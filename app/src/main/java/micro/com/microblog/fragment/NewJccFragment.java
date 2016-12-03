package micro.com.microblog.fragment;

import micro.com.microblog.R;
import micro.com.microblog.base.fragment.NewBaseFragment;
import micro.com.microblog.base.fragment.NewContainerFragment;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 15:44
 */
public class NewJccFragment extends NewContainerFragment {
    @Override
    protected String getFragmentTag() {
        return "JCC";
    }

    @Override
    protected int getStringArrayId() {
        return R.array.tab_jcc;
    }

    @Override
    protected NewBaseFragment getImplFragment() {
        return new NewJccContentFragment();
    }

    @Override
    protected void initPresenter() {

    }
}
