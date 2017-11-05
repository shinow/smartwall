/**
 * 答题排行榜
 * Created by Yuffie on 2017/10/12.
 */
var Y = (function () {
    var GUID = T.getQueryString("guid") || "";
    var Yuffie = {};

    Yuffie.init = function () {
        getData(function (result){
            initVue(result);
        });
    };

    function getData(cb){
        var userInfo = T.getEmpInfo();
        Promise.all([getNationalRankings(), getMyRankings(userInfo.userId)]).then(function (result) {
            var data = {
                userInfo: userInfo,
                rankingList: result[0],
                myRanking: result[1],
            };
            cb(data);
        }).catch(function(reason){});
    }

    /*获取全国排名*/
    function getNationalRankings(){
        return new Promise(function (resolve, reject) {
            var url = "/exam_service/v1/rank";
            var params = {
                exam_guid: GUID
            };
            T.get(url, params, function(result){
                result = result ? result : [];
                resolve(result);
            });
        });
    }

    /*获取我的排名*/
    function getMyRankings(emp_id){
        return new Promise(function (resolve, reject) {
            var url = "/exam_service/v1/rank/mine";
            var params = {
                emp_id: emp_id,
                exam_guid: GUID
            };
            T.get(url, params, function(result){
                result = result ? result : [];
                resolve(result);
            });
        });
    }

    function initVue(data){
        var view = new Vue({
            el: "#app",
            data: {
                isShow: false,
                userInfo: data.userInfo,
                rankingList: data.rankingList,
                myRanking: data.myRanking,
            },
            methods: {
                rankingHandler: function (index){
                    return index < 3 ? "" : index + 1;
                },
                back: function (){
                    goback();
                },
                backtopx: function (){
                    mobile.gotoPower();
                }
            },
            computed: {
                score: function (){
                    return '本次测试得分:' + this.myRanking.score || 0 + '分';
                },
                avatarHandler: function (){
                    return "background-image:url(" + this.userInfo.avatar +")";
                },
            },
            watch: {

            }
        });

        view.$nextTick(function (){
            view._data.isShow = true;
            mui.init();
            mui('.mui-scroll-wrapper').scroll({
                deceleration: 0.0005
            });
        })
    }

    return Yuffie;
})();
