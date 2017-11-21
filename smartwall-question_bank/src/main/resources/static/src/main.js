import Vue from 'vue';
import App from './app.vue';
import VueRouter from 'vue-router';
import {
	routers
} from './router';

Vue.use(VueRouter);

// 路由配置
const RouterConfig = {
	routes: routers
};

const router = new VueRouter(RouterConfig);
	
new Vue({
	el: '#app',
	router: router,
	render: h => h(App),
	data: {}
});