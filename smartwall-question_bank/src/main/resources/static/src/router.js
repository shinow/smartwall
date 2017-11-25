import Chapter from './views/Chapter.vue';
import Exam from './views/Exam.vue';
import First from './views/First.vue';
import Second from './views/Second.vue';

export const chapterRouter = {
	path: '/Chapter',
	name: 'Chapter',
	meta: {
		title: '章节'
	},
	component: Chapter
};

export const examRouter = {
	path: '/Exam',
	meta: {
		title: '考试'
	},
	component: Exam,
	children: [{
		path: '/',
		component: First
	},{
		path: 'first',
		component: First
	}, {
		path: 'second',
		component: Second
	}],
};

export const routers = [
	chapterRouter,
	examRouter
];