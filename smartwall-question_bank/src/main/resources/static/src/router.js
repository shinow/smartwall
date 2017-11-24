import Chapter from './views/Chapter.vue';
import Exam from './views/Exam.vue';

export const chapterRouter = {
	path: '/Chapter',
	name: 'Chapter',
	meta: {
		title: '章节'
	},
	component: Chapter
};

export const examRouter = {
	path: '/Exam/:chapter',
	name: 'Exam',
	meta: {
		title: '考试'
	},
	component: Exam,
	children: [{
		path: '/',
		component: 
	}, {
		path: 'info',
		component: userInfo
	}, {
		path: 'love',
		component: userLove
	}],
};

export const routers = [
	chapterRouter,
	examRouter
];