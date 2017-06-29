define(function(require, exports) {
    "use strict";

    var utils = require("utils/utils");
    require("ligerui");
    require("ligerui-css");
    require("ligerui-css-icons");
    require("controls/umeditor");
    require("./css/editor.css");

    var editor;
    var curr_question;
    exports.init = function(conf) {
        editor = new QEditor(conf);
        UM.getEditor('UM-SC').addListener("blur", function(type, event) {
            $("#editor-SC .qe-item-title").html(UM.getEditor('UM-SC').getContent());
        });
        UM.getEditor('UM-MT').addListener("blur", function(type, event) {
            $("#editor-MT .qe-item-title").html(UM.getEditor('UM-MT').getContent());
        });
        UM.getEditor('UM-AS').addListener("blur", function(type, event) {
            $("#editor-AS .qe-item-title").html(UM.getEditor('UM-AS').getContent());
        });
        UM.getEditor('UM-PG').addListener("blur", function(type, event) {
            $("#editor-PG .qe-item-title").html(UM.getEditor('UM-PG').getContent());
        });

        var opts = '<option>0.5</option>';
        for (var i = 1; i < 51; i++) {
            opts = opts + '<option>' + i + '</option>';
        }
        $('select').html(opts).val("1");

        $('.qe-item-btn-finish').click(function() {
            if (curr_question) {
                curr_question.save();
            }
        });
    };

    exports.save = function() {
        utils.postJson("exam/save.mvc", {
            type: editor.examType,
            guid: editor.examGuid,
            value: JSON.stringify(editor.getValue())
        }, function(result) {
            //alert(JSON.stringify(result));
        });
    };

    exports.addQuestion = function(type) {
        editor.addQuestion(type, null);
    };

    function extend(subClass, superClass) {
        var F = function() {};
        F.prototype = superClass.prototype;
        subClass.prototype = new F();
        subClass.superClass = superClass.prototype;
    };

    var QEditor = function(conf) {
        this._init_(conf);
    };

    QEditor.prototype._init_ = function(conf) {
        this.examGuid = conf["guid"];
        this.examType = conf["type"];

        this.lastQ = null;
        this._initTitle();
        this._loadExam();
    };

    QEditor.prototype._initTitle = function() {
        $("#qe-title").dblclick(function() {
            UM.getEditor("editor-title-um").setContent($("#qe-title-sub").html());
            $('#editor-title').find("#editor-title-ti").val($("#qe-title-main").text());
            $.ligerDialog.open({
                title: "修改",
                width: 600,
                hegith: 400,
                target: $('#editor-title'),
                buttons: [{
                    text: '保存',
                    onclick: function(i, d) {
                        $("#qe-title-sub").html(UM.getEditor("editor-title-um").getContent());
                        $("#qe-title-main").text($('#editor-title').find("#editor-title-ti").val());

                        d.hide();
                    }
                }, {
                    text: '关闭',
                    onclick: function(i, d) {
                        d.hide();
                    }
                }]
            });
        });
    };

    QEditor.prototype._loadExam = function() {
        var that = this;
        utils.postJson("exam/get.mvc", {
            type: this.examType,
            guid: this.examGuid
        }, function(result) {
            if (result.code == 0) {
                var m = result.message;
                if (m) {
                    that._parseExam(JSON.parse(m));
                }
            } else {
                alert("error");
            }
        });
    };

    QEditor.prototype._parseExam = function(exam) {
        $("#qe-title-sub").html(exam.comment);
        $("#qe-title-main").text(exam.title);

        for (var i = 1; i < 500; i++) {
            var q = exam["Q" + i];

            if (!q) {
                break;
            }

            editor.addQuestion(q.type, q);
        }
    };

    QEditor.prototype.addQuestion = function(type, data) {
        var conf = {
            //editor: this
        };
        var question;
        switch (type) {
            case 'SC':
                question = new SCQuestion(conf, data);
                break;
            case 'MT':
                question = new MTQuestion(conf, data);
                break;
            case 'AS':
                question = new ASQuestion(conf, data);
                break;
            case 'PG':
                question = new PGQuestion(conf, data);
                break;
        }
    };

    QEditor.prototype.getValue = function() {
        var v = {};
        v["title"] = $("#qe-title-main").text();
        v["comment"] = $("#qe-title-sub").html();

        $(".qe-item-question").each(function(index) {
            var question = $(this).data("question");
            v["Q" + (index + 1)] = question.getValue();
        });

        return v;
    };

    var Question = function(conf, data) {
        this._init_(conf, data || {});
    };

    var tpl = '<tr class="qe-item qe-item-question"><td>{no}</td><td>{title}</td></tr>';
    Question.prototype._init_ = function(conf, data) {
        /*注释题型*/
        this.isMemo = false;

        /*题目编号,注释题型没有*/
        this.no = data.no;
        this.data = data;

        this.nav = $(utils.format(tpl, data)).appendTo($("#nav-item-container"));

        var that = this;
        /*添加到data.question中*/
        this.nav.data("question", this).click(function() {
            curr_question = $(this).data("question");
            that.edit();
        });
    };

    Question.prototype.edit = function() {
        this.showUI();
    };

    Question.prototype.showUI = function() {};
    Question.prototype.save = function() {
        alert("save")
    };

    Question.prototype.updateOptions = function() {};
    Question.prototype.createOprAdd = function() {
        var that = this;
        return $('<span class="eq-item-e-btn eq-item-e-add"></span>').click(function() {
            that.addOption();

            that.updateOptions();
        });
    };

    Question.prototype.createOprDel = function() {
        var that = this;
        return $('<span class="eq-item-e-btn eq-item-e-del"></span>').click(function() {
            $(this).parent().parent().remove();

            that.updateOptions();
        });
    };

    Question.prototype.createOprUp = function() {
        var that = this;

        return $('<span class="eq-item-e-btn eq-item-e-up"></span>').click(function() {
            /*tbody>tr>td>span*/
            var pp = $(this).parent().parent();
            var prev = pp.prev();
            if (prev.length > 0) {
                pp.after(prev);

                that.updateOptions();
            }
        });
    };

    Question.prototype.createOprDown = function() {
        var that = this;
        return $('<span class="eq-item-e-btn eq-item-e-down"></span>').click(function() {
            /*tbody>tr>td>span*/
            var pp = $(this).parent().parent();
            var next = pp.next();
            if (next.length > 0) {
                pp.before(next);

                that.updateOptions();
            }
        });
    };

    /*单选*/
    var SCQuestion = function(conf, data) {
        this._init_(conf, data);
    }
    extend(SCQuestion, Question);
    SCQuestion.prototype.showUI = function() {
        $('.editor-item').hide();
        var that = this;
        var data = this.data

        $("#editor-SC .qe-item-title").html(data.title);
        UM.getEditor('UM-SC').setContent(data.title);

        $("#editor-SC").find("tbody").empty();
        $.each(data.opts, function() {
            that.addOption(this);
        });

        $("#editor-SC").find("select").val(data.score);
        $("#editor-SC").find("textarea").val(data.analysis);
        this.updateOptions();
        $('#editor-SC').show();
    };

    SCQuestion.prototype.addOption = function(opt) {
        var that = this;
        var opt = opt || {
            text: "选项",
            right: false
        };
        var tr = $('<tr/>').data("model", opt);
        var td1 = $('<td><input/></td>').appendTo(tr);
        td1.find("input").blur(function() {
            var this$ = $(this);
            var data = tr.data("model");
            data.text = this$.val() || '';
            tr.data("model", data);

            that.updateOpt(tr.index(), data);
        }).val(opt.text);

        var td2 = $("<td/>").appendTo(tr);
        var cid = "C-" + this.guid;
        var td3 = $('<td><input type="checkbox" cid="' + cid + '"/></td>').appendTo(tr);
        td3.find("input").change(function() {
            var that$ = $(this);
            if (that$.is(":checked")) {
                $('input[cid="' + cid + '"]', tr.parent()).each(function() {
                    var this$ = $(this);
                    if (that$.is(this$)) {
                        return true;
                    }

                    if (this$.is(":checked")) {
                        var ctr = this$.parent().parent();
                        var cdata = ctr.data("model");
                        this$.attr("checked", false);
                        cdata.right = false;
                        ctr.data("model", cdata);

                        that.updateOpt(ctr.index(), cdata);
                    }
                });
            }

            var data = tr.data("model");
            data.right = $(this).is(":checked");
            tr.data("model", data);

            that.updateOpt(tr.index(), data);
        }).attr("checked", opt.right);

        var td4 = $("<td/>").append(this.createOprAdd()).append(this.createOprDel()).append(this.createOprUp()).append(this.createOprDown()).appendTo(tr);

        $("#editor-SC").find("tbody").append(tr);
    };

    SCQuestion.prototype.updateOpt = function(index, data) {
        $("#editor-SC .qe-item-content").children().eq(index).find("label").text(data.text);
    };

    SCQuestion.prototype.updateOptions = function() {
        var content = $("#editor-SC .qe-item-content");

        content.empty();
        var dv = $("#editor-SC").find("tbody").children();
        dv.each(function() {
            var text = $(this).data("model").text;
            content.append('<span><input type="radio"></input><label>' + text + '</label></span>')
        });
    };

    SCQuestion.prototype.save = function() {
        var v = {
            no: this.no,
            type: "SC"
        };

        v["title"] = $("#editor-SC .qe-item-title").html();
        var opts = {};
        $("#editor-SC").find("tbody").children().each(function(index) {
            var model = $(this).data("model");
            if (model) {
                var opt = {};
                opt["text"] = model.text;
                opt["right"] = model.right;
                opts["" + (index + 1)] = opt;
            }
        });

        v["opts"] = opts;
        v["score"] = $("#editor-SC").find("select").val();
        v["analysis"] = $("#editor-SC").find("textarea").val();

        this.data = v;
    };

    /*多选*/
    var MTQuestion = function(conf, data) {
        this._init_(conf, data);
    };
    extend(MTQuestion, Question);
    MTQuestion.prototype.showUI = function() {
        $('.editor-item').hide();
        var that = this;
        var data = this.data

        $("#editor-MT .qe-item-title").html(data.title);
        UM.getEditor('UM-MT').setContent(data.title);

        $("#editor-MT").find("tbody").empty();
        $.each(data.opts, function() {
            that.addOption(this);
        });

        $("#editor-MT").find("select").val(data.score);
        $("#editor-MT").find("textarea").val(data.analysis);
        this.updateOptions();
        $('#editor-MT').show();
    };

    MTQuestion.prototype.addOption = function(opt) {
        var that = this;
        var opt = opt || {
            text: "选项",
            right: false
        };
        var tr = $('<tr/>').data("model", opt);
        var td1 = $("<td><input/></td>").appendTo(tr);
        td1.find("input").blur(function() {
            var this$ = $(this);
            var data = tr.data("model");
            data.text = this$.val() || '';
            tr.data("model", data);

            that.updateOpt(tr.index(), data);

        }).val(opt.text);;

        var td2 = $("<td/>").appendTo(tr);
        var td3 = $('<td><input type="checkbox"/></td>').appendTo(tr);
        td3.find("input").change(function() {
            var data = tr.data("model");
            data.right = $(this).is(":checked");
            tr.data("model", data);

            that.updateOpt(tr.index(), data);
        }).attr("checked", opt.right);
        var td4 = $("<td/>").append(this.createOprAdd()).append(this.createOprDel()).append(this.createOprUp()).append(this.createOprDown()).appendTo(tr);

        $("#editor-MT").find("tbody").append(tr);
    };

    MTQuestion.prototype.updateOpt = function(index, data) {
        $("#editor-MT .qe-item-content").children().eq(index).find("label").text(data.text);
    };

    MTQuestion.prototype.updateOptions = function() {
        var content = $("#editor-MT .qe-item-content");

        content.empty();
        var dv = $("#editor-MT").find("tbody").children();
        dv.each(function() {
            var text = $(this).data("model").text;
            content.append('<span><input type="checkbox"></input><label>' + text + '</label></span>')
        });
    };

    MTQuestion.prototype.save = function() {
        var v = {
            no: this.no,
            type: "MT"
        };

        v["title"] = $("#editor-MT .qe-item-title").html();
        var opts = {};
        $("#editor-MT").find("tbody").children().each(function(index) {
            var model = $(this).data("model");
            if (model) {
                var opt = {};
                opt["text"] = model.text;
                opt["right"] = model.right;
                opts["" + (index + 1)] = opt;
            }
        });

        v["opts"] = opts;
        v["score"] = $("#editor-MT").find("select").val();
        v["analysis"] = $("#editor-MT").find("textarea").val();

        this.data = v;
    };

    /*问答题*/
    var ASQuestion = function(conf, data) {
        this._init_(conf, data);
    }
    extend(ASQuestion, Question);
    ASQuestion.prototype.showUI = function() {
        $('.editor-item').hide();
        var that = this;
        var data = this.data

        $("#editor-AS .qe-item-title").html(data.title);
        UM.getEditor('UM-AS').setContent(data.title);

        $("#editor-AS").find("select").val(data.score);
        $("#editor-AS").find("textarea").val(data.analysis);

        $('#editor-AS').show();
    };

    ASQuestion.prototype.save = function() {
        var v = {
            no: this.no,
            type: "AS"
        };

        v["title"] = $("#editor-AS .qe-item-title").html();
        v["score"] = $("#editor-AS").find("select").val();
        v["analysis"] = $("#editor-AS").find("textarea").val();

        this.data = v;
    };

    /*段落说明*/
    var PGQuestion = function(conf, data) {
        this._init_(conf, data);
        this.isMemo = true;
    }

    extend(PGQuestion, Question);
    PGQuestion.prototype.showUI = function() {
        $('.editor-item').hide();
        var that = this;
        var data = this.data

        $("#editor-PG .qe-item-title").html(data.title);
        UM.getEditor('UM-PG').setContent(data.title);

        $('#editor-PG').show();
    };

    PGQuestion.prototype.save = function() {
        var v = {
            no: this.no,
            type: "PG"
        };

        v["title"] = $("#editor-PG .qe-item-title").html();
        v["score"] = $("#editor-PG").find("select").val();
        v["analysis"] = $("#editor-PG").find("textarea").val();

        this.data = v;
    };
});