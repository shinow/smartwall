package link.smartwall.kygj.questionbank.adapter;

import android.view.View;

import link.smartwall.kygj.questionbank.domain.Subject;

/**
 * Created by LEXLEK on 2017/11/28.
 */

public class ItemData {
    private Subject subject;
    private View view;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
