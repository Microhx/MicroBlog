package micro.com.microblog.emotion;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import micro.com.microblog.R;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.UIUtils;

/**
 * Created by guoli on 2016/9/26.
 */
public class EmojiIndicatorView extends LinearLayout {

    private Context mContext ;

    /**
     * 点的大小
     */
    private int mPointSize = 5;

    /**
     * 左间距
     */
    private int mMarginLeft = 10 ;

    /**
     * 所有点的集合
     */
    private List<View> mListView ;

    /**
     * 当前所在位置
     */
    private int mCurrentLocation ;


    public EmojiIndicatorView(Context context) {
        this(context,null);
    }

    public EmojiIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmojiIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);

        mContext = context ;
        mPointSize = UIUtils.dip2px(mPointSize);
        mMarginLeft = UIUtils.dip2px(mMarginLeft);

    }

    public void initIndicator(int count) {
        mListView = new ArrayList<>() ;
        removeAllViews();
        View _view ;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mPointSize,mPointSize);
        for (int i = 0; i < count; i++) {
            _view = new View(mContext);
            lp.leftMargin = i!= 0 ? mMarginLeft : 0 ;
            _view.setLayoutParams(lp);

            if(i == 0) {
                _view.setBackgroundResource(R.color.app_text_black_color);
            }else {
                _view.setBackgroundResource(R.color.app_theme_color_day);
            }

            mListView.add(_view);
            addView(_view);
        }
    }


    public void changePointBackground(int newPost) {
        LogUtils.d("the point : " + mCurrentLocation + "---" + newPost);

        if(newPost == mCurrentLocation) return;
        if(newPost < 0 || newPost > mListView.size()) {
            newPost = 0 ;
        }

        View oldView = mListView.get(mCurrentLocation) ;
        View newView = mListView.get(newPost) ;

        oldView.setBackgroundResource(R.color.app_theme_color_day);
        newView.setBackgroundResource(R.color.app_text_black_color);

        mCurrentLocation = newPost ;
    }
}
