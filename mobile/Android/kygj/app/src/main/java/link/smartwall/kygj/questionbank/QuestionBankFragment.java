package link.smartwall.kygj.questionbank;

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

public class QuestionBankFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Chapter> dataBeanList;
    private RecyclerAdapter mAdapter;
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
        initData();
    }

    private void initEvents() {
        this.titleView.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击返回，可在此处调用finish()", Toast.LENGTH_LONG).show();
            }
        });

//        this.titleView.getRightTextTv().setOnClickListener(new );
    }

    private void initData() {
        dataBeanList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Chapter dataBean = new Chapter();
            dataBean.setID(i + "");
            dataBean.setType(0);
            dataBean.setParentLeftTxt("父--" + i);
            dataBean.setParentRightTxt("父内容--" + i);
            dataBean.setChildLeftTxt("子--" + i);
            dataBean.setChildRightTxt("子内容--" + i);
            dataBean.setChildBean(dataBean);
            dataBeanList.add(dataBean);
        }
        setData();
    }

    private void setData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAdapter = new RecyclerAdapter(this.getContext(), dataBeanList);
        mRecyclerView.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
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
