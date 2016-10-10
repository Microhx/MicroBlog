package micro.com.microblog.base;

import android.widget.GridView;

import butterknife.Bind;
import micro.com.microblog.R;
import micro.com.microblog.adapter.EmotionAdapter;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.UIUtils;

/**
 * Created by guoli on 2016/9/27.
 */
public class BaseEmotionContentFragment extends BaseFragment {

    @Bind(R.id.gridView)
    GridView gridView ;

    EmotionAdapter mEmotionAdapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.emotion_content_layout;
    }

    @Override
    protected void initViewAndEvent() {
        mEmotionAdapter = new EmotionAdapter(0, UIUtils.getScreenWidth(getActivity())/7) ;
        gridView.setAdapter(mEmotionAdapter);
    }
}
