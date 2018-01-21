package com.example.keepalivedemo;

import android.content.Context;

import com.marswin89.marsdaemon.DaemonApplication;
import com.marswin89.marsdaemon.DaemonConfigurations;

/**
 * Created by example on 2016/10/8.
 *
 *实现第三方库来保持进程运行
 */
public class MyApplication extends DaemonApplication {
	/**
	 * you can override this method instead of {@link android.app.Application attachBaseContext}
	 * @param base
	 */
	@Override
	public void attachBaseContextByDaemon(Context base) {
		super.attachBaseContextByDaemon(base);
	}


	/**
	 * give the configuration to lib in this callback
	 * @return
	 */
	@Override
	protected DaemonConfigurations getDaemonConfigurations() {
		DaemonConfigurations.DaemonConfiguration configuration1 = new DaemonConfigurations.DaemonConfiguration(
				"com.example.keepalivedemo:process1",
				Service1.class.getCanonicalName(),
				Receiver1.class.getCanonicalName());

		DaemonConfigurations.DaemonConfiguration configuration2 = new DaemonConfigurations.DaemonConfiguration(
				"com.example.keepalivedemo:process2",
				Service2.class.getCanonicalName(),
				Receiver2.class.getCanonicalName());

		DaemonConfigurations.DaemonListener listener = new MyDaemonListener();
		//return new DaemonConfigurations(configuration1, configuration2);//listener can be null
		return new DaemonConfigurations(configuration1, configuration2, listener);
	}


	class MyDaemonListener implements DaemonConfigurations.DaemonListener{
		@Override
		public void onPersistentStart(Context context) {
		}

		@Override
		public void onDaemonAssistantStart(Context context) {
		}

		@Override
		public void onWatchDaemonDaed() {
		}
	}
}
