package micro.com.microblog.controller;

import micro.com.microblog.utils.SPUtils;

public class ChangeModeHelper {
    public static final int MODE_DAY = 1;

    public static final int MODE_NIGHT = 2;

    public static void setChangeMode( int mode){
        SPUtils.saveInt("config_mode",mode);
    }
    public static int getChangeMode(){
       return SPUtils.getInt("config_mode",MODE_DAY);
    }
}