package link.smartwall.kygj.questionbank.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.smartwall.kygj.R;

/**
 * 注册页面
 */
public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.edtMobile)
    EditText edtMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
    }

    public void register(View v) {
        String mobile = edtMobile.getText().toString().trim();

        if(mobile.length() < 10) {
            Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();

            return ;
        }


    }
}
