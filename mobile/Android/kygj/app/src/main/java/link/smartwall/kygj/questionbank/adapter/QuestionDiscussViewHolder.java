package link.smartwall.kygj.questionbank.adapter;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.domain.QuestionDiscuss;
import link.smartwall.kygj.questionbank.http.LocalDataReader;
import link.smartwall.kygj.questionbank.http.RemoteDataReader;

public class QuestionDiscussViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private View view;
    private TextView parentLeftText;
    private TextView parentRightText;
    private Button btnReply;

    public QuestionDiscussViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final QuestionDiscuss questionDiscuss, final int pos) {
        parentLeftText = (TextView) view.findViewById(R.id.parent_left_text);
        parentRightText = (TextView) view.findViewById(R.id.parent_right_text);

        parentLeftText.setText(questionDiscuss.getUserName());
        parentRightText.setText(questionDiscuss.getComment());

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
                String replierGuid = LocalDataReader.EMP_GUID;
                String replierName = LocalDataReader.EMP_NAME;
                String replierComment = mComment.getText().toString();

                RemoteDataReader.saveReplyComment(questionGuid, userGuid, userName, comment, replierGuid, replierName, replierComment);

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