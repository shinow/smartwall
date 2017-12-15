package link.smartwall.kygj.questionbank.adapter.likes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.adapter.BaseViewHolder;
import link.smartwall.kygj.questionbank.adapter.ItemClickListener;
import link.smartwall.kygj.questionbank.domain.BaseItem;
import link.smartwall.kygj.questionbank.domain.Chapter;
import link.smartwall.kygj.questionbank.domain.Subject;
import link.smartwall.kygj.questionbank.domain.Types;


/**
 * 题库科目章节数据Adapter
 */
public class LikeQuestionAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<BaseItem> itemList;
    private LayoutInflater mInflater;
    private OnScrollListener mOnScrollListener;

    public LikeQuestionAdapter(Context context, List<BaseItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Types.TYPE_SUBJECT:
                view = mInflater.inflate(R.layout.recycleview_item_parent, parent, false);
                return new LikedSubjectViewHolder(context, view);
            case Types.TYPE_CHAPTER:
                view = mInflater.inflate(R.layout.recycleview_item_child, parent, false);
                return new LikedChapterViewHolder(context, view);
            default:
                view = mInflater.inflate(R.layout.recycleview_item_parent, parent, false);
                return new LikedSubjectViewHolder(context, view);
        }
    }

    /**
     * 根据不同的类型绑定View
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case Types.TYPE_SUBJECT:
                LikedSubjectViewHolder parentViewHolder = (LikedSubjectViewHolder) holder;
                parentViewHolder.bindView((Subject) itemList.get(position), position, itemClickListener);
                break;
            case Types.TYPE_CHAPTER:
                LikedChapterViewHolder childViewHolder = (LikedChapterViewHolder) holder;
                childViewHolder.bindView((Chapter) itemList.get(position), position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getType();
    }

    private ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void onExpandChildren(Subject subject) {
            int position = getCurrentPosition(subject.getGuid());
            List<Chapter> chapters = subject.getChapters();
            if (chapters.isEmpty()) {
                return;
            }

            add(chapters, position + 1);
            if (mOnScrollListener != null) {
                mOnScrollListener.scrollTo(position + 1);
            }
        }

        @Override
        public void onHideChildren(Subject subject) {
            int position = getCurrentPosition(subject.getGuid());
            List<Chapter> chapters = subject.getChapters();
            if (chapters.isEmpty()) {
                return;
            }
            remove(position + 1, chapters.size());//删除
            if (mOnScrollListener != null) {
                mOnScrollListener.scrollTo(position);
            }
        }
    };

    /**
     * 插入章节
     *
     * @param chapters 章节
     * @param position 位置
     */
    public void add(List<Chapter> chapters, int position) {
        itemList.addAll(position, chapters);
        notifyItemRangeInserted(position, chapters.size());
    }

    /**
     * 移除章节
     *
     * @param position 位置
     * @param length   长度
     */
    protected void remove(int position, int length) {
        for (int i = length - 1; i > -1; i--) {
            itemList.remove(position + i);
        }

        notifyItemRangeRemoved(position, length);
    }


    /**
     * 确定当前点击的item位置并返回
     *
     * @param subjectGuid 科目guid
     * @return 当前位置
     */
    protected int getCurrentPosition(String subjectGuid) {
        for (int i = 0, size = itemList.size(); i < size; i++) {
            BaseItem item = itemList.get(i);

            if (item.getType() == Types.TYPE_SUBJECT && subjectGuid.equals(item.getGuid())) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 滚动监听接口
     */
    public interface OnScrollListener {
        void scrollTo(int pos);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }
}
