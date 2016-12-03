package micro.com.microblog.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import micro.com.microblog.R;

/**
 * Created by guoli on 2016/8/28.
 */
public class ComUtils {

    /**
     * 用于模拟聊天的 用户Id
     */
    public static final int USER_ID = 1;

    /**
     * 每次数据库中获取的数量
     */
    public static final int PAGE_SIZE = 20;

    /**
     * 数据库查询的时长
     */
    public static final int QUERY_TIME_OUT = 2000;

    /**
     * 解析日期
     */
    public static Pattern pattern = Pattern.compile("\\d+-\\d+-\\d+");


    public static boolean objectIsNull(Object obj) {
        return null == obj || TextUtils.isEmpty(obj.toString());
    }


    public static boolean CollectionIsNull(Collection<?> mDatas) {
        return mDatas == null || mDatas.isEmpty();
    }

    public static String getStandardTime(String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? matcher.group() : "";
    }

    /**
     * 关闭键盘
     *
     * @param focusView
     */
    public static void hideKeyBroad(View focusView) {
        InputMethodManager methodManager = (InputMethodManager) UIUtils.getAppContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);

    }

    public static boolean CollectionIndex(Collection<?> coll, int index) {
        return index >= 0 && index < coll.size();
    }

    /**
     * InfoQ模板模式
     *
     * @param article
     * @param type
     * @return
     */
    public static String getInfoQArticleType(String article, String type) {
        if (article.equals("infoQ")) {  //info
            if (type.equals("架构")) {
                return "architecture-design";
            } else if (type.equals("云计算")) {
                return "cloud-computing";
            } else if (type.equals("大数据")) {
                return "bigdata";
            } else if (type.equals("运维")) {
                return "operation";
            }
            return "mobile";
        }

        if (article.equals("ITeye")) {
            if (type.equals("资讯")) {
                return "news";
            } else if (type.equals("精华")) {
                return "magazines";
            } else if (type.equals("博客")) {
                return "blogs";
            } else
                return "blogs/subjects";
        }

        if (article.equals("CSDN")) {
            if (type.equals("云计算")) {
                return "cloud";
            } else if (type.equals("大数据")) {
                return "bigdata";
            } else if (type.equals("前端")) {
                return "frontend";
            } else  //移动
                return "mobile";
        }

        if (article.equals("JCC")) {
            if (type.equals("综合资讯")) {
                return "4";
            } else if (type.equals("程序设计")) {
                return "6";
            } else if (type.equals("安卓开发")) {
                return "16";
            } else if (type.equals("前端开发")) {
                return "5";
            } else  //IOS开发
                return "27";
        }

        if (article.equals("OSC")) {
            if (type.equals("全部")) {
                return "0";
            } else if (type.equals("移动开发")) {
                return "428602";
            } else if (type.equals("前端开发")) {
                return "428612";
            } else if (type.equals("服务器")) {
                return "428640";
            } else if (type.equals("游戏开发")) {
                return "429511";
            } else if (type.equals("编程语言")) {
                return "428609";
            }
        }

        return "";
    }


    /**
     * 获取ITEYE在本文中的type样式 用做解析
     *
     * @param type
     * @return
     */
    public static int getITeyeType(String type) {
        if (type.equals("news")) {
            return 0;
        } else if (type.equals("magazines")) {
            return 1;
        } else if (type.equals("blogs")) {
            return 2;
        } else if (type.equals("blogs/subjects")) {
            return 3;
        }
        return 0;
    }

    /**
     * 切割字符串
     *
     * @param message
     * @param length
     * @return
     */
    public static String cutOffString(String message, int length) {
        if (null == message || message.length() < length) return message;
        return message.substring(0, length) + "...";
    }

    public static int getJccType(String type) {
        return safe2ParseInt(type);
    }

    public static int safe2ParseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static int getITeyeTypeByChineseName(String attr) {
        LogUtils.d("attr:" + attr);

        if ("特约稿件".equals(attr)) {
            return 5;
        } else if ("名家访谈".equals(attr)) {
            return 3;
        } else if ("精选文摘".equals(attr)) {
            return 4;
        } else if ("原创新闻".equals(attr)) {
            return 1;
        } else if ("转载新闻".equals(attr)) {
            return 2;
        }
        return -1;
    }
}
