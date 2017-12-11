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

import link.smartwall.cloud.medical.questionbank.domain.QuestionDiscuss;
import link.smartwall.cloud.medical.questionbank.domain.QuestionDoInfo;
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

		List<QuestionDiscuss> li = this.mongoTemplate.find(query, QuestionDiscuss.class,
				getDiscussCollectionName(questionGuid));

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

	private String getQuestionDoInfoCollectionName(String questionGuid) {
		return "Q" + questionGuid;
	}

	private String getQuestionUserDoInfoCollectionName(String questionGuid) {
		return "U" + questionGuid;
	}

	/**
	 * 保存答题信息
	 * 
	 * @param userGuid
	 * @param questionGuid
	 * @param chapterGuid
	 * @param answer
	 *            答案
	 * @param result
	 *            答题结果： 1-正确，2-错误
	 */
	public void saveQuestionDoInfo(String userGuid, String questionGuid, String chapterGuid, String answer,
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
}
