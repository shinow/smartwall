package link.smartwall.kygj.questionbank.adapter;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.domain.QuestionDiscuss;
import link.smartwall.kygj.questionbank.domain.UserInfo;
import link.smartwall.kygj.questionbank.http.RemoteDataReader;

public class QuestionDiscussViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private View view;
    private QuestionDiscussAdapter adapter;

    TextView mainLeftText;
    TextView mainCommentText;
    TextView tvReplierName;
    TextView tvReplierComment;
    RelativeLayout replierLayout;

    private Button btnReply;

    public QuestionDiscussViewHolder(Context context, View itemView, QuestionDiscussAdapter adapter) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
        this.adapter = adapter;
    }

    public void bindView(final QuestionDiscuss questionDiscuss, final int pos) {
        mainLeftText = (TextView) view.findViewById(R.id.main_left_text);
        mainCommentText = (TextView) view.findViewById(R.id.tv_comment);
        if (questionDiscuss.getReplierGuid() != null) {
            mainLeftText.setText(questionDiscuss.getReplierName());
            mainCommentText.setText(questionDiscuss.getReplierComment());

            tvReplierName = (TextView) view.findViewById(R.id.tv_replier_name);
            tvReplierComment = (TextView) view.findViewById(R.id.tv_reply);
            tvReplierName.setText(questionDiscuss.getUserName());
            tvReplierComment.setText(questionDiscuss.getComment());
        } else {
            mainLeftText.setText(questionDiscuss.getUserName());
            mainCommentText.setText(questionDiscuss.getComment());

            replierLayout = (RelativeLayout) view.findViewById(R.id.lyt_reply);
            replierLayout.setVisibility(View.GONE);
        }

        View popView = LayoutInflater.from(this.mContext)
                .inflate(R.layout.view_comment_input, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this.mContext);
        dialog.setContentView(popView);

        final EditText mComment = popView.findViewById(R.id.txt_comment);
        popView.findViewById(R.id.comment_input_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        popView.findViewById(R.id.comment_input_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionGuid = questionDiscuss.getQuestionGuid();
                String userGuid = questionDiscuss.getUserGuid();
                String userName = questionDiscuss.getUserName();
                String comment = questionDiscuss.getComment();

                UserInfo userInfo = QuestionBankAppplication.getInstance().getUserInfo();
                String replierGuid = userInfo.getGuid();
                String replierName = userInfo.getName();
                String replierComment = mComment.getText().toString();

                RemoteDataReader.saveReplyComment(questionGuid, userGuid, userName, comment, replierGuid, replierName, replierComment);

                QuestionDiscuss qd = new QuestionDiscuss();
                qd.setQuestionGuid(questionDiscuss.getQuestionGuid());
                qd.setUserGuid(questionDiscuss.getUserGuid());
                qd.setUserName(questionDiscuss.getUserName());
                qd.setComment(questionDiscuss.getComment());
                qd.setReplierGuid(userInfo.getGuid());
                qd.setReplierName(userInfo.getName());
                qd.setReplierComment(replierComment);

                adapter.add(qd);

                dialog.dismiss();
            }
        });

        btnReply = (Button) view.findViewById(R.id.btn_reply);
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
}