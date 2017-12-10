package link.smartwall.kygj;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import link.smartwall.controls.fragment.NvWebViewFragment;
import link.smartwall.kygj.questionbank.control.BottomNavigationViewHelper;
import link.smartwall.kygj.questionbank.control.CustomViewPager;
import link.smartwall.kygj.questionbank.control.ViewPagerAdapter;
import link.smartwall.kygj.questionbank.frgment.QuestionBankFragment;

public class KygjActivity extends AppCompatActivity {
    private CustomViewPager viewPager;
    private MenuItem prevMenuItem;
    BottomNavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_question_bank:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_share:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_live_streaming:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_big_v:
                    viewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_me:
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

        viewPager = (CustomViewPager) this.findViewById(R.id.viewpager);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
//        默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
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

        setupViewPager(viewPager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setTitle("注意")
                        .setMessage("确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                finish();

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                            }
                        })
                        .show();
                break;

            default:
                break;
        }
        return false;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(QuestionBankFragment.newInstance());
        adapter.addFragment(this.createBase("分享"));
        adapter.addFragment(this.createBase("直播"));
        adapter.addFragment(this.createBase("大V"));
        adapter.addFragment(this.createOptionsFragment("我"));
        viewPager.setAdapter(adapter);
    }

    private NvWebViewFragment createBase(String info) {
        Bundle args = new Bundle();
        NvWebViewFragment fragment = new NvWebViewFragment();
        args.putString("url", "file:///android_asset/exam/test.html");
        fragment.setArguments(args);

        return fragment;
    }

    private NvWebViewFragment createQuestionBankFragment(String info) {
        Bundle args = new Bundle();
        NvWebViewFragment fragment = new NvWebViewFragment();
        args.putString("url", "http://121.43.96.235/question-bank/app#/Chapter");
        fragment.setArguments(args);

        return fragment;
    }

    private NvWebViewFragment createOptionsFragment(String info) {
        Bundle args = new Bundle();
        NvWebViewFragment fragment = new NvWebViewFragment();
        args.putString("url", "http://121.43.96.235/question-bank/app#/Options");
        fragment.setArguments(args);

        return fragment;
    }
}
