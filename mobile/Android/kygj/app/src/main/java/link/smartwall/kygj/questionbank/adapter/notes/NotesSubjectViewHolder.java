package link.smartwall.kygj.questionbank.adapter.notes;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.adapter.BaseViewHolder;
import link.smartwall.kygj.questionbank.adapter.EncapsulationItem;
import link.smartwall.kygj.questionbank.adapter.ItemClickListener;
import link.smartwall.kygj.questionbank.adapter.ItemData;
import link.smartwall.kygj.questionbank.data.LocalDataReader;
import link.smartwall.kygj.questionbank.domain.Subject;

/**
 * Created by LEXLEK on 2017/11/28.
 */

public class NotesSubjectViewHolder extends BaseViewHolder {
    private Context mContext;
    private View view;
    private TextView parentLeftView;
    private TextView parentRightView;
    private ImageView expand;

    public NotesSubjectViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final Subject subject, final int pos, final ItemClickListener listener) {
        parentLeftView = (TextView) view.findViewById(R.id.parent_left_text);
//        parentRightView = (TextView) view.findViewById(R.id.parent_right_text);
        expand = (ImageView) view.findViewById(R.id.expend);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) expand
                .getLayoutParams();
        expand.setLayoutParams(params);
        parentLeftView.setText(subject.getName());
//        parentRightView.setText(subject.getName());

        if (subject.isExpand()) {
            expand.setRotation(90);
        } else {
            expand.setRotation(0);
        }

        //父布局OnClick监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    if (!subject.isChildLoaded()) {
                        subject.setChildLoaded(true);
                        subject.setChapters(LocalDataReader.readNotesChapters(subject));
                    }

                    ItemData itemData = EncapsulationItem.notesItemData;
                    if (itemData != null && !subject.getGuid().equals(itemData.getSubject().getGuid())) {
                        //如果有展开的Subject，先关闭
                        listener.onHideChildren(itemData.getSubject());
                        itemData.getView().findViewById(R.id.parent_dashed_view).setVisibility(View.VISIBLE);
                        itemData.getSubject().setExpand(false);

                        rotationExpandIcon(90, 0, itemData.getView().findViewById(R.id.expend));
                        EncapsulationItem.notesItemData = null;
                    }

                    if (subject.isExpand()) {
                        listener.onHideChildren(subject);
                        EncapsulationItem.notesItemData = null;
                        subject.setExpand(false);
                        rotationExpandIcon(90, 0, expand);
                    } else {
                        listener.onExpandChildren(subject);

                        ItemData newItemData = new ItemData();
                        newItemData.setSubject(subject);
                        newItemData.setView(view);
                        EncapsulationItem.notesItemData = newItemData;
                        subject.setExpand(true);
                        rotationExpandIcon(0, 90, expand);
                    }
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void rotationExpandIcon(float from, float to, final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);//属性动画
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }
}
