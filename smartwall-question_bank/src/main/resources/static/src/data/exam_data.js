import {
    fetch
} from './post_fetch';

export default {
    /**
     * 获取所有Category
     */
    loadAllCategory() {
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

        loadQeustions: function(chapterGuid) {
            return fetch("v1/question/chapter/get", {
                'chapter_guid': chapterGuid
            });
        }
};