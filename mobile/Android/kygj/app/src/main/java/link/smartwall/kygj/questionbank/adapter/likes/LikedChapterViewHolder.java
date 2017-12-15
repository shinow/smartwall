package link.smartwall.kygj.questionbank.adapter.likes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.activity.SelectQuestionActivity;
import link.smartwall.kygj.questionbank.adapter.BaseViewHolder;
import link.smartwall.kygj.questionbank.domain.Chapter;

/**
 * 章节
 */

public class LikedChapterViewHolder extends BaseViewHolder {

    private Context mContext;
    private View view;
    private TextView childLeftText;
    private TextView childRightText;

    public LikedChapterViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final Chapter chapter, final int pos) {
        childLeftText = (TextView) view.findViewById(R.id.child_left_text);
        childRightText = (TextView) view.findViewById(R.id.child_right_text);

        childLeftText.setText(chapter.getName());
//        childRightText.setText(chapter.getName());

        this.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(mContext, SelectQuestionActivity.class);
                Bundle argBundle = new Bundle();
                argBundle.putString("subjectName", chapter.getSubjectName());
                argBundle.putString("chapterName", chapter.getName());
                argBundle.putString("chapterGuid", chapter.getGuid());
                argBundle.putString("type", "liked");

                startIntent.putExtras(argBundle);
                mContext.startActivity(startIntent);
            }
        });
    }
}
