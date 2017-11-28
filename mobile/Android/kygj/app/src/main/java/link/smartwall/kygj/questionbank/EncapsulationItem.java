package link.smartwall.kygj.questionbank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LEXLEK on 2017/11/28.
 */

public class EncapsulationItem {
    public static List<ItemData> lastBeanList = new ArrayList<>();

    //将展开的item添加到list中
    public static void addLastBeanData(ItemData beanData) {
        lastBeanList.add(beanData);
    }

    //清空list
    public static void cleraListBeanData() {
        lastBeanList.clear();
    }
}
