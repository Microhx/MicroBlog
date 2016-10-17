package micro.com.microblog.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

}
