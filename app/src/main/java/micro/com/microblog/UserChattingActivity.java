package micro.com.microblog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import micro.com.microblog.adapter.BottomRecyclerAdapter;
import micro.com.microblog.adapter.ChattingAdapter;
import micro.com.microblog.adapter.EmotionPageAdapter;
import micro.com.microblog.emotion.EmotionKeyBroad;
import micro.com.microblog.emotion.NotScrollViewPager;
import micro.com.microblog.entity.MsgBean;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.GlobalItemClickListenerUtils;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.RandomUtils;
import micro.com.microblog.widget.PublicHeadLayout;
import micro.com.microblog.widget.RecyclerViewWithHead;

/**
 * Created by guoli on 2016/9/21.
 *
 */
public class UserChattingActivity extends BaseActivity implements RecyclerViewWithHead.OnDataLoadListener {
    /**
     * 测试返回代码
     */
    private String[] contentMsg = {"这是测试1", "这是测试2", "这是测试3", "这是测试4", "毕竟这不是重点", "这是测试5", "这是测试6", "这是测试7", "这是测试8"};

    @Bind(R.id.title)
    PublicHeadLayout title;

    @Bind(R.id.recyclerView)
    RecyclerViewWithHead mRecyclerView;

    @Bind(R.id.et_input)
    EditText et_input;

    @Bind(R.id.iv_add_emotion)
    ImageButton iv_add_emotion ;

    @Bind(R.id.layout_emotion_layout)
    LinearLayout layout_emotion_layout ;

    /**
     * 不滑动的ViewPager
     */
    @Bind(R.id.view_pager)
    NotScrollViewPager view_pager ;

    @Bind(R.id.bottom_recycler)
    RecyclerView bottom_recycler;

    ChattingAdapter mChattingAdapter;

    EmotionPageAdapter mEmotionPageAdapter ;

    BottomRecyclerAdapter mBottomRecyclerAdapter ;

    private boolean hasMoreData = true;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_chatting;
    }

    @Override
    protected void initViewsAndData() {
        initRecyclerView();

        initEditTextListener();

        initEmotionKeyBroad();
    }

    private void initEmotionKeyBroad() {
        GlobalItemClickListenerUtils.getInstance().bindEditText(et_input);

        EmotionKeyBroad.getInstance(this).
                bindEditText(et_input).
                bindContentView(mRecyclerView).
                bindEmotionLayout(layout_emotion_layout).
                bindEmotionButton(iv_add_emotion);
    }

    private void initEditTextListener() {
        mEmotionPageAdapter = new EmotionPageAdapter(getSupportFragmentManager(),5) ;
        view_pager.setAdapter(mEmotionPageAdapter);
        view_pager.setNotScroll(true);

        mBottomRecyclerAdapter = new BottomRecyclerAdapter(5);
        mBottomRecyclerAdapter.setRecyclerViewListener(new BottomRecyclerAdapter.RecyclerViewListener() {
            @Override
            public void onAdapterChange(int position) {
                view_pager.setCurrentItem(position);
            }
        });

        bottom_recycler.setLayoutManager(new LinearLayoutManager(this,0,false));
        bottom_recycler.setAdapter(mBottomRecyclerAdapter);
    }

    private void initRecyclerView() {
        List<MsgBean> userMsgBean = DBDataUtils.getUserMsgBean(this, ComUtils.USER_ID, 20, 0);
        mChattingAdapter = new ChattingAdapter(userMsgBean);

        mRecyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.getRecyclerView().setAdapter(mChattingAdapter);
        mRecyclerView.getRecyclerView().smoothScrollToPosition(mChattingAdapter.getItemCount());

        mRecyclerView.setDataLoadListener(this);
    }


    @OnClick(R.id.ib_send)
    public void onCall(View view) {
            sendInfo();
    }

    @OnClick(R.id.iv_back)
    public void toFinish(View view) {
        finish();
    }


    private void sendInfo() {
        String content = et_input.getText().toString().trim();
        if (TextUtils.isEmpty(content)) return;

        et_input.setText("");
        MsgBean bean = new MsgBean(System.currentTimeMillis(), content, false, "", 2, 1);
        mChattingAdapter.addMsgBean(bean);

        DBDataUtils.addUserMsgBean(this, bean);

        //滑动到最底部
        mRecyclerView.getRecyclerView().smoothScrollToPosition(mChattingAdapter.getItemCount());

        sendSomethingByPhone();
    }

    private void sendSomethingByPhone() {
        mRecyclerView.getRecyclerView().postDelayed(new Runnable() {
            @Override
            public void run() {
                MsgBean bean = new MsgBean(System.currentTimeMillis(),
                        contentMsg[RandomUtils.getRandomInt(contentMsg.length)],
                        false,
                        "",
                        ComUtils.USER_ID, 2);
                mChattingAdapter.addMsgBean(bean);
                DBDataUtils.addUserMsgBean(UserChattingActivity.this, bean);
                //滑动到最底部
                mRecyclerView.getRecyclerView().smoothScrollToPosition(mChattingAdapter.getItemCount());
            }
        }, 3000);
    }

    @Override
    public void toLoadData() {
        int _currentCount = 0;

        if (hasMoreData) {
            List<MsgBean> userMsgBean = DBDataUtils.getUserMsgBean(this, ComUtils.USER_ID, 20, mChattingAdapter.getItemCount());
            LogUtils.d(":" + userMsgBean);

            hasMoreData = userMsgBean.size() >= 20;
            _currentCount = userMsgBean.size();
            mChattingAdapter.addMsgBeanList(userMsgBean);
        }
        mRecyclerView.getRecyclerView().smoothScrollToPosition(_currentCount);
        mRecyclerView.loadDataSuccess();
    }
}
