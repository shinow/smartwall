/**
 * Created by Yuffie on 2017/2/20.
 */
define(function (require, exports) {
    /**
     * 有关form的页面分为两种情况
     * 1.点击按钮弹出窗口展示表格，htmlType的值且默认值为iframe
     * 2.点击按钮跳转页面展示表格，htmlType的值为open
     */
    var htmlType = getQueryString('type') || 'iframe';
    if(htmlType != 'iframe'){
        require("ligerui-css");
        require("ligerui-css-icons");
    }

    require("jquery_validate_all");
    if(htmlType != 'iframe'){
        require("ligerui");
    }
    var formUtils = require("utils/formUtilsDms");
    var utils = require("utils/utils");
    var tree = require("pub/tree");
    //自定义字段变量数组
    var ext_fields = [];
    /**
     * 变量用途
     * Yuffie   对外暴露方法
     * guid     修改时需要用到的guid，若没有则为新增
     * _formConfig  表单生成配置信息
     * _form$       表单和表格DOM对象,ligerUI根据此生成，也可再此处修改表单或者表格id
     * _form, _grid     表单和表格对应的ligerUI对象
     * *********
     * _type  操作类型: add|edit
     * _bizCode  业务代码
     * _guid  当前数据guid
     * _shouldCheck  是否需要验证编码
     * _win  窗体
     */
    Yuffie = {};
    var guid = utils.getQueryString('guid');
    var _config, _formConfig;
    var _fields = [], count = 0, popupNum = 0;
    var _form$ = htmlType != 'iframe' ? $('<form id="formID"></form>').appendTo($("#list")) : $('<form id="formID" style="visibility: hidden"></form>').appendTo($("body"));
    var _form, _grid;
    var _guid, _type, _bizCode, _shouldCheck, _win;
    var prevUrl = document.referrer;

    /**
     * 页面初始化方法
     * @param config 表单和表格及其他的配置信息
     */
    exports.init = function (config) {
        _config = config;
        _formConfig = config._formConfig;
        getFieldsExt(config.is_extend,config.line_num);
    };

    /**
    * 判断模块是否支持自定义字段扩展功能。
    * flag 是否具有自定义字段功能   num 每行显示个数
    * yty 2017.4.7
    **/
    function getFieldsExt(flag,num){
        if(flag){
            utils.postJson("field/extend/getAllFields/" + _formConfig.bizCode +".mvc",  function(result) {
                        if (result.code == 0) {
                          var i = 0;
                          $.each(result.message,function(){
                           if('select' == this.type){
                                var sources = this.source;
                                var sdata =[];
                                $.each(exec(sources),function(){
                                    var v = {};
                                    v['id'] = this;
                                    v['text'] = this;
                                    sdata.push(v);
                                })
                               _formConfig.columns.push({
                                   display : this.display,
                                   type : this.type,
                                   name : this.guid,
                                   group : '自定义字段',
                                   newline : 0 == i%num,
                                   validate:{
                                       required : 0 == this.required,
                                       maxLength : 200,
                                   },
                                   config: {
                                       type: 'data',
                                       source: sdata,
                                   },
                               });
                           }else{
                               _formConfig.columns.push({
                                   display : this.display,
                                   type : this.type,
                                   name : this.guid,
                                   group : '自定义字段',
                                   newline : 0 == i%num,
                                   validate:{
                                       required : 0 == this.required,
                                       maxLength : 200,
                                   }
                               });
                           }
                            ext_fields.push(this.guid);
                            i++;
                          })
                        if(typeof guid != 'undefined'){
                            getAllData();
                        } else {
                            formatFormConfig();
                        }
                        } else {
                            Notyf.tips(result.message);
                        }
                    });
        }else{
            if(typeof guid != 'undefined'){
                getAllData();
            } else {
                formatFormConfig();
            }
        }
    }

    /**
     * 修改时，获取所有的表格表单数据
     */
    function getAllData(){
        /**
         * 增加is_extend 参数，用于区分使用模块是否能配置自定义字段
         */
        var data = {"is_extend": _config.is_extend, "code": _formConfig.bizCode, "guid": guid };
        var url = 'common/getData.mvc';
        utils.postJson(url, {data: JSON.stringify(data)}, function (result) {
            if (result.code == 0) {
                var formData = {
                    Rows: result.message
                };
                formatFormConfig(formData);
            } else {
                Notyf.tips(typeof result.message != 'undefined' ? result.message : '获取表单或表格数据失败');
            }
        });
    }

    /**
     * 渲染表单
     * @param data 若有值时进行赋值
     */
    function renderForm(data) {
        _form = _form$.ligerForm({//ligerPanel
            width: _formConfig.width - 60 || 960,
            inputWidth: _formConfig.inputWidth || 190,
            labelWidth: _formConfig.labelWidth || 110,
            space: _formConfig.space || 20,
            labelAlign: 'right',
            validate: true,
            allowClose: true,
            isHidden: true,
            fields: _fields,
            onAfterSetFields: function (){
                if(typeof _formConfig.onAfterSetFields != 'undefined'){
                    _formConfig.onAfterSetFields();
                }
            }
        });

        //若为弹出页面需要添加保存取消按钮
        if(htmlType != 'iframe'){
            renderBotton();
        }
        //若data有值，需要给form进行赋值
        if(typeof data != 'undefined'){
            setFormData(data);
            defaultValue(true);
        } else {
            defaultValue(false);//为false需要给付初始值
        }

    }

    /**
     * 渲染提交，取消按钮
     */
    function renderBotton(){
        var button = $('<div class="btn-inner" style="margin-top:3px;"></div>');
        $('<a class="l-button l-button-default" onclick="Yuffie.save()">保存</a>').appendTo(button);
        $('<a class="l-button l-button-default" onclick="Yuffie.cancel()">取消</a>').appendTo(button);
        $('#btns').append(button);
        /*var button = $(
            '<div class="btn-inner" >' +
            '<a class="l-button l-button-default" onclick="Yuffie.save()">保存</a>' +
            '<a class="l-button l-button-default" onclick="Yuffie.cancel()">取消</a>' +
            '</div>'
        ).appendTo($('btns'));*/
    }

    /**
     * 有些组件需要设置初始值或设置不能修改
     */
    function defaultValue(isChange){
        $.each(_fields, function (){
            var key = this.name;
            var disabled = this.disabled || false;
            var input = $('input[name="'+ key + '"]');
            input.attr('disabled', disabled);
            if(!isChange){
                var defaultValue = this.defaultValue;
                var type = this.type;
                if(typeof defaultValue == 'undefined' || !(type == 'text' || type == 'date' || type == 'number' || type=='hidden')){
                    return;
                }
                var valueType = defaultValue.type;
                var source = defaultValue.source;
                switch (valueType){
                    case "autoNumber":
                        utils.postJson('dms/code/initialize.mvc?code='+source,null, function (result) {
                            if (result.code == 0) {
                                input.val(result.message);
                            } else {
                                Notyf.tips(typeof result.message != 'undefined' ? result.message : '获取表单或表格数据失败');
                            }
                        });
                        break;
                    case "text":
                    case "date":
                        input.val(source);
                        break;
                    default:
                        break;
                }
            }
        })
    }

    /**
     * 保存和修改功能 save
     * 返回按钮 cancel
     */
    Yuffie.save = function (){
        if (!_form.valid()) {
            Notyf.tips('请检查带星项是否输入正确');
            return;
        }
        showMask();
        var formData = _form.getData();
        if(_config.is_extend && ext_fields.length > 0){
            //配置有自定义字段，特殊处理 yty 2017.4.7
             var extendFields = {};
             for (var Key in formData) {
                if (ext_fields.indexOf(Key)> -1) {
                    extendFields[Key] = formData[Key];
                    delete formData[Key];
                }

             }
             formData['extendFields'] = extendFields;
        }

        /**
         * 由于此处过滤了空值，所以一些非必填项的值清空后保存无效，
         * 暂时改为直接获取formdata。
         */
        //var formData = getFormData();
        //formData = formatFormData(formData);
        var url = 'common/insert.mvc';
        if(typeof guid != 'undefined'){
            formData.guid = guid;
            url = 'common/update.mvc';
        }
        var allData = {
            is_extend: _config.is_extend,
            code: _formConfig.bizCode,
            values: formData,
        };
        utils.postJson(url, {data: JSON.stringify(allData)}, function(data) {
            if (data.code == 0) {
                Notyf.success('保存成功');
                setTimeout(function (){
                    hideMask();
                    window.location.href = prevUrl;
                }, 2000);
            } else {
                hideMask();
                Notyf.tips(typeof data.message != 'undefined' ? data.message : '保存失败');
            }
        });
    };
    Yuffie.cancel = function (){
        window.location.href = prevUrl;
    };

    /**
     * 获取form的值 getFormData
     * @returns {key:value,key:value, ...}
     * 设置form的值 setFormData
     * @param formData
     * 剔除isSave为false的form值
     */
    function getFormData() {
        var values = _form.getData();
        for (var Key in values) {
            if (values[Key] === "") {
                delete values[Key];
            }
        }
        return values;
    }
    function setFormData(formData){
        if (formData.Rows) {
            _form.setData(formData.Rows);
        }
    }
    function formatFormData(formData){
        $.each(_formConfig.columns, function () {
            if ((typeof this.isSave == 'boolean') && !this.isSave) {
                delete formData[this.name];
            }
        });
        return formData;
    }

    /**
     * 重置表单配置
     * @param config
     */
    function formatFormConfig(formData) {
        var popupFlag = true;
        $.each(_formConfig.columns, function (index) {
            var type = this.type;
            var conf = {};
            if (typeof type == 'undefined') {
                return false;
            }
            switch (type) {
                case "select":
                case "combobox":
                case "radiolist":
                    conf = formatFormConfig.combobox(this);
                    break;
                case "popup":
                    popupFlag = false;
                    formatFormConfig.popup(this, index, formData);
                    conf = this;
                    break;
                default:
                    conf = this;
                    break;
            }
            _fields.push(conf);
        });
        if(popupFlag){
            renderForm(formData);
        }
    }
    formatFormConfig.combobox = function (conf) {
        if(typeof conf.config == 'undefined'){
            return conf;
        }
        conf.comboboxName = conf.name;
        var optionType = conf.config.type;
        var optionSource = conf.config.source;
        delete conf.config.source;
        delete conf.config.type;
        if (optionType == 'dict') {
            conf.options = conf.config;
            conf.options.url = CONTEXT + "webcontrol/combobox/data.mvc?code=dms_dict&mainCode=" + optionSource;
        } else if (optionType == 'url') {
            conf.options = conf.config;
            conf.options.url = CONTEXT + "webcontrol/combobox/data.mvc?code=" + optionSource;
        } else if (optionType == 'data') {
            conf.options = conf.config;
            conf.options.data = optionSource;
        } else if (optionType == 'tree') {
            conf.options = formUtils.Combobox(optionSource, false);
            conf.options.onselected = conf.config.onselected;
        }else if(optionType == 'popup'){
            conf.options = formUtils.popSelect(optionSource, false);
        } else {
            alert('label为' + conf.display + '的组件类型配置错误。');
        }
        delete conf.config;
        return conf;
    };
    formatFormConfig.popup = function (conf, index, formData) {
        if(typeof conf.config == 'undefined'){
            return;
        }
        conf.comboboxName = conf.name;
        var config = conf.config;
        var popupType = typeof config.type == 'undefined' ? 'grid': config.type;
        var popupSource = config.source;
        var width = config.width;
        var height = config.height;
        var params = config.params;
        var gridUrl = createUrl(popupSource,params);
        popupNum++;
        var url = CONTEXT + 'grid/config.mvc?code=' + popupSource;
        if(popupType == 'grid'){
            $.ajax({
                type: 'GET',
                url: url,
                success: function (result){
                    if(typeof result != 'undefined'){
                        count++;
                        var editor = {
                            type: 'popup',
                            valueField: 'guid',
                            textField: 'name',
                            grid: {
                                width: width || 400,
                                height: height || 300,
                                columns : toGridColumns(result.columns),
                                switchPageSizeApplyComboBox : false,
                                url : gridUrl,
                                pageSize : 10,
                                checkbox : true,
                                isSingleCheck : true,
                            },
                            condition: {
                                labelWidth : '',
                                prefixID: 'condtion_',
                                fields: formatFilterConfig(result.filters),
                            },
                        };
                        conf.editor = editor;
                        delete conf.config;
                        _fields[index] = conf;
                        if(count == popupNum){
                            renderForm(formData);
                        }
                    }
                },
            });
        }

    };

    /**
     * 重置表格配置
     * @param conf
     */
    function toGridColumns(conf) {
        var columns = [];
        $.each(conf, function() {
            if (this["visible"]) {
                var that = this;
                var column = {
                    display: this["caption"],
                    name: this["code"],
                    width: this["width"],
                    type: (this["type"] || ""),
                    align: (this["align"] || "").toLowerCase(),
                    showInSuperFilter: this["showInSuperFilter"]
                };
                if (this['valueFunc']) {
                    var fnObj = this["valueFunc"].split(",");
                    var fnName = fnObj.shift();
                    column.render = Grid[fnName].apply(null, fnObj);
                } else {
                    var render = this["type"];
                    var renderFn;

                    switch (render) {
                        case "STRING":
                            renderFn = _RENDERS["stringRender"];
                            break;
                        case "NUMBER":
                            renderFn = _RENDERS["stringRender"];
                            break;
                        case "DATE":
                            renderFn = _RENDERS["stringRender"];
                            break;
                        case "DATETIME":
                            renderFn = _RENDERS["stringRender"];
                            break;
                        case "BOOL":
                            renderFn = _RENDERS["stringRender"];
                            break;
                        case "IMAGE":
                            renderFn = _RENDERS["stringRender"];
                            break;
                        case "BIGTEXT":
                            renderFn = _RENDERS["stringRender"];
                            break;
                        case "CURRENCY":
                            renderFn = _RENDERS["stringRender"];
                            break;
                    }
                    if (renderFn) {
                        column.render = renderFn;
                    }
                }
                //若未设置组件宽度，默认为100
                column.width = column.width == 0 ? 100 : column.width;
                columns.push(column);
            }
        });
        return columns;
    };
    var _RENDERS = {};
    _RENDERS.stringRender = function(rowdata, index, value) {
        return value;
    };

    /**
     * 重置过滤配置
     * @param filterConfig
     */
    function formatFilterConfig(filterConfig) {
        var newFilterConfig = [];
        $.each(filterConfig, function (){
            var conf = {
                name : this.code,
                type : this.type,
                label : this.caption,
                newline : false
            };
            newFilterConfig.push(conf);
        });
        return newFilterConfig;
    }


    /**
     * 以下方法用于htmlType为iframe
     */
    function ensureForm(conf) {
        if (!_form) {
            _formConfig = conf;
            formatFormConfig();
        } else {
            resetForm(conf);
        }
        document.onkeydown = function(event){
            if (event && event.keyCode==27 ){//|| event.keyCode==96
                if (_win) {
                    _win.hide();
                }
            }
        }
    };

    function resetForm(conf) {
        var empty = {};
        $.each(conf.columns, function() {
            _form.hideFieldError(this["name"]);
            if(this["type"] == 'checkbox'){
                empty[this["name"]] = false;
            }else if(this["type"] == 'select'){
                liger.get(this["name"]).clear();
            }else {
                empty[this["name"]] = "";
            }
        });
        var radio = $('#formID input[type=radio]');
        $.each(radio, function (){
            this.checked = false;
        });

        _form.setData(empty);
        _guid = null;
    };

    function checkSelectedRow() {
        var row = _grid.getGrid().getSelectedRow();
        if (!row) {
            Notyf.tips('请选择行');
            return false;
        }
        return true;
    };

    function showSelectedData(conf, cb) {
        if (!checkSelectedRow()) {
            return;
        };

        var row = _grid.getGrid().getSelectedRow();
        _guid = row["guid"];
        if (!_guid) {
            Notyf.tips("guid值不存在，请检查");
        }

        var params = {
            code: _bizCode,
            guid: row["guid"]
        };

        utils.postJson("common/getData.mvc", {
            data: JSON.stringify(params),
        }, function(result) {
            if (result.code == 0) {
                _form.setData(result.message);
                cb();
            } else {
                Notyf.tips(result.message);
            }
        });
    };

    submitForm = function() {
        showMask();
        if (!_form.valid()) {
            Notyf.tips('请检查带星项是否输入正确');
            hideMask();
            return;
        }

        var data = {
            code: _bizCode,
            //values: formatFormData(getFormData()),
            /**
             * 由于此处过滤了空值，所以一些非必填项的值清空后保存无效，
             * 暂时改为直接获取formdata。
             */
            values: _form.getData(),
        };

        var url;
        if (_type == "edit") {
            data["values"]["guid"] = _guid;
            url = 'common/update.mvc';
        } else {
            url = 'common/insert.mvc';
        }
        utils.postJson(url, {
            data: JSON.stringify(data),
        }, function(result) {
            hideMask();
            if (result.code == 0) {
                if (_win) {
                    _win.hide();
                }
                Notyf.success("操作成功");
                _grid.getGrid().loadData();
            } else {
                /*if (_win) {
                    _win.hide();
                }*/
                parent.Notyf.tips(result.message);
            }
        });
    };

    cancleForm = function() {
        if (_win) {
            _win.hide();
        }
    };

    showAddForm = function(conf) {
        ensureForm(conf);

        _bizCode = conf["bizCode"];
        if (!_bizCode) {
            Notyf.tips("bizCode配置不存在，请修改");
        }

        _type = "add";

        if (_win) {
            _win._setTitle("增加");
            _win.show();
        } else {
            _win = $.ligerDialog.open({
                target: _form$,
                width: conf.width || 420,
                height: conf.height || 500,
                title: '增加',
                onAfterSetFields: conf["onAfterSetFields"] || function() {
                    alert("onAfterSetFields")
                },
                buttons: [{
                    text: "保存",
                    onclick: function(a,b) {
                        submitForm();
                    }
                }, {
                    text: "取消",
                    onclick: function() {
                        cancleForm();
                    }
                }]
            });
        }
        _form$.css('visibility', 'inherit');
    };

    showEditForm = function(conf) {
        var rows = _grid.getGrid().getSelectedRows();
        if (rows.length==0 || rows.length > 1) {
            Notyf.tips('请选择一条数据');
            return;
        }
        _bizCode = conf["bizCode"];

        if (!_bizCode) {
            Notyf.tips("bizCode配置不存在，请修改");
        }

        _type = "edit";
        ensureForm(conf);
        showSelectedData(conf, function() {
            if (_win) {
                _win._setTitle("修改");
                _win.show();
            } else {
                _win = $.ligerDialog.open({
                    target: _form$,
                    width: conf.width || 420,
                    height: conf.height || 500,
                    title: '修改',
                    buttons: [{
                        text: "保存",
                        onclick: function() {
                            submitForm();
                        }
                    }, {
                        text: "取消",
                        onclick: function() {
                            cancleForm();
                        }
                    }]
                });
            }
            _form$.css('visibility', 'inherit');
        });
    };

    del = function(code, onBeforeDel) {
        var rows = _grid.getGrid().getSelectedRows();
        if (rows.length == 0) {
            Notyf.tips('请选择需要删除的数据');
            return;
        }

        if (onBeforeDel) {
            if (onBeforeDel(rows)) {
                Notyf.tips('不能删除父节点');
                return;
            }
        }

        $.ligerDialog.confirm('确定删除选中数据？', function(ok) {
            if (ok) {
                var datas = [];
                $.each(rows, function(n, v) {
                    datas.push(v.guid);
                });
                var data = {
                    tName: code,
                    guids: datas
                };
                utils.getJson("common/batchDel.mvc", {
                    data: JSON.stringify(data),
                }, function(result) {
                    if (result["code"] == "0") {
                        Notyf.success("操作成功");
                        _grid.getGrid().loadData();
                    } else {
                        Notyf.tips(result.message);
                    }
                });
            }
        });
    };

    exports.initDefMethod = function(obj, conf) {
        obj["add"] = obj["add"] || function() {
                var newConf = conf;
                if (conf["beforeAdd"]) {
                    newConf = conf["beforeAdd"]();
                }
                /**@Author:csj    newConf可能为空*/
                if (newConf){
                    showAddForm(newConf);
                }else {
                    return ;
                }

                if (conf["afterAdd"]) {
                    newConf["afterAdd"]();
                }
            };

        obj["edit"] = obj["edit"] || function() {
                if (conf["beforeEdit"]) {
                    conf = conf["beforeEdit"]();
                }
                showEditForm(conf);
                if (conf["afterEdit"]) {
                    conf["afterEdit"]();
                }
            };

        obj.del = obj.del || function() {
                del(conf["bizCode"], conf["onBeforeDel"]);
            };

        obj["addRedirect"] = obj["addRedirect"] || function() {
                window.location = CONTEXT + "dms/goto/" + obj.jsName + ".mvc?"
                    + "type=open";
            };

        obj["editRedirect"] = obj["editRedirect"] || function() {
                var rows = _grid.getGrid().getSelectedRows();
                if (rows.length != 1) {
                    Notyf.tips('请选择一条数据');
                    return;
                }
                window.location = CONTEXT + "dms/goto/" + obj.jsName + ".mvc?"
                    + "guid=" + _grid.getGrid().getSelectedRow().guid
                    + "&type=open";
            };
    };
    exports.setGrid = function(grid) {
        _grid = grid;
    };
    /* 获取下拉数据源URL */
    exports.getSelectDataUrl = function(code) {
        return CONTEXT + "webcontrol/combobox/data.mvc?code=" + code;
    };
    /*
     * 获取树状数据
     */
    exports.getNodeData = function(tName) {
        return CONTEXT + "webcontrol/combobox/queryNodeData.mvc?tName=" + tName;
    }
    /*
     * 获取条件树状数据
     */
    exports.getConditionNodeData = function(condition) {
        return CONTEXT + "webcontrol/combobox/NodeDataByCondition.mvc?condition=" + condition;
    }

    /**
     * 获取指定name的url参数
     */
    function getQueryString(name) {
        var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        }
        return null;
    }
    /**
     * 拼接url参数
     * @param popupSource  表单code
     * @param param  地址中的参数
     * @returns {string}
     */
    function createUrl(popupSource, param){
        var params = "";
        if (null != param) {
            // 开始遍历
            var f = true;
            for (var p in param) { // 方法
                if (typeof(param[p]) == " function ") {
                    param[p]();
                } else { // p 为属性名称，obj[p]为对应属性的值
                    if (f) {
                        params = params + "?" + p + "=" + param[p];
                        f = false;
                    } else {
                        params = params + "&" + p + "=" + param[p];
                    }
                }
            }
            return utils.context + "grid/data/" + popupSource + ".mvc" + params;
        }
        return utils.context + "grid/data/" + popupSource + ".mvc";
    }

    /**
     * 遮罩层用于避免重复提交
     */
    function showMask(){
        var mask = $('#mask');
        if(mask.length != 0){
            mask.show();
        } else {
            mask = $('<div id="mask" class="mask"></div>');
            mask.css({"height":$(document).height(), "width": $(document).width()});
            mask.css({"position":"absolute", "top": '0px', "left": '0px', "opacity": '0',"-moz-opacity": '0',"filter": 'alpha(opacity=0)', "background-color": '#fff', "z-index": '9999'});
            $('body').append(mask);
        }
    }
    function hideMask(){
        $("#mask").hide();
    }

    var ACTIONS = {};
    // 没有别的，避免使用eval
    function exec(action) {
        var p = utils.md5(action);
        var a = ACTIONS[p];
        if (!a) {
            var fn = Function;
            a = ACTIONS[p] = new fn('return ' + action);
        }

        return a();
    };
});


