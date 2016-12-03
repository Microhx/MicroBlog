package micro.com.microblog.manager;

import micro.com.microblog.R;
import micro.com.microblog.base.view.BaseRequestView;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.NetWorkUtils;
import micro.com.microblog.utils.UIUtils;
import rx.Subscriber;

/**
 * author : micro_hx
 * desc :  多层级View显示控制
 * email: javainstalling@163.com
 * date : 2016/11/13 - 11:08
 */
public abstract class RxViewSubscriber<T> extends Subscriber<T> {

    private BaseRequestView<T> mBaseRequestView ;
    private boolean isFirstTime ;

    public RxViewSubscriber(BaseRequestView requestView , boolean isFirstTime) {
        this.mBaseRequestView = requestView ;
        this.isFirstTime = isFirstTime ;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(null != mBaseRequestView && isFirstTime) {
            mBaseRequestView.showContentLoading();
        }
    }

    @Override
    public void onCompleted() {
        if(null != mBaseRequestView && isFirstTime) {
            mBaseRequestView.restoreContentView();
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.d("request error : " + e);
        if(null != mBaseRequestView && isFirstTime) {
            mBaseRequestView.showContentError();
        }

        if (NetWorkUtils.isNetConnected(UIUtils.getAppContext())) {
            _onError(UIUtils.getString(R.string.str_network_error));
        } else {
            _onError(UIUtils.getString(R.string.str_other_error));
        }
    }

    @Override
    public void onNext(T o) {
        _onNext(o);
    }


    public abstract void _onNext(T t);

    public abstract void _onError(String msg);
}
