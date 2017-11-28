package link.smartwall.kygj.questionbank;

import android.view.View;

/**
 * Created by LEXLEK on 2017/11/28.
 */

public class ItemData {
    public Chapter dataBean;//数据类
    public View view;//展开的item,一定要将view一起封装起来，否则会有问题的。

    public Chapter getDataBean() {
        return dataBean;
    }

    public void setDataBean(Chapter dataBean) {
        this.dataBean = dataBean;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
