package com.example.keepalivedemo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//不进行任何特殊处理,在原生系统中可以实现
		Intent intent = new Intent(this,AlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(this,0,intent,0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND,10);
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
		manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+5000, 5000, sender);

		//通过AIDL实现双进程守护
		Intent service = new Intent(this,LocalService.class);
		startService(service);
		Intent remoteService = new Intent(this,RomoteService.class);
		startService(remoteService);

		//通过设置Notification来使进程保持，但是一旦通知去了之后，service还是会被关闭
		Intent i = new Intent(this,  TestService .class);
		i.setAction("MainActivity.START");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(i);

		//通过MarsDaemon第三方库，该配置在MyApplication中

		//通过AppWidget,在MyWidgetProvider中配置

	}

	private void startNotificaiton() {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle("测试标题")//设置通知栏标题
				.setContentText("测试内容")
				.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//  .setNumber(number) //设置通知集合的数量
				.setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
				.setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
				.setSmallIcon(R.mipmap.ic_launcher).setVibrate(new long[] {0,300,500,700});//设置通知小ICON
		mNotificationManager.notify(0, mBuilder.build());
	}

	public PendingIntent getDefalutIntent(int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
		return pendingIntent;
	}
}
