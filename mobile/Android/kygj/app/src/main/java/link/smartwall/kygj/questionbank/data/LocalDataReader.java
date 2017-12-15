package link.smartwall.kygj.questionbank.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.xutils.DbManager;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.questionbank.domain.Chapter;
import link.smartwall.kygj.questionbank.domain.ChapterQuestionDo;
import link.smartwall.kygj.questionbank.domain.ChapterQuestions;
import link.smartwall.kygj.questionbank.domain.Likes;
import link.smartwall.kygj.questionbank.domain.Notes;
import link.smartwall.kygj.questionbank.domain.Subject;
import link.smartwall.kygj.questionbank.domain.UserInfo;

/**
 * Created by LEXLEK on 2017/12/3.
 */

public class LocalDataReader {
    public static DbManager getDb() {
        return x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());
    }

    public static UserInfo getUserInfo() {
        try {
            return getDb().findFirst(UserInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取科目
     *
     * @param categoryGuid 分类
     */
    public static List<Subject> readSubjects(String categoryGuid) {
        try {
            return getDb().selector(Subject.class).where("categoryGuid", "=", categoryGuid).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 获取有收藏的科目
     */
    public static List<Subject> readLikedSubjects() {
        try {
            String sql = "select distinct t1.guid, t1.name from exam_subject t1 inner join exam_chapter_questions t2 on t1.guid = t2.subjectGuid inner join exam_likes t3 on t2.guid = t3.questionGuid";
            List<DbModel> ss = getDb().findDbModelAll(new SqlInfo(sql));
            List<Subject> rs = new ArrayList<Subject>();
            for (DbModel s : ss) {
                Subject subject = new Subject();

                subject.setGuid(s.getString("guid"));
                subject.setName(s.getString("name"));
                rs.add(subject);
            }
            return rs;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 获取有笔记的科目
     */
    public static List<Subject> readNotesSubjects() {
        try {
            String sql = "select distinct t1.guid, t1.name from exam_subject t1 inner join exam_chapter_questions t2 on t1.guid = t2.subjectGuid inner join exam_notes t3 on t2.guid = t3.questionGuid";
            List<DbModel> ss = getDb().findDbModelAll(new SqlInfo(sql));
            List<Subject> rs = new ArrayList<Subject>();
            for (DbModel s : ss) {
                Subject subject = new Subject();

                subject.setGuid(s.getString("guid"));
                subject.setName(s.getString("name"));
                rs.add(subject);
            }
            return rs;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 获取有错题的科目
     */
    public static List<Subject> readErrorSubjects() {
        try {
            String sql = "select distinct t1.guid, t1.name from exam_subject t1 inner join exam_chapter_questions t2 on t1.guid = t2.subjectGuid inner join exam_chapter_question_do t3 on t2.guid = t3.question_guid and t3.result = 2";
            List<DbModel> ss = getDb().findDbModelAll(new SqlInfo(sql));
            List<Subject> rs = new ArrayList<Subject>();
            for (DbModel s : ss) {
                Subject subject = new Subject();

                subject.setGuid(s.getString("guid"));
                subject.setName(s.getString("name"));
                rs.add(subject);
            }
            return rs;
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
     * 获取章节
     *
     * @param subject 科目
     */
    public static List<Chapter> readLikedChapters(Subject subject) {
        try {
            String sql = "select distinct t1.guid, t1.name from exam_chapter t1 inner join exam_chapter_questions t2 on t1.guid = t2.chapterGuid inner join exam_likes t3 on t2.guid = t3.questionGuid where t1.subjectGuid = '" + subject.getGuid() + "'";
            List<DbModel> ss = getDb().findDbModelAll(new SqlInfo(sql));

            List<Chapter> chapters = new ArrayList<>();
            for (DbModel c : ss) {
                Chapter chapter = new Chapter();
                chapter.setGuid(c.getString("guid"));
                chapter.setName(c.getString("name"));
                chapter.setSubjectName(subject.getName());

                chapters.add(chapter);
            }

            return chapters;
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
    public static List<Chapter> readNotesChapters(Subject subject) {
        try {
            String sql = "select distinct t1.guid, t1.name from exam_chapter t1 inner join exam_chapter_questions t2 on t1.guid = t2.chapterGuid inner join exam_notes t3 on t2.guid = t3.questionGuid where t1.subjectGuid = '" + subject.getGuid() + "'";
            List<DbModel> ss = getDb().findDbModelAll(new SqlInfo(sql));

            List<Chapter> chapters = new ArrayList<>();
            for (DbModel c : ss) {
                Chapter chapter = new Chapter();
                chapter.setGuid(c.getString("guid"));
                chapter.setName(c.getString("name"));
                chapter.setSubjectName(subject.getName());

                chapters.add(chapter);
            }

            return chapters;
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
    public static List<Chapter> readErrosChapters(Subject subject) {
        try {
            String sql = "select distinct t1.guid, t1.name from exam_chapter t1 inner join exam_chapter_questions t2 on t1.guid = t2.chapterGuid inner join exam_chapter_question_do t3 on t2.guid = t3.question_guid and t3.result = 2 where t1.subjectGuid = '" + subject.getGuid() + "'";
            List<DbModel> ss = getDb().findDbModelAll(new SqlInfo(sql));

            List<Chapter> chapters = new ArrayList<>();
            for (DbModel c : ss) {
                Chapter chapter = new Chapter();
                chapter.setGuid(c.getString("guid"));
                chapter.setName(c.getString("name"));
                chapter.setSubjectName(subject.getName());

                chapters.add(chapter);
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
        try {
            List<ChapterQuestions> questions = getDb().selector(ChapterQuestions.class)
                    .where("chapterGuid", "=", chapterGuid).orderBy("index").findAll();

            if (questions != null) {
                JSONArray arr = new JSONArray();

                for (ChapterQuestions c : questions) {
                    JSONObject q = JSON.parseObject(c.getData());
                    q.put("index", c.getIndex());
                    arr.add(q);
                }

                return arr;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return new JSONArray();
    }

    /**
     * 获取章节收藏的试题
     *
     * @param chapterGuid 章节
     */
    public static JSONArray readLikedQuestions(String chapterGuid) {
        JSONArray arr = new JSONArray();

        try {
            String sql = "select t1.guid, t1.'index', t1.data from exam_chapter_questions t1 " +
                    "inner join exam_likes t2 on t1.guid = t2.questionGuid " +
                    "where t1.chapterGuid = '" + chapterGuid + "'";
            List<DbModel> ss = getDb().findDbModelAll(new SqlInfo(sql));
            for (DbModel m : ss) {
                JSONObject q = JSON.parseObject(m.getString("data"));
                q.put("index", m.getString("index"));

                arr.add(q);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return arr;
    }

    /**
     * 获取章节笔记的试题
     *
     * @param chapterGuid 章节
     */
    public static JSONArray readNotesQuestions(String chapterGuid) {
        JSONArray arr = new JSONArray();

        try {
            String sql = "select t1.guid, t1.'index', t1.data from exam_chapter_questions t1 " +
                    "inner join exam_notes t2 on t1.guid = t2.questionGuid " +
                    "where t1.chapterGuid = '" + chapterGuid + "'";
            List<DbModel> ss = getDb().findDbModelAll(new SqlInfo(sql));
            for (DbModel m : ss) {
                JSONObject q = JSON.parseObject(m.getString("data"));
                q.put("index", m.getString("index"));

                arr.add(q);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return arr;
    }

    /**
     * 获取章节笔记的试题
     *
     * @param chapterGuid 章节
     */
    public static JSONArray readErrorQuestions(String chapterGuid) {
        JSONArray arr = new JSONArray();

        try {
            String sql = "select t1.guid, t1.'index', t1.data from exam_chapter_questions t1 " +
                    "inner join exam_chapter_question_do t2 on t1.guid = t2.question_guid and t2.result = 2 " +
                    "where t1.chapterGuid = '" + chapterGuid + "'";
            List<DbModel> ss = getDb().findDbModelAll(new SqlInfo(sql));
            for (DbModel m : ss) {
                JSONObject q = JSON.parseObject(m.getString("data"));
                q.put("index", m.getString("index"));

                arr.add(q);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return arr;
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

    /**
     * 获取章节试题答题情况
     *
     * @param chapterGuid 章节
     */
    public static void deleteChapterQuestionDo(String chapterGuid) {
        DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        try {
            db.delete(ChapterQuestionDo.class, WhereBuilder.b("chapterGuid", "=", chapterGuid));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取单题答题状态
     *
     * @param questionGuid 题目gguid
     */
    public static ChapterQuestionDo getQuestionDo(String questionGuid) {
        DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        try {
            ChapterQuestionDo chapterQuestionDo = db.findById(ChapterQuestionDo.class, questionGuid);

            return chapterQuestionDo;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveDoQuestion(String questionGuid, String chapterGuid, String answer, int result) {
        DbManager db = x.getDb(QuestionBankAppplication.getInstance().getDaoConfig());

        try {
            ChapterQuestionDo chapterQuestionDo = new ChapterQuestionDo();
            chapterQuestionDo.setQuestionGuid(questionGuid);
            chapterQuestionDo.setChapterGuid(chapterGuid);
            chapterQuestionDo.setAnswer(answer);
            chapterQuestionDo.setResult(result);

            db.saveOrUpdate(chapterQuestionDo);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void saveLikes(String questionGuid) {
        Likes likes = new Likes();
        likes.setQuestionGuid(questionGuid);

        try {
            getDb().saveOrUpdate(likes);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLikes(String questionGuid) {
        try {
            getDb().deleteById(Likes.class, questionGuid);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void saveNotes(String questionGuid, String notes) {
        Notes o = new Notes();
        o.setQuestionGuid(questionGuid);
        o.setNotes(notes);

        try {
            getDb().saveOrUpdate(o);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static String getNotes(String questionGuid) {
        try {
            Notes notes = getDb().findById(Notes.class, questionGuid);
            System.out.println("==========================================");
            System.out.println(notes);
            if (notes != null) {
                return notes.getNotes();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void deleteNotes(String questionGuid) {
        try {
            getDb().deleteById(Notes.class, questionGuid);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
