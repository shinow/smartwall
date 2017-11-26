import Chapter from './views/Chapter.vue';
import Exam from './views/Exam.vue';
import Exama from './views/Exama.vue';
import Question from './views/Question.vue';
import Options from './views/Option/Options.vue';
import SelectExam from './views/Option/SelectExam.vue';
import AnswerSheet from './views/Exam/AnswerSheet.vue';
import Result from './views/Exam/Result.vue';
import QuestionWithAnalysis from './views/Exam/QuestionWithAnalysis.vue';

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

export const examaRouter = {
	path: '/Exama',
	meta: {
		title: '考试'
	},
	component: Exama,
	children: [{
		path: 'q',
		component: QuestionWithAnalysis
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

export const answerSheetRouter = {
	path: '/AnswerSheet',
	component: AnswerSheet
};

export const resultRouter = {
	path: '/Result',
	component: Result
};


export const routers = [
	chapterRouter,
	examRouter,
	examaRouter,
	optionsRouter,
	selectExamRouter,
	answerSheetRouter,
	resultRouter
];