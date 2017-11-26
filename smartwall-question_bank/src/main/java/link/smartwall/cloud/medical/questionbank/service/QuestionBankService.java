package link.smartwall.cloud.medical.questionbank.service;

import java.util.Date;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.Exps;
import org.springframework.beans.factory.annotation.Autowired;
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
import link.smartwall.cloud.medical.questionbank.domain.UserCategory;

@Service
public class QuestionBankService {
	@Autowired
	private ItekDao dao;

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

	public void saveChapterQuestion(String chapterGuid, String data) {
		ChapterQuestion cq = dao.fetch(ChapterQuestion.class, Cnd.where(Exps.eq("chapterGuid", chapterGuid)));

		if (cq == null) {
			cq = new ChapterQuestion();
			cq.setChapterGuid(chapterGuid);
			cq.setData(data);
			cq.setModifyTime(new Date());

			dao.insert(cq);
		} else {
			cq.setData(data);
			cq.setModifyTime(new Date());

			dao.update(cq);
		}
	}

	public JSONArray getChapterQuestion(String chapterGuid) {
		ChapterQuestion cq = dao.fetch(ChapterQuestion.class, Cnd.where(Exps.eq("chapterGuid", chapterGuid)));

		if (cq != null) {
			return JSON.parseArray(cq.getData());
		}

		return null;
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
}
