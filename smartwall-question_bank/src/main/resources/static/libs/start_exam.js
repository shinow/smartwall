axios.defaults.timeout = 5000;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.baseURL = '/question-bank';

axios.interceptors.request.use((config) => {
	if (config.method === 'post') {
		var c = config.data;

		if (c) {
			var data = [];
			for (var i in c) {
				data.push(i + '=' + c[i]);
			}

			config.data = data.join('&');
		}
	}
	return config;
}, function(error) {
	alert(error);
});

new Vue({
	el: '#app',
	data: {
		questions: null,
		current: {},
		no: 0;
	},
	compute: {
		count: function() {
			return this.questions.length;
		}
	},

	created: function() {
		this.loadQeustions('5DCA16610870507BE050840A06394546');
	},
	methods: {
		loadQeustions: function(chapterGuid) {
			var that = this;
			axios.post("v1/question/chapter/get", {
					'chapter_guid': chapterGuid
				})
				.then(function(req, err) {
					that.questions = req.data;

					that.no = 0;
					that.current = that.questions[0];
				})
				.catch(function(error) {
					console.log(error);
				});
		},
		select: function(answer) {
			this.current.select = answer;
		},
		next: function() {
			if (this.no == this.count() - 1) {
				alert('已经最后');
			} else {
				this.no++;
				this.current = this.questions[this.no];
			}
		},
		prev: function() {
			if (this.no == 0) {
				alert('已经最前');
			} else {
				this.no--;
				this.current = this.questions[this.no];
			}
		}
	}
});