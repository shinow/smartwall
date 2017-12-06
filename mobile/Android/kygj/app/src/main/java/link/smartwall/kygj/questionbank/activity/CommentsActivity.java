package link.smartwall.kygj.questionbank.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import link.smartwall.controls.view.TitleView;
import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.adapter.EndlessRecyclerOnScrollListener;
import link.smartwall.kygj.questionbank.adapter.QuestionDiscussAdapter;
import link.smartwall.kygj.questionbank.domain.QuestionDiscuss;
import link.smartwall.kygj.questionbank.http.RemoteDataReader;
import link.smartwall.kygj.questionbank.http.RemoteDataReader.IDataReader;

public class CommentsActivity extends AppCompatActivity {
    private TitleView mTitleView;
    private String questionGuid;
    private RecyclerView mRecyclerView;
    private QuestionDiscussAdapter mAdapter;
    private List<QuestionDiscuss> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
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

        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycle_view);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new QuestionDiscussAdapter(this, itemList);
        mRecyclerView.setAdapter(mAdapter);
//        //滚动监听
//        mAdapter.setOnScrollListener(new SubjectChapterAdapter.OnScrollListener() {
//            @Override
//            public void scrollTo(int pos) {
//                mRecyclerView.scrollToPosition(pos);
//            }
//        });
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(lm) {
            @Override
            public void onLoadMore(int currentPage) {
                Toast.makeText(CommentsActivity.this, "loadMore", Toast.LENGTH_SHORT).show();
            }
        });
        RemoteDataReader.getQuestionComments(questionGuid, 1, new IDataReader<List<QuestionDiscuss>>() {
            @Override
            public void readData(List<QuestionDiscuss> data) {
                mAdapter.add(data);
            }
        });
    }
}
