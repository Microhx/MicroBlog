package micro.com.microblog.mvc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import micro.com.microblog.R;

/**
 * Created by guoli on 2016/8/27.
 */
public class FooterView extends RelativeLayout {

    public enum State {
        LOADING,
        FINISH,
        NO_DATA,
        NO_NULL  //隐藏
    }

    private State mCurrent = State.LOADING;

    private TextView tvLoading;
    private ProgressBar pbBar;

    public FooterView(Context context) {
        this(context, null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        iniView(context);
    }

    private void iniView(Context context) {
        View.inflate(context, R.layout.footer_view, this);

        tvLoading = (TextView) findViewById(R.id.id_tv_loading);
        pbBar = (ProgressBar) findViewById(R.id.id_pb_loading);
        setState(mCurrent);
    }

    public void setState(State state) {
        switch (state) {
            case LOADING:
                tvLoading.setVisibility(VISIBLE);
                tvLoading.setText(R.string.data_loading);
                pbBar.setVisibility(VISIBLE);

                break;

            case FINISH:
                tvLoading.setVisibility(VISIBLE);
                tvLoading.setText(R.string.data_finish);
                pbBar.setVisibility(GONE);
                break;

            case NO_DATA:
                tvLoading.setVisibility(VISIBLE);
                tvLoading.setText(R.string.data_no_data);
                pbBar.setVisibility(GONE);
                break;

            case NO_NULL:
                tvLoading.setVisibility(GONE);
                pbBar.setVisibility(GONE);
                break;

        }
    }


}
