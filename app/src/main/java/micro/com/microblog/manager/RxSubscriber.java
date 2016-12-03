package micro.com.microblog.manager;

import android.app.Activity;
import android.content.Context;

import micro.com.microblog.R;
import micro.com.microblog.utils.DialogUtils;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.NetWorkUtils;
import micro.com.microblog.utils.UIUtils;
import rx.Subscriber;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 17:44
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;

    public RxSubscriber(Context mContext, String msg, boolean showDialog) {
        this.mContext = mContext;
        this.msg = msg;
        this.showDialog = showDialog;
    }

    public RxSubscriber(Context mContext) {
        this(mContext, UIUtils.getString(R.string.str_loading), true);
    }

    public RxSubscriber(Context mContext, boolean showDialog) {
        this(mContext, UIUtils.getString(R.string.str_loading), showDialog);
    }


    @Override
    public void onStart() {
        super.onStart();

        if (showDialog) {
            try {
                DialogUtils.showDialog((Activity) mContext, msg, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog)
            DialogUtils.hideDialog();

        LogUtils.d("the error : " + e);

        if (NetWorkUtils.isNetConnected(UIUtils.getAppContext())) {
            _onError(UIUtils.getString(R.string.str_network_error));
        } else {
            _onError(UIUtils.getString(R.string.str_other_error));
        }
    }

    @Override
    public void onCompleted() {
        if (showDialog) {
            DialogUtils.hideDialog();
        }
    }

    public abstract void _onNext(T t);

    public abstract void _onError(String msg);

}
