import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
	state: {
		currNo: 0,
		currQ: {},
		length: 0,
		questions: []
	},

	mutations: {
		SET_QUESTIONS(state, questions) {
			state.questions = questions;

			state.length = questions.length;
			if (state.length > 0) {
				state.currQ = questions[0];
			}
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