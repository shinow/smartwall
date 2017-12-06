package link.smartwall.kygj.questionbank.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import link.smartwall.controls.view.TitleView;
import link.smartwall.kygj.R;

public class CommentsActivity extends AppCompatActivity {
    private TitleView mTitleView;
    private String questionGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle bundle = this.getIntent().getExtras();
        questionGuid = bundle.getString("questionGuid");
        mTitleView = (TitleView) this.findViewById(R.id.question_bank_titleview);
        mTitleView.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentsActivity.this.finish();
            }
        });
    }
}
