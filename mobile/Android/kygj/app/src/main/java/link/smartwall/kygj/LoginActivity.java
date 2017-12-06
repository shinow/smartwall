package link.smartwall.kygj;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.donkingliang.imageselector.utils.ImageSelectorUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import link.smartwall.kygj.questionbank.domain.Result;

public class LoginActivity extends AppCompatActivity {
    ImageView imView;

    Handler handler = new Handler( ) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("HHHHHH", String.valueOf(msg.obj));

            final String url = String.valueOf(msg.obj);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imView.setImageBitmap(returnBitMap(url));
                }
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        imView = (ImageView) findViewById(R.id.imageView);
//        RemoteDataReader.readSubjects("5DCA16610870507BE050840A06394546");
    }

    public void loginClick(View v) {
        //单选
//        ImageSelectorUtils.openPhoto(LoginActivity.this, 10, true, 0);

//限数量的多选(比喻最多9张)
//        ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE, false, 9);

//不限数量的多选
//        ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE);
//或者
//        ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE, false, 0);

//单选并剪裁
//        ImageSelectorUtils.openPhotoAndClip(LoginActivity.this, 10);
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, KygjActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);
            Log.i("IMAGE:", String.valueOf(images));


            for (String img : images) {
                postImage(img);
            }
        }
    }

    private void postImage(String image) {
        RequestParams params = new RequestParams("http://192.168.3.28:6666/file-service/v1/upload?tenant_id=9999&category=IM_STORE");

//        (@RequestPart("file") MultipartFile file,
//        @RequestParam("tenant_id") int tenantId,
//        @RequestParam("category") String category,
//        @RequestParam("name") String name) {
//
//        }\

        params.setMultipart(true);
        params.addBodyParameter("file", new File(image));
//        params.addQueryStringParameter("tenant_id", "9999");
//        params.addQueryStringParameter("category", "IM_STORE");
        params.addQueryStringParameter("name", image.substring(image.lastIndexOf("/") + 1));
        x.http().post(params, new Callback.CommonCallback<Result>() {
            @Override
            public void onSuccess(Result result) {
                final String url = "http://112.124.108.130/" + result.getMessage();

                Log.i("HHHHHH", url);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep( 2000 );
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message message=new Message();
                        message.obj = url;
                        handler.sendMessage(message);
                    }
                }).start();


//                System.out.println(JSON.toJSONString(result));
//                try {
//                    for (Subject s : subjectList) {
//                        db.saveOrUpdate(s);
//                        RemoteDataReader.readChapters(s.getGuid());
//                    }
//                } catch (DbException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
            }

            @Override
            public void onFinished() {
                System.out.println("finished");
            }
        });
    }

    public Bitmap returnBitMap(String url){
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
