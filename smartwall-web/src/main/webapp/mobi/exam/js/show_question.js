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
        $(".question-nav #prev").tap(function(event) {
            event.stopPropagation();

            if (Q.ptr > 1) {
                Q.ptr--;
                Q.showItem();
            }
        });
        $(".question-nav #next").tap(function(event) {
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
        /*答案*/
        this.answers = {};
        this.ptr = 1;
        this.sptr = "";
        this.showItem();
    };

    Questions.prototype.showItem = function() {
        var that = this;

        this.sptr = "Q" + this.ptr;
        var data = this.template[this.sptr];
        var answer = this.answers[this.sptr];
        var c = $("#question-area").empty();

        var html = "";
        switch (data["type"]) {
            case "SC":
                html += '<div><div class="title">' + data.qno + "." + data["title"] + '</div>';
                $.each(data.opts, function(index) {
                    html += '<div class="options"><span class="radio">' + EA[index] + '</span><span>' + this.text + '</span></div>';
                });
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
                html += '<div><div class="title">' + data.qno + "." + data["title"] + '</div>';
                $.each(data.opts, function(index) {
                    html += '<div class="options"><span class="checkbox">' + EA[index] + '</span><span>' + this.text + '</span></div>';
                });

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

                    that.answers[that.sptr] = ans;
                });

                if (answer) {
                    $.each(answer, function() {
                        html.find('.options').eq(this - 1).addClass("selected");
                    });
                }

                c.append(html);
                break;

            case "AS":
                html += '<div class="title">' + data.qno + "." + data["title"] + '</div>';
                html += '<div class="options"><textarea/></div>';
                html = $(html);
                html.find("textarea").blur(function() {
                    that.answers[that.sptr] = $(this).val();
                });

                c.append(html);
                break;

            case "PG":
                html += '<div class="title">' + data["title"] + '</div>';

                c.append(html);
                break;
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
    };
});