package micro.com.microblog.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import micro.com.microblog.R;
import micro.com.microblog.base.model.BaseModel;
import micro.com.microblog.base.presenter.BasePresenter;
import micro.com.microblog.controller.MultiLayerController;
import micro.com.microblog.manager.RxManager;
import micro.com.microblog.utils.ClassUtils;
import micro.com.microblog.utils.LogUtils;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 14:17
 */
public abstract class NewBaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    public RxManager mRxManager;

    public Context mContext;
    public T mBasePresenter;
    public E mBaseModel;

    private boolean isFirstUserOnResume = true;

    private boolean isFirstTimeVisiable = true;
    private boolean isFirstTimeInVisiable = true;
    private boolean hasPrepared = false;
    private boolean activityCreated = true;

    protected View rootView;
    private MultiLayerController mMultiLayerController ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOnCreateEvent(getArguments()) ;
    }

    protected void initOnCreateEvent(Bundle arguments) {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(getResLayoutId(), container, false);
        }

        ButterKnife.bind(this, rootView);
        mRxManager = new RxManager();
        mContext = getActivity();
        mBasePresenter = ClassUtils.getT(this, 0);
        mBaseModel = ClassUtils.getT(this, 1);
        if (null != mBasePresenter) {
            mBasePresenter.mContext = getActivity();
        }

        mMultiLayerController = new MultiLayerController(getTargetLoadingView()) ;

        initPresenter();
        initViews();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (activityCreated) {
            activityCreated = false;
            initPrepared();
        }
    }

    protected abstract int getResLayoutId();

    protected abstract void initViews();

    protected abstract void initPresenter();

    protected abstract View getTargetLoadingView();

    @Override
    public void onResume() {
        super.onResume();

        if (isFirstUserOnResume) {
            isFirstUserOnResume = false;
            onUserVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstTimeVisiable) {
                initPrepared();
                isFirstTimeVisiable = false;
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstTimeInVisiable) {
                isFirstTimeInVisiable = false;
                onUserFirstTimeInVisible();
            } else {
                onUserInVisible();
            }
        }
    }

    private synchronized void initPrepared() {
        if (hasPrepared) {
            if (null != mBasePresenter) {
                mBasePresenter.onStart();
                onUserFirstTimeVisible();
            }
        } else {
            hasPrepared = true;
        }
    }

    protected void onUserFirstTimeInVisible() {}

    protected void onUserInVisible() {}

    /**
     * 用户可见
     */
    protected void onUserVisible() {

    }

    protected void onUserFirstTimeVisible() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        if (null != mBasePresenter) {
            mBasePresenter.onDestroy();
        }
        mRxManager.clear();
    }

    /**
     * 显示为空
     */
    public void showContentEmpty() {
        View empty = View.inflate(getActivity(), R.layout.layout_empty,null) ;
        mMultiLayerController.changeCurrentView(empty);
    }

    /**
     * 显示错误
     */
    public void showContentError() {
        View error = View.inflate(getActivity(), R.layout.layout_error,null) ;
        TextView tvError = (TextView) error.findViewById(R.id.tv_error);
        tvError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMultiLayerController.restoreView();
                onUserFirstTimeVisible();
            }
        });

        mMultiLayerController.changeCurrentView(error);
    }

    /**
     * 显示正在加载中
     */
    public void showContentLoading() {
        View loadingView = View.inflate(getActivity(),R.layout.layout_loading,null) ;
        mMultiLayerController.changeCurrentView(loadingView);
    }


    public void restoreContentView(){
        mMultiLayerController.restoreView();
    }


}
