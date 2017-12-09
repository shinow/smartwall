package link.smartwall.kygj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
    /**
     * 延时时间，设置为3000ms
     */
    private static final int SHOW_TIME_MIN = 1000;
    private final int STOP_SPLASH = 0;

    private Handler splashHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP_SPLASH:
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_splash);


        Message msg = new Message();
        msg.what = STOP_SPLASH;

        splashHandler.sendMessageDelayed(msg, SHOW_TIME_MIN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
