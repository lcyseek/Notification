package com.example.luchunyang.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Notification有两种视觉风格，一种是标准视图（Normal view），一种是大视图（Big view）。
 * 标准视图在Android中各版本是通用的，但是对于大视图而言，仅支持Android4.1+的版本
 *
 * Notification
 *      构建Notifications主要用的类，发现好多方法都给移除给NotificationCompat.Builder替代了
 * Notification.Builder
 *      Notification.Builder是为了让开发者更容易构建出Notifications而诞生的。
 * NotificationCompat.Builder
 *      NotificationCompat.Builder是解决Notification.Builder的兼容问题而诞生的。compat：兼容性
 */
public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void defaultNotification1(View view) {
        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(this, NotificationActivity.class);  //需要跳转指定的页面
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.research);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.clock));
        builder.setTicker("您有新的通知1");//通知首次出现在通知栏，带上升动画效果的,不会显示在通知栏里.
        builder.setContentTitle("通知的标题1");//设置通知栏标题
        builder.setContentText("通知的详细内容1:此通知不能被滑动删除,优先级最高");//设置通知栏显示内容
        builder.setContentIntent(pendingIntent);
        //builder.setNumber(4);//设置通知集合的数量
        builder.setAutoCancel(true);//将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失
        builder.setOngoing(true);//将Ongoing设为true 那么notification将不能滑动删除

        //设置notification的优先级，优先级越高的，通知排的越靠前，优先级低的，不会在手机最顶部的状态栏显示图标
        //优先级	用户
        //MAX	重要而紧急的通知，通知用户这个事件是时间上紧迫的或者需要立即处理的。
        //HIGH	高优先级用于重要的通信内容，例如短消息或者聊天，这些都是对用户来说比较有兴趣的。
        //DEFAULT	默认优先级用于没有特殊优先级分类的通知。
        //LOW	低优先级可以通知用户但又不是很紧急的事件。
        //MIN	用于后台消息 (例如天气或者位置信息)。最低优先级通知将只在状态栏显示图标，只有用户下拉通知抽屉才能看到内容。
        builder.setPriority(Notification.PRIORITY_MAX);

        //向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
        builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);//震动 声音

        //实现效果：延迟2000ms，然后振动1000ms，在延迟3000ms，接着在振动1000ms。和上面的Notification.DEFAULT_VIBRATE 不能同时设置
//        builder.setVibrate(new long[]{2000,1000,3000,1000});

        //也可以设置自定义通知铃声
        //builder.setSound(Uri.parse("file:///sdcard/xx/xx.mp3"));
        //.setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "5"))获取Android多媒体库内的铃声

        //将该notification发送到状态条上，如果id相同且没有消失，则直接更新该notification对象信息，否则创建一个Notification实例对象
        notificationManager.notify(3,builder.build());
    }

    public void defaultNotification2(View view) {
        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.research);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.clock));
        builder.setTicker("您有新的通知2");
        builder.setContentTitle("通知的标题2");
        builder.setContentText("通知的详细内容2:app有新的版本");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        notificationManager.notify(1,builder.build());
    }

    public void defaultNotification3(View view) {
        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.research);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.clock));
        builder.setTicker("您有新的通知3");
        builder.setContentTitle("通知的标题3");
        builder.setContentText("通知的详细内容3:app有新的版本");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis()+3000);
        notificationManager.notify(1,builder.build());
    }

    public void canceltNotification(View view) {
        notificationManager.cancelAll();//取消所有通知
//        notificationManager.cancel(1);
    }

    //此方法在4.0及以后版本才有用，如果为早期版本：需要自定义通知布局，其中包含ProgressBar视图
    private Notification.Builder progressBuilder;
    private int progress = 0;
    public void progresstNotification(View view) {
        progressBuilder = new Notification.Builder(this);
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        progressBuilder.setSmallIcon(R.drawable.research);
        progressBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.clock));
        progressBuilder.setTicker("您有新的通知3");
        progressBuilder.setContentTitle("通知的标题3");
        progressBuilder.setContentText("通知的详细内容3:app有新的版本");
        progressBuilder.setContentIntent(pendingIntent);
        progressBuilder.setAutoCancel(true);
        progressBuilder.setProgress(100,progress,false);
        notificationManager.notify(5,progressBuilder.build());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(++progress > 100)
                    return;
                progressBuilder.setProgress(100,progress,false);
                notificationManager.notify(5,progressBuilder.build());
                handler.postDelayed(this,80);
            }
        },80);
    }


    /**
     * 默认的Notification只能通过setContentIntent设置整体的点击事件。不过通过RemoteViews我们可以设置不同地方不同的点击事件，当然这里的事件指的是PendingIntent
     * @param view
     */
    public void customNotification(View view) {
        RemoteViews remoteView = new RemoteViews(getPackageName(),R.layout.layout_notification);
        remoteView.setTextViewText(R.id.tv,"通知栏的内容");
        remoteView.setImageViewResource(R.id.iv,R.drawable.youtube);

        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent intent_btn = PendingIntent.getActivity(this,requestCode,new Intent(this,NotificationActivity.class),0);
        //设置按钮点击的Intent
        remoteView.setOnClickPendingIntent(R.id.btn,intent_btn);

        PendingIntent intent_root = PendingIntent.getBroadcast(this,requestCode,new Intent("click"),0);
        remoteView.setOnClickPendingIntent(R.id.root,intent_root);


        Notification.Builder builder = new Notification.Builder(this);
        builder.setContent(remoteView).setTicker("通知来了").setOngoing(true).setSmallIcon(R.drawable.research);

        notificationManager.notify(8,builder.build());
        IntentFilter filter = new IntentFilter("click");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                notificationManager.cancel(8);
            }
        },filter);
    }
}
