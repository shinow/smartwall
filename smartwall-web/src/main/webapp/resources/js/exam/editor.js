define(function(require, exports) {
    "use strict";

    var utils = require("utils/utils");
    require("ligerui");
    require("ligerui-css");
    require("ligerui-css-icons");
    require("controls/umeditor");

    exports.init = function(conf) {
        var editor = new QEditor(conf);
        editor.addQuestion();
    }

    function extend(subClass, superClass) {
        var F = function() {};
        F.prototype = superClass.prototype;
        subClass.prototype = new F();
        subClass.superClass = superClass.prototype;
    }

    var QEditor = function(conf) {
        this._init_(conf);
    };

    QEditor.prototype._init_ = function(conf) {
        this.container = $("#" + conf["container"]);

        this._initTitle(this.container);
    };

    QEditor.prototype._initTitle = function() {
        var dom = $('<div id="qe-title" class="qe-item"><div id="qe-title-main">问卷标题</div><div id="qe-title-sub">问卷说明</div></div>');
        dom.click(function() {
            UM.getEditor("editor-title-um");
            $.ligerDialog.open({
                title: "修改",
                width: 600,
                hegith: 400,
                target: $('#editor-title'),
                buttons: [{
                    text: '保存',
                    onclick: function(i, d) {
                        //f_save();
                    }
                }, {
                    text: '关闭',
                    onclick: function(i, d) {
                        //$("input").ligerHideTip();
                        d.hide();
                    }
                }]
            });
        });

        this.container.append(dom);
    };

    QEditor.prototype.addQuestion = function() {
        var conf = {
            editor: this
        };

        new SCQuestion(conf);
        new MTQuestion(conf);
        new ASQuestion(conf);
        new PGQuestion(conf);
    };

    var Question = function(conf) {
        this._init_();
    };

    Question.prototype._init_ = function(conf) {
        this.editor = conf.editor;
        this.container = $('<div class="qe-item"></div>').appendTo(this.editor.container);
        this.guid = utils.guid();
        this.model = {};
        this.renderUI();
        this.updateOptions();
    };

    /*界面绘制*/
    Question.prototype.renderUI = function() {
        /*绘制题干区*/
        this.renderTitle();
        /*绘制内容区*/
        this.renderContent();
        /*绘制工具栏*/
        this.renderToolbar();
        /*绘属性编辑区*/
        this.renderWorkarea();
    }

    Question.prototype.setEditing = function(editing) {
        if (editing) {
            this.container.addClass("qe-item-editing");
        } else {
            this.container.removeClass("qe-item-editing");
        }
    }
    Question.prototype.renderTitle = function() {
        this.title$ = $('<div class="qe-item-title"></div>');
        this.container.append(this.title$);
    };

    Question.prototype.renderContent = function() {
        this.content$ = $('<div class="qe-item-content"></div>');

        this.container.append(this.content$);
        this.container.append("<hr/>");
    };

    Question.prototype.renderToolbar = function() {
        /*编辑|删除|上移|下移|最前|最后*/
        var toolbar$ = $('<div class="qe-item-toolbar"></div>');
        var that = this;
        $('<a>编辑</a>').click(function() {
            if ($(this).text() == '编辑') {
                $(this).text("完成");
                that.setEditing(true);
            } else {
                $(this).text("编辑");
                that.setEditing(false);
            }
        }).appendTo(toolbar$);
        $('<a>删除</a>').click(function() {
            $(this).parent().parent().remove();
        }).appendTo(toolbar$);
        $('<a>上移</a>').click(function() {
            var pp = $(this).parent().parent();
            var prev = pp.prev();
            if (prev.length > 0) {
                pp.after(prev);
            }
        }).appendTo(toolbar$);
        $('<a>下移</a>').click(function() {
            var pp = $(this).parent().parent();
            var next = pp.next();
            if (next.length > 0) {
                pp.before(next);
            }
        }).appendTo(toolbar$);
        $('<a>最前</a>').click(function() {
            var pp = $(this).parent().parent();
            pp.parent().prepend(pp);
        }).appendTo(toolbar$);
        $('<a>最后</a>').click(function() {
            var pp = $(this).parent().parent();
            pp.parent().append(pp);
        }).appendTo(toolbar$);

        this.container.append(toolbar$);
    };

    Question.prototype.renderWorkarea = function() {
        this.workarea$ = $('<div class="qe-item-workarea">标题</div>').appendTo(this.container);
        this.titleEditor = $('<div class="qe-item-e-title"><script id="' +
            this.guid +
            '" style="width:400px;height:160px;" type="text/plain" ></script></div>');
        this.optEditor = $('<div class="qe-item-e-opt"></div>');
        this.required$ = $('<label><input type="checkbox"></input>必答题</label>').appendTo(this.optEditor);

        this.workarea$.append(this.titleEditor).append(this.optEditor);

        var that = this;
        UM.getEditor(this.guid).addListener("blur", function(type, event) {
            that.title$.html(UM.getEditor(that.guid).getContent()); 
        });

        this.renderPropUI();
    };

    Question.prototype.renderPropUI = function() {};
    Question.prototype.addOption = function() {};
    Question.prototype.updateOpt = function(index, text) {};
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
    var SCQuestion = function(conf) {
        this._init_(conf);
    }
    extend(SCQuestion, Question);
    SCQuestion.prototype.renderPropUI = function() {
        this.prop$ = $('<table class="qe-item-e-prop"><thead></thead><tbody></tbody></table>');

        this.workarea$.append(this.prop$);
        this.prop$.find("thead").append('<tr><th style="width:300px">选项文字</th><th style="width:80px">图片</th><th style="width:100px">操作</th><tr>');

        this.addOption();
        this.addOption();
        this.addOption();
    };

    SCQuestion.prototype.addOption = function(opt) {
        var that = this;
        var opt = opt || {};
        var tr = $('<tr/>');
        var td1 = $("<td><input/></td>").appendTo(tr);
        td1.find("input").blur(function() {
            var this$ = $(this);
            var data = tr.data("model") || {};
            data.text = this$.val() || '';
            tr.data("model", data);

            that.updateOpt(tr.index(), data.text);
        }).val(opt.text || '选项');

        var td2 = $("<td/>").appendTo(tr);
        var td3 = $("<td/>").append(this.createOprAdd()).append(this.createOprDel()).append(this.createOprUp()).append(this.createOprDown()).appendTo(tr);

        this.prop$.find("tbody").append(tr);
    };

    SCQuestion.prototype.updateOpt = function(index, text) {
        this.content$.children().eq(index).find("label").text(text);
    };
    SCQuestion.prototype.updateOptions = function() {
        var content = this.content$;

        content.empty();
        var dv = this.prop$.find("tbody").children();
        dv.each(function() {
            var text = ($(this).data("model") || {}).text || '选项';
            content.append('<span><input type="radio"></input><label>' + text + '</label></span>')
        });
    };

    /*多选*/
    var MTQuestion = function(conf) {
        this._init_(conf);
    }
    extend(MTQuestion, Question);
    MTQuestion.prototype.renderPropUI = function() {
        this.prop$ = $('<table class="qe-item-e-prop"><thead></thead><tbody></tbody></table>');

        this.workarea$.append(this.prop$);
        this.prop$.find("thead").append('<tr><th style="width:300px">选项文字</th><th style="width:80px">图片</th><th style="width:100px">操作</th><tr>');

        this.addOption();
        this.addOption();
    };

    MTQuestion.prototype.addOption = function(opt) {
        var that = this;
        var opt = opt || {};
        var tr = $('<tr/>');
        var td1 = $("<td><input/></td>").appendTo(tr);
        td1.find("input").blur(function() {
            var this$ = $(this);
            var data = this$.data("model") || {};
            data.text = this$.val() || '';
            this$.data("model", data);

            that.updateOpt(tr.index(), data.text);
        }).val(opt.text || '选项');;

        var td2 = $("<td/>").appendTo(tr);
        var td3 = $("<td/>").append(this.createOprAdd()).append(this.createOprDel()).append(this.createOprUp()).append(this.createOprDown()).appendTo(tr);

        this.prop$.find("tbody").append(tr);
    };

    MTQuestion.prototype.updateOpt = function(index, text) {
        this.content$.children().eq(index).find("label").text(text);
    };

    MTQuestion.prototype.updateOptions = function() {
        var content = this.content$;

        content.empty();
        var dv = this.prop$.find("tbody").children();
        dv.each(function() {
            var text = ($(this).data("model") || {}).text || '选项';
            content.append('<span><input type="checkbox"></input><label>' + text + '</label></span>')
        });
    };

    /*问答题*/
    var ASQuestion = function(conf) {
        this._init_(conf);
    }
    extend(ASQuestion, Question);
    ASQuestion.prototype.renderPropUI = function() {
        this.prop$ = $('<table class="qe-item-e-prop"><thead></thead><tbody></tbody></table>');

        this.workarea$.append(this.prop$);
        this.prop$.find("thead").append('<tr><th style="width:300px">选项文字</th><th style="width:80px">图片</th><th style="width:100px">操作</th><tr>');

        this.addOption();
        this.addOption();
        this.addOption();
    };

    ASQuestion.prototype.addOption = function() {
        var tr = $('<tr/>');
        var td1 = $("<td><input/></td>").appendTo(tr);
        td1.find("input").blur(function() {
            var this$ = $(this);
            var data = this$.data("model") || {};
            data.text = this$.val() || '';
            this$.data("model", data);
        });

        var td2 = $("<td/>").appendTo(tr);
        var td3 = $("<td/>").append(this.createOprAdd()).append(this.createOprDel()).append(this.createOprUp()).append(this.createOprDown()).appendTo(tr);

        this.prop$.find("tbody").append(tr);
    };

    /*段落说明*/
    var PGQuestion = function(conf) {
        this._init_(conf);
    }
    extend(PGQuestion, Question);
    PGQuestion.prototype.renderPropUI = function() {};

    PGQuestion.prototype.addOption = function() {};
});