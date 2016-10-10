package micro.com.microblog.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import micro.com.microblog.R;
import micro.com.microblog.entity.EmotionEntity;

/**
 * Created by guoli on 2016/9/26.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class EmotionUtils {

    /**
     *经典表情
     */
   public static final int CLASSIC_EMOTION_TYPE = 0x02 ;


    static ArrayMap<String, Integer> EMPTY_MAP;
    static ArrayMap<String, Integer> CLASSIC_MAP;  //经典表情

    static {
        EMPTY_MAP = new ArrayMap<>();
        CLASSIC_MAP = new ArrayMap<>();

        CLASSIC_MAP.put("[呵呵]", R.drawable.d_hehe);
        CLASSIC_MAP.put("[嘻嘻]", R.drawable.d_xixi);
        CLASSIC_MAP.put("[哈哈]", R.drawable.d_haha);
        CLASSIC_MAP.put("[爱你]", R.drawable.d_aini);
        CLASSIC_MAP.put("[挖鼻屎]", R.drawable.d_wabishi);
        CLASSIC_MAP.put("[吃惊]", R.drawable.d_chijing);
        CLASSIC_MAP.put("[晕]", R.drawable.d_yun);
        CLASSIC_MAP.put("[泪]", R.drawable.d_lei);
        CLASSIC_MAP.put("[馋嘴]", R.drawable.d_chanzui);
        CLASSIC_MAP.put("[抓狂]", R.drawable.d_zhuakuang);
        CLASSIC_MAP.put("[哼]", R.drawable.d_heng);
        CLASSIC_MAP.put("[可爱]", R.drawable.d_keai);
        CLASSIC_MAP.put("[怒]", R.drawable.d_nu);
        CLASSIC_MAP.put("[汗]", R.drawable.d_han);
        CLASSIC_MAP.put("[害羞]", R.drawable.d_haixiu);
        CLASSIC_MAP.put("[睡觉]", R.drawable.d_shuijiao);
        CLASSIC_MAP.put("[钱]", R.drawable.d_qian);
        CLASSIC_MAP.put("[偷笑]", R.drawable.d_touxiao);
        CLASSIC_MAP.put("[笑cry]", R.drawable.d_xiaoku);
        CLASSIC_MAP.put("[doge]", R.drawable.d_doge);
        CLASSIC_MAP.put("[喵喵]", R.drawable.d_miao);
        CLASSIC_MAP.put("[酷]", R.drawable.d_ku);
        CLASSIC_MAP.put("[衰]", R.drawable.d_shuai);
        CLASSIC_MAP.put("[闭嘴]", R.drawable.d_bizui);
        CLASSIC_MAP.put("[鄙视]", R.drawable.d_bishi);
        CLASSIC_MAP.put("[花心]", R.drawable.d_huaxin);
        CLASSIC_MAP.put("[鼓掌]", R.drawable.d_guzhang);
        CLASSIC_MAP.put("[悲伤]", R.drawable.d_beishang);
        CLASSIC_MAP.put("[思考]", R.drawable.d_sikao);
        CLASSIC_MAP.put("[生病]", R.drawable.d_shengbing);
        CLASSIC_MAP.put("[亲亲]", R.drawable.d_qinqin);
        CLASSIC_MAP.put("[怒骂]", R.drawable.d_numa);
        CLASSIC_MAP.put("[太开心]", R.drawable.d_taikaixin);
        CLASSIC_MAP.put("[懒得理你]", R.drawable.d_landelini);
        CLASSIC_MAP.put("[右哼哼]", R.drawable.d_youhengheng);
        CLASSIC_MAP.put("[左哼哼]", R.drawable.d_zuohengheng);
        CLASSIC_MAP.put("[嘘]", R.drawable.d_xu);
        CLASSIC_MAP.put("[委屈]", R.drawable.d_weiqu);
        CLASSIC_MAP.put("[吐]", R.drawable.d_tu);
        CLASSIC_MAP.put("[可怜]", R.drawable.d_kelian);
        CLASSIC_MAP.put("[打哈气]", R.drawable.d_dahaqi);
        CLASSIC_MAP.put("[挤眼]", R.drawable.d_jiyan);
        CLASSIC_MAP.put("[失望]", R.drawable.d_shiwang);
        CLASSIC_MAP.put("[顶]", R.drawable.d_ding);
        CLASSIC_MAP.put("[疑问]", R.drawable.d_yiwen);
        CLASSIC_MAP.put("[困]", R.drawable.d_kun);
        CLASSIC_MAP.put("[感冒]", R.drawable.d_ganmao);
        CLASSIC_MAP.put("[拜拜]", R.drawable.d_baibai);
        CLASSIC_MAP.put("[黑线]", R.drawable.d_heixian);
        CLASSIC_MAP.put("[阴险]", R.drawable.d_yinxian);
        CLASSIC_MAP.put("[打脸]", R.drawable.d_dalian);
        CLASSIC_MAP.put("[傻眼]", R.drawable.d_shayan);
        CLASSIC_MAP.put("[猪头]", R.drawable.d_zhutou);
        CLASSIC_MAP.put("[熊猫]", R.drawable.d_xiongmao);
        CLASSIC_MAP.put("[兔子]", R.drawable.d_tuzi);

    }


    public static List<EmotionEntity> getEmotionEntityByIndex(int index) {
        List<EmotionEntity> emotionEntityList = new ArrayList<>();
        int startStep = index * 20;  //开始地方
        EmotionEntity entity;

        Set<String> keySet = CLASSIC_MAP.keySet();
        Iterator<String> iterator = keySet.iterator();
        int tempStep = 0;
        while (iterator.hasNext()) {
            String key = iterator.next();
            Integer value = CLASSIC_MAP.get(key);

            if (++tempStep < startStep) continue;
            if (emotionEntityList.size() >= 20) break;

            entity = new EmotionEntity();
            entity.topicName = key;
            entity.topSrc = value;
            emotionEntityList.add(entity);
        }

        return emotionEntityList;
    }

    public static Integer getImgByName(int emotion_type, String key) {
        switch (emotion_type) {
            default:
            case CLASSIC_EMOTION_TYPE :

                return CLASSIC_MAP.get(key) ;
        }
    }
}
