/**
 * Created by Yuffie on 2017/5/19.
 */
function Dialog(o, t){
    this.width = o.width || 600;
    this.height = o.height || 400;
    this.d = o.data || {};
    this.$t = t;
    this.renderUI();
    this.addEvent();
}
Dialog.prototype = {
    constructor: Dialog,
    addEvent : function (){
        var that = this;
        this.$close.on('click', function (){
            that.close();
        });
    },
    renderUI : function (){
        this.$wrapper = $('<div class="c-dialog-wrapper" style="display: none">' +
            '<div class="c-mask"></div>' +
            '<div class="c-dialog-content">' +
            '<div class="c-dialog-close">×</div>' +
            '</div>' +
            '</div>').appendTo($('body'));
        this.$close = this.$wrapper.find('.c-dialog-close');
        this.$content = this.$wrapper.find('.c-dialog-content');
        this.$content.css({width: this.width + 'px', height: this.height + 'px'});
        this.$t.appendTo(this.$content).css('display', 'block');
    },
    open : function (){
        this.$wrapper.show();
    },
    close : function (){
        this.$wrapper.hide();
    }
};

var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
Math.uuid = function (len, radix) {
    var chars = CHARS, uuid = [], i;
    radix = radix || chars.length;

    if (len) {
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
        var r;
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random()*16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }

    return uuid.join('');
};


var questionArr = [];
/*富文本编辑器的配置*/
var UeditorConfig = {
    toolbars: [[
        'undo', 'redo', '|',
        'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
        'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
        'rowspacingtop', 'rowspacingbottom', 'lineheight', 'indent', '|',

        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
        'link', 'insertimage', 'emotion'
    ]],
    initialFrameWidth : 498,
    initialFrameHeight: 300,
    autoHeightEnabled: false,
    autoFloatEnabled: false,
    elementPathEnabled : false,
};
/*选项数量*/
var optionMaxCount = 4;

function View(t, conf){
    questionArr.push(this);
    this.$t = t;
    this.i = conf;
    this.init();
}
View.prototype  = {
    constructor: View,
    init: function (){
        var that = this;
        this.config();
        this.renderUI();
        this.initEvents();
        if(typeof this.i != 'undefined'){
            setTimeout(function (){
                that.setValue();
            }, 1000)
        }
    },
    initEvents: function (){},
    renderUI : function (){
        this.index = questionArr.length - 1;
        this.uuid = Math.uuid();
        /*wrapper*/
        this.$wrapper = $('<div class="e-questions-wrapper c-hover" questionId="' + this.uuid + '"></div>').appendTo(this.$t);
        /*展示部分*/
        this.$show = $('<div class="e-question-item">' +
            '<div class="e-q-title">' + (this.c.editorText || '请在此处编辑问题') + '</div>' +
            '</div>').appendTo(this.$wrapper);
        /*展示选项*/
        if(this.c.questionType == 'single-choice' || this.c.questionType == 'multiple-choice'){
            this.renderShowSelect();
        }
        if(this.c.questionType == 'question-answer'){
            this.renderShowTextarea();
        }
        /*按钮栏*/
        this.renderToolbar();
        /*隐藏部分*/
        this.$hide = $('<div class="e-q-hide"></div>').appendTo(this.$wrapper);
        /*题干编辑*/
        this.renderTitle();
        /*题目配置*/
        this.renderConfig();
        /*题目选项*/
        if(this.c.option.length != 0){
            this.renderOptions();
        }
        /*渲染提交按钮*/
        this.renderSubmitBtn();
    },

    /*渲染展示部-分选项*/
    renderShowSelect: function (){
        this.$showSelect = $('<ul class="e-q-options"></ul>').appendTo(this.$show);
    },
    /*渲染展示部分-文本框*/
    renderShowTextarea: function (){
        this.$showTextarea = $('<textarea class="e-q-textarea" rows="5" disabled="disabled"></textarea>').appendTo(this.$show);
    },
    /*渲染展示部分-编辑按钮栏*/
    renderToolbar: function (){
        var that = this;
        this.$btns = $('<div class="e-question-btns"></div>').appendTo(this.$wrapper);
        /*编辑*/
        $('<button class="c-button btn-edit">编辑</button>').on('click', function (){
            if(!that.$wrapper.hasClass('e-editing')){
                that.$wrapper.siblings('.e-questions-wrapper').removeClass('e-editing');
                that.$wrapper.siblings('.e-questions-wrapper').find('.btn-edit').html('编辑');
            }
            that.$wrapper.toggleClass('e-editing');
            $(this).html(that.$wrapper.hasClass('e-editing') ? '完成': '编辑');
        }).appendTo(this.$btns);
        /*删除*/
        $('<button class="c-button">删除</button>').on('click', function (){
            that.$wrapper.remove();
            questionArr.splice(that.$wrapper.index(), 1);
        }).appendTo(this.$btns);
        /*上移*/
        $('<button class="c-button">上移</button>').on('click', function (){
            if(!that.$wrapper.prev().hasClass('e-questions-wrapper')){
                alert('已经在最上面');
                return;
            }
            var index = that.$wrapper.index();
            var questionPrevEle = questionArr[index-1];
            var questionPrevContent = questionPrevEle.editor.getContent();
            that.$wrapper.after(that.$wrapper.prev());
            changeArr(questionArr, index, index-1);
            setTimeout(function (){
                questionPrevEle.editor.setContent(questionPrevContent);
            }, 100);
        }).appendTo(this.$btns);
        /*下移*/
        $('<button class="c-button">下移</button>').on('click', function (){
            if(!that.$wrapper.next().hasClass('e-questions-wrapper')){
                alert('已经在最下面');
                return;
            }
            var index = that.$wrapper.index();
            var questionNextEle = questionArr[index];
            var questionNextContent = questionNextEle.editor.getContent();
            that.$wrapper.before(that.$wrapper.next());
            changeArr(questionArr, index, index + 1);
            setTimeout(function (){
                questionNextEle.editor.setContent(questionNextContent);
            }, 100);
        }).appendTo(this.$btns);
        /*最前*/
        $('<button class="c-button">最前</button>').on('click', function (){
            if(!that.$wrapper.prev().hasClass('e-questions-wrapper')){
                alert('已经在最上面');
                return;
            }
            var index = that.$wrapper.index();
            var questionFirst = $(that.$wrapper.siblings('.e-questions-wrapper')[0]);
            var questionContent = that.editor.getContent();
            questionFirst.before(that.$wrapper);
            changeArr(questionArr, index, 1);
            setTimeout(function (){
                that.editor.setContent(questionContent);
            }, 100);
        }).appendTo(this.$btns);
        /*最后*/
        $('<button class="c-button">最后</button>').on('click', function (){
            if(!that.$wrapper.next().hasClass('e-questions-wrapper')){
                alert('已经在最下面');
                return;
            }
            var index = that.$wrapper.index();
            var questionLast = that.$wrapper.siblings('.e-questions-wrapper');
            var questionLength = questionLast.length;
            questionLast = $(questionLast[questionLength - 1]);
            var questionContent = that.editor.getContent();
            questionLast.after(that.$wrapper);
            changeArr(questionArr, index, questionLength);
            setTimeout(function (){
                that.editor.setContent(questionContent);
            }, 100);
        }).appendTo(this.$btns);
    },


    /*渲染标题编辑栏*/
    renderTitle: function (){
        var that = this;
        this.$title = $('<div class="e-question-title">' +
            '<script id="' + this.uuid + '" class="ueditor-item" type="text/plain" ></script>' +
            '</div>').appendTo(this.$hide);
        this.editor = UE.getEditor(this.uuid, UeditorConfig);
        this.editor.addListener('contentChange', function (){
            that.$show.find('.e-q-title').html(that.editor.getContent());
        });
    },
    setValue: function (){
        var that = this;
        this.$show.find('.e-q-title').html(this.i.subject);
        this.editor.setContent(this.i.subject);
        this.$showSelect && this.$showSelect.html('');
        this.$select && this.$select.find('tbody tr').remove();
        $.each(this.i.options, function (){
            that.createOption(this);
        });
    },
    /*渲染配置栏*/
    renderConfig: function (){
        var that = this;
        this.$config = $('<div class="e-question-config">' +
            '<div>' +
            '<label>当前题型: </label>' +
            '<span class="e-q-display-type">' + this.c.displayType + '</label>' +
            '</div>' +
            '</div>').appendTo(this.$hide);
        if(!this.c.control || this.c.control.length == 0){
            return;
        }
        $.each(this.c.control, function (index, item){
            if(item === 'isRequired'){
                that.renderConfigIsRequired();
                return;
            }
            if(item === 'addSelect'){
                that.renderConfigAddSelect();
                return;
            }
            if(item === 'addPhoto'){
                that.renderConfigAddPhoto();
                return;
            }
        });
    },
    renderConfigIsRequired: function (){
        var that = this;
        var checked = typeof this.i == 'undefined' ? true : this.i.required;
        var $div = $('<div>' +
            '<label>是否必填: </label>' +
            '<input type="checkbox" value="是否必填" class="e-question-required">' +
            '</div>').appendTo(this.$config);
        $div.find('input').prop('checked', checked);
    },
    renderConfigAddSelect:  function (){
        var that = this;
        var $div = $('<button class="e-q-add-select c-button">添加选项</button>').appendTo(this.$config);
        $div.on('click', function (){
            that.createOption({});
        });
    },
    renderConfigAddPhoto: function (){
        var that = this;
        var $labelPhoto = $('<label>答题需拍照: </label>').appendTo(this.$config);
        var $inputPhonto = $('<input type="checkbox" class="e-question-photo">').appendTo($labelPhoto);
        var $labelPhontoR = $('<label>答题必须拍照: </label>').appendTo(this.$config);
        var $inputPhontoR = $('<input type="checkbox" class="e-question-photo-r" disabled="disabled">').appendTo($labelPhontoR);
        if(this.i && this.i.extra.has_pic){
            $inputPhonto.prop('checked', true);
            $inputPhontoR.prop('disabled', '');
        }
        if(this.i && this.i.extra.require_pic){
            $inputPhontoR.prop('checked', true);
        }
        $inputPhonto.on('change', function (){
            if($inputPhonto.prop('checked')){
                $inputPhontoR.prop('disabled', '');
            } else {
                $inputPhontoR.prop('disabled', 'disabled');
                $inputPhontoR.prop('checked', false);
            }
        })
    },

    /*渲染隐藏部分-选项栏*/
    renderOptions: function (){
        var that = this;
        this.$select = $('<div class="e-question-select"><table>' +
            '<thead><tr></tr></thead>' +
            '<tbody></tbody>' +
            '</table></div>').appendTo(this.$hide);
        $.each(this.c.option, function (index, item){
            if(item == 'text'){
                $('<th>选项文字</th>').appendTo(that.$select.find('tr'));
            } else if(item == 'img'){
                $('<th>图片</th>').appendTo(that.$select.find('tr'));
            } else if(item == 'photo'){
                $('<th>答题需拍照</th><th>答题必须拍照</th>').appendTo(that.$select.find('tr'));
            } else if(item == 'control'){
                $('<th>操作</th>').appendTo(that.$select.find('tr'));
            }else{

            }
        });
        if(this.i && this.i.options && this.i.options.length != 0){
            return;
        }
        /*创建两个选项*/
        this.createOption({title : '选项1'});
        this.createOption({title : '选项2'});
    },
    /*创建选项*/
    createOption: function (o){
        var that = this;
        var length = this.$select.find('tbody tr').length;
        var title = o.caption || o.title || '选项' + (length + 1);
        var image = o.image;

        if(length >= optionMaxCount){
            alert('最多能创建' + optionMaxCount + '个选项');
            return;
        }
        /*展示选项*/
        var type = this.c.questionType == 'single-choice' ? 'radio' : 'checkbox';
        var $option = $('<li class="e-q-option">' +
                '<div class="e-q-option-img" ></div>' +
                '<div class="e-q-option-text">' +
                    '<input type="' + type + '" disabled="disabled"><label>' + title + '</label>' +
                '</div>' +
            '</li>');
        this.$show.find('.e-q-options').append($option);

        /*选项*/
        var $item = $('<tr></tr>');
        this.$select.find('tbody').append($item);
        $.each(this.c.option, function (index){
            if(this == 'text'){
                that.createOptionText($item, title);
            } else if(this == 'img'){
                that.createOptionImg($item, image);
            } else if(this == 'photo'){
                that.createOptionPhoto($item, o);
            } else if(this == 'control'){
                that.createOptionCtl($item, o);
            } else{

            }
        });
    },
    createOptionText: function ($t, title){
        var that = this;
        var wrapper = $('<td></td>').appendTo($t);
        var input = $('<input type="text" class="q-option-text">').val(title).appendTo(wrapper);
        input.on('change', function (){
            var index = $t.index();
            var $showSelect = $(that.$show.find('.e-q-option')[index]);
            $showSelect.find('label').html($(this).val());
        })
    },
    createOptionImg: function ($t, imageName){
        var uuid = Math.uuid();
        var wrapper = $('<td></td>').appendTo($t);
        var img = $('<span class="q-option-img" id=uuid"' + uuid + '"></span>').appendTo(wrapper);
        var iu = new Uploader();
        var length = this.$select.find('tbody tr').length;
        var option = (this.$showSelect.find('.e-q-option-img'))[length - 1];
        iu.init({
            container: img,
            option: option,
            innerHTML: "选择图片"
        });
        if(typeof imageName != 'undefined' && imageName != ''){
            iu.setImage(imageName);
        }
        var remove = $('<span class="q-option-img-del">删除</span>').appendTo(wrapper);
        remove.on('click', function (){
            img.removeClass('remove').attr('file-name', '');
            $(option).html('');
        });
    },
    createOptionPhoto: function ($t, o){
        var wrapper = $('<td></td>').appendTo($t);
        var photo = $('<input type="checkbox" class="p-option-photo">').appendTo(wrapper);
        wrapper = $('<td></td>').appendTo($t);
        var photoR = $('<input type="checkbox" class="p-option-photo-r" disabled="disabled">').appendTo(wrapper);
        if(o && o.has_pic){
            photo.prop('checked', true);
            photoR.prop('disabled', '');
        }
        if(o && o.require_pic){
            photoR.prop('checked', true);
        }
        photo.on('change', function (){
            if(photo.prop('checked')){
                photoR.prop('disabled', '');
            } else {
                photoR.prop('disabled', 'disabled');
                photoR.prop('checked', false);
            }
        })
    },
    createOptionCtl: function ($t){
        var that = this;
        var wrapper = $('<td></td>').appendTo($t);
        var addBtn = $('<span class="e-q-option-ctl">添加</span>').on('click', function (){
            var length = that.$select.find('tr').length;
            that.createOption({});
        }).appendTo(wrapper);
        var delBtn = $('<span class="e-q-option-ctl">删除</span>').on('click', function (){
            var index = $t.index();
            var $showSelect = $(that.$show.find('.e-q-option')[index]);
            $t.remove();
            $showSelect.remove();
        }).appendTo(wrapper);
        var upBtn = $('<span class="e-q-option-ctl">上移</span>').on('click', function (){
            var index = $t.index();
            var $showSelect = $(that.$show.find('.e-q-option')[index]);
            $t.prev('tr').before($t);
            $showSelect.prev().before($showSelect);
        }).appendTo(wrapper);
        var downBtn = $('<span class="e-q-option-ctl">下移</span>').on('click', function (){
            var index = $t.index();
            var $showSelect = $(that.$show.find('.e-q-option')[index]);
            $t.next('tr').after($t);
            $showSelect.next().after($showSelect);
        }).appendTo(wrapper);
    },

    /*渲染提交按钮*/
    renderSubmitBtn: function (){
        var that = this;
        var $btnWrapper = $('<div class="e-question-submit"></div>').appendTo(this.$hide);
        var $btn = $('<button class="q-submit-btn c-button">完成编辑</button>').appendTo($btnWrapper);
        $btn.on('click', function (){
            that.$wrapper.removeClass('e-editing');
            that.$btns.find('.btn-edit').html('编辑');
        });
    },
    /*方法*/
    getEleById: function (questionId){
        var count = null;
        $.each(questionArr, function (index){
            if(this.uuid == questionId){
                count = index;
                return false;
            }
        });
        return questionArr[count];
    },
    getValue: function (){
        var data = {
            type: this.c.questionType,
            subject: this.getEditorContent(),
            required: this.getRequired(),
            options: this.getOptions() || [],
            extra: this.getPhotoCtr(),
        };
        return data;
    },
    /*获取试题内容*/
    getEditorContent: function (){
        return this.editor.getContent();
    },
    /*获取是否必填*/
    getRequired: function (){
        return this.$config.find('.e-question-required').prop('checked');
    },
    /*获取是否必填*/
    getPhotoCtr: function (){
        if(this.c.control.indexOf('addPhoto') == -1){
            return '';
        }
        var data = {
            has_pic : this.$config.find('.e-question-photo').prop('checked'),
            require_pic : this.$config.find('.e-question-photo-r').prop('checked'),
        };
        return data;
    },
    /*获取选项*/
    getOptions: function (){
        if(!this.$select){
            return;
        }
        var that = this;
        var optionsArr = [];
        $.each(this.$select.find('tbody tr'), function (index){
            var data = {
                no: index + 1,
                caption: $(this).find('.q-option-text').val(),
                image: $(this).find('.q-option-img').attr('file-name') || '',
                has_pic: $(this).find('.p-option-photo').prop('checked'),
                require_pic: $(this).find('.p-option-photo-r').prop('checked'),
            };
            optionsArr.push(data);
        });
        return optionsArr;
    },
};

/**
 * 1.单选题
 */
var SingleChoice = function (t, conf){
    SingleChoice.superClass.constructor.call(this, t, conf);
};
extend(SingleChoice, View);
SingleChoice.prototype.config = function (){
    this.c = {
        questionType : 'single-choice',
        displayType : '单选题',
        editorText: '<p>请点击编辑按钮进行试题编辑</p>',
        control : ['isRequired', 'addSelect'],
        option : ['text', 'img', 'photo', 'control'],
    };
};

/**
 * 2.多选题
 */
var MultipleChoice = function (t, conf){
    MultipleChoice.superClass.constructor.call(this, t, conf);
};
extend(MultipleChoice, View);
MultipleChoice.prototype.config = function (){
    this.c = {
        questionType : 'multiple-choice',
        displayType : '多选题',
        editorText: '<p>请点击编辑按钮进行试题编辑</p>',
        control : ['isRequired', 'addSelect'],
        option : ['text', 'img', 'photo', 'control'],
    };
};

/**
 * 3.问答题
 */
var QuestionAnswer = function (t, conf){
    QuestionAnswer.superClass.constructor.call(this, t, conf);
};
extend(QuestionAnswer, View);
QuestionAnswer.prototype.config = function (){
    this.c = {
        questionType : 'question-answer',
        displayType : '问答题',
        editorText: '<p>请点击编辑按钮进行试题编辑</p>',
        control : ['isRequired', 'addPhoto'],
        option : [],
    };
};

/**
 * 4.段落说明
 */
var Paragraph = function (t, conf){
    Paragraph.superClass.constructor.call(this, t, conf);
};
extend(Paragraph, View);
Paragraph.prototype.config = function (){
    this.c = {
        questionType : 'paragraph',
        displayType : '段落说明',
        editorText: '<p>请点击编辑按钮编辑段落说明</p>',
        toolbars : ['edit', 'del', 'up', 'down', 'first', 'last'],
        control : [],
        option : [],
    };
};

/**
 * 5.拍照题
 */
var PhotoTake = function (t, conf){
    PhotoTake.superClass.constructor.call(this, t, conf);
};
extend(PhotoTake, View);
PhotoTake.prototype.config = function (){
    this.c = {
        questionType : 'photo-take',
        displayType : '拍照题',
        editorText: '<p>请点击编辑按钮进行试题编辑</p>',
        toolbars : ['edit', 'del', 'up', 'down', 'first', 'last'],
        control : ['isRequired'],
        option : [],
    };
};


/**
 * @param subClass  子类
 * @param superClass   父类
 */
function extend(subClass, superClass) {
    var F = function () {};
    F.prototype = superClass.prototype;
    subClass.prototype = new F();
    subClass.superClass = superClass.prototype;
}
function changeArr(arr, x, y){
    arr.splice(x - 1, 1, arr.splice(y - 1, 1, arr[x - 1])[0])
}
/**
 * Created by Yuffie on 2016/12/9.
 */

var Q = (function () {
    var QEditor = {
        version: "1.0",
        create: {
            'single-choice': SingleChoice,
            'multiple-choice': MultipleChoice,
            'question-answer': QuestionAnswer,
            'paragraph':　Paragraph,
            'photo-take': PhotoTake,
        },
        questionArr: [],
        init: function (ele, conf){
            var that = this;
            this.$wrapper = $('#' + ele);
            this.conf = conf;
            this.title = typeof conf != 'undefined' ? conf.name : '问卷标题';
            this.subject = typeof conf != 'undefined' ? conf.subject : '添加问卷说明';
            var imageUrl = {
                imagePath: _NoticeUrl,
                scrawlPath: _NoticeUrl,
                filePath: _NoticeUrl,
                catcherPath: _NoticeUrl,
                imageManagerPath: _NoticeUrl,
                snapscreenPath: _NoticeUrl,
                wordImagePath: _NoticeUrl,
                videoPath: _NoticeUrl,
            };
            UeditorConfig = $.extend(UeditorConfig, imageUrl);
            this.renderUI(conf);
            this.initEvents();
            if(typeof this.conf != 'undefined'){
                setTimeout(function (){
                    that.setValue();
                }, 1000)
            }
        },
        initEvents: function (){
            var that = this;
            /*试卷说明*/
            var ueditor = document.getElementById('p_title_ueditor');
            this.ueditor = UE.getEditor(ueditor, UeditorConfig);

            var dialog_p_title = {};
            dialog_p_title = new Dialog({ width : 610, height: 500 }, this.$title_e);
            that.$title.on('click', function (){
                dialog_p_title.open();
            });
            dialog_p_title.$t.find('#p_title_submit').on('click', function (){
                var title = that.$title_e.find('.p_title').val().trim();
                if(title.length == 0){
                    title = '问卷标题';
                    that.$title_e.find('.p_title').val(title);
                }
                var info = that.ueditor.getContent().trim();
                if(info.length == 0){
                    info = '添加问卷说明';
                    that.ueditor.setContent('<p>添加问卷说明</p>');
                }
                that.$title.find('.paper_title').html(title);
                that.$title.find('.paper_desc').html(info);
                dialog_p_title.close();
            });
        },
        renderUI: function (conf){
            var that = this;
            /*试卷标题和说明*/
            this.$content = $('<div class="e-paper"></div>').appendTo(this.$wrapper);
            this.$title = $('<div class="e-paper-title-wrapper c-hover" title="编辑问卷标题与问卷说明">' +
                '<h1 class="paper_title">' + this.title + '</h1>' +
                '<div class="paper_desc">' + this.subject + '</div>' +
                '</div>').appendTo(this.$content);
            /*试卷标题和说明编辑器*/
            this.$title_e = $('<div class="c-group-row" style="display: none">' +
                '<div class="c-row-input">' +
                '<label>问卷标题：</label>' +
                '<input class="p_title" type="text">' +
                '</div>' +
                '<div class="c-row-input">' +
                '<label>问卷说明：</label>' +
                '<script id="p_title_ueditor" type="text/plain" >添加问卷说明</script>' +
                '</div>' +
                '<div class="c-row-btn">' +
                '<button id="p_title_submit" class="c-button">确定</button>' +
                '</div>' +
                '</div>').appendTo('body');
        },
        createQuestion: function (questionType, conf){
            var question = new QEditor.create[questionType](this.$content, conf);
            this.questionArr.push(question);
        },
        getPaperName: function (){
            return this.$title.find('.paper_title').html();
        },
        getPaperDesc: function (){
            return this.ueditor.getContent();
        },
        getQuestion: function (){
            var questions = [];
            var index = 1;
            $.each(questionArr, function (){
                var value = this.getValue();
                if(this.c.questionType != 'paragraph'){
                    value.no = index;
                    index++;
                } else {
                    value.no = '';
                }
                questions.push(value);
            });
            return questions;
        },
        getValue: function (){
            var data = {
                guid: _guid,
                name: this.getPaperName(),
                subject: this.getPaperDesc(),
                questions: this.getQuestion(),
            };
            console.log(JSON.stringify(data));
            return data;
        },
        setValue: function (){
            var that = this;
            this.$title_e.find('.p_title').val(this.title);
            this.ueditor.setContent(this.subject);
            $.each(this.conf.questions, function (index){
                var type = this.type;
                that.createQuestion(type, that.conf.questions[index]);
            });
        },
    };

    return QEditor;
})();

