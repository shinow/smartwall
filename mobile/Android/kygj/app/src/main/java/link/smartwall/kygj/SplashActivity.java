package link.smartwall.kygj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	/**
	 * 成功
	 */
	private static final int SUCCESS = 1;
	/**
	 * 延时时间，设置为800ms
	 */
	private static final int SHOW_TIME_MIN = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.setContentView(R.layout.activity_splash);
		new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {
				int result;

				long startTime = System.currentTimeMillis();
				result = loadingCache();
				long loadingTime = System.currentTimeMillis() - startTime;
				if (loadingTime < SHOW_TIME_MIN) {
					try {
						Thread.sleep(SHOW_TIME_MIN - loadingTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				return result;
			}

			@Override
			protected void onPostExecute(Integer result) {
				Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		}.execute(new Void[] {});
	}

	private int loadingCache() {
		return SUCCESS;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
