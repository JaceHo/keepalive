package com.example.keepalivedemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by example on 2016/10/8.
 */
public class RomoteService extends Service{
	MyConn conn;
	MyBinder binder;

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		conn = new MyConn();
		binder = new MyBinder();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Toast.makeText(this, " 远程服务活了", Toast.LENGTH_SHORT).show();
		this.bindService(new Intent(this, LocalService.class), conn, Context.BIND_IMPORTANT);

		return START_STICKY;
	}

	class MyBinder extends IMyAidlInterface.Stub {
		@Override
		public String getServiceName() throws RemoteException {
			return RomoteService.class.getSimpleName();
		}
	}

	class MyConn implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i("example", "绑定本地服务成功");
			// Toast.makeText(RomoteService.this, "绑定本地服务成功", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i("example", "本地服务被干掉了");
			Toast.makeText(RomoteService.this, "本地服务挂了", Toast.LENGTH_SHORT).show();

			//开启本地服务
			RomoteService.this.startService(new Intent(RomoteService.this, LocalService.class));
			//绑定本地服务
			RomoteService.this.bindService(new Intent(RomoteService.this, LocalService.class), conn, Context.BIND_IMPORTANT);
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//开启本地服务
		RomoteService.this.startService(new Intent(RomoteService.this, LocalService.class));
		//绑定本地服务
		RomoteService.this.bindService(new Intent(RomoteService.this, LocalService.class), conn, Context.BIND_IMPORTANT);

	}
}
