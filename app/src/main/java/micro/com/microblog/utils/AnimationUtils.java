package micro.com.microblog.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by guoli on 2016/10/14.
 */
public class AnimationUtils {

    /**
     * 旋转动画
     *
     * @param iv
     * @param ivDownFlag
     */
    public static void rotate(ImageView iv, boolean ivDownFlag) {
        float startRotation = iv.getRotation();
        float endRotation = ivDownFlag ? startRotation + 180 : startRotation - 180;

        ObjectAnimator.
                ofFloat(iv, "rotation", startRotation, endRotation).
                setDuration(500).
                start();
    }

    public static void alpha(final View view, final boolean isShow) {
        float startColor = isShow ? 0 : 1;
        float endColor = isShow ? 1 : 0;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", startColor, endColor);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                /**
                 * 此时没有设置visible时 gone状态下的view没有动画效果
                 */
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(isShow ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animator.start();
    }


}
