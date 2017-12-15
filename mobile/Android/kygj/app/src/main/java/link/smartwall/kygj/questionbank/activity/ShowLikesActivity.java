package link.smartwall.kygj.questionbank.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import link.smartwall.controls.view.TitleView;
import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.adapter.likes.LikeQuestionAdapter;
import link.smartwall.kygj.questionbank.data.LocalDataReader;
import link.smartwall.kygj.questionbank.domain.BaseItem;
import link.smartwall.kygj.questionbank.domain.Subject;
import link.smartwall.kygj.questionbank.domain.UserInfo;

public class ShowLikesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<BaseItem> itemList = new ArrayList<>();
    private LikeQuestionAdapter mAdapter;
    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_likes);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycle_view);
        titleView = (TitleView) this.findViewById(R.id.question_bank_titleview);

        this.titleView.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLikesActivity.this.finish();
            }
        });

        initSubjects();
    }

    /*
 * 初始化科目
 */
    private void initSubjects() {
        UserInfo userInfo = QuestionBankAppplication.getInstance().getUserInfo();
        List<Subject> subjects = LocalDataReader.readLikedSubjects();
        itemList.clear();
        if (subjects != null) {
            itemList.addAll(subjects);
        }

        setData();
    }

    private void setData() {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new LikeQuestionAdapter(this, itemList);
        mRecyclerView.setAdapter(mAdapter);
//        //滚动监听
//        mAdapter.setOnScrollListener(new SubjectChapterAdapter.OnScrollListener() {
//            @Override
//            public void scrollTo(int pos) {
//                mRecyclerView.scrollToPosition(pos);
//            }
//        });
    }
}
