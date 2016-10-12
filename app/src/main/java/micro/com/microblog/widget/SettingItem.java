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
 * Created by Javainstalling@163.com on 2016/10/12.
 *
 *
 */
public class SettingItem extends LinearLayout {

    private TextView tvItem ;
    private ImageView leftIcon ;

    public SettingItem(Context context) {
        this(context,null);
    }

    public SettingItem(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingItem) ;
        int icon = a.getResourceId(R.styleable.SettingItem_iv_icon,R.mipmap.ic_launcher) ;
        String msg = a.getString(R.styleable.SettingItem_msg) ;

        tvItem.setText(msg);
        leftIcon.setImageResource(icon);

        a.recycle();

    }

    private void initViews(Context context) {
        View rootView = View.inflate(context,R.layout.setting_item_layout,this);
        tvItem = (TextView) rootView.findViewById(R.id.tv_msg);
        leftIcon = (ImageView) rootView.findViewById(R.id.iv_icon);
    }
}
