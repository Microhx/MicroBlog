package micro.com.microblog.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import micro.com.microblog.entity.EventBean;
import micro.com.microblog.utils.LogUtils;

/**
 * Created by guoli on 2016/8/24.
 * fragment基类
 * <p>
 * 1.onAttach
 * 2.onCreate
 * 3.onCreateView
 * 4.onActivityCreated
 * 5.onStart
 * 6.onResume
 * 7.onPause
 * 8.onStop
 * 9.onDestroy
 * 10.onDetach
 */
public abstract class BaseFragment extends Fragment {


    private boolean isFirstUserOnResume = true;
    private boolean isFirstTimeVisiable = true;
    private boolean isFirstTImeInVisiable = true;
    private boolean isPrepared = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (0 < getLayoutId()) {
            return inflater.inflate(getLayoutId(), null);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(BaseFragment.this, view);

        initViewAndEvent();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isFirstUserOnResume) {
            isFirstUserOnResume = false;
            onUserVisible();
        }
    }

    /*获取布局id值*/
    protected abstract int getLayoutId();

    /*初始化相关参数*/
    protected void initViewAndEvent() {
    }

    /*第一次请求数据*/
    protected void onUserFirstTimeRequest() {
    }

    /*当fragment对用可见时*/
    private void onUserVisible() {
    }

    /*当fragment第一次不可见时*/
    protected void onUserFirstTimeInvisible() {}

    /**
     * 当fragment不可见时
     */
    protected void onUserInvisible() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstTimeVisiable) {
                isFirstTimeVisiable = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstTImeInVisiable) {
                isFirstTImeInVisiable = false;
                firstTimeUserInvisiable();
            } else {
                onUserInvisible();
            }
        }
    }

    protected void firstTimeUserInvisiable() {
    }

    protected synchronized void initPrepare() {
        if (isPrepared) {
            onUserFirstTimeRequest();
        } else {
            isPrepared = true;
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

}
