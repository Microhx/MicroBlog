package micro.com.microblog.fragment;

import micro.com.microblog.R;
import micro.com.microblog.base.fragment.NewBaseFragment;
import micro.com.microblog.base.fragment.NewContainerFragment;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 14:26
 */
public class NewInfoQFragment extends NewContainerFragment {

    @Override
    protected String getFragmentTag() {
        return "infoQ";
    }

    @Override
    protected NewBaseFragment getImplFragment() {
        return new NewInfoQContentFragment();
    }

    @Override
    protected int getStringArrayId() {
        return R.array.tab_infoq;
    }

    @Override
    protected void initPresenter() {

    }
}
