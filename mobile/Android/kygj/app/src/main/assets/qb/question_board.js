new Vue({
	el: '#app',
	data: {
		length: 0,
	},
	computed: {
		title: function() {
			return this.chapterName;
		}
	},
	methods: {
		doQuestion: function(index) {
			__Native__.startDoQuestion(this.subjectName, this.chapterGuid, this.chapterName);
		}
	},
	created: function() {
		this.chapterGuid = getUrlKey('chapterGuid');
		this.chapterName = getUrlKey('chapterName');
		this.subjectName = getUrlKey('subjectName');
		this.length = __Native__.getChapterQuestionLength(this.chapterGuid);
	}
});

function getUrlKey(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
};