package micro.com.microblog.utils;

import android.widget.Toast;

/**
 * Created by guoli on 2016/9/28.
 */
public class ToUtils {

    public static void toast(String msg) {
        Toast.makeText(UIUtils.getAppContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
