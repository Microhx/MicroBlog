package micro.com.microblog.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import micro.com.microblog.ui.activity.ArticleDetailActivity;
import micro.com.microblog.R;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.EventBean;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.Config;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.UIUtils;
import micro.com.microblog.widget.recyclerview.universaladapter.ViewHolderHelper;
import micro.com.microblog.widget.recyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

/**
 * author : micro_hx
 * desc : OSChina adapter
 * email: javainstalling@163.com
 * date : 2016/11/21 - 22:07
 * interface :
 */
public class OSChinaAdapter extends CommonRecycleViewAdapter<Blog> {

    public OSChinaAdapter(Context context) {
        super(context, R.layout.blog_osc_item_layout);
    }

    @Override
    public void convert(ViewHolderHelper helper, final Blog blog, final int position) {
        helper.setVisible(R.id.label_view, blog.hasCollect);
        helper.setTextColorRes(R.id.tv_title, blog.hasRead ? R.color.text_normal_color : R.color.app_text_black_color);

        helper.setSmallImageUrl(R.id.iv_icon, blog.photo);

        helper.setText(R.id.tv_title, blog.title);
        helper.setText(R.id.tv_desc, blog.description);
        // helper.setText(R.id.)

        helper.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDataInput();

                Intent _intent = new Intent(UIUtils.getAppContext(), ArticleDetailActivity.class);
                _intent.putExtra(ArticleDetailActivity.PASS_ARTICLE, blog);
                _intent.putExtra("position", position);

                UIUtils.startActivity(_intent);
            }
        });
    }

    @Override
    protected void onArticleHasRead(EventBean bean) {
        if (null != bean &&
                ComUtils.CollectionIndex(mDatas, bean.position) &&
                !mDatas.get(bean.position).hasRead) {
            mDatas.get(bean.position).hasRead = true;
            notifyItemChanged(bean.position);
            LogUtils.d("some datas has read....");
        }

        mRxManager.unRegister(Config.RX_ARTICLE_HAS_READ);
    }


    @Override
    protected void onArticleHasCollected(EventBean bean) {
        if (null != bean &&
                ComUtils.CollectionIndex(mDatas, bean.position) &&
                bean.isCollect != mDatas.get(bean.position).hasCollect) {

            mDatas.get(bean.position).hasCollect = bean.isCollect;
            notifyItemChanged(bean.position);
            LogUtils.d("data collection has collected....");
        }
    }

}
