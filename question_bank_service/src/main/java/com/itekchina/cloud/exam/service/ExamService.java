package com.itekchina.cloud.exam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author <a herf="wangbo@infinitek.net">wangbo</a>
 * @version 1.0
 * @since 2017-09-21，16:00
 */
@Service
public class ExamService {
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 获取 Exam Collection Name
	 * 
	 * @param tenantId
	 *            企业Id
	 * @return 获取Collection Name
	 */
	private String getExamCollectionName(int tenantId) {
		return "Exam-" + tenantId;
	}

	/**
	 * 获取 Answer Collection Name
	 * 
	 * @param tenantId
	 *            企业Id
	 * @return 获取Collection Name
	 */
	private String getAnswerCollectionName(int tenantId) {
		return "Answer-" + tenantId;
	}

	private String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 获取试题列表
	 * 
	 * @param tenantId
	 *            企业Id
	 * @return 试题列表
	 */
	public List<DBObject> getPaperList(int tenantId) {
		DBCursor curosr = mongoTemplate.getCollection(getExamCollectionName(tenantId)).find();
		List<DBObject> data = new ArrayList<>();

		while (null != curosr && curosr.hasNext()) {
			data.add(curosr.next());
		}

		return data;
	}

	/**
	 * 获取试题
	 * 
	 * @param tenantId
	 *            企业Id
	 * @param guid
	 *            试题guid
	 * @return 试题信息
	 */
	public DBObject getPaper(int tenantId, String guid) {
		BasicDBObject query = new BasicDBObject();
		query.put("guid", guid);

		return mongoTemplate.getCollection(getExamCollectionName(tenantId)).findOne(query);
	}

	/**
	 * 保存试题
	 * 
	 * @param tenantId
	 *            企业Id
	 * @param guid
	 *            试题guid
	 * @param data
	 *            试题内容
	 * @return 试题guid
	 */
	public String savePaper(int tenantId, String guid, String data) {
		String rid = guid;
		DBObject dbo = (DBObject) JSON.parse(data);
		BasicDBObject query = new BasicDBObject();
		query.put("guid", rid);

		mongoTemplate.getCollection(getExamCollectionName(tenantId)).remove(query);
		dbo.put("guid", rid);

		mongoTemplate.getCollection(getExamCollectionName(tenantId)).insert(dbo);

		return rid;
	}

	/**
	 * 获取答案
	 * 
	 * @param tenantId
	 *            企业Id
	 * @param examGuid
	 *            试卷GUID
	 * @return 答案信息
	 */
	public Object getAnswer(int empId, String examGuid) {
		BasicDBObject query = new BasicDBObject();
		query.put("emp_id", empId);

		return mongoTemplate.getCollection("Answer-" + examGuid).findOne(query);
	}

	/**
	 * 保存答案
	 * 
	 * @param examGuid
	 *            试卷guid
	 * @param choiceScore
	 *            选择题得分
	 * @param scoringScore
	 *            打分题得分
	 * @param tenantId
	 *            企业id
	 * @param empId
	 *            员工id
	 * @param empName
	 *            员工姓名
	 * @param tenantName
	 *            企业简称
	 * @param guid
	 *            答案guid
	 * @param data
	 *            答题情况
	 * @return 答案guid
	 */
	public Object saveAnswer(String examGuid, int choiceScore, int scoringScore, int tenantId, int empId,
			String empName, String tenantName, String guid, String data) {
		String rid = guid;
		DBObject dbo = (DBObject) JSON.parse(data);
		if (null == rid) {
			rid = uuid();
			dbo.put("exam_guid", examGuid);
			dbo.put("choice_score", choiceScore);
			dbo.put("scoring_score", scoringScore);
			dbo.put("tenant_id", tenantId);
			dbo.put("emp_id", empId);
			dbo.put("emp_name", empName);
			dbo.put("tenant_name", tenantName);
			dbo.put("guid", rid);
			dbo.put("time_stamp", new Date());
		} else {
			BasicDBObject query = new BasicDBObject();
			query.put("guid", rid);
			mongoTemplate.getCollection("Answer-" + examGuid).remove(query);
		}

		mongoTemplate.getCollection("Answer-" + examGuid).insert(dbo);

		return rid;
	}

	/**
	 * 排名列表
	 * 
	 * @param examGuid
	 *            试题guid
	 * @return result
	 */
	public List<DBObject> getRank(String examGuid) {
		BasicDBObject sort = new BasicDBObject();
		sort.put("choice_score", -1);
		sort.put("time_stamp", 1);

		DBCursor curosr = mongoTemplate.getCollection("Answer-" + examGuid).find().sort(sort).limit(100);
		List<DBObject> data = new ArrayList<>();

		while (null != curosr && curosr.hasNext()) {
			data.add(curosr.next());
		}

		return data;
	}

	/**
	 * 我的排名
	 * 
	 * @param empId
	 *            员工id
	 * @param examGuid
	 *            试题guid
	 * @return 我的排名
	 */
	public Object getRankMine(int empId, String examGuid) {
		Map<String, Object> rtn = new HashMap<String, Object>();

		BasicDBObject q = new BasicDBObject();
		q.put("emp_id", empId);
		DBObject value = mongoTemplate.getCollection("Answer-" + examGuid).findOne(q);

		BasicDBObject choiceScore = new BasicDBObject();
		choiceScore.put("$gte", value.get("choice_score"));

		BasicDBObject timeStamp = new BasicDBObject();
		timeStamp.put("$lt", value.get("time_stamp"));

		BasicDBObject query = new BasicDBObject();
		query.put("choice_score", choiceScore);
		query.put("time_stamp", timeStamp);

		rtn.put("score", value.get("choice_score"));
		rtn.put("ranknum", mongoTemplate.getCollection("Answer-" + examGuid).find(query).count());

		return rtn;
	}
}
