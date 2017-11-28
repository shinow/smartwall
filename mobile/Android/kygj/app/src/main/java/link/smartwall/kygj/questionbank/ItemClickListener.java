package link.smartwall.kygj.questionbank;

/**
 * Created by LEXLEK on 2017/11/28.
 */

public interface ItemClickListener {
    /**
     * 展开子Item
     * @param bean
     */
    void onExpandChildren(Chapter bean);

    /**
     * 隐藏子Item
     * @param bean
     */
    void onHideChildren(Chapter bean);
}
