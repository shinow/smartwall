import {
	fetch
} from './fetch';

export default {
	/**
	 * 更新数据状态
	 */
	updateSelected: function(data, selected) {
		for (let item of data) {
			item.select = selected;
		}

		return data;
	},
	/**
	 * 获取分类
	 */
	getKinds() {
		return fetch('/v1/list/kind', {});
	},

	getCategoris(kindGuid) {
		return fetch('/v1/list/catagory', {
			'kind_guid': kindGuid
		});
	},

	getSubjects(categoryGuid) {
		return fetch('/v1/list/subject', {
			'category_guid': categoryGuid
		});
	},

	getChapters(subjectGuid) {
		return fetch('/v1/list/chapter', {
			'subject_guid': subjectGuid
		});
	},

	saveKind(name) {
		return fetch('/v1/add/kind', {
			'name': name
		});
	},

	saveCategory(name, kindGuid) {
		return fetch('/v1/add/category', {
			'name': name,
			'kind_guid': kindGuid
		});
	},

	saveSubject(name, categoryGuid) {
		return fetch('/v1/add/subject', {
			'name': name,
			'category_guid': categoryGuid
		});
	},

	saveChapter(name, subjectGuid) {
		return fetch('/v1/add/chapter', {
			'name': name,
			'subject_guid': subjectGuid
		});
	},

	delKind(guid) {
		return fetch('/v1/del/kind', {
			'guid': guid
		});
	},

	delCategory(guid) {
		return fetch('/v1/del/category', {
			'guid': guid
		});
	},

	delSubject(guid) {
		return fetch('/v1/del/subject', {
			'guid': guid
		});
	},

	delChapter(guid) {
		return fetch('/v1/del/chapter', {
			'guid': guid
		});
	},

	getChapterQuestions(categoryGuid, subjectGuid, chapterGuid) {
		return fetch('/v1/question/chapter/get', {
			'category_guid': categoryGuid,
			'subject_guid': subjectGuid,
			'chapter_guid': chapterGuid
		});
	},

	saveChapterQuestions(index, guid, categoryGuid, subjectGuid, chapterGuid, data) {
		return fetch('/v1/question/chapter/save', {
			'index': index,
			'guid': guid,
			'category_guid': categoryGuid,
			'subject_guid': subjectGuid,
			'chapter_guid': chapterGuid,
			'data': data
		});
	},
	getSubjectQuestions(subjectGuid) {
		return fetch('/v1/question/subject/get', {
			'subject_guid': subjectGuid
		});
	},

	saveSubjectQuestions(subjectGuid, data) {
		return fetch('/v1/question/subject/save', {
			'subject_guid': subjectGuid,
			'data': data
		});
	}
};