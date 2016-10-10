package micro.com.microblog.mvc.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import micro.com.microblog.R;
import micro.com.microblog.widget.LabelView;

/**
 * Created by guoli on 2016/8/28.
 */
public class CustomerViewHolder extends RecyclerView.ViewHolder {
    int mType;

    View itemView;
    FooterView footerView;

    public TextView tv_title;
    public TextView tv_dec;
    public TextView tv_other_info;
    public LinearLayout layout_content ;
    public LabelView labelView ;

    public CustomerViewHolder(View itemView, int mType) {
        super(itemView);
        this.itemView = itemView;
        this.mType = mType;

        initViews();
    }

    private void initViews() {
        if (mType == 0x02) {
            footerView = (FooterView) itemView;
        } else {  //普通Item
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_other_info = (TextView) itemView.findViewById(R.id.tv_other_info);
            tv_dec = (TextView) itemView.findViewById(R.id.tv_dec);
            layout_content = (LinearLayout) itemView.findViewById(R.id.layout_content);
            labelView = (LabelView) itemView.findViewById(R.id.label_view);
        }
    }

    public void setFooterState(FooterView.State st) {
        if (null != footerView) {
            footerView.setState(st);
        }
    }
}
