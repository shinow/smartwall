package link.smartwall.kygj;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class KygjActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    BottomNavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_university:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_forum:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_question_bank:
                    viewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_my_option:
                    viewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kygj);

        viewPager = (ViewPager)this.findViewById(R.id.viewpager);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigationView.getMenu().getItem(0).setChecked(false);
                }
                navigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 如果想禁止滑动，可以把下面的代码取消注释
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(DemoFragment.newInstance("首页"));
        adapter.addFragment(DemoFragment.newInstance("院校"));
        adapter.addFragment(DemoFragment.newInstance("论坛"));
        adapter.addFragment(DemoFragment.newInstance("题库"));
        adapter.addFragment(DemoFragment.newInstance("我的"));
        viewPager.setAdapter(adapter);
    }
}
