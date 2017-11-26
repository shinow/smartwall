import Chapter from './views/Chapter.vue';
import Exam from './views/Exam.vue';
import Question from './views/Question.vue';
import Options from './views/Option/Options.vue';
import SelectExam from './views/Option/SelectExam.vue';

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

export const optionsRouter = {
	path: '/Options',
	component: Options
};

export const selectExamRouter = {
	path: '/SelectExam',
	component: SelectExam
};

export const routers = [
	chapterRouter,
	examRouter,
	optionsRouter,
	selectExamRouter
];