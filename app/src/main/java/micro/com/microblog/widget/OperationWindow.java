package micro.com.microblog.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import micro.com.microblog.R;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.manager.ShareManager;
import micro.com.microblog.utils.ToUtils;
import micro.com.microblog.utils.UIUtils;

/**
 * Created by guoli on 2016/9/28.
 */
public class OperationWindow extends PopupWindow {

    private Activity mDetachActivity;
    private Blog mBlog;

    Button tv1;
    Button tv2;
    Button tvCancel;

    ShareItem share_qq;
    ShareItem share_weibo;
    ShareItem share_weixin;

    OperationClickListener mListener;

    public OperationWindow(Activity detachActivity, Blog blog) {
        this.mDetachActivity = detachActivity;
        this.mBlog = blog;

        View contentView = LayoutInflater.from(UIUtils.getAppContext()).inflate(R.layout.operation_window_layuot, null);
        tv1 = (Button) contentView.findViewById(R.id.btn_op1);
        tv2 = (Button) contentView.findViewById(R.id.btn_op2);
        tvCancel = (Button) contentView.findViewById(R.id.btn_cancel);

        share_qq = (ShareItem) contentView.findViewById(R.id.share_qq);
        share_weibo = (ShareItem) contentView.findViewById(R.id.share_weibo);
        share_weixin = (ShareItem) contentView.findViewById(R.id.share_weixin);

        //设置主题
        setContentView(contentView);
        //设置宽/高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置背景
        setBackgroundDrawable(new ColorDrawable(0xFF000000));
        //设置点击外部可以取消
        setOutsideTouchable(true);
        //设置动画

        setCancel(tvCancel);

        initItemListener();
    }

    public void setCancel(Button cancel) {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initItemListener() {
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) mListener.onItem1Click();

                dismiss();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) mListener.onItem2Click();

                dismiss();
            }
        });

        share_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareManager.getInstance().shareToQQ(mDetachActivity, mBlog);
            }
        });

        share_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToUtils.toast("share wechat");
                ShareManager.getInstance().shareToWeixin(mDetachActivity,mBlog);
            }
        });

        share_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToUtils.toast("share weibo");
            }
        });
    }

    public OperationWindow setBtn1Text(String msg) {
        tv1.setText(msg);
        return this;
    }

    public OperationWindow setBtn2Text(String msg) {
        tv2.setText(msg);
        return this;
    }

    public OperationWindow setOperationListener(OperationClickListener listener) {
        this.mListener = listener;
        return this;
    }

    public interface OperationClickListener {
        void onItem1Click();

        void onItem2Click();
    }
}
