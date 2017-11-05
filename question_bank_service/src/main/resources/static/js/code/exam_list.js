/**
 * 试卷列表
 * Created by Yuffie on 2017/10/12.
 */

var Y = (function () {
    var TENANT_ID = 100;
    var Yuffie = {};

    Yuffie.init = function () {
        getData(function (result){
            initVue(result);
        });
    };

    function getData(cb){
        var url = "/exam_service/v1/paper_list";
        var params = {
            "tenant_id": TENANT_ID
        };
        T.get(url, params, function(result){
            console.log(result);
            result = result ? result : [];
            cb(result);
        });
    }

    function initVue(data){
        var view = new Vue({
            el: "#app",
            data: {
                isShow: false,
                examList: data || [],
            },
            methods: {
                createExam: function (){

                },
                urlEditHandler: function (item){
                    return "examination.html" + (typeof item == 'undefined' ? '' : "?guid=" + item.guid);
                },
                urlAnswerHandler: function (item){
                    return "mobi_answer.html" + (typeof item == 'undefined' ? '' : "?guid=" + item.guid);
                },
            },
            computed: {

            },
            watch: {

            }
        });

        view.$nextTick(function (){
            view._data.isShow = true;
        })
    }

    return Yuffie;
})();
