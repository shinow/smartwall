package link.smartwall.kygj.questionbank.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import link.smartwall.controls.view.TitleView;
import link.smartwall.kygj.R;

/**
 * 选择题目
 */
public class SelectQuestionActivity extends AppCompatActivity {
    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_question);

        Bundle bundle = this.getIntent().getExtras();

        titleView = (TitleView)this.findViewById(R.id.question_bank_titleview);
        titleView.getTitleTv().setText(bundle.getString("subjectName"));
        titleView.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectQuestionActivity.this.finish();
            }
        });
    }
}
