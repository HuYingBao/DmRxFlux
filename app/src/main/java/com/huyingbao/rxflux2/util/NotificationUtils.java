package com.huyingbao.rxflux2.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.message.model.Message;

/**
 * Created by liujunfeng on 2017/1/1.
 */
public class NotificationUtils {

    /**
     * 聊天推送消息显示提醒
     *
     * @param context
     * @param notice
     * @param clazz
     */
    public static void showNotification(Context context, Message notice, Class<?> clazz) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_logo);
        builder.setContentTitle(context.getString(R.string.app_dm_name));
        builder.setContentText(notice.getMsg());
        builder.setTicker(notice.getMsg());// 第一次提示消息的时候显示在通知栏上
        builder.setAutoCancel(true);// 自己维护通知的消失
        builder.setDefaults(Notification.DEFAULT_ALL);

        // 构建一个Intent
        Intent resultIntent = new Intent(context, clazz);
        resultIntent.putExtra(ActionsKeys.NOTICE, notice);
        // 封装一个Intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        builder.setContentIntent(resultPendingIntent);
        // 获取通知管理器对象
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notice.getSendTime().hashCode(), builder.build());
    }

    /**
     * 清除聊天推送消息
     *
     * @param context
     * @param unique
     */
    public static void clearNotification(Context context, @NonNull String unique) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(unique.hashCode());
    }
}
