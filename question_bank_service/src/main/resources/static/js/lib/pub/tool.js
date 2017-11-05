/**
 * 常用工具
 * Created by Yuffie on 2017/10/12.
 */


var T = (function() {
    var Tool = {};

    /**
     * 日期格式化 根据formatStr生成返回的日期格式
     * @param date
     * @param formatStr 例如：YYYY-MM-DD hh:mm
     * 格式 YYYY/yyyy/YY/yy 表示年份     * MM/M 月份     * W/w 星     * dd/DD/d/D 日期
     * hh/HH/h/H 时间     * mm/m 分钟     * ss/SS/s/S 秒
     * @returns {String}
     */
    Tool.dateFormat = function(date, formatStr) {
        var str = formatStr;
        var Week = ['日', '一', '二', '三', '四', '五', '六'];

        str = str.replace(/yyyy|YYYY/, date.getFullYear());
        str = str.replace(/yy|YY/, (date.getYear() % 100) > 9 ? (date.getYear() % 100).toString() : '0' + (date.getYear() % 100));

        str = str.replace(/MM/, (date.getMonth() - (-1)) > 9 ? (date.getMonth() - (-1)).toString() : '0' + (date.getMonth() - (-1)));
        str = str.replace(/M/g, date.getMonth() - (-1));

        str = str.replace(/w|W/g, Week[date.getDay()]);

        str = str.replace(/dd|DD/, date.getDate() > 9 ? date.getDate().toString() : '0' + date.getDate());
        str = str.replace(/d|D/g, date.getDate());

        str = str.replace(/hh|HH/, date.getHours() > 9 ? date.getHours().toString() : '0' + date.getHours());
        str = str.replace(/h|H/g, date.getHours());
        str = str.replace(/mm/, date.getMinutes() > 9 ? date.getMinutes().toString() : '0' + date.getMinutes());
        str = str.replace(/m/g, date.getMinutes());

        str = str.replace(/ss|SS/, date.getSeconds() > 9 ? date.getSeconds().toString() : '0' + date.getSeconds());
        str = str.replace(/s|S/g, date.getSeconds());

        return str;
    };

    /**
     * 获取所有url参数
     * @return {Object}
     */
    Tool.getRequest = function() {
        var url = location.search;
        var theRequest = {};
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            str = str.split("&");
            for (var i = 0; i < str.length; i++) {
                theRequest[str[i].split("=")[0]] = decodeURI(str[i].split("=")[1]);
            }
        }
        return theRequest;
    };

    /**
     * 获取指定url参数
     * @param name 需要获取的参数名
     * @return {String}
     */
    Tool.getQueryString = function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null;
    };

    /**
     * 拼接url参数
     * @return {String}
     */
    Tool.urlEncode = function(path, param) {
        var i, url = '';
        for (i in param) url += '&' + i + '=' + param[i];
        return path + url.replace(/./, '?');
    };


    /**
     * 遮罩层 需加载图片样式css
     */
    Tool.loading = function (){
        var body = document.querySelector('body');
        var mask = document.createElement("div");
        mask.className = "y-mask";

        var div = document.createElement("div");
        div.style.position = "fixed";
        div.style.zIndex = "999";
        div.style.top = "0";
        div.style.right = "0";
        div.style.bottom = "0";
        div.style.left = "0";
        div.style.width = "80px";
        div.style.height = "80px";
        div.style.borderRadius = "10px";
        div.style.margin = "auto";
        div.style.backgroundColor = "rgba(255,255,255,.3)";

        var img = document.createElement("div");
        img.className = "y-loading";
        img.style.position = "fixed";
        img.style.zIndex = "1000";
        img.style.top = "0";
        img.style.right = "0";
        img.style.bottom = "0";
        img.style.left = "0";
        img.style.width = "60px";
        img.style.height = "60px";
        img.style.margin = "auto";

        div.appendChild(img);
        mask.appendChild(div);
        body.appendChild(mask);
    };
    Tool.loadDone = function (){
        var mask = document.querySelector('.y-mask');
        mask.parentNode.removeChild(mask);
    };

    /**
     * T.extend( [deep ], target, object1 [, objectN ] ) 函数用于将一个或多个对象的内容合并到目标对象。
     * @param deep 	可选。 Boolean类型 指示是否深度合并对象，默认为false。如果该值为true，且多个对象的某个同名属性也都是对象，则该"属性对象"的属性也将进行合并。
     * @param target Object类型 目标对象，其他对象的成员属性将被附加到该对象上。
     * @param object1 可选。 Object类型 第一个被合并的对象。
     * @param objectN 可选。 Object类型 第N个被合并的对象。
     */
    Tool.extend =  function() {
        var copyIsArray,
            toString = Object.prototype.toString,
            hasOwn = Object.prototype.hasOwnProperty,

            class2type = {
                '[object Boolean]' : 'boolean',
                '[object Number]' : 'number',
                '[object String]' : 'string',
                '[object Function]' : 'function',
                '[object Array]' : 'array',
                '[object Date]' : 'date',
                '[object RegExp]' : 'regExp',
                '[object Object]' : 'object'
            },

            type = function(obj) {
                return obj == null ? String(obj) : class2type[toString.call(obj)] || "object";
            },

            isWindow = function(obj) {
                return obj && typeof obj === "object" && "setInterval" in obj;
            },

            isArray = Array.isArray || function(obj) {
                    return type(obj) === "array";
                },

            isPlainObject = function(obj) {
                if (!obj || type(obj) !== "object" || obj.nodeType || isWindow(obj)) {
                    return false;
                }

                if (obj.constructor && !hasOwn.call(obj, "constructor")
                    && !hasOwn.call(obj.constructor.prototype, "isPrototypeOf")) {
                    return false;
                }

                var key;
                for (key in obj) {
                }

                return key === undefined || hasOwn.call(obj, key);
            },

            extend = function(deep, target, options) {
                for (var name in options) {
                    var src = target[name];
                    var copy = options[name];
                    var clone;
                    if (target === copy) { continue; }

                    if (deep && copy
                        && (isPlainObject(copy) || (copyIsArray = isArray(copy)))) {
                        if (copyIsArray) {
                            copyIsArray = false;
                            clone = src && isArray(src) ? src : [];

                        } else {
                            clone = src && isPlainObject(src) ? src : {};
                        }

                        target[name] = extend(deep, clone, copy);
                    } else if (copy !== undefined) {
                        target[name] = copy;
                    }
                }

                return target;
            };

        return extend;
    }();


    /*mui中方法封装 依赖mui*/
    /**
     * T.toast( str, obj ) 函数用于将一个或多个对象的内容合并到目标对象。
     * 使用时需要引用mui
     * @param str String 必填，提示语
     * @param obj Object类型 可选 mui.toast方法的配置属性。
     */
    Tool.toast = function (str, obj){
        alert(str);
    };

    Tool.alert = function (message, callback, title, btnValue){
        vm.$refs.alert.title = title;
        vm.$refs.alert.value = message;
        if(callback){
            vm.$refs.alert.cb = callback;
        }
    };

    Tool.confirm = function (message, callback, title, btnValue){
        vm.$refs.confirm.value = message;
        vm.$refs.confirm.cb = callback;
        vm.$refs.confirm.title = title;
    };

    Tool.mobiToast = function (str, obj){
        mui.toast(str, obj || { duration:'long', type:'div' })
    };

    Tool.mobiAlert = function (message, callback, title, btnValue){
        mui.alert(message, title || '提示', btnValue || '确定', callback || function (){})
    };

    Tool.uuid = function (){
        function S4() {
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        }
        return (S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4()).toUpperCase();
    };

    /*手机端接口封装 若调用手机端接口建议封装，以免接口大改的情况出现*/

    /**
     * T.get( params, cbSucc, cbErr ) 带遮罩的get请求方法 T.post相同
     * @param params.url 请求地址
     * @param params.code 请求的code
     * @param params.data 请求提交的参数
     * @param cbSucc 成功回调 !需要做非空判断!
     * @param cbErr 失败回调 若不填写cbErr方法 则使用默认方法
     * @param option 配置参数
     */
    var _delay = 45;
    Tool.get = function (url, params, cbSucc, cbErr){
        T.loading();
        var timer = setTimeout(function (){
            T.loadDone();
            cbSucc();
            //T.alert('连接超时，请检查网络链接');
        }, _delay * 1000);
        $.get(url, params, function (result){
            T.loadDone();
            clearTimeout(timer);
            /*成功回调*/
            if(result.code === 0 || result.code === '0'){
                cbSucc(result.message);
                return;
            }
            /*失败处理*/
            if(cbErr){
                cbErr(result);
                return;
            }
            if(result){
                T.toast(result.message || result.result || '错误信息未返回，请联系后台');
            }
        });
    };

    Tool.post = function (url, params, cbSucc, cbErr){
        T.loading();
        var timer = setTimeout(function (){
            T.loadDone();
            cbSucc();
            //T.alert('连接超时，请检查网络链接');
        }, _delay * 1000);
        $.post(url, params, function (result){
            console.log(result);
            T.loadDone();
            clearTimeout(timer);
            /*成功回调*/
            if(result.code === 0 || result.code === '0'){
                cbSucc(result.message);
                return;
            }
            /*失败处理*/
            if(cbErr){
                cbErr(result);
                return;
            }
            if(result){
                T.toast(result.message || result.result || '错误信息未返回，请联系后台');
            }
        });
    };

    Tool.getEmpInfo = function (){
        var userInfo = mobile.getEmpInfo();
        if(typeof userInfo == 'string'){
        	userInfo = JSON.parse(userInfo);
        }
        if(userInfo.avatar === ""){
            userInfo.avatar = "css/img/icon_user.png"
        }
        return userInfo;
    };

    return Tool;
})();

/*var mobile = {};
mobile.getEmpInfo = function (){
    return {
        tenantId: 100,
        tenantName: "得意",
        userId: "123",
        userName: "Yuffie",
        deptId: "456",
        deptName: "部门名称",
        avatar: "",
    }
};*/
