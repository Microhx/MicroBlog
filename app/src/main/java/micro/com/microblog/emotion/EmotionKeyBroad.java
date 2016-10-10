package micro.com.microblog.emotion;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import micro.com.microblog.R;
import micro.com.microblog.utils.LogUtils;
import micro.com.microblog.utils.SPUtils;

/**
 * Created by guoli on 2016/9/22.
 * <p>
 * Emotion表情控制类
 */
public class EmotionKeyBroad {

    Activity mActivity;
    /**
     * 等待输入的EditText
     */
    EditText mEditText;

    /**
     * 内容布局
     */
    View mContentView;

    /**
     * 表情布局
     */
    View mEmotionLayout;


    InputMethodManager mInputMethodManager;

    private EmotionKeyBroad() {}

    public static EmotionKeyBroad getInstance(Activity activity) {
        EmotionKeyBroad emotionKeyBroad = new EmotionKeyBroad();
        emotionKeyBroad.mActivity = activity;
        emotionKeyBroad.mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        return emotionKeyBroad;
    }

    public EmotionKeyBroad bindEmotionLayout(View emotionLayout) {
        this.mEmotionLayout = emotionLayout;
        return this;
    }

    public EmotionKeyBroad bindContentView(View mContentView) {
        this.mContentView = mContentView;
        return this;
    }


    public EmotionKeyBroad bindEditText(EditText editText) {
        this.mEditText = editText;
        this.mEditText.requestFocus();
        this.mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && mEmotionLayout.isShown()) {
                    //显示软件盘 锁定内容布局Layout的高度
                    lockContentHeight();
                    //隐藏EmotionLayout
                    hideEmotionLayout(true);

                    //解锁内容布局
                    unlockContentHeight();

                }
                return false;
            }
        });

        return this;
    }

    public void bindEmotionButton(final ImageButton emotionButton) {
        emotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmotionLayout.isShown()) { //表情布局存在 此时显示软键盘
                    emotionButton.setImageResource(R.drawable.key_emotion);

                    lockContentHeight();
                    hideEmotionLayout(true);
                    unlockContentHeight();

                } else {
                    emotionButton.setImageResource(R.drawable.key_board);

                    if (softInputIsShow()) { //软件盘显示 显示EmotionLayout
                        lockContentHeight();
                        showEmotionLayout();
                        unlockContentHeight();
                    } else {
                        showEmotionLayout();
                    }
                }
            }
        });
    }

    private void showEmotionLayout() {
        int softHeight = getSoftInputHeight();
        if (softHeight <= 0) {
            softHeight = SPUtils.getInt(SPUtils.SOFT_INPUT_HEIGHT, 450);
        }
        hideSoftLayout();
        mEmotionLayout.getLayoutParams().height = softHeight;
        mEmotionLayout.setVisibility(View.VISIBLE);

//        showEmotionLayoutAnimation(0,softHeight);
    }

    private boolean softInputIsShow() {
        boolean result = getSoftInputHeight() != 0;
        System.out.println("------the result-------" + result);
        return result;
    }

    private int getSoftInputHeight() {
        Rect r = new Rect();

        //获取decorView的高度
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕高度
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        //软件盘的高度
        int softHeight = screenHeight - r.bottom;

        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            softHeight -= getTheVirtualHeight();
        }

        LogUtils.d("the softHeight is " + softHeight);
        if (softHeight <= 0) {
            //TODO
        } else {
            SPUtils.saveInt(SPUtils.SOFT_INPUT_HEIGHT, softHeight);
        }

        return softHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getTheVirtualHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindow().getDecorView().getDisplay().getMetrics(metrics);
        int dPMetrics = metrics.heightPixels;
        //获取真实的显示大小
        mActivity.getWindow().getDecorView().getDisplay().getRealMetrics(metrics);
        int realDpMetrics = metrics.heightPixels;

        if (realDpMetrics > dPMetrics) {
            return realDpMetrics - dPMetrics;
        }
        return 0;
    }

    private void unlockContentHeight() {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
                params.weight = 1.0f;
            }
        }, 200);
    }


    private void hideEmotionLayout(boolean showSoft) {
        if (mEmotionLayout.isShown()) {
            mEmotionLayout.setVisibility(View.GONE);

            if (showSoft) {
                showSoftKeyBroad();
            }
        }
    }

    /**
     * 打开软键盘
     */
    private void showSoftKeyBroad() {
        mInputMethodManager.showSoftInput(mEditText, 0);
    }

    /**
     * 关闭软键盘
     */
    private void hideSoftLayout() {
        mInputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        params.height = mContentView.getHeight();
        params.weight = 0.0f;
    }

    private void showEmotionLayoutAnimation(int startHeight, int endHeight) {
        ValueAnimator valueHeight = ValueAnimator.ofInt(startHeight, endHeight);
        valueHeight.setDuration(500);
        valueHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int softHeight = (int) animation.getAnimatedValue();

                mEmotionLayout.getLayoutParams().height = softHeight;
                mEmotionLayout.setVisibility(View.VISIBLE);
                mEmotionLayout.requestLayout();
            }
        });

        valueHeight.start();
    }
}
