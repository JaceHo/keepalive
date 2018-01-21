package com.example.keepalivedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * Created by example on 2016/10/8.
 */
public class AlarmReceiver extends BroadcastReceiver{
	long lastTime = 0;
	MediaPlayer alarmMusic;
	@Override
	public void onReceive(Context context, Intent intent) {
		long time = System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		Toast.makeText(context, "闹铃响了, 可以做点事情了~~" + time, Toast.LENGTH_LONG).show();
		alarmMusic = MediaPlayer.create(context, R.raw.message_come);
		alarmMusic.start();
	}
}
