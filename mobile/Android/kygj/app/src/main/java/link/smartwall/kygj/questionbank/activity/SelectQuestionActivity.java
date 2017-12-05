package link.smartwall.kygj.questionbank.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import link.smartwall.controls.view.TitleView;
import link.smartwall.controls.webview.NativeWebView;
import link.smartwall.kygj.R;

/**
 * 选择题目
 */
public class SelectQuestionActivity extends AppCompatActivity {
    private NativeWebView mWebView;
    private TitleView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_select_question);

        Bundle bundle = this.getIntent().getExtras();

        mTitleView = (TitleView) this.findViewById(R.id.question_bank_titleview);
        mTitleView.getTitleTv().setText(bundle.getString("subjectName"));
        mTitleView.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectQuestionActivity.this.finish();
            }
        });

        mWebView = (NativeWebView) this.findViewById(R.id.web_view);
        String chapterName = bundle.getString("chapterName");
        String chapterGuid = bundle.getString("chapterGuid");
        String subjectName = bundle.getString("subjectName");
        this.onResume();

        mWebView.loadUrl("file:///android_asset/qb/question_board.html?chapterName=" + chapterName + "&chapterGuid=" + chapterGuid + "&subjectName=" + subjectName);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mWebView.reload();
    }
}
