package link.smartwall.kygj.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.controls.fragment.NvWebViewFragment;
import link.smartwall.controls.view.TitleView;
import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.control.QuestionsViewPager;
import link.smartwall.kygj.questionbank.control.QuestionsViewPagerAdapter;
import link.smartwall.kygj.questionbank.data.LocalDataReader;
import link.smartwall.kygj.questionbank.domain.UserInfo;
import link.smartwall.kygj.questionbank.http.RemoteDataReader;

public class DoQuestionActivity extends AppCompatActivity {
    QuestionsViewPager viewPager;
    TitleView mTitleView;
    EditText mComment;
    public static JSONArray questions;
    QuestionsViewPagerAdapter adapter;
    String chapterGuid;

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

        TextView chapterTv = (TextView) this.findViewById(R.id.lbl_chapter);
        chapterTv.setText(bundle.getString("chapterName"));

        viewPager = (QuestionsViewPager) this.findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        String chapterGuid = bundle.getString("chapterGuid");
        int index = bundle.getInt("index");
        String type = bundle.getString("type");

        setupViewPager(viewPager, chapterGuid, type, index);

        View view = LayoutInflater.from(this)
                .inflate(R.layout.view_comment_input, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        mComment = view.findViewById(R.id.txt_comment);
        view.findViewById(R.id.comment_input_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.comment_input_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = QuestionBankAppplication.getInstance().getUserInfo();
                String userGuid = userInfo.getGuid();
                String userName = userInfo.getName();
                String comment = mComment.getText().toString();
                JSONObject question = questions.getJSONObject(viewPager.getCurrentItem());
                String questionGuid = question.getString("guid");

                RemoteDataReader.saveComment(questionGuid, userGuid, userName, comment);
                NvWebViewFragment fragment = (NvWebViewFragment) adapter.getItem(viewPager.getCurrentItem());
                fragment.execFunc("vue.incComments()");

                dialog.dismiss();
            }
        });
//        dialog.show();
//        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, String text) {
//                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });

        TextView commentW = (TextView) this.findViewById(R.id.comment_w);
        commentW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        ImageView commentS = (ImageView) this.findViewById(R.id.comment_s);
        commentS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = QuestionBankAppplication.getInstance().getUserInfo();
                String userGuid = userInfo.getGuid();

                JSONObject question = questions.getJSONObject(viewPager.getCurrentItem());
                String questionGuid = question.getString("guid");

                RemoteDataReader.saveLikes(userGuid, questionGuid);
                LocalDataReader.saveLikes(questionGuid);

                Toast.makeText(DoQuestionActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView commentE = (ImageView) this.findViewById(R.id.comment_e);
        commentE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject question = questions.getJSONObject(viewPager.getCurrentItem());
                String questionGuid = question.getString("guid");

                Intent intent = new Intent(DoQuestionActivity.this, NotesActivity.class);
                Bundle argBundle = new Bundle();
                argBundle.putString("questionGuid", questionGuid);

                intent.putExtras(argBundle);
                DoQuestionActivity.this.startActivity(intent);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager, String chapterGuid, String type, int index) {
        adapter = new QuestionsViewPagerAdapter(getSupportFragmentManager());

        if ("null".equals(type)) {
            questions = LocalDataReader.readQuestions(chapterGuid);
        } else if ("liked".equals(type)) {
            questions = LocalDataReader.readLikedQuestions(chapterGuid);
        } else if ("notes".equals(type)) {
            questions = LocalDataReader.readNotesQuestions(chapterGuid);
        } else if ("error".equals(type)) {
            questions = LocalDataReader.readErrorQuestions(chapterGuid);
        }

        int length = questions.size();

        for (int i = 1; i <= length; i++) {
            adapter.addFragment(this.createQuestionFragment(i, chapterGuid));
        }

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index - 1);
    }

    private NvWebViewFragment createQuestionFragment(int no, String chapterGuid) {
        NvWebViewFragment fragment = new NvWebViewFragment();

        Bundle args = new Bundle();
        args.putString("url", "file:///android_asset/qb/do_question.html?no=" + no + "&chapter_guid=" + chapterGuid);
        fragment.setArguments(args);

        return fragment;
    }
}
