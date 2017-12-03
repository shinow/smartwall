package link.smartwall.kygj.questionbank.frgment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import link.smartwall.controls.view.TitleView;
import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.adapter.EndlessRecyclerOnScrollListener;
import link.smartwall.kygj.questionbank.adapter.SubjectChapterAdapter;
import link.smartwall.kygj.questionbank.domain.BaseItem;
import link.smartwall.kygj.questionbank.domain.Subject;
import link.smartwall.kygj.questionbank.http.LocalDataReader;

public class QuestionBankFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<BaseItem> itemList = new ArrayList<>();
    private SubjectChapterAdapter mAdapter;
    private TitleView titleView;

    public QuestionBankFragment() {
    }

    public static QuestionBankFragment newInstance() {
        QuestionBankFragment fragment = new QuestionBankFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_bank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        titleView = (TitleView) view.findViewById(R.id.question_bank_titleview);

        initEvents();

        initSubjects();
    }

    private void initEvents() {
        this.titleView.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击返回，可在此处调用finish()", Toast.LENGTH_LONG).show();
            }
        });

        this.titleView.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "点击返回(Right)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * 初始化科目
     */
    private void initSubjects() {
        List<Subject> subjects = LocalDataReader.readSubjects("5DCA16610870507BE050840A06394546");

        itemList.clear();
        itemList.addAll(subjects);

        setData();
    }

    private void setData() {
        LinearLayoutManager lm = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new SubjectChapterAdapter(this.getContext(), itemList);
        mRecyclerView.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new SubjectChapterAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
            }
        });
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(lm) {
            @Override
            public void onLoadMore(int currentPage) {
                Toast.makeText(getContext(), "loadMore", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
