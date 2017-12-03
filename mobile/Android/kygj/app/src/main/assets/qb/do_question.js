var s = '{"text":"蛋白质生理价值大小主要取决于（ ）","opt_D":"必需氨基酸种类","opt_E":"必需氨基酸数量、种类及比例","answer":"C","opt_A":"氨基酸种类 ","opt_B":"氨基酸数量","opt_C":"必需氨基酸数量","type":"A1","analysis":""}';
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
			console.log(answer);
			this.question.select = answer;
		},

		submit: function() {
			alert('submit');
		}
	},
	created: function() {
		//this.no = parseInt(getUrlKey('no'));
		this.no = 0;
		//var data = JSON.parse(__Native__.getDoQuestion(this.no));
		var data = JSON.parse(s);
		data.select = data.select || '';
		this.question = data;	
	}
});

function getUrlKey(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
};

