package link.smartwall.kygj.questionbank.http;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.Collections;
import java.util.List;

import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.questionbank.domain.Chapter;
import link.smartwall.kygj.questionbank.domain.Subject;

/**
 * Created by LEXLEK on 2017/12/3.
 */

public class LocalDataReader {
    private List<Chapter> chapters;

    /**
     * 获取科目
     *
     * @param categoryGuid 分类
     */
    public static List<Subject> readSubjects(String categoryGuid) {
        DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        try {
            return db.selector(Subject.class).where("categoryGuid", "=", categoryGuid).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 获取章节
     *
     * @param subject 科目
     */
    public static List<Chapter> readChapters(Subject subject) {
        DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        try {
            List<Chapter> chapters = db.selector(Chapter.class).where("subjectGuid", "=", subject.getGuid()).findAll();
            for (Chapter c : chapters) {
                c.setSubjectName(subject.getName());
            }

            return chapters;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }
}
