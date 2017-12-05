package link.smartwall.kygj.questionbank.http;

import android.util.Log;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.questionbank.domain.Chapter;
import link.smartwall.kygj.questionbank.domain.ChapterQuestions;
import link.smartwall.kygj.questionbank.domain.Subject;

/**
 * Created by LEXLEK on 2017/12/3.
 */

public class RemoteDataReader {
    private static final String URL_PREFIX = "http://192.168.1.8:7001/question-bank/v1/";

    /**
     * 加载科目
     *
     * @param categoryGuid 分类
     */
    public static void readSubjects(String categoryGuid) {
        RequestParams params = new RequestParams(URL_PREFIX + "list/subject");
        params.addQueryStringParameter("category_guid", categoryGuid);
        final DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());
        x.http().post(params, new Callback.CommonCallback<List<Subject>>() {
            @Override
            public void onSuccess(List<Subject> subjectList) {
                try {
                    for (Subject s : subjectList) {
                        db.saveOrUpdate(s);
                        //TODO 更新算法需要考虑
                        RemoteDataReader.readChapters(s.getGuid());
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
            }

            @Override
            public void onFinished() {
                System.out.println("finished");
            }
        });
    }

    /**
     * 加载科目下所有章节
     *
     * @param subjectGuid 科目
     */
    public static void readChapters(String subjectGuid) {
        RequestParams params = new RequestParams(URL_PREFIX + "list/chapter");
        params.addQueryStringParameter("subject_guid", subjectGuid);
        final DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        x.http().post(params, new Callback.CommonCallback<List<Chapter>>() {
            @Override
            public void onSuccess(List<Chapter> chapterList) {
                try {
                    for (Chapter c : chapterList) {
                        db.saveOrUpdate(c);

                        readQuestions(c.getGuid());
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
            }

            @Override
            public void onFinished() {
                System.out.println("finished");
            }
        });
    }

    /**
     * 加载章节试题
     *
     * @param chapterGuid 章节
     */
    public static void readQuestions(String chapterGuid) {
        RequestParams params = new RequestParams(URL_PREFIX + "question/chapter/get2");
        params.addQueryStringParameter("chapter_guid", chapterGuid);
        final DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        x.http().post(params, new Callback.CommonCallback<ChapterQuestions>() {
            @Override
            public void onSuccess(ChapterQuestions chapterQuestions) {
                try {
                    Log.i("question", "chapterQuestions: " + chapterQuestions);
                    if (chapterQuestions != null) {
                        db.saveOrUpdate(chapterQuestions);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
            }

            @Override
            public void onFinished() {
                System.out.println("finished");
            }
        });
    }
}
