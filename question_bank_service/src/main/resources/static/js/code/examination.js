/**
 * 编辑调查问卷题目
 * Created by Yuffie on 2017/9/26.
 */

var vm;
var Exam = (function () {
    var GUID = T.getQueryString("guid") || "",
        TENANT_ID = 100,
        CATEGORY = "IMG_STORE",
    Exam = {},
    DATA = {
        isShow: false,
        conf: {
            subjectType: {
                "single-choice": {
                    type: "single-choice",
                    index: 0,
                    no: 0,
                    subject: "<p>请在文本编辑区域内编辑试题</p>",
                    required: true,
                    record: 5,
                    analysis: "无",
                    option: [{
                        no: 0,
                        caption: "选项1",
                        image: "",
                        is_right: true,
                    }, {
                        no: 0,
                        caption: "选项2",
                        image: "",
                        is_right: false,
                    }],
                    value: "",
                },
                "multiple-choice": {
                    type: "multiple-choice",
                    index: 0,
                    no: 0,
                    subject: "<p>请在文本编辑区域内编辑试题</p>",
                    required: true,
                    record: 5,
                    analysis: "无",
                    option: [{
                        no: 0,
                        caption: "选项1",
                        image: "",
                        is_right: true,
                        selected: false,
                    }, {
                        no: 0,
                        caption: "选项2",
                        image: "",
                        is_right: false,
                        selected: false,
                    }],
                    value: "",
                },
                "truefalse-question":{
                    type: "truefalse-question",
                    index: 0,
                    no: 0,
                    subject: "<p>请在文本编辑区域内编辑试题</p>",
                    required: true,
                    record: 5,
                    analysis: "无",
                    answer: true,
                    value: "",
                },
                "question-answer": {
                    type: "question-answer",
                    index: 0,
                    no: 0,
                    subject: "<p>请在文本编辑区域内编辑试题</p>",
                    required: true,
                    record: 5,
                    analysis: "无",
                    value: "",
                },
                "score-choice": {
                    type: "score-choice",
                    index: 0,
                    no: 0,
                    subject: "<p>请在文本编辑区域内编辑试题</p>",
                    required: true,
                    option: [{
                        no: 0,
                        caption: "选项1",
                        record: 5,
                    }, {
                        no: 0,
                        caption: "选项2",
                        record: 5,
                    }],
                    remark: "",
                    value: "",
                },
                "paragraph": {
                    type: "paragraph",
                    index: 0,
                    no: 0,
                    subject: "<p>请在文本编辑区域内编辑试题</p>",
                    required: false,
                },
            },
            optionType: {
                "single-choice": {
                    no: 0,
                    caption: "",
                    image: "",
                    is_right: false,
                },
                "multiple-choice": {
                    no: 0,
                    caption: "",
                    image: "",
                    is_right: false,
                    selected: false,
                },
                "score-choice": {
                    no: 0,
                    caption: "",
                    record: 5,
                },
            },
            recordType: [1,2,3,4,5,6,7,8,9,10],
            optionMaxCount: 5,
        },
        analysis: '',
        subjectType: [
            {type: "single-choice", label: "单选题", flag: true},
            {type: "multiple-choice", label: "多选题", flag: true},
            {type: "truefalse-question", label: "判断题", flag: true},
            {type: "question-answer", label: "问答题", flag: true},
            {type: "score-choice", label: "评分题", flag: true},
            {type: "paragraph", label: "段落说明", flag: true},
        ],
        subjectList: [],
        subjectShow: "",
        subjectShowIndex: "",
        subjectOption: {},
        isEditor: false,
        alertValue: "",
        tenant_id: TENANT_ID,
        category: CATEGORY,
    },
    UeditorConfig = {
        toolbars: [
            [
                'undo', 'redo', '|',
                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', 'indent', '|',

                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
                'link'
            ]
        ],
        initialFrameWidth: '100%',
        initialFrameHeight: 200,
        autoHeightEnabled: false,
        autoFloatEnabled: false,
        elementPathEnabled: false,
    },
    Ueditor;

    Exam.init = function () {
        getData(function (result){
            eInit(result);
        });
    };

    function getData(cb){
        if(!GUID){
            cb(formatData([]));
            return;
        }
        var params = {
            tenant_id: TENANT_ID,
            guid: GUID
        };
        T.get("/exam_service/v1/paper", params, function (result){
            result = result ? result : {content: []};
            cb(formatData(result.content));
        });
    }

    function eInit(data){
        vm = new Vue({
            el: "#app",
            data: DATA,
            methods: {
                /*题目类型展示*/
                subjectTypeHandler: function (type){
                    if(!type){
                        return;
                    }
                    return this.subjectType.filter(function (item){
                        return type == item.type;
                    })[0].label;
                },
                subjectContentHandler: function (item){
                    return item ? item.subject.replace(/<\/?.+?>/g, "").replace(/&nbsp;/g, "") : '';
                },
                subjectImage: function (option){
                    return "http://112.124.108.130/" + option.image;
                },
                /*试题 新增、编辑、排序、删除、保存*/
                createSubject: function (type){
                    var subject = $.extend(true, {}, this.conf.subjectType[type]);
                    this.subjectList.push(subject);
                    if(this.subjectShowIndex === ''){
                        this.subjectShowIndex = this.subjectList.length - 1;
                        this.subjectShow = $.extend(true, {}, this.conf.subjectType[type]);
                    }
                },
                selectSubject: function (item, index){
                    var that = this;
                    if( this.subjectShow != '' && index != this.subjectShowIndex && this.isEditor ){
                        var result = this.subjectVerification();
                        if(typeof result === "string"){
                            T.alert(result);
                            return;
                        }
                        T.confirm('是否要保存当前题目再进行其它试题编辑？', function (result){
                            if(result){
                                that.saveSubject();
                                that.subjectShow = $.extend(true, {}, item);
                                that.subjectShowIndex = index;
                            }
                        });
                    } else {
                        this.subjectShow = $.extend(true, {}, item);
                        this.subjectShowIndex = index;
                    }
                },
                editorSubject: function (){
                    this.isEditor = true;
                },
                upSubject: function (item, index){
                    if(index === 0) return;
                    if(index - this.subjectShowIndex === 0){
                        this.subjectShowIndex--;
                    } else if(index - this.subjectShowIndex === 1){
                        this.subjectShowIndex++;
                    }
                    exchangeSubject(this.subjectList, index + 1, index);
                },
                downSubject: function (item, index){
                    if(index === this.subjectList.length - 1) return;
                    if(index - this.subjectShowIndex === 0){
                        this.subjectShowIndex++;
                    } else if(index - this.subjectShowIndex === -1){
                        this.subjectShowIndex--;
                    }
                    exchangeSubject(this.subjectList, index + 1, index + 2);
                },
                delSubject: function (item, index){
                    if(this.isEditor && index === this.subjectShowIndex){
                        T.alert("正在编辑不能删除");
                        return;
                    }
                    if(index == this.subjectShowIndex){
                        this.subjectShow = "";
                    }
                    if(index < this.subjectShowIndex){
                        this.subjectShowIndex--;
                    }
                    this.subjectList.splice(index, 1);
                },
                saveSubject: function (){
                    var result = this.subjectVerification();
                    if(typeof result === "string"){
                        T.alert(result);
                        return;
                    }
                    this.subjectList[this.subjectShowIndex] = result;
                    this.isEditor = false;
                    this.saveExam();
                },
                subjectVerification: function (){
                    /*验证是否有正确答案*/
                    if(this.subjectShow.type === "multiple-choice"){
                        var rightNum = 0;
                        this.subjectShow.option.map(function (option, optionIndex){
                            rightNum += option.is_right ? 1 : 0;
                        });
                        if(rightNum === 0) return "请至少保留一项正确答案";
                    }
                    return this.subjectShow;
                },
                /*选项控制*/
                confOption: function (item, conf){
                    var type = item.type;
                    if(!type) return;
                    return typeof this.conf.optionType[type][conf] != 'undefined';
                },
                addOption: function (item){
                    var length = this.subjectShow.option.length;
                    if(this.conf.optionMaxCount === length){
                        T.alert('最多只能添加' + length + '个选项');
                        return;
                    }
                    var type = item.type;
                    var option = $.extend(true, {}, this.conf.optionType[type]);
                    option.caption = '选项' + (this.subjectShow.option.length + 1 );
                    this.subjectShow.option.push(option);
                },
                upOption: function (item, index){
                    if(index === 0){
                        return;
                    }
                    exchangeSubject(this.subjectShow.option, index + 1, index);
                },
                downOption: function (item, index){
                    if(index === this.subjectShow.option.length - 1){
                        return;
                    }
                    exchangeSubject(this.subjectShow.option, index + 1, index + 2);
                },
                delOption: function (item, index){
                    if(item.is_right){
                        T.alert('不能删除正确答案选项');
                        return;
                    }
                    if(this.subjectShow.option.length == 1){
                        T.alert('请至少保留一项选项');
                        return;
                    }
                    this.subjectShow.option.splice(index, 1);
                },
                inputTypeOption: function (subjectShow){
                    return subjectShow.type == 'single-choice' ? 'radio' : 'checkbox';
                },
                changeRightOpiton: function (item, index){
                    if(this.subjectShow.type === "single-choice"){
                        this.subjectShow.option.map(function (option, optionIndex){
                            option.is_right = index == optionIndex;
                        });
                    } else {
                        item.is_right = !item.is_right;
                    }
                },
                /*答案解析*/
                analysisEditOpen: function (){
                    this.analysis = this.subjectShow.analysis;
                    vm.$refs.dialog.open();
                },
                analysisEditSave: function (){
                    this.subjectShow.analysis = this.analysis;
                    this.analysis = '';
                    vm.$refs.dialog.close();
                },
                analysisEditCancel: function (){
                    this.analysis = '';
                },
                /*上传图片*/
                selectImage: function (option, index){
                    $("#file-add").val("");
                    $(".q-select-img-text").html("请选择图片");
                    this.subjectOption = option;
                    vm.$refs.dialogImg.open();
                },
                chkimg: function (inp){
                    var path = $(inp.target).val();
                    if(path === ""){
                        $(".q-select-img-text").html("请选择图片");
                        return;
                    }
                    var arr = path.split(".");
                    var length = arr.length;
                    var suf = arr[length - 1];
                    if(suf != "png" && suf != "jpg" && suf !="jpeg"){
                        T.alert("只能上传图片");
                        $(".q-select-img-text").html("请选择图片");
                        $(inp.target).val("");
                        return;
                    }
                    arr = path.split("\\");
                    length = arr.length;
                    suf = arr[length - 1];
                    $(".q-select-img-text").html(suf);
                },
                selectImageToggle: function (){
                    $("#file-add").trigger('click');
                },
                delImage: function (option){
                    option.image = "";
                },
                submitImage: function (){
                    /*判断是否选择图片*/
                    var file = $("#file-add").val();
                    if (!file) {
                        T.alert("请选择文件");
                        return;
                    }
                    /*获取上传图片文件名*/
                    var fileName = file.substr(file.lastIndexOf('\\') + 1);
                    $("#fileName").val(fileName);
                    /*上传图片*/
                    var that = this;
                    var action = "/file-service/v1/upload";
                    $("#form-add").ajaxSubmit({
                        type: 'post',
                        url: action,
                        success: function (result) {
                            console.log(result);
                            that.subjectOption.image = result.message || "";
                            vm.$refs.dialogImg.close();
                        },
                        error: function () {
                            vm.$refs.dialogImg.close();
                            T.alert("上传文件失败");
                        }
                    });
                },
                /*试卷 保存 返回*/
                saveExam: function (str){
                    if(this.isEditor != ''){
                        T.alert('请先完成试题编辑');
                        return;
                    }
                    this.subjectList = this.numberHander(this.subjectList);
                    var url = "/exam_service/v1/paper";
                    var params = {
                        tenant_id: TENANT_ID,
                        data: JSON.stringify({content: this.subjectList}),
                    };
                    if(GUID){
                        params.guid = GUID;
                    }
                    T.post(url, params, function (result){
                        str && T.alert(str, function (){
                            window.history.go(-1);
                        });
                        if(!GUID){
                            GUID = result;
                        }
                    });

                },
                backExamList: function (){
                    window.history.go(-1);
                },
                /*试卷序号计算*/
                numberHander: function (list){
                    var count = 0;
                    return list.map(function (subject, index){
                        subject.index = index + 1;
                        if(subject.type != 'paragraph'){
                            subject.no = ++count;
                        }
                        if(subject.option){
                            subject.option = subject.option.map(function (option, index){
                                option.no = index + 1;
                                return option;
                            });
                        }
                        return subject;
                    })
                },
            },
            computed: {
                /*是否正在编辑试题*/
                subjectShowHandler: function (){
                    return this.subjectShow ? true : false;
                }
            },
            watch: {
                subjectShow: function (){
                    if(this.subjectShow === '') return;
                    var that = this;
                    this.$nextTick(function (){
                        Ueditor = UE.getEditor('myEditor', UeditorConfig);
                        var timer = setInterval( function (){
                            if(Ueditor.getContent() != that.subjectShow.subject){
                                Ueditor.setContent(that.subjectShow.subject);
                            } else {
                                clearInterval(timer);
                            }
                        }, 100 );
                        Ueditor.addListener('contentChange', function() {
                            that.subjectShow.subject = Ueditor.getContent();
                        });
                    });
                }
            }
        });

        vm.$nextTick(function (){
            this.isShow = true;
        });
    }

    function chkimg(inp) {
        if (img)img.removeNode(true);
        img = document.createElement("img");
        img.attachEvent("onreadystatechange", isimg);
        img.attachEvent("onerror", notimg);
        img.src = inp;
    }

    function formatData(content){
        DATA.subjectList = content;
        return DATA;
    }

    function exchangeSubject(arr, x, y){
        arr.splice(x - 1, 1, ...arr.splice(y - 1, 1, arr[x - 1]));
        return arr;
    }

    return Exam;
})();

