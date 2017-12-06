package link.smartwall.cloud.medical.questionbank.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import link.smartwall.cloud.medical.questionbank.domain.QuestionDiscuss;
import link.smartwall.cloud.medical.questionbank.domain.QuestionNote;

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
		return "D" + questionGuid;
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
}
