package link.smartwall.kygj.questionbank.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;

import link.smartwall.controls.fragment.NvWebViewFragment;
import link.smartwall.controls.view.TitleView;
import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.control.QuestionsViewPager;
import link.smartwall.kygj.questionbank.control.QuestionsViewPagerAdapter;
import link.smartwall.kygj.questionbank.http.LocalDataReader;

public class DoQuestionActivity extends AppCompatActivity {
    private QuestionsViewPager viewPager;
    private TitleView mTitleView;
    public static JSONArray questions;

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
        setupViewPager(viewPager, chapterGuid);
    }

    private void setupViewPager(ViewPager viewPager, String chapterGuid) {
        QuestionsViewPagerAdapter adapter = new QuestionsViewPagerAdapter(getSupportFragmentManager());

        questions = LocalDataReader.readQuestions(chapterGuid);
        int length = questions.size();

        for (int i = 0; i < length; i++) {
            adapter.addFragment(this.createQuestionFragment(i));
        }

        viewPager.setAdapter(adapter);
    }

    private NvWebViewFragment createQuestionFragment(int no) {
        NvWebViewFragment fragment = new NvWebViewFragment();

        Bundle args = new Bundle();
        args.putString("url", "file:///android_asset/qb/do_question.html?no=" + no);
        fragment.setArguments(args);

        return fragment;
    }
}
