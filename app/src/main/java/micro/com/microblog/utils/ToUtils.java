package micro.com.microblog.utils;

import android.os.Looper;
import android.widget.Toast;

import micro.com.microblog.base.activity.MicroApplication;

/**
 * Created by guoli on 2016/9/28.
 */
public class ToUtils {

    public static void toast(final String msg,final boolean isLong) {
        if(Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(UIUtils.getAppContext(), msg, isLong ?Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
        }else {
            MicroApplication.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(UIUtils.getAppContext(), msg, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
                }
            }) ;
        }
    }

    public static void toast(String msg) {
        toast(msg,false);
    }

    public static void toast(final int msg) {
        toast(UIUtils.getString(msg),false);
    }

    public static void toastLong(String msg) {
        toast(msg,true);
    }


}
