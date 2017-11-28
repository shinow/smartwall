import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
	state: {
		user: '5EE331015D332A99E050840A06390D03',
		chapter: '',
		currNo: 0,
		currQ: {},
		length: 0,
		questions: [{}]
	},

	mutations: {
		SET_QUESTIONS(state, questions) {
			for(let item of questions) {
				item.select = 'M';
			}
			state.questions = questions;

			state.length = questions.length;
			if (state.length > 0) {
				state.currQ = questions[0];
			}

			console.log(questions);
		},
		RESET(state) {
			state.currNo = 0;
			state.currQ = state.questions[0];
		},
		NEXT(state) {
			state.currQ = state.questions[++state.currNo];
		},
		PREV(state) {
			state.currQ = state.questions[--state.currNo];
		},
		SET_CHAPTER(state, chapter) {
			state.chapter = chapter;
		}
	},
	actions: {
		setQuestions({
			commit
		}, questions) {
			commit('SET_QUESTIONS', questions);
		},
		reset({
			commit
		}) {
			commit('RESET');
		},
		next({
			commit
		}) {
			commit('NEXT');
		},
		prev({
			commit
		}) {
			commit('PREV');
		},

		setChapter({
			commit
		}, chapter) {
			commit('SET_CHAPTER', chapter);
		}
	},
	getters: {
		isLast: state => {
			return state.currNo == state.length - 1;
		},

		isFirst: state => {
			return state.currNo == 0;
		}
	}
});

export default store;