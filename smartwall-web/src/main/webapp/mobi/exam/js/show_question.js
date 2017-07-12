$(function() {
    "use strict";

    function getUrlVars() {
        var vars = [],
            hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for (var i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    };

    function getUrlVar(name) {
        return getUrlVars()[name];
    };

    function init() {
        var vars = getUrlVars();

        $("#title").text(decodeURIComponent(vars["caption"]));
        $(".itekui-action-back").tap(function() {
            history.back();
        });

        loadQuestions(vars["type"], vars["guid"]);
        $(".question-nav #prev").click(function(event) {
            event.stopPropagation();

            if (Q.ptr > 1) {
                Q.ptr--;
                Q.showItem();
            }
        });
        $(".question-nav #next").click(function(event) {
            event.stopPropagation();

            Q.ptr++;
            if (Q.hasQuestion()) {
                Q.showItem();
            } else {
                alert("已经是最后一题");
                Q.ptr--;
            }
        });

        $("#submit-page").click(function() {
            $(".page").hide();
            $("#page-answer-sheet").show();
            Q.showResult();
        });


        $("#discard").click(function() {
            $(".page").hide();
            $("#page-question").show();
        });

        $("#submit").click(function() {
            $(".page").hide();
            $("#page-result").show();

            Q.showSubmit();
        });

        $("#show-analysis").click(function() {
            $(".page").hide();
            $("#submit-page").hide();
            $("#analysis").hide();
            $("#page-question").show();
            Q.analysis();
        });
    };

    function loadQuestions(type, guid) {
        $.post("/sfa/mobi/exam/get.mvc", {
            type: type,
            guid: guid
        }, function(result) {
            var message = result.message;
            Q = new Questions(JSON.parse(message));
        }, "json");
    };

    init();

    var EA = {
        1: "A",
        2: "B",
        3: "C",
        4: "D",
        5: "E"
    };

    function getEA(index) {

    };

    var Q;
    var Questions = function(template) {
        /*试卷*/
        this.template = template;
        this.R = this.getR();
        this.showAnalysis = false;
        /*答案*/
        this.answers = {};
        this.ptr = 1;
        this.sptr = "";
        this.showItem();
    };

    /*计算标准答案*/
    Questions.prototype.getR = function() {
        var R = {};
        for (var ptr = 1;; ptr++) {
            var sptr = "Q" + ptr;
            var data = this.template[sptr];

            if (data == undefined) {
                break;
            }

            if (data.type == 'SC' || data.type == 'MT') {
                var ro = [];
                $.each(data.opts, function(k, v) {
                    if (v.right) {
                        ro.push(k);
                    }
                });
                R[sptr] = ro.join(",");
            }
        }

        return R;
    };

    Questions.prototype.showPg = function(data) {
        var p = $("#question-pg-title");
        var c = $("#question-area");
        if (data.show_pg) {
            c.css({
                top: "50%"
            });

            p.empty();
            for (var i = this.ptr - 1; i > 0; i--) {
                var item = this.template['Q' + i];
                if (item.type == 'PG') {
                    p.html(item.title);
                    break;
                }
            }

            p.show();
        } else {
            c.css({
                top: "45px"
            });

            p.hide();
        }
    };

    Questions.prototype.showItem = function(show) {
        var that = this;

        this.sptr = "Q" + this.ptr;
        var data = this.template[this.sptr];
        var answer = this.answers[this.sptr];
        var c = $("#question-area").empty();

        var html = "";
        switch (data["type"]) {
            case "SC":
                this.showPg(data);

                html += '<div><div class="title">' + data.qno + "." + data["title"] + '</div>';
                $.each(data.opts, function(index) {
                    html += '<div class="options"><span class="radio">' + EA[index] + '</span><span>' + this.text + '</span></div>';
                });

                if (this.showAnalysis) {
                    html += '<hr/><div>解析</div>'
                    html += '<div>正确答案</div>';
                    html += '<div>' + this.R[this.sptr] + '</div>';
                    html += '<div>答案解析</div>';
                    html += '<div>' + data.analysis + '</div>';
                }

                html += '</div>';
                html = $(html);
                html.find('.options').tap(function(event) {
                    event.stopPropagation();

                    $(this).addClass("selected").siblings().removeClass("selected");
                    that.answers[that.sptr] = $(this).index();
                });

                if (answer) {
                    html.find('.options').eq(answer - 1).addClass("selected");
                }

                c.append(html);

                break;

            case "MT":
                this.showPg(data);

                html += '<div><div class="title">' + data.qno + "." + data["title"] + '</div>';
                $.each(data.opts, function(index) {
                    html += '<div class="options"><span class="checkbox">' + EA[index] + '</span><span>' + this.text + '</span></div>';
                });

                if (this.showAnalysis) {
                    html += '<hr/><div>解析</div>'
                    html += '<div>正确答案</div>';
                    html += '<div>' + this.R[this.sptr] + '</div>';
                    html += '<div>答案解析</div>';
                    html += '<div>' + data.analysis + '</div>';
                }
                html += '</div>';
                html = $(html);
                html.find(".options").tap(function(event) {
                    event.stopPropagation();

                    var this$ = $(this);
                    if (this$.hasClass("selected")) {
                        this$.removeClass("selected");
                    } else {
                        this$.addClass("selected");
                    }

                    var ans = [];
                    $(".options", this$.parent()).each(function(index) {
                        if ($(this).hasClass("selected")) {
                            ans.push(index + 1);
                        }
                    });

                    if (ans.length > 0) {
                        that.answers[that.sptr] = ans;
                    } else {
                        that.answers[that.sptr] = null;
                    }
                });

                if (answer) {
                    $.each(answer, function() {
                        html.find('.options').eq(this - 1).addClass("selected");
                    });
                }

                c.append(html);

                break;

            case "AS":
                this.showPg(data);

                html += '<div class="title">' + data.qno + "." + data["title"] + '</div>';
                html += '<div class="options"><textarea/></div>';
                html = $(html);
                html.find("textarea").blur(function() {
                    that.answers[that.sptr] = $(this).val();
                });

                c.append(html);
                break;

            case "PG":
                this.showPg(data);
                html += '<div class="title">' + data["title"] + '</div>';

                c.append(html);
                break;
        }
    };

    Questions.prototype.gotoItem = function(num) {
        for (var i = 1; i < 500; i++) {
            var item = this.template['Q' + i];
            if (!item) {
                return;
            }

            if (item.qno == num) {
                this.ptr = i;

                $(".page").hide();
                $("#page-question").show();
                this.showItem();

                break;
            }
        }
    };

    Questions.prototype.hasQuestion = function() {
        return this.template["Q" + this.ptr] != undefined;
    };

    Questions.prototype.showResult = function() {
        var c = $("#answer-sheet");
        c.empty();

        var html = '';
        for (var ptr = 1;; ptr++) {
            var sptr = "Q" + ptr;
            var data = this.template[sptr];

            if (data == undefined) {
                break;
            }

            var qno = data.qno;
            if (qno) {
                html += '<span class="answer-no';
                if (this.answers[sptr]) {
                    html += ' answer-no-do';
                }
                html += '">' + qno + '</span>';
            }
        }

        c.append(html);

        var that = this;
        c.find(".answer-no").click(function() {
            that.gotoItem($(this).text());
        });
    };


    Questions.prototype.showSubmit = function() {
        var that = this;
        var rd = {};
        var rt = {
            "U": 0,
            "R": 0,
            "E": 0
        };

        var R = this.R;

        $.each(R, function(key, value) {
            var ans = that.answers[key];
            if (ans) {
                if (ans == value) {
                    rt.R = rt.R + 1;
                    rd[key] = 'R';
                } else {
                    rt.E = rt.E + 1;
                    rd[key] = 'E';
                }
            } else {
                rt.U = rt.U + 1;
                rd[key] = 'U';
            }
        });


        var html = '';
        for (var ptr = 1;; ptr++) {
            var sptr = "Q" + ptr;
            var data = this.template[sptr];

            if (data == undefined) {
                break;
            }

            var qno = data.qno;
            if (qno) {
                html += '<span class="answer-no';
                if (rd[sptr] == 'R') {
                    html += ' answer-no-do';
                } else
                if (rd[sptr] == 'E') {
                    html += ' answer-no-error';
                }
                html += '">' + qno + '</span>';
            }
        }

        $("#result-sheet").empty().html(html);
        $("#result-sheet").find('.answer-no').click(function() {
            that.showAnalysis = true;
            $("#submit-page").hide();
            $("#analysis").hide();
            that.gotoItem($(this).text());
        });

        $('#r-right-s').html('共答对：' + rt.R + ' 题');
        $('#r-error-s').html('共答错：' + rt.E + ' 题');
        $('#r-no-s').html('未回答：' + rt.U + ' 题');
        $('#r-right-p').html('正确率:' + parseInt(rt.R * 100 / (rt.R + rt.E + rt.U)) + '%')
    };

    Questions.prototype.analysis = function() {
        this.ptr = 1;
        this.showAnalysis = true;
        this.showItem();
    };
});