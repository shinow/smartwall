new Vue({
	el: '#app',
	data: {
		question: null
	},
	computed: {
		isRight: function() {
			return this.question.status == 1;
		},
		isError: function() {
			return this.question.status == 2;
		},
		isNotDo: function() {
			return this.question.status == 0;
		}
	},
	methods: {
		select: function(answer) {
			this.question.select = answer;
		},

		submit: function() {
			if (!this.question.select) {
				alert("请选择答案");
				return;
			}

			let result = (this.question.select === this.question.answer) ? 1 : 2;

			__Native__.saveDoQuestion(this.question.guid, this.chapterGuid, this.question.select, result);
			window.location.reload();
		}
	},
	created: function() {
		this.no = parseInt(getUrlKey('no'));
		this.chapterGuid = getUrlKey('chapter_guid');
		var data = JSON.parse(__Native__.getDoQuestion(this.no));
		data.select = data.select || '';

		this.question = data;
	}
});

function getUrlKey(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
};