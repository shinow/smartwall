package link.smartwall.kygj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import link.smartwall.kygj.questionbank.http.RemoteDataReader;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RemoteDataReader.readSubjects("5DCA16610870507BE050840A06394546");
    }

    public void loginClick(View v) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, KygjActivity.class);

        startActivity(intent);
    }
}
