import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
	state: {
		currNo: 0,
		currQ: {},
		questions: []
	},
	mutations: {
		setQuestions(state, questions){
			state.questions = questions;

			if(questions.length) {
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
	actions: {}
});

export default store;