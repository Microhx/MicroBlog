package micro.com.microblog.fragment;

import micro.com.microblog.R;
import micro.com.microblog.base.fragment.NewBaseFragment;
import micro.com.microblog.base.fragment.NewContainerFragment;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 15:34
 */
public class NewITeyeFragment extends NewContainerFragment {
    @Override
    protected int getStringArrayId() {
        return R.array.tab_iteye;
    }

    @Override
    protected String getFragmentTag() {
        return "ITeye";
    }

    @Override
    protected NewBaseFragment getImplFragment() {
        return new NewITeyeContentFragment();
    }

    @Override
    protected void initPresenter() {

    }
}
