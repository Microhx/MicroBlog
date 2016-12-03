package micro.com.microblog.fragment;

import micro.com.microblog.R;
import micro.com.microblog.base.fragment.NewBaseFragment;
import micro.com.microblog.base.fragment.NewContainerFragment;
import micro.com.microblog.fragment.NewCSDNContentFragment;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 15:42
 */
public class NewCSDNFragment extends NewContainerFragment {

    @Override
    protected int getStringArrayId() {
        return R.array.tab_csdn;
    }

    @Override
    protected String getFragmentTag() {
        return "CSDN";
    }

    @Override
    protected NewBaseFragment getImplFragment() {
        return new NewCSDNContentFragment();
    }

    @Override
    protected void initPresenter() {

    }
}
