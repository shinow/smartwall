import Category from './views/report/Category.vue';
import Reports from './views/report/Reports.vue';

export const categoryRouter = {
	path: '/category',
	name: 'category',
	meta: {
		title: '报表分类'
	},
	component: Category
};

export const defaultRouter = {
	path: '/',
	name: 'category',
	meta: {
		title: '报表分类'
	},
	component: Category
};

export const reportsRouter = {
	path: '/Reports',
	name: 'reports',
	meta: {
		title: '报表详细'
	},
	component: Reports
};

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
	defaultRouter,
	categoryRouter,
	reportsRouter
];