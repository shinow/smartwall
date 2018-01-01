package link.smartwall.kygj.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import link.smartwall.kygj.KygjActivity;
import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.R;
import link.smartwall.kygj.questionbank.data.LocalDataReader;
import link.smartwall.kygj.questionbank.domain.Category;
import link.smartwall.kygj.questionbank.domain.Chapter;
import link.smartwall.kygj.questionbank.domain.ChapterQuestions;
import link.smartwall.kygj.questionbank.domain.Kind;
import link.smartwall.kygj.questionbank.domain.Subject;
import link.smartwall.kygj.questionbank.domain.UserInfo;
import link.smartwall.kygj.questionbank.http.ReadDataCallback;
import link.smartwall.kygj.questionbank.http.RemoteDataReader;

/**
 * 选择考试种类
 */
public class SelectExamCategoryActivity extends AppCompatActivity {
    @BindView(R.id.tvExam)
    TextView tvKind;

    private OptionsPickerView pvOptions;
    private String kindGuid;
    private String categoryGuid;

    private List<Kind> kinds = new ArrayList<Kind>();
    private List<List<Category>> categorys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exam_category);

        ButterKnife.bind(this);

        initData();
//        initPickView();
    }

    /**
     * 更新数据
     */
    private void initData() {
        UserInfo userInfo = QuestionBankAppplication.getInstance().getUserInfo();
        kindGuid = userInfo.getExamKind();
        categoryGuid = userInfo.getExamCategory();

        kinds.add(new Kind("61AEAB78A7CD3671E050840A063959A8", "医师资格"));
        kinds.add(new Kind("61AEAB78A7CE3671E050840A063959A8", "卫生资格"));

        final List<Category> category1 = new ArrayList<>();
        final List<Category> category2 = new ArrayList<>();
        categorys.add(category1);
        categorys.add(category2);

        RemoteDataReader.readAllCategorys(new ReadDataCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> result) {
                for (Category c : result) {
                    if ("61AEAB78A7CD3671E050840A063959A8".equals(c.getKindGuid())) {
                        category1.add(c);
                    } else {
                        category2.add(c);
                    }
                }

                initPickView();
            }
        });
    }

    @OnClick(R.id.tvExam)
    void selectCategory(View v) {
        pvOptions.show();
    }

    @OnClick(R.id.btnSave)
    void saveCategory(View v) {
        if (kindGuid == null || kindGuid.length() < 1) {
            Toast.makeText(this, "请选择考试", Toast.LENGTH_SHORT).show();

            return;
        }

        UserInfo userInfo = QuestionBankAppplication.getInstance().getUserInfo();

        userInfo.setExamKind(kindGuid);
        userInfo.setExamCategory(categoryGuid);

        try {
            LocalDataReader.getDb().update(userInfo, "exam_kind", "exam_category");
            RemoteDataReader.saveUserCategory(userInfo);

            loadExamInfo();
            Intent intent = new Intent(SelectExamCategoryActivity.this, KygjActivity.class);
            startActivity(intent);

            finish();
        } catch (DbException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 加载考试信息
     */
    private void loadExamInfo() {
        try {
            DbManager db = LocalDataReader.getDb();
            db.dropTable(Subject.class);
            db.dropTable(Chapter.class);
            db.dropTable(ChapterQuestions.class);

            RemoteDataReader.readSubjects(this.categoryGuid);
            RemoteDataReader.readChapters(this.categoryGuid);
            RemoteDataReader.readQuestions(this.categoryGuid);

        } catch (DbException ex) {

        }
    }

    private void initPickView() {
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Kind kind = kinds.get(options1);
                Category category = categorys.get(options1).get(options2);

                kindGuid = kind.getGuid();
                categoryGuid = category.getGuid();
                String tx = kind.getName() + "--" + category.getName();

                tvKind.setText(tx);
            }
        }).setTitleText("选择考试")
                .setCyclic(false, false, false)
                .setSelectOptions(0, 0, 1)
                .setOutSideCancelable(false)
                .build();

        pvOptions.setPicker(kinds, categorys);
    }
}
