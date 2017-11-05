/**
 * 答题界面
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
        getAnswer(userInfo).then(function (answered){
            if(answered){
                cb(answered);
            } else {
                getExam(userInfo).then(function (result){
                    result.type = 'answer';
                    result.userInfo = userInfo;
                    cb(result)
                });
            }
        });
    }

    /*获取答题结果*/
    function getAnswer(userInfo){
        return new Promise(function (resolve, reject) {
            var url = "/exam_service/v1/answer";
            var params = {
                tenant_id: userInfo.tenantId,
                emp_id: userInfo.userId,
                exam_guid: GUID
            };
            T.get(url, params, function(result){
                result = result ? result : null;
                resolve(result);
            });
        });
    }

    /*获取试卷*/
    function getExam(userInfo){
        return new Promise(function (resolve, reject) {
            var url = "/exam_service/v1/paper";
            var params = {
                tenant_id: 100,
                guid: GUID
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
                type: data.type,
                userInfo: data.userInfo,
                questionShowIndex: 0,
                questionList: data.content || [],
                question: {},
            },
            methods: {
                selectedHandler: function (option, index){
                    if(this.question.type === 'multiple-choice'){
                        return this.question.option[index].selected;
                    } else {
                        return this.question.value == index + 1
                    }
                },
                /*上一题*/
                prevProblem: function (){
                    this.questionShowIndex--;
                },
                /*下一题*/
                nextProblem: function (){
                    /*计算多选题选择的答案*/
                    if (this.question.type === 'multiple-choice') {
                        this.question.value = this.question.option.filter(function (option){
                            return option.selected;
                        }).join(',');
                    }
                    if(this.type === 'answer' && this.question.required && (this.question.value + "").trim().length === 0){
                        T.mobiToast("请回先回答问题");
                        return;
                    }
                    this.questionShowIndex++;
                },
                /*提交答案*/
                submit: function (){
                    if(!this.question.value){
                        T.mobiToast("请回先回答问题");
                        return;
                    }
                    var url = "/exam_service/v1/answer";
                    var params = {
                        exam_guid: GUID,
                        choice_score: this.getScore().choice_score,
                        scoring_score: this.getScore().scoring_score,
                        tenant_id: this.userInfo.tenantId,
                        tenant_name: this.userInfo.tenantName,
                        emp_id: this.userInfo.userId,
                        emp_name: this.userInfo.userName,
                        data: JSON.stringify({content: this.questionList}),
                    };
                    T.post(url, params, function(result){
                        T.mobiAlert("提交成功", function (){
                            window.location.href='mobi_ranking.html' + (GUID === '' ? '' : "?guid=" + GUID);
                        });
                    });
                },
                /*计算得分*/
                getScore: function (){
                    var choice_score = 0,
                        scoring_score = 0;
                    this.questionList.map(function (question){
                        var value = question.value;
                        var type = question.type;
                        /*单选题*/
                        if(type === 'single-choice'){
                            question.option.map(function (option){
                                if(option.is_right && value === option.no){
                                    choice_score += question.record;
                                }
                            })
                        }
                        /*多选题*/
                        if(type === 'multiple-choice'){
                            var errorLength = question.option.filter(function (option){
                                return option.is_right != option.selected;
                            }).length;
                            if(errorLength === 0){
                                choice_score += question.record;
                            }
                        }
                        /*判断题*/
                        if(type === 'truefalse-question'){
                            if(value === question.answer){
                                choice_score += question.record;
                            }
                        }
                        /*问答题、段落说明 暂时不计分*/
                        /*评分提 暂时不计分*/
                        if(type === 'score-choice'){
                            scoring_score += value;
                        }
                    });
                    return {
                        choice_score: choice_score,
                        scoring_score: scoring_score
                    };
                },
                /*跳转排名页面*/
                showRanking: function (){
                    window.location.href='mobi_ranking.html' + (GUID === '' ? '' : "?guid=" + GUID);
                },
            },
            computed: {
                questionIndex: function (){
                    return this.question.index + '/' + this.questionList.length;
                }
            },
            watch: {
                questionShowIndex: {
                    handler: function (val, oldVal) {
                        this.question = this.questionList[val];
                    },
                    deep: true
                },
            }
        });

        view.$nextTick(function (){
            view._data.isShow = true;
            view._data.question = view._data.questionList.length == 0 ? [] : view._data.questionList[0];
            mui.init();
            mui.back = function (){
                goback();
            }
        })
    }

    return Yuffie;
})();
