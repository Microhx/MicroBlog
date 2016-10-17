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

    public static SpannableString getSpannableString(int emotion_type,
                                                     EditText editText,
                                                     String source) {

        SpannableString sString = new SpannableString(source);
        Resources res = UIUtils.getAppContext().getResources();

        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(sString);

        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = EmotionUtils.getImgByName(emotion_type, key);
            if (imgRes != null) {
                // 压缩表情图片
                int size;

                if (null != editText) {
                    size = (int) editText.getTextSize() * 13 / 10;
                } else {
                    size = UIUtils.dip2px(16) * 13 / 10;
                }

                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(UIUtils.getAppContext(), scaleBitmap);
                sString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return sString;
    }

    public static String getStandardTime(String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? matcher.group() : "";
    }

    /**
     * 关闭键盘
     * @param focusView
     */
    public static void hideKeyBroad(View focusView) {
        InputMethodManager methodManager = (InputMethodManager) UIUtils.getAppContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);

    }

    public static boolean CollectionIndex(Collection<?> coll , int index) {
        return index >= 0 && index < coll.size() ;
    }

}
