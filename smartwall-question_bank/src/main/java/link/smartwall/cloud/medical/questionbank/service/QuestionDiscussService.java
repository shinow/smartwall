package link.smartwall.cloud.medical.questionbank.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.cloud.medical.questionbank.domain.QuestionDiscuss;
import link.smartwall.cloud.medical.questionbank.domain.QuestionDoInfo;
import link.smartwall.cloud.medical.questionbank.domain.QuestionLikes;
import link.smartwall.cloud.medical.questionbank.domain.QuestionNote;
import link.smartwall.cloud.medical.questionbank.domain.QuestionUserDoInfo;

@Service
public class QuestionDiscussService {
    @Autowired
    private MongoTemplate mongoTemplate;

    private String getDiscussCollectionName(String questionGuid) {
        return "D" + questionGuid;
    }

    public void insertQuestionDiscuss(QuestionDiscuss qd) {
        this.mongoTemplate.save(qd, getDiscussCollectionName(qd.getQuestionGuid()));
    }

    public List<QuestionDiscuss> getQuestionDiscuss(String questionGuid, int page) {
        Query query = new Query();
        query.with(new Sort(Direction.DESC, "dataTime")).skip((page - 1) * 50).limit(50);

        List<QuestionDiscuss> li =
                this.mongoTemplate.find(query, QuestionDiscuss.class, getDiscussCollectionName(questionGuid));

        return li;
    }

    private String getNoteCollectionName(String questionGuid) {
        return "N" + questionGuid;
    }

    public void upsertQuestionNote(String userGuid, String questionGuid, String note) {
        Query query = Query.query(Criteria.where("userGuid").is(userGuid).and("questionGuid").is(questionGuid));
        Update update = Update.update("note", note).set("dataTime", new Date());

        this.mongoTemplate.upsert(query, update, QuestionNote.class, getNoteCollectionName(userGuid));
    }

    public void upsertQuestionLikes(String userGuid, String questionGuid) {
        Query query = Query.query(Criteria.where("userGuid").is(userGuid).and("questionGuid").is(questionGuid));
        Update update = Update.update("dataTime", new Date());

        this.mongoTemplate.upsert(query, update, QuestionNote.class, getNoteCollectionName(userGuid));
    }

    public long getQestionCommentCount(String questionGuid) {
        Query query = Query.query(Criteria.where("questionGuid").is(questionGuid));

        return this.mongoTemplate.count(query, QuestionDiscuss.class, getDiscussCollectionName(questionGuid));
    }

    /**
     * 保存答题信息
     * 
     * @param userGuid
     * @param questionGuid
     * @param chapterGuid
     * @param answer 答案
     * @param result 答题结果： 1-正确，2-错误
     */
    public void saveQuestionDoInfo(String userGuid,
                                   String questionGuid,
                                   String chapterGuid,
                                   String answer,
                                   int result) {
        Query query = Query.query(Criteria.where("questionGuid").is(questionGuid));
        Query queryUser = Query.query(Criteria.where("userGuid").is(userGuid).and("questionGuid").is(questionGuid));

        Update update = (new Update()).inc("total", 1);
        if (result == 1) {
            update.inc("right", 1);
        } else {
            update.inc("error", 1);
        }

        // 总统计信息
        this.mongoTemplate.upsert(query, update, QuestionDoInfo.class, "question_do_info");
        // 用户统计信息
        this.mongoTemplate.upsert(queryUser, update, QuestionUserDoInfo.class, "question_user_do_info");
    }

    /**
     * 获取用户答题信息
     * 
     * @param userGuid 用户Guid
     * @param questionGuid 题目信息
     * @return 答题信息
     */
    public Object getQuestionDoInfo(String userGuid, String questionGuid) {
        JSONObject data = new JSONObject();

        Query query = Query.query(Criteria.where("questionGuid").is(questionGuid));
        Query queryUser = Query.query(Criteria.where("userGuid").is(userGuid).and("questionGuid").is(questionGuid));

        QuestionDoInfo qdi = this.mongoTemplate.findOne(query, QuestionDoInfo.class, "question_do_info");
        QuestionUserDoInfo qudi =
                this.mongoTemplate.findOne(queryUser, QuestionUserDoInfo.class, "question_user_do_info");

        long likes = this.mongoTemplate.count(query, QuestionLikes.class, "question_likes");
        data.put("questionGuid", questionGuid);
        data.put("likes", likes);

        if (qdi == null) {
            data.put("total", 0);
            data.put("tr", 0);
            data.put("te", 0);
        } else {
            data.put("total", qdi.getTotal());
            data.put("tr", qdi.getRight());
            data.put("te", qdi.getError());
        }

        if (qudi == null) {
            data.put("ur", 0);
            data.put("ue", 0);
        } else {
            data.put("uotal", qudi.getTotal());
            data.put("ur", qudi.getRight());
            data.put("ue", qudi.getError());
        }

        return data;
    }

    /**
     * 收藏题目
     * 
     * @param userGuid 用户
     * @param questionGuid 题目号
     * @return 无
     */
    public void setQuestionLikes(String userGuid, String questionGuid) {
        Query query = Query.query(Criteria.where("userGuid").is(userGuid).and("questionGuid").is(questionGuid));
        Update update = (new Update()).inc("total", 1);

        this.mongoTemplate.upsert(query, update, QuestionLikes.class, "question_likes");
    }
}
