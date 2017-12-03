package link.smartwall.kygj.questionbank.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import link.smartwall.controls.view.TitleView;
import link.smartwall.controls.webview.NativeWebView;
import link.smartwall.kygj.R;

public class DoQuestionActivity extends AppCompatActivity {
    private NativeWebView mWebView;
    private TitleView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_question);

        Bundle bundle = this.getIntent().getExtras();
        mTitleView = (TitleView) this.findViewById(R.id.question_bank_titleview);
        mTitleView.getTitleTv().setText(bundle.getString("subjectName"));
        mTitleView.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoQuestionActivity.this.finish();
            }
        });

        TextView chapterTv = (TextView)this.findViewById(R.id.lbl_chapter);
        chapterTv.setText(bundle.getString("chapterName"));
    }
}
