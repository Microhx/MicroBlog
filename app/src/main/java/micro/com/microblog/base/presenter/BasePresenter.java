package micro.com.microblog.base.presenter;

import android.content.Context;

import micro.com.microblog.manager.RxManager;

/**
 * author : micro_hx
 * desc : 基类的Presenter
 * email: javainstalling@163.com
 * date : 2016/10/28 - 13:19
 */
public class BasePresenter<T, E> {

    public Context mContext;
    public T mModel;
    public E mView;

    public RxManager mRxManager = new RxManager();

    public void setVM(T t, E e) {
        mModel = t;
        mView = e;
    }

    public void onStart() {

    }

    public void loadingRequest(int currentPage, boolean isFirstTime, boolean isRefresh, String articleType) {

    }


    public void onDestroy() {
        mRxManager.clear();
    }


}
