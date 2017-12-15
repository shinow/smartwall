package link.smartwall.cloud.medical.questionbank.service;

import java.util.Date;
import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.Exps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.itekchina.cloud.common.dao.ItekDao;

import link.smartwall.cloud.medical.questionbank.domain.Category;
import link.smartwall.cloud.medical.questionbank.domain.Chapter;
import link.smartwall.cloud.medical.questionbank.domain.ChapterQuestion;
import link.smartwall.cloud.medical.questionbank.domain.Kind;
import link.smartwall.cloud.medical.questionbank.domain.Subject;
import link.smartwall.cloud.medical.questionbank.domain.SubjectQuestion;
import link.smartwall.cloud.medical.questionbank.domain.User;
import link.smartwall.cloud.medical.questionbank.domain.UserCategory;
import link.smartwall.cloud.medical.questionbank.exception.ExamException;

@Service
public class QuestionBankService {
    @Autowired
    private ItekDao dao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Kind> getKinds() {
        return dao.query(Kind.class, null);
    }

    public List<Category> getCategorys(String kindGuid) {
        return dao.query(Category.class, Cnd.where(Exps.eq("kindGuid", kindGuid)));
    }

    public List<Category> getAllCategorys() {
        return dao.query(Category.class, null);
    }

    public List<Subject> getSubjects(String categoryGuid) {
        return dao.query(Subject.class, Cnd.where(Exps.eq("categoryGuid", categoryGuid)));
    }

    public List<Chapter> getChapters(String subjectGuid) {
        return dao.query(Chapter.class, Cnd.where(Exps.eq("subjectGuid", subjectGuid)));
    }

    public Object getCategoryChapters(String categoryGuid) {
        String strSql = "select t1.guid, t1.subject_guid, t1.name "
                        + "from exam_medical_chapter t1 "
                        + "inner join exam_medical_subject t2 on t1.subject_guid = t2.guid "
                        + "where t2.category_guid = @categoryGuid";

        Sql sql = Sqls.create(strSql);
        sql.setParam("categoryGuid", categoryGuid);
        sql.setEntity(dao.getEntity(Chapter.class));
        sql.setCallback(Sqls.callback.entities());

        dao.execute(sql);

        return sql.getResult();
    }

    public String saveKind(String name) {
        Kind kind = new Kind();
        kind.setName(name);
        kind.setModifyFlag(1);
        kind.setModifyTime(new Date());

        dao.insert(kind);

        return kind.getGuid();
    }

    public String saveCategory(String name, String kindGuid) {
        Category category = new Category();
        category.setName(name);
        category.setKindGuid(kindGuid);
        category.setModifyFlag(1);
        category.setModifyTime(new Date());

        dao.insert(category);

        return category.getGuid();
    }

    public String saveSubject(String name, String categoryGuid) {
        Subject subject = new Subject();

        subject.setName(name);
        subject.setCategoryGuid(categoryGuid);
        subject.setModifyFlag(1);
        subject.setModifyTime(new Date());

        dao.insert(subject);

        return subject.getGuid();
    }

    public String saveChapter(String name, String subjectGuid) {
        Chapter chapter = new Chapter();
        chapter.setName(name);
        chapter.setSubjectGuid(subjectGuid);
        chapter.setModifyFlag(1);
        chapter.setModifyTime(new Date());

        dao.insert(chapter);

        return chapter.getGuid();
    }

    public int delKind(String guid) {
        return dao.delete(Kind.class, guid);
    }

    public int delCategory(String guid) {
        return dao.delete(Category.class, guid);
    }

    public int delSubject(String guid) {
        return dao.delete(Subject.class, guid);
    }

    public int delChapter(String guid) {
        return dao.delete(Chapter.class, guid);
    }

    public void saveChapterQuestion(int index,
                                    String guid,
                                    String categoryGuid,
                                    String subjectGuid,
                                    String chapterGuid,
                                    String data) {
        Query query = Query.query(Criteria.where("guid").is(guid));
        Update update = Update.update("index", index)
                              .set("categoryGuid", categoryGuid)
                              .set("subjectGuid", subjectGuid)
                              .set("chapterGuid", chapterGuid)
                              .set("data", data)
                              .set("modifyTime", new Date());
        this.mongoTemplate.upsert(query, update, ChapterQuestion.class, "exam_questions");
    }

    public List<ChapterQuestion> getChapterQuestion(String chapterGuid) {
        Query query = Query.query(Criteria.where("chapterGuid").is(chapterGuid)).with(new Sort(Direction.ASC, "index"));

        List<ChapterQuestion> li = this.mongoTemplate.find(query, ChapterQuestion.class, "exam_questions");

        return li;
    }

    public List<ChapterQuestion> getCategoryQuestion(String categoryGuid) {
        Query query = Query.query(Criteria.where("categoryGuid").is(categoryGuid))
                           .with(new Sort(Direction.ASC, "chapterGuid").and(new Sort(Direction.ASC, "index")));

        List<ChapterQuestion> li = this.mongoTemplate.find(query, ChapterQuestion.class, "exam_questions");

        System.out.println(li);
        return li;
    }

    public void saveSubjectQuestion(String subjectGuid, String data) {
        SubjectQuestion sq = dao.fetch(SubjectQuestion.class, Cnd.where(Exps.eq("subjectGuid", subjectGuid)));

        if (sq == null) {
            sq = new SubjectQuestion();
            sq.setSubjectGuid(subjectGuid);
            sq.setData(data);
            sq.setModifyTime(new Date());

            dao.insert(sq);
        } else {
            sq.setData(data);
            sq.setModifyTime(new Date());

            dao.update(sq);
        }

    }

    public JSONArray getSubjectQuestion(String subjectGuid) {
        SubjectQuestion sq = dao.fetch(SubjectQuestion.class, Cnd.where(Exps.eq("subjectGuid", subjectGuid)));

        if (sq != null) {
            return JSON.parseArray(sq.getData());
        }

        return null;
    }

    public String getUserCategory(String userGuid) {
        UserCategory userCategory = dao.fetch(UserCategory.class, Cnd.where(Exps.eq("userGuid", userGuid)));

        if (userCategory == null) {
            return null;
        } else {
            return userCategory.getCategoryGuid();
        }
    }

    public Category getUserCategoryObject(String userGuid) {
        UserCategory userCategory = dao.fetch(UserCategory.class, Cnd.where(Exps.eq("userGuid", userGuid)));

        if (userCategory == null) {
            return null;
        } else {
            return dao.fetch(Category.class, userCategory.getCategoryGuid());
        }
    }

    // TODO 加了同步，需要注意
    public synchronized String setUserCategory(String userGuid, String categoryGuid) {
        UserCategory userCategory = dao.fetch(UserCategory.class, Cnd.where(Exps.eq("userGuid", userGuid)));
        if (userCategory == null) {
            userCategory = new UserCategory();
            userCategory.setUserGuid(userGuid);
            userCategory.setCategoryGuid(categoryGuid);
            userCategory.setModifyFlag(1);
            userCategory.setModifyTime(new Date());

            dao.insert(userCategory);
        } else {
            userCategory.setCategoryGuid(categoryGuid);
            userCategory.setModifyFlag(2);
            userCategory.setModifyTime(new Date());

            dao.update(userCategory);
        }

        return "success";
    }

    /**
     * 用户注册
     * 
     * @param user 用户信息
     */
    public void register(User user) {
        User u = dao.fetch(User.class, Cnd.where(Exps.eq("mobile", user.getMobile())));
        if (u != null) {
            throw new ExamException("该手机号已经注册");
        }

        user.setModifyTime(new Date());
        user.setRegTime(new Date());

        dao.insert(user);
    }

    /**
     * 用户登录
     * 
     * @param mobile 手机号
     * @param password 密码
     */
    public User login(String mobile, String password) {
        User u = dao.fetch(User.class, Cnd.where(Exps.eq("mobile", mobile)).and(Exps.eq("password", password)));

        if (u == null) {
            throw new ExamException("手机号或密码错误");
        }

        return u;
    }

    public String saveUserKindCategory(String guid, String kind, String category) {
        dao.update(User.class,
                   Chain.make("examKind", kind).add("examCategory", category),
                   Cnd.where(Exps.eq("guid", guid)));

        return "SUCCESS";
    }
}
