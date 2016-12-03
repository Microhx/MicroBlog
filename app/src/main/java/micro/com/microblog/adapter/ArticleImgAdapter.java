package micro.com.microblog.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import micro.com.microblog.R;
import micro.com.microblog.ui.activity.ShowArticleImgActivity;
import micro.com.microblog.utils.ImageUtils;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by guoli on 2016/10/14.
 */
public class ArticleImgAdapter extends PagerAdapter {

    Context mCtx;
    List<String> mImageList;

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

        initData(rootView,position);
        container.addView(rootView);
        return rootView;
    }

    private void initData(final View rootView, final int position) {
        //LogUtils.d(position + "--source image->>" + mImageList.get(position));

        ImageUtils.showBigImage((PhotoView) rootView.findViewById(R.id.photo_img),
                mImageList.get(position),
                new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e,
                                               String model,
                                               Target<Bitmap> target,
                                               boolean isFirstResource) {

                        rootView.findViewById(R.id.pb_loading).setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource,
                                                   String model,
                                                   Target<Bitmap> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        rootView.findViewById(R.id.pb_loading).setVisibility(View.GONE);
                        return false;
                    }
                });

        initListener((PhotoView) rootView.findViewById(R.id.photo_img));
    }

    private void initListener(PhotoView photoView) {
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
