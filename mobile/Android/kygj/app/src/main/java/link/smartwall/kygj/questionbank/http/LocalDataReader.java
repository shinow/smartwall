package link.smartwall.kygj.questionbank.http;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.Collections;
import java.util.List;

import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.questionbank.domain.Subject;

/**
 * Created by LEXLEK on 2017/12/3.
 */

public class LocalDataReader {
    private static final String URL_PREFIX = "http://192.168.1.8:7001/question-bank/v1/";

    /**
     * 获取科目
     *
     * @param categoryGuid 分类
     *
     */
    public static List<Subject> readSubjects(String categoryGuid) {
        DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        try {
            return db.selector(Subject.class).where("categoryGuid","=",categoryGuid).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }
}
