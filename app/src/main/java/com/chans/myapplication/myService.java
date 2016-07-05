package com.chans.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * description :
 * Created by Chans Renhenet
 * 2015/8/22
 */
public class myService extends Service {

	//定时
	private Handler handler = new Handler();
	private Runnable task = new Runnable() {
		public void run() {
			handler.postDelayed(this, 30 * 1000);//设置延迟时间，此处是5秒
			//需要执行的代码
			System.out.println("----循环了么----" + System.currentTimeMillis());
			//			new ContactsUtil(RenheService.this).SyncMobileContacts(getApplication().getLastSyscTime());
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		handler.postDelayed(task, 60 * 1000);//延迟调用
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
