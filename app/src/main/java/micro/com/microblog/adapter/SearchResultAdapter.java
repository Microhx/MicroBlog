package micro.com.microblog.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import micro.com.microblog.entity.ArticleType;
import micro.com.microblog.ui.activity.ArticleDetailActivity;
import micro.com.microblog.R;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.utils.UIUtils;
import micro.com.microblog.widget.recyclerview.universaladapter.ViewHolderHelper;
import micro.com.microblog.widget.recyclerview.universaladapter.recyclerview.MultiItemRecycleViewAdapter;
import micro.com.microblog.widget.recyclerview.universaladapter.recyclerview.MultiItemTypeSupport;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/29 - 15:21
 * interface :
 */
public class SearchResultAdapter extends MultiItemRecycleViewAdapter<Blog> {

    private int mEnterType = -1;

    public SearchResultAdapter(Context context, int mEnterType) {
        this(context);
        this.mEnterType = mEnterType;
    }

    public SearchResultAdapter(Context context) {
        super(context, new MultiItemTypeSupport<Blog>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == 0) {
                    return R.layout.blog_infoq_item_layout;
                } else if (itemType == 1) {
                    return R.layout.blog_iteye_item_layout;
                } else if (itemType == 2) {
                    return R.layout.blog_csdn_item_layout;
                } else if (itemType == 3) {
                    return R.layout.blog_jcc_item_layout;
                } else if (itemType == 4) {
                    return R.layout.blog_osc_item_layout;
                }

                return 0;
            }

            @Override
            public int getItemViewType(int position, Blog blog) {
                switch (blog.articleType) {
                    default:
                    case INFOQ:
                        return 0;

                    case ITEYE:
                        return 1;

                    case CSDN:
                        return 2;

                    case PAOWANG:
                        return 3;

                    case OSCHINA:
                        return 4;

                }
            }
        });
    }

    @Override
    public void convert(ViewHolderHelper helper, final Blog blog, final int position) {
        switch (helper.getItemViewType()) {
            case 0:
                initInfoQ(helper, blog);
                break;

            case 1:
                initITEye(helper, blog);
                break;

            case 2:
                initCSDN(helper, blog);
                break;

            case 3:
                initJcc(helper, blog);
                break;

            case 4:
                initOsc(helper, blog);
                break;
        }

        helper.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = new Intent(UIUtils.getAppContext(), ArticleDetailActivity.class);
                _intent.putExtra(ArticleDetailActivity.PASS_ARTICLE, blog);
                _intent.putExtra("position", position);
                initOtherInfos(blog);

                UIUtils.startActivity(_intent);
            }
        });

    }

    private void initOtherInfos(Blog blog) {
        if (blog.articleType == ArticleType.ITEYE) {
            blog.articleInnerType = 2;  //来自博客
        }
    }

    private void initOsc(ViewHolderHelper helper, Blog blog) {
        helper.setVisible(R.id.label_view, mEnterType == Blog.COLLECT_TYPE);
        helper.setImageResource(R.id.iv_icon, R.drawable.osc);

        helper.setText(R.id.tv_title, blog.title);
        helper.setText(R.id.tv_desc, blog.description);
    }

    private void initJcc(ViewHolderHelper helper, Blog blog) {
        helper.setVisible(R.id.label_view, mEnterType == Blog.COLLECT_TYPE);
        helper.setVisible(R.id.iv_icon, !TextUtils.isEmpty(blog.photo));
        helper.setBigImageUrl(R.id.iv_icon, blog.photo);
        helper.setText(R.id.tv_title, blog.title);
        helper.setText(R.id.tv_desc, blog.description);
        helper.setText(R.id.tv_other_info, blog.author + " " + blog.publishTime);
    }

    private void initCSDN(ViewHolderHelper helper, Blog blog) {
        helper.setImageResource(R.id.iv_icon, R.drawable.csdn);

        helper.setText(R.id.tv_title, blog.title);
        helper.setText(R.id.tv_other_info, blog.author + " " + blog.publishTime);
        helper.setText(R.id.tv_count, blog.voteCount);
    }

    private void initInfoQ(ViewHolderHelper helper, Blog blog) {
        helper.setText(R.id.tv_title, blog.title);
        helper.setText(R.id.tv_dec, blog.description);
        helper.setText(R.id.tv_other_info, blog.author + " " + blog.publishTime);
        helper.setGone(R.id.iv_icon, TextUtils.isEmpty(blog.photo));
        helper.setSmallImageUrl(R.id.iv_icon, blog.photo);

        helper.setVisible(R.id.label_view, mEnterType == Blog.COLLECT_TYPE);
    }

    private void initITEye(ViewHolderHelper helper, Blog blog) {
        helper.setVisible(R.id.label_view, mEnterType == Blog.COLLECT_TYPE);
        helper.setImageResource(R.id.iv_icon, R.drawable.iteye);
        helper.setGone(R.id.iv_tag, true);

        helper.setText(R.id.tv_title, blog.title);
        helper.setText(R.id.tv_dec, blog.description);
        helper.setText(R.id.tv_other_info, blog.author + " " + blog.publishTime);
    }
}
