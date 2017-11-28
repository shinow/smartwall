package link.smartwall.kygj.questionbank;

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

/**
 * Created by LEXLEK on 2017/11/28.
 */

public class ParentViewHolder extends BaseViewHolder {
    private Context mContext;
    private View view;
    private RelativeLayout containerLayout;
    private TextView parentLeftView;
    private TextView parentRightView;
    private ImageView expand;
    private View parentDashedView;
    private ItemData itemData;

    public ParentViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final Chapter dataBean, final int pos, final ItemClickListener listener){

        containerLayout = (RelativeLayout) view.findViewById(R.id.container);
        parentLeftView = (TextView) view.findViewById(R.id.parent_left_text);
        parentRightView = (TextView) view.findViewById(R.id.parent_right_text);
        expand = (ImageView) view.findViewById(R.id.expend);
        parentDashedView = view.findViewById(R.id.parent_dashed_view);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) expand
                .getLayoutParams();
        expand.setLayoutParams(params);
        parentLeftView.setText(dataBean.getParentLeftTxt());
        parentRightView.setText(dataBean.getParentRightTxt());

        if (dataBean.isExpand()) {
            expand.setRotation(90);
            parentDashedView.setVisibility(View.INVISIBLE);
        } else {
            expand.setRotation(0);
            parentDashedView.setVisibility(View.VISIBLE);
        }

        //父布局OnClick监听
        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    itemData = new ItemData();
                    if (EncapsulationItem.lastBeanList.size() > 0 && !EncapsulationItem.lastBeanList.get(0).getDataBean().getID().equals(dataBean.getID())) {
                        //如果有展开的item，先关闭
                        listener.onHideChildren(EncapsulationItem.lastBeanList.get(0).getDataBean());
                        EncapsulationItem.lastBeanList.get(0).getView().findViewById(R.id.parent_dashed_view).setVisibility(View.VISIBLE);
                        EncapsulationItem.lastBeanList.get(0).getDataBean().setExpand(false);
                        rotationExpandIcon(90, 0, EncapsulationItem.lastBeanList.get(0).getView().findViewById(R.id.expend));
                        EncapsulationItem.cleraListBeanData();//清空集合
                    }

                    if (dataBean.isExpand()) {
                        listener.onHideChildren(dataBean);
                        parentDashedView.setVisibility(View.VISIBLE);
                        EncapsulationItem.cleraListBeanData();
                        dataBean.setExpand(false);
                        rotationExpandIcon(90, 0, expand);
                    } else {
                        listener.onExpandChildren(dataBean);
                        itemData.setDataBean(dataBean);
                        itemData.setView(view);
                        EncapsulationItem.addLastBeanData(itemData);
                        parentDashedView.setVisibility(View.INVISIBLE);
                        dataBean.setExpand(true);
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
