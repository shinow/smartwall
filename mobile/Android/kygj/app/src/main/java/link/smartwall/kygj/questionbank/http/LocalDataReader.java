package link.smartwall.kygj.questionbank.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.Collections;
import java.util.List;

import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.questionbank.domain.Chapter;
import link.smartwall.kygj.questionbank.domain.ChapterQuestionDo;
import link.smartwall.kygj.questionbank.domain.ChapterQuestions;
import link.smartwall.kygj.questionbank.domain.Subject;

/**
 * Created by LEXLEK on 2017/12/3.
 */

public class LocalDataReader {
    /**
     * 员工GUID
     */
    public static String EMP_GUID = "5DCA79B30460D1DEE050840A063947A2";

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

    /**
     * 获取章节试题
     *
     * @param chapterGuid 章节
     */
    public static JSONArray readQuestions(String chapterGuid) {
        DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        try {
            ChapterQuestions questions = db.selector(ChapterQuestions.class)
                    .where("chapterGuid", "=", chapterGuid).findFirst();

            if (questions != null) {
                JSONArray arr = JSON.parseArray(questions.getData());

                return arr;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return new JSONArray();
    }


    /**
     * 获取章节试题答题情况
     *
     * @param chapterGuid 章节
     */
    public static List<ChapterQuestionDo> readChapterQuestionDo(String chapterGuid) {
        DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        try {
            List<ChapterQuestionDo> chapterQuestionDo = db.selector(ChapterQuestionDo.class)
                    .where("chapterGuid", "=", chapterGuid).findAll();

            if (chapterQuestionDo == null) {
                return Collections.emptyList();
            } else {
                return chapterQuestionDo;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


}
