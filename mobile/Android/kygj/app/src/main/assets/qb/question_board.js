new Vue({
	el: '#app',
	data: {
		length: 0
	},
	computed: {
		title: function() {
			return getUrlKey('chapterName');
		}
	},
	methods: {
		doQuestion: function(index) {
			alert(index);
		}
	},
	created: function() {
		this.length = __Native__.getChapterQuestionLength(getUrlKey('chapterGuid'));
	}
});

function getUrlKey(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
};