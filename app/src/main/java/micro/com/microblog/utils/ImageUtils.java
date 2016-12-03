package micro.com.microblog.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import micro.com.microblog.R;

/**
 * Created by guoli on 2016/10/14.
 */
public class ImageUtils {

    /**
     * 显示大图
     *
     * @param imageView
     * @param url
     */
    public static void showBigImage(ImageView imageView, String url, RequestListener<String, Bitmap> listener) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(UIUtils.getAppContext()).
                load(url).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(listener)
                .placeholder(new ColorDrawable(Color.parseColor("#FF000000")))
                .error(R.drawable.ic_empty_picture)
                .into(imageView);
    }

    public static void showSmallImage(ImageView iv , String url) {
        Glide.with(UIUtils.getAppContext()).
                load(url).
                fitCenter().
                error(R.drawable.ic_empty_picture).
                placeholder(new ColorDrawable(Color.parseColor("#88000000"))).
                into(iv) ;
    }

    /**
     * 1.原创
     * 2.转载
     * 3.访问
     * 4.摘要
     * 5.约谈
     */
    public static void showImageViewTag(ImageView iv, int itEyeType) {
        if(itEyeType == 1) {
            iv.setImageResource(R.drawable.yuan);
        }else if(itEyeType == 2) {
            iv.setImageResource(R.drawable.zhuan);
        }else if(itEyeType == 3) {
            iv.setImageResource(R.drawable.fang);
        }else if(itEyeType == 4) {
            iv.setImageResource(R.drawable.zhai);
        }else if(itEyeType == 5) {
            iv.setImageResource(R.drawable.yue);
        }else {
            iv.setVisibility(View.GONE);
        }
    }
}
