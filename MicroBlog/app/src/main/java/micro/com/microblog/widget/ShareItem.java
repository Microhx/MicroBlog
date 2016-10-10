package micro.com.microblog.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import micro.com.microblog.R;

/**
 * Created by guoli on 2016/9/28.
 */
public class ShareItem extends LinearLayout {

    ImageView iv ;
    TextView tv ;

    public ShareItem(Context context) {
        this(context,null);
    }

    public ShareItem(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShareItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initViews(context);

        TypedArray a = context.getResources().obtainAttributes(attrs,R.styleable.ShareItem);
        tv.setText(a.getString(R.styleable.ShareItem_desc));
        iv.setImageResource(a.getResourceId(R.styleable.ShareItem_item_icon,R.mipmap.ic_launcher));

        a.recycle();
    }

    private void initViews(Context context) {
        View contentView = View.inflate(context, R.layout.share_item,this) ;
        iv = (ImageView) contentView.findViewById(R.id.iv_icon);
        tv = (TextView) contentView.findViewById(R.id.tv_desc);
    }
}
