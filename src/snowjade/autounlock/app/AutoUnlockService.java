package snowjade.autounlock.app;

import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

public class AutoUnlockService extends Service {

	private IntentFilter intentFilter;
	private ScreenOnReceiver screenOnReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("AutoUnlockService", "service create");
		intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		screenOnReceiver = new ScreenOnReceiver();
		registerReceiver(screenOnReceiver, intentFilter);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("AutoUnlockService", "receive");
		KeyguardManager km= (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);  
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");  
        //½âËø  
        kl.disableKeyguard();
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				Instrumentation inst = new Instrumentation();
//				inst.sendPointerSync(MotionEvent.obtain(
//						SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
//						MotionEvent.ACTION_DOWN, 240, 400, 0));
//				inst.sendPointerSync(MotionEvent.obtain(
//						SystemClock.uptimeMillis(),
//						SystemClock.uptimeMillis() + 1000,
//						MotionEvent.ACTION_MOVE, 10, 10, 0));
//				inst.sendPointerSync(MotionEvent.obtain(
//						SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
//						MotionEvent.ACTION_UP, 240, 400, 0));
//				Log.d("AutoUnlockService", "receiverun");
//			}
//		}).start();

		// KeyguardManager km= (KeyguardManager)
		// getSystemService(Context.KEYGUARD_SERVICE);
		// KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		// //½âËø
		// kl.disableKeyguard();
		return super.onStartCommand(intent, flags, startId);
	}

	class ScreenOnReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Intent i = new Intent(context, AutoUnlockService.class);
			startService(i);
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(screenOnReceiver);
	}

}
