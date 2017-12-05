new Vue({
	el: '#app',
	data: {
		question: null
	},
	computed: {
		title: function() {
			return this.chapterName;
		}
	},
	methods: {
		select: function(answer){
			this.question.select = answer;
		},

		submit: function() {
			alert('submit');
		}
	},
	created: function() {
		this.no = parseInt(getUrlKey('no'));
		var data = JSON.parse(__Native__.getDoQuestion(this.no));
		data.select = data.select || '';

		this.question = data;	
	}
});

function getUrlKey(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
};

