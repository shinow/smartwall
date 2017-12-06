package link.smartwall.kygj.questionbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.domain.QuestionDiscuss;

public class QuestionDiscussViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private View view;
    private TextView parentLeftText;
    private TextView parentRightText;

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
    }
}