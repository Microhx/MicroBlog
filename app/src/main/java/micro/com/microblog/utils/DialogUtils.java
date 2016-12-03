package micro.com.microblog.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import micro.com.microblog.R;

/**
 * Created by guoli on 2016/10/9.
 */
public class DialogUtils {

    public static Dialog getDialog(Context ctx, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(true);
        builder.setMessage(msg);
        return builder.create();
    }

    static Dialog mDialog;

    public static Dialog showDialog(Activity activity, String msg, boolean canCancel) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.loading_dialog_layout, null);
        TextView tvMsg = (TextView) rootView.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);

        mDialog = new Dialog(activity, R.style.CustomProgressDialog);
        mDialog.setCancelable(canCancel);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(rootView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mDialog.show();
        return mDialog;
    }

    public static Dialog showDialog(Activity activity) {
        return showDialog(activity, UIUtils.getString(R.string.str_loading), false);
    }

    public static void hideDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


}
