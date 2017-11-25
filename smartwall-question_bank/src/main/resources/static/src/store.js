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
		setQuestions(state, questions) {
			state.questions = questions;

			state.length = questions.length;
			if (state.length > 0) {
				state.currQ = questions[0];
			}
		},
		reset(state) {
			state.currNo = 0;
			state.currQ = state.questions[0];
		},
		next(state) {
			state.currQ = state.questions[++state.currNo];
		},
		prev(state) {
			state.currQ = state.questions[--state.currNo];
		}
	},

	getters: {
		isLast: state => {
			return state.currNo == state.length - 1;
		},

		isFirst: state => {
			return state.currNo == 0;
		}
	},
	actions: {},

});

console.log("init store");
export default store;