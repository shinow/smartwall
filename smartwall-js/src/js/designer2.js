/**
 * 题库设计
 */
import Vue from 'vue';
import Designer from '../views/question_bank/Designer2'
require('font-awesome-webpack');

new Vue({
	el: "#app",
	render: h => h(Designer)
});