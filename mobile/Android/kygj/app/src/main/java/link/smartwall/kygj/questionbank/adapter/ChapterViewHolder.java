package link.smartwall.kygj.questionbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.domain.Chapter;

/**
 * 章节
 */

public class ChapterViewHolder extends BaseViewHolder {

    private Context mContext;
    private View view;
    private TextView childLeftText;
    private TextView childRightText;

    public ChapterViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final Chapter chapter, final int pos) {
        childLeftText = (TextView) view.findViewById(R.id.child_left_text);
        childRightText = (TextView) view.findViewById(R.id.child_right_text);

        childLeftText.setText(chapter.getGuid());
        childRightText.setText(chapter.getName());
    }
}
