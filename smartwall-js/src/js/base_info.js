/**
 * 题库基本信息
 */
import Vue from 'vue';
import Base from '../views/base_info/Base'
require('font-awesome-webpack');

//定义全局对象用于组件间交互
// window.bus = new Vue();

new Vue({
	el: "#app",
	render: h => h(Base)
});