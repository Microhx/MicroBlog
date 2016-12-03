package micro.com.microblog.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import micro.com.microblog.ui.activity.AboutAuthorActivity;
import micro.com.microblog.R;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/7 - 18:29
 */
public class NotificationHelper {

    @TargetApi(16)
    public static void showNotification(Context context, String message) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, AboutAuthorActivity.class), 0);
        Notification notification = new Notification.
                Builder(context).
                setSmallIcon(R.mipmap.ic_launcher).
                setTicker("ticker: new message").
                setContentTitle("content : notification title").
                setContentText("content:this is the text.....").
                setContentIntent(pendingIntent).
                //setNumber(1).
                        build();

        //添加声音
        notification.defaults += Notification.DEFAULT_SOUND;
        //添加震动
        //notification.defaults += Notification.DEFAULT_VIBRATE ;
        //添加光线
        //notification.defaults += Notification.DEFAULT_LIGHTS;

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        manager.notify(1, notification);
    }

    /**
     * 使用自定义视图
     *
     * @param context
     * @param message
     */
    @TargetApi(16)
    public static void createCustomNotification(Context context, String message) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, AboutAuthorActivity.class), 0);
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.notification_item_layout);
        remoteView.setImageViewResource(R.id.iv_icon, R.mipmap.ic_launcher);
        remoteView.setTextViewText(R.id.tv_msg, message);

        Notification mNotification = new Notification.
                Builder(context).
                setWhen(System.currentTimeMillis()).
                setContentIntent(pendingIntent).
                setContent(remoteView).
                build();

        mNotification.flags += Notification.FLAG_AUTO_CANCEL;
        manager.notify(1, mNotification);
    }


}
