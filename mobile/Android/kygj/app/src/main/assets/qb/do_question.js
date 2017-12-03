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
	},
	created: function() {
		this.no = getUrlKey('no');
		this.question = __Native__.getDoQuestion(parseInt(this.no));
	}
});

function getUrlKey(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
};