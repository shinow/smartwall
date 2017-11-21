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

function getUrlKey(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
};

function addProperty(arr, prop, value) {
	for (i = 0, len = arr.length; i < len; i++) {
		arr[i][prop] = value;
	}
};

Vue.component('Chapters', {
	props: ['chapter'],
	template: '<div class="chapter" @click="selectChapter(chapter.guid)">{{chapter.name}}</div>',
	methods: {
		selectChapter: function(chapterGuid){
			alert(chapterGuid);
		}
	}
});

new Vue({
	el: '#app',
	data: {
		category: '医疗执业医生',
		subjects: null,
		chapters: null
	},
	created: function() {
		this.loadSubjects('5DCA16610870507BE050840A06394546');
	},
	methods: {
		loadSubjects: function(categoryGuid) {
			var that = this;
			axios.post("v1/list/subject", {
					'category_guid': categoryGuid
				})
				.then(function(req, err) {
					that.subjects = req.data;
					addProperty(that.subjects, 'active', false);
					that.selectSubject(that.subjects[0]);
				})
				.catch(function(error) {
					console.log(error);
				});
		},
		loadChapters: function(subjectGuid) {
			var that = this;
			axios.post("v1/list/chapter", {
					'subject_guid': subjectGuid
				})
				.then(function(req, err) {
					that.chapters = req.data;
				})
				.catch(function(error) {
					console.log(error);
				});
		},
		clearActive: function() {
			for (i = 0, len = this.subjects.length; i < len; i++) {
				this.subjects[i].active = false;
			}
		},
		selectSubject: function(subject) {
			this.clearActive();

			subject.active = true;
			this.loadChapters(subject.guid);
		}
	}
});