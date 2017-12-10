package link.smartwall.kygj.questionbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.domain.QuestionDiscuss;


/**
 * 评论Adapter
 */
public class QuestionDiscussAdapter extends RecyclerView.Adapter<QuestionDiscussViewHolder> {
    private Context context;
    private List<QuestionDiscuss> itemList;
    private LayoutInflater mInflater;
    private View view;
    private RecyclerView recyclerView;

    public QuestionDiscussAdapter(Context context, RecyclerView recyclerView, List<QuestionDiscuss> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.recyclerView = recyclerView;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public QuestionDiscussViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_item_comment, parent, false);
        return new QuestionDiscussViewHolder(context, view, this);
    }

    /**
     * 根据不同的类型绑定View
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(QuestionDiscussViewHolder holder, int position) {
        holder.bindView((QuestionDiscuss) itemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void add(List<QuestionDiscuss> questionDiscuss) {
        int size = this.itemList.size();
        itemList.addAll(questionDiscuss);
        notifyItemRangeInserted(size, questionDiscuss.size());
    }

    public void add(QuestionDiscuss questionDiscuss) {
        itemList.add(0, questionDiscuss);
        notifyItemInserted(0);

        this.recyclerView.scrollToPosition(0);
    }
}
