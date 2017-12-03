package link.smartwall.kygj.questionbank.control;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by LEXLEK on 2017/11/27.
 */

public class QuestionsViewPager extends ViewPager {
    public QuestionsViewPager(Context context) {
        super(context);
    }

    public QuestionsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return false;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        return false;
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
}
