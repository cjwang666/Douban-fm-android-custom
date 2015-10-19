package com.zzxhdzj.app.play.delegate;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.zzxhdzj.app.DoubanFmApp;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.app.base.service.PlayerService;

/**
 * Created with IntelliJ IDEA. User: yangning.roy Date: 7/5/14 To change this
 * template use File | Settings | File Templates.
 */
public class PlayDelegate {

	private static PlayDelegate playDelegate;
	/** Messenger for communicating with service. */
	static Messenger mService = null;

	public void play() {
		startAction(PlayerService.ACTION_PLAY);
	}

	public void pause() {

	}

	public void skip() {
		startAction(PlayerService.ACTION_SKIP);
	}

	public void fav() {
		startAction(PlayerService.ACTION_FAV);
	}

	public void ban() {
		startAction(PlayerService.ACTION_BAN);
	}

	public void stop() {

	}

	public void setPlayerEngineListener(
			PlayerEngineListener playerEngineListener) {
		DoubanFmApp.getInstance().addPlayerEngineListener(playerEngineListener);
		if (DoubanFmApp.getInstance().getServicePlayerEngine() != null
				|| playerEngineListener != null) {
			startAction(PlayerService.ACTION_BIND_LISTENER);
		}
	}

	private void startAction(int action) {
		Message msg = Message.obtain(null, action);
		// msg.replyTo = mMessenger;
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Class for interacting with the main interface of the service.
	 */
	private static ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = new Messenger(service);
			Log.i(DoubanFmApp.TAG, "Attached.");
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			mService = null;
			Log.i(DoubanFmApp.TAG, "Disconnected.");
		}
	};

	private PlayDelegate() {
	}

	public static synchronized PlayDelegate getInstance() {
		if (playDelegate == null) {
			playDelegate = new PlayDelegate();
			DoubanFmApp.getInstance().bindService(
					new Intent(DoubanFmApp.getInstance(), PlayerService.class),
					mConnection, Context.BIND_AUTO_CREATE);
		}
		return playDelegate;
	}

	public void unfav() {
		startAction(PlayerService.ACTION_UNFAV);
	}
}