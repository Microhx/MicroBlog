package micro.com.microblog.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import micro.com.microblog.R;

/**
 * Created by guoli on 2016/10/14.
 */
public class SearchItemView extends LinearLayout {

    TextView tvMsg ;
    ImageView ivChoose ;

    public SearchItemView(Context context) {
        this(context,null);
    }

    public SearchItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initViews(context);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.SearchItemView);
        String searchStr = a.getString(R.styleable.SearchItemView_search_str) ;
        boolean hasChoose = a.getBoolean(R.styleable.SearchItemView_search_choose , false) ;

        tvMsg.setText(searchStr);
        ivChoose.setVisibility(hasChoose ? VISIBLE : GONE);
    }

    private void initViews(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.search_item_view,this) ;
        tvMsg = (TextView) rootView.findViewById(R.id.tv_msg);
        ivChoose = (ImageView) rootView.findViewById(R.id.iv_choose);
    }

    public void setChooseVisibility(boolean shouldShow) {
        ivChoose.setVisibility(shouldShow ? VISIBLE :GONE);
    }


}
