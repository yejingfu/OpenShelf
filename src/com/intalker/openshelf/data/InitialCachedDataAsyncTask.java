package com.intalker.openshelf.data;

import com.intalker.openshelf.HomeActivity;
import com.intalker.openshelf.cloud.CloudAPI;
import com.intalker.openshelf.cloud.CloudUtility;
import com.intalker.openshelf.cloud.CloudAPIAsyncTask.ICloudAPITaskListener;
import com.intalker.openshelf.ui.control.TransparentProgressDialog;
import com.intalker.openshelf.util.DBUtil;
import com.intalker.openshelf.util.StorageUtil;

import android.os.AsyncTask;
import android.widget.Toast;

public class InitialCachedDataAsyncTask extends AsyncTask<Void, Void, Void> {

	private TransparentProgressDialog mProgressDialog = null;
	
	@Override
	protected Void doInBackground(Void... params) {
		StorageUtil.loadCachedBooks();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		HomeActivity.getApp().getBookGallery().initialWithCachedData();
		if (CloudUtility.setAccessToken(DBUtil.loadToken())) {
			CloudAPI.getLoggedInUserInfo(HomeActivity.getApp(), new ICloudAPITaskListener() {

				@Override
				public void onFinish(int returnCode) {
					HomeActivity app = HomeActivity.getApp();
					if (CloudAPI.isSuccessful(app, returnCode)) {
						app.getBookGallery().updatePanels(UserInfo.getCurLoggedinUser());
						app.getSocialPanel().getFriendsView().refreshList();
						app.getNavigationPanel().updateLoginStatus();
					}
					mProgressDialog.dismiss();
				}

			});
		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mProgressDialog = new TransparentProgressDialog(HomeActivity.getApp(), false);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage("Loading");
		mProgressDialog.show();
	}

}
