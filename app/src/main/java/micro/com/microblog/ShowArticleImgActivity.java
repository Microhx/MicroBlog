package micro.com.microblog;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import micro.com.microblog.adapter.ArticleImgAdapter;
import micro.com.microblog.manager.DownManager;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.ToUtils;
import micro.com.microblog.widget.FixViewPager;

/**
 * Created by guoli on 2016/10/14.
 * <p/>
 * 展示文章中图片
 */
public class ShowArticleImgActivity extends BaseActivity {

    public static final String IMG_LIST_PATH = "img_list_path";

    public static final String IMG_LIST_POSITION = "img_list_position";

    @Bind(R.id.view_pager)
    FixViewPager mViewPager;
    @Bind(R.id.tv_count)
    TextView mTvCount;

    List<String> mImagePath;
    int mTotalCount;

    int mCurrentPosition;

    private ArticleImgAdapter mArticleImgAdapter;

    public static void startThisActivity(Context ctx, ArrayList<String> imageList, int position) {
        Intent _intent = new Intent(ctx, ShowArticleImgActivity.class);
        _intent.putExtra(IMG_LIST_PATH, imageList);
        _intent.putExtra(IMG_LIST_POSITION, position);
        ctx.startActivity(_intent);
    }

    @Override
    protected void initIntent(Intent intent) {
        mImagePath = intent.getStringArrayListExtra(IMG_LIST_PATH);
        mTotalCount = mImagePath == null ? 0 : mImagePath.size();
        mCurrentPosition = intent.getIntExtra(IMG_LIST_POSITION, 0);
    }

    @Override
    protected void initViewsAndData() {
        mArticleImgAdapter = new ArticleImgAdapter(this,mImagePath);
        mViewPager.setAdapter(mArticleImgAdapter);
        mViewPager.setCurrentItem(mCurrentPosition);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                mTvCount.setText((position + 1) + "/" + mTotalCount);
                mCurrentPosition = position ;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_show_article_img;
    }


    /**
     * 下载文件
     * @param view
     */
    @OnClick(R.id.tv_save)
    public void onDownLoadPicture(View view) {
        ToUtils.toast(R.string.download_start);
        if(ComUtils.CollectionIndex(mImagePath,mCurrentPosition)) {
            String imgPath = mImagePath.get(mCurrentPosition) ;
            if(!TextUtils.isEmpty(imgPath)) {
                DownManager.downFromOk3(imgPath, new DownManager.DownLoadListener() {
                    @Override
                    public void onSuccess() {
                        ToUtils.toast(R.string.download_success);
                    }

                    @Override
                    public void onError() {
                        ToUtils.toast(R.string.download_error);
                    }
                });
            }
        }
    }
}
