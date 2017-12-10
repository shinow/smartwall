package link.smartwall.kygj.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.ex.DbException;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.smartwall.kygj.LoginActivity;
import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.data.LocalDataReader;
import link.smartwall.kygj.questionbank.domain.UserInfo;
import link.smartwall.kygj.questionbank.http.ReadDataResultCallback;
import link.smartwall.kygj.questionbank.http.RemoteDataReader;

/**
 * 注册页面
 */
public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.edtMobile)
    EditText edtMobile;

    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.edtVerifyCode)
    EditText edtVerifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
    }

    public void register(View v) {
        String mobile = edtMobile.getText().toString().trim();
        if (mobile.length() < 10) {
            Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();

            return;
        }

        String name = edtName.getText().toString().trim();
        if (name.length() < 2) {
            Toast.makeText(this, "请输入登录名称", Toast.LENGTH_SHORT).show();

            return;
        }

        String password = edtPassword.getText().toString().trim();
        if (password.length() < 1) {
            Toast.makeText(this, "请输入登录密码", Toast.LENGTH_SHORT).show();

            return;
        }

        String verifyCode = edtVerifyCode.getText().toString().trim();
        if (verifyCode.length() < 1) {
            Toast.makeText(this, "请输入注册码", Toast.LENGTH_SHORT).show();

            return;
        }

        RemoteDataReader.register(mobile, name, password, verifyCode, new ReadDataResultCallback<UserInfo>() {
            @Override
            public void onResultSuccess(UserInfo userInfo) {
                try {
                    LocalDataReader.getDb().save(userInfo);

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } catch (DbException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
