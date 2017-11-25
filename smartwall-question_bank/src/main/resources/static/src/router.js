import Chapter from './views/Chapter.vue';
import Exam from './views/Exam.vue';
import Question from './views/Question.vue';

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
		path: 'q',
		component: Question
	}],
};

export const routers = [
	chapterRouter,
	examRouter
];