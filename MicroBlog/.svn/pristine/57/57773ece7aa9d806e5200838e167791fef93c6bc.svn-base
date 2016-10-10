package micro.com.microblog.adapter;

import android.content.Intent;
import android.view.View;

import micro.com.microblog.ArticleDetailActivity;
import micro.com.microblog.R;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.mvc.view.CustomerViewHolder;
import micro.com.microblog.utils.UIUtils;

/**
 * Created by guoli on 2016/8/28.
 */
public class  CSDNAdapter extends BaseListAdapter<Blog> {

    @Override
    protected void setData(CustomerViewHolder holder, final Blog s, final int position) {
        int color = !s.hasRead ? R.color.app_text_black_color : R.color.text_has_read_color;
        holder.tv_title.setTextColor(UIUtils.getColor(color));

        holder.tv_title.setText(s.title);
        holder.tv_dec.setText(s.description);
        holder.tv_other_info.setText(s.author + " " + s.publishTime);
        holder.labelView.setVisibility(s.hasCollect ? View.VISIBLE : View.GONE);

        setListener(holder, s, position);
    }

    private void setListener(CustomerViewHolder holder, final Blog s, final int position) {
        holder.layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = new Intent(UIUtils.getAppContext(), ArticleDetailActivity.class);
                _intent.putExtra(ArticleDetailActivity.PASS_ARTICLE, s);
                _intent.putExtra("position", position);

                UIUtils.startActivity(_intent);
            }
        });
    }

    public void setSingleDataChange(int position, boolean isCollect, boolean isRead) {
        if (null == mDatas || mDatas.size() < position || position < 0) return;
        if (mDatas.get(position).hasCollect == isCollect && mDatas.get(position).hasRead == isRead)
            return;

        mDatas.get(position).hasCollect = isCollect;
        mDatas.get(position).hasRead = isRead;

        //告知并通知刷新
        notifyDataSetChanged();
    }
}
