package com.intalker.openshelf.util;

import android.app.Activity;
import android.widget.Toast;

public class Debug {
	public static void toast(Activity container, String message) {
		container.runOnUiThread(new ToastHelper(container, message));

	}
}

class ToastHelper implements Runnable {
	private Activity mContainer = null;
	private String mMessage = null;

	public ToastHelper(Activity cont, String message) {
		mContainer = cont;
		mMessage = message;
	}

	@Override
	public void run() {
		Toast.makeText(mContainer.getApplicationContext(), mMessage,
				Toast.LENGTH_SHORT).show();
	}
}
