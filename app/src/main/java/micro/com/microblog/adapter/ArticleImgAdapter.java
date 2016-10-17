package micro.com.microblog.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import micro.com.microblog.R;
import micro.com.microblog.ShowArticleImgActivity;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.ImageUtils;
import micro.com.microblog.utils.LogUtils;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by guoli on 2016/10/14.
 */
public class ArticleImgAdapter extends PagerAdapter {

    Context mCtx;
    List<String> mImageList;

    private PhotoView photoView;
    private ProgressBar pbLoading;

    public ArticleImgAdapter(Context context, List<String> mImageList) {
        this.mCtx = context;
        this.mImageList = mImageList;
    }

    @Override
    public int getCount() {
        return null == mImageList ? 0 : mImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.img_show_item, null);
        initView(rootView);
        initData(position);
        container.addView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        photoView = (PhotoView) rootView.findViewById(R.id.photo_img);
        pbLoading = (ProgressBar) rootView.findViewById(R.id.pb_loading);
    }

    private void initData(final int position) {
        LogUtils.d("--source image->>" + mImageList.get(position));

        ImageUtils.showBigImage(photoView, mImageList.get(position),
                new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e,
                                               String model,
                                               Target<Bitmap> target,
                                               boolean isFirstResource) {
                        pbLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource,
                                                   String model,
                                                   Target<Bitmap> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        pbLoading.post(new Runnable() {
                            @Override
                            public void run() {
                                pbLoading.setVisibility(View.GONE);

                            }
                        });

                        return false;
                    }
                });

        initListener();
    }

    private void initListener() {
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                if (mCtx instanceof ShowArticleImgActivity) {
                    ((ShowArticleImgActivity) mCtx).finish();
                }
            }
        });

        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float v, float v1) {
                if (mCtx instanceof ShowArticleImgActivity) {
                    ((ShowArticleImgActivity) mCtx).finish();
                }
            }
        });
    }


}
