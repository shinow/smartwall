import {
    fetch
} from './post_fetch';

export default {
    /**
     * 获取所有Category
     */
    loadAllCategory: function() {
        return fetch('/v1/list/all_catagory');
    },
    /**
     * 获取分类
     */
    loadSubjects(categoryGuid) {
        return fetch('/v1/list/subject', {
            'category_guid': categoryGuid
        });
    },

    /**
     * 获取科目
     */
    loadChapters(subjectGuid) {
        return fetch('/v1/list/chapter', {
            'subject_guid': subjectGuid
        });
    },

    loadQuestions(chapterGuid) {
        return fetch("v1/question/chapter/get", {
            'chapter_guid': chapterGuid
        });
    },
    saveUserCategory(userGuid, categoryGuid) {
        return fetch("v1/user/category/set", {
            'user_guid': userGuid,
            'category_guid': categoryGuid
        });
    },
    getUserCategory(userGuid) {
        return fetch("v1/user/category/get", {
            'user_guid': userGuid
        });
    },
    getUserCategoryObject(userGuid) {
        return fetch("v1/user/categoryObject/get", {
            'user_guid': userGuid
        });
    }
};