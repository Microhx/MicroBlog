package micro.com.microblog.ui.activity;

import android.view.View;

import butterknife.OnClick;
import micro.com.microblog.base.activity.BaseActivity;
import micro.com.microblog.R;


/**
 * Created by guoli on 2016/10/6.
 */
public class AboutAuthorActivity extends BaseActivity {

    @Override
    protected void initViewsAndData() {}

    @OnClick(R.id.iv_back)
    public void onCall(View view) {
        finish();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_about_author;
    }

}
