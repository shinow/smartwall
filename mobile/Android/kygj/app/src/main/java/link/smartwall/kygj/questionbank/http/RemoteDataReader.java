package link.smartwall.kygj.questionbank.http;

import android.util.Log;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.questionbank.domain.Category;
import link.smartwall.kygj.questionbank.domain.Chapter;
import link.smartwall.kygj.questionbank.domain.ChapterList;
import link.smartwall.kygj.questionbank.domain.ChapterQuestions;
import link.smartwall.kygj.questionbank.domain.QuestionDiscuss;
import link.smartwall.kygj.questionbank.domain.Subject;
import link.smartwall.kygj.questionbank.domain.SubjectList;
import link.smartwall.kygj.questionbank.domain.UserInfo;

import static org.xutils.x.http;

/**
 * Created by LEXLEK on 2017/12/3.
 */

public class RemoteDataReader {
    public interface IDataReader<T> {
        void readData(T data);
    }

    //    private static final String URL_PREFIX = "http://121.43.96.235:7001/question-bank/v1/";
//    private static final String URL_PREFIX = "http://192.168.3.20:7001/question-bank/v1/";
    private static final String URL_PREFIX = "http://192.168.1.3:7001/question-bank/v1/";

    /**
     * @return 数据访问对象
     */
    private static DbManager getDb() {
        return x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());
    }

    /**
     * 获取所有分类
     */
    public static void readAllCategorys(ReadDataCallback<List<Category>> callback) {
        RequestParams params = new RequestParams(URL_PREFIX + "list/all_catagory");
        http().post(params, callback);
    }


    /**
     * 加载科目
     *
     * @param categoryGuid 分类
     */
    public static void readSubjects(String categoryGuid) {
        RequestParams params = new RequestParams(URL_PREFIX + "list/subject");
        params.addQueryStringParameter("category_guid", categoryGuid);

        try {
            SubjectList subjects = http().postSync(params, SubjectList.class);

            for (Subject s : subjects) {
                getDb().saveOrUpdate(s);

                RemoteDataReader.readChapters(s.getGuid());
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 加载科目下所有章节
     *
     * @param subjectGuid 科目
     */

    public static void readChapters(String subjectGuid) {
        RequestParams params = new RequestParams(URL_PREFIX + "list/chapter");
        params.addQueryStringParameter("subject_guid", subjectGuid);

        try {
            ChapterList chapters = x.http().postSync(params, ChapterList.class);

            for (Chapter c : chapters) {
                getDb().saveOrUpdate(c);

                readQuestions(c.getGuid());
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 加载章节试题
     *
     * @param chapterGuid 章节
     */
    public static void readQuestions(String chapterGuid) {
        RequestParams params = new RequestParams(URL_PREFIX + "question/chapter/get2");
        params.addQueryStringParameter("chapter_guid", chapterGuid);

        try {
            ChapterQuestions chapterQuestions = x.http().postSync(params, ChapterQuestions.class);

            if (chapterQuestions != null) {
                getDb().saveOrUpdate(chapterQuestions);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 保存评论
     */
    public static void saveComment(String questionGuid,
                                   String userGuid,
                                   String userName,
                                   String comment
    ) {
        RequestParams params = new RequestParams(URL_PREFIX + "question/comment");
        params.addQueryStringParameter("questionGuid", questionGuid);
        params.addQueryStringParameter("userGuid", userGuid);
        params.addQueryStringParameter("userName", userName);
        params.addQueryStringParameter("comment", comment);

        http().post(params, new ReadDataCallback<String>() {

            @Override
            public void onSuccess(String result) {

            }
        });
    }

    /**
     * 保存评论回复
     */
    public static void saveReplyComment(String questionGuid,
                                        String userGuid,
                                        String userName,
                                        String comment,
                                        String replierGuid,
                                        String replierName,
                                        String replierComment) {
        RequestParams params = new RequestParams(URL_PREFIX + "question/reply_comment");
        params.addQueryStringParameter("questionGuid", questionGuid);
        params.addQueryStringParameter("userGuid", userGuid);
        params.addQueryStringParameter("userName", userName);
        params.addQueryStringParameter("comment", comment);
        //params.addQueryStringParameter("commentTime",commentTime);
        params.addQueryStringParameter("replierGuid", replierGuid);
        params.addQueryStringParameter("replierName", replierName);
        params.addQueryStringParameter("replierComment", replierComment);

        x.http().post(params, new ReadDataCallback<String>() {

            @Override
            public void onSuccess(String result) {
            }
        });
    }

    /**
     * 获取问题评论
     */
    public static void getQuestionComments(String questionGuid, int page, final IDataReader<List<QuestionDiscuss>> dataReader) {
        RequestParams params = new RequestParams(URL_PREFIX + "question/list_comment");
        params.addQueryStringParameter("question_guid", questionGuid);
        params.addQueryStringParameter("page", String.valueOf(page));

        final DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        http().post(params, new Callback.CommonCallback<List<QuestionDiscuss>>() {
            @Override
            public void onSuccess(List<QuestionDiscuss> result) {
                Log.i("APPP", result.toString());

                dataReader.readData(result);
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
     * 注册
     *
     * @param mobile     手机号
     * @param name       名称
     * @param password   密码
     * @param verifyCode 注册码
     */
    public static void register(String mobile, String name, String password, String verifyCode, ReadDataResultCallback<UserInfo> callback) {
        RequestParams params = new RequestParams(URL_PREFIX + "user/register");
        params.addQueryStringParameter("mobile", mobile);
        params.addQueryStringParameter("name", name);
        params.addQueryStringParameter("password", password);
        params.addQueryStringParameter("verifyCode", verifyCode);

        http().post(params, callback);
    }

    /**
     * 登录
     *
     * @param mobile   手机号
     * @param password 密码
     */
    public static void login(String mobile, String password, ReadDataResultCallback<UserInfo> callback) {
        RequestParams params = new RequestParams(URL_PREFIX + "user/login");
        params.addQueryStringParameter("mobile", mobile);
        params.addQueryStringParameter("password", password);

        http().post(params, callback);
    }

    /**
     * 保存用户关注的考试
     */
    public static void saveUserCategory(UserInfo userInfo) {
        RequestParams params = new RequestParams(URL_PREFIX + "user/kind_category");
        params.addQueryStringParameter("guid", userInfo.getGuid());
        params.addQueryStringParameter("kind", userInfo.getExamKind());
        params.addQueryStringParameter("category", userInfo.getExamCategory());

        http().post(params, new ReadDataCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }
        });
    }

    /**
     * 获取点赞次数
     *
     * @param questionGuid 题目Guid
     * @return
     */
    public static String getCommentCount(String questionGuid) {
        RequestParams params = new RequestParams(URL_PREFIX + "question/comment_count");
        params.addQueryStringParameter("question_guid", questionGuid);

        try {
            System.out.println("---->" + http().postSync(params, String.class));
            return http().postSync(params, String.class);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return "0";
    }

    public static void saveDoQuestion(String questionGuid, String chapterGuid, String answer, int result) {
        UserInfo userInfo = QuestionBankAppplication.getInstance().getUserInfo();

        RequestParams params = new RequestParams(URL_PREFIX + "question/do_info/set");
        params.addQueryStringParameter("user_guid", userInfo.getGuid());
        params.addQueryStringParameter("question_guid", questionGuid);
        params.addQueryStringParameter("chapter_guid", chapterGuid);
        params.addQueryStringParameter("answer", answer);
        params.addQueryStringParameter("result", String.valueOf(result));

        try {
            http().post(params, new ReadDataCallback<String>() {

                @Override
                public void onSuccess(String result) {

                }
            });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static String getDoQuestion(String questionGuid) {
        UserInfo userInfo = QuestionBankAppplication.getInstance().getUserInfo();

        RequestParams params = new RequestParams(URL_PREFIX + "question/do_info/get");
        params.addQueryStringParameter("user_guid", userInfo.getGuid());
        params.addQueryStringParameter("question_guid", questionGuid);

        try {
            return http().postSync(params, String.class);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void saveLikes(String userGuid, String questionGuid) {
        RequestParams params = new RequestParams(URL_PREFIX + "question/likes/set");
        params.addQueryStringParameter("user_guid", userGuid);
        params.addQueryStringParameter("question_guid", questionGuid);

        try {
            http().post(params, new ReadDataCallback<String>() {
                @Override
                public void onSuccess(String result) {

                }
            });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void saveNotes(String userGuid, String questionGuid, String notes) {
        RequestParams params = new RequestParams(URL_PREFIX + "question/notes/set");
        params.addQueryStringParameter("user_guid", userGuid);
        params.addQueryStringParameter("question_guid", questionGuid);
        params.addQueryStringParameter("notes", notes);

        try {
            http().post(params, new ReadDataCallback<String>() {
                @Override
                public void onSuccess(String result) {

                }
            });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
