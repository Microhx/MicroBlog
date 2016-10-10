package micro.com.microblog.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import micro.com.microblog.R;

/**
 * Created by guoli on 2016/9/16.
 */
public class PublicHeadLayout extends LinearLayout {
    ImageView ivBack;
    TextView tvTitle;
    TextView tvMsg;

    public PublicHeadLayout(Context context) {
        this(context, null);
    }

    public PublicHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        initViews(context);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PublicHeadLayout);
        String _title = a.getString(R.styleable.PublicHeadLayout_tv_title);
        String _msg = a.getString(R.styleable.PublicHeadLayout_tv_right_msg);

        if (!TextUtils.isEmpty(_title)) {
            tvTitle.setText(_title);
        }

        if (!TextUtils.isEmpty(_msg)) {
            tvMsg.setText(_msg);

        }
        a.recycle();
    }

    private void initViews(Context context) {
        View.inflate(context, R.layout.public_head_layout, this);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvMsg = (TextView) findViewById(R.id.tv_msg);

    }

    public void setTitle(String msg) {
        tvTitle.setText(msg);
    }

    public void setTitle(int msg) {
        tvTitle.setText(msg);
    }


    public void setRightMsg(String msg) {
        tvMsg.setText(msg);
    }

    public void setRightMsg(int msg) {
        tvMsg.setText(msg);
    }

}
