package link.smartwall.controls.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import link.smartwall.kygj.R;

public class NavigationView extends LinearLayout {
    private Button btnLeft;
    private Button btnRight;
    private TextView tvTitle;

    public NavigationView(Context context) {
        super(context);
    }

    public NavigationView(final Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.view_navigation, this, true);

        Typeface font = Typeface.createFromAsset (context.getAssets(), "fonts/fontawesome-webfont.ttf");
        btnLeft = (Button) view.findViewById(R.id.btn_left);
        btnLeft.setText(R.string.fa_angle_left);
        btnLeft.setTypeface(font);
        btnRight = (Button) view.findViewById(R.id.btn_right);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);

        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
    }

    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }
}
