package link.smartwall.kygj.questionbank.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import link.smartwall.controls.view.TitleView;
import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.data.LocalDataReader;
import link.smartwall.kygj.questionbank.domain.UserInfo;
import link.smartwall.kygj.questionbank.http.RemoteDataReader;

public class NotesActivity extends AppCompatActivity {
    TitleView mTitleView;
    EditText etNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Bundle bundle = this.getIntent().getExtras();
        final String questionGuid = bundle.getString("questionGuid");

        etNotes = (EditText) this.findViewById(R.id.tvNotes);
        mTitleView = (TitleView) this.findViewById(R.id.question_bank_titleview);
        mTitleView.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesActivity.this.finish();
            }
        });
        mTitleView.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = QuestionBankAppplication.getInstance().getUserInfo();
                String userGuid = userInfo.getGuid();

                RemoteDataReader.saveNotes(userGuid, questionGuid, etNotes.getText().toString());
                LocalDataReader.saveNotes(questionGuid, etNotes.getText().toString());

                NotesActivity.this.finish();
            }
        });
    }
}
