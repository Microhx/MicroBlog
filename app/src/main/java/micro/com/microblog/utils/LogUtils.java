package micro.com.microblog.utils;

import android.util.Log;

/**
 * Created by guoli on 2016/8/23.
 */
public class LogUtils {

    private static final String TAG = "micro_blog" ;

    private static int VERBOSE = 0x01 ;
    private static int DEBUG = 0x02 ;
    private static int INFO = 0x03 ;
    private static int WARN = 0x04 ;
    private static int ERROR = 0x05 ;

    private static int current_code = ERROR ;

    public static void v(String msg) {
        if(current_code >= VERBOSE){
            Log.d(TAG,msg) ;
        }
    }

    public static void d(String msg) {
        if(current_code >= DEBUG){
            Log.d(TAG,msg) ;
        }
    }

    public static void i(String msg) {
        if(current_code >= INFO){
            Log.i(TAG,msg) ;
        }
    }

    public static void w(String msg) {
        if(current_code >= WARN){
            Log.w(TAG,msg) ;
        }
    }

    public static void e(String msg) {
        if(current_code >= ERROR){
            Log.e(TAG,msg) ;
        }
    }


}
