package link.smartwall.kygj.questionbank.adapter;

import link.smartwall.kygj.questionbank.domain.Subject;

/**
 * 单击项
 */

public interface ItemClickListener {
    /**
     * 转开科目
     * @param subject 科目
     */
    void onExpandChildren(Subject subject);

    /**
     * 收缩科目
     * @param subject 科目
     */
    void onHideChildren(Subject subject);
}
