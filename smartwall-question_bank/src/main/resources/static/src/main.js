import Vue from 'vue';
import Vuex from 'vuex';
import App from './app.vue';
import VueRouter from 'vue-router';
import store from './store';
import {
	routers
} from './router';

Vue.use(VueRouter);
Vue.use(Vuex);

// 路由配置
const RouterConfig = {
	routes: routers
};

const router = new VueRouter(RouterConfig);
new Vue({
	el: '#app',
	router,
	store,
	render: h => h(App),
	data: {}
});