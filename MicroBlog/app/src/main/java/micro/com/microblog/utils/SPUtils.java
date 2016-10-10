package micro.com.microblog.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by guoli on 2016/9/21.
 */
public class SPUtils {
    /**
     * 软键盘高度
     */
    public static final String SOFT_INPUT_HEIGHT = "soft_input_height";

    private static SharedPreferences mSp;


    static SharedPreferences getSp() {
        if (null == mSp) {
            synchronized (SPUtils.class) {
                if (null == mSp) {
                    mSp = UIUtils.getAppContext().getSharedPreferences("sp.data", Context.MODE_PRIVATE);
                }
            }
        }
        return mSp;
    }

    public static void saveString(String key, String value) {
        getSp().edit().putString(key, value).commit();
    }

    public static void saveInt(String key, int value) {
        getSp().edit().putInt(key, value).commit();
    }


    public static int getInt(String key, int defValue) {
        return getSp().getInt(key, defValue);
    }


    public static Set<String> getStringSet(String key) {
        return getSp().getStringSet(key, new HashSet<String>());
    }

    public static void saveStringSet(String s, HashSet<String> newCookies) {
        getSp().edit().putStringSet(s, newCookies).commit();

    }
}
