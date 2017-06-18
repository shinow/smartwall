define(function(require, exports) {
	var utils = require("utils/utils");
	var form = require("pub/form_dms");
	exports.date = function(id) {
		$(id).datetimepicker({
			timepicker : false,
			format : 'Y-m-d'
		});
	};

	exports.Combobox = function(tName, flag) {
		var options = {
			width : 180,
			selectBoxWidth : 200,
			selectBoxHeight : 200,
			valueField : 'id',
			treeLeafOnly : false,
			tree : {
				url : form.getNodeData(tName),
				checkbox : flag,// 是否单选
				ajaxType : 'get',
				idFieldName : 'id',
				parentIDFieldName : 'pid',
			}
		};
		return options;
	};
    exports.popSelect = function(config) {
        var options = {
        	width: 100,
            slide: false,
            selectBoxWidth: 200,
            selectBoxHeight: 240,
            valueField: 'id',
            textField: 'text',
            grid: getGrid(config),
            condition: config.condition
        };
        return options;
    };
    function getGrid(config)
    {
        var options = {
            columns: config.columns,
			switchPageSizeApplyComboBox: false,
            data: $.extend({}, config.data),
            //url : 'xxx',
            pageSize: 10,
            checkbox: false,
        };
        return options;
    }

    exports.getGridOptions = function(data, url, singleCheck, usePager) {
		var columnsData = [];
		$.each(data, function() {
			var column;
			if (this.hide) {
				column = {
					name : this.name,
					hide : true
				}
			} else {
				var that = this;
				column = {
					display : this.display || '',
					type : this.type || 'text',
					name : this.name || '',
					render:function(rowdata, index, value){
						if(value && that.type && that.type==="date"){
                            var newTime = /\d{4}-\d{1,2}-\d{1,2}/g.exec(value);
                            return newTime[0];
						}else{
                            return value;
						}

					},
					align : this.align || 'left',
					width : this.width || 100,
					minWidth : 60
				}
			}
			columnsData.push(column);
		});
		var options = {
			columns : columnsData,
			switchPageSizeApplyComboBox : false,
			url : url,
            usePager: usePager ? usePager: true,
			pageSize : 10,
            dataAction: 'server',
            pageSizeOptions: [10,30,50,100,200],  //可选择设定的每页结果数
			checkbox : true,
			isSingleCheck : singleCheck,//isChecked,isSelected
            isChecked: f_isChecked,
			onCheckRow: f_onCheckRow,
			onCheckAllRow: f_onCheckAllRow

        };
		return options;
	};
    function f_onCheckAllRow(checked)
    {
        for (var rowid in this.records)
        {
            if(checked)
                addCheckedCustomer(this.records[rowid]['guid']);
            else
                removeCheckedCustomer(this.records[rowid]['guid']);
        }
    }

	/*
	 该例子实现 表单分页多选
	 即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
	 */
    checkedCustomer = [];
    function findCheckedCustomer(guid)
    {
        for(var i =0;i<checkedCustomer.length;i++)
        {
            if(checkedCustomer[i] == guid) return i;
        }
        return -1;
    }
    function addCheckedCustomer(guid)
    {
        if(findCheckedCustomer(guid) == -1)
            checkedCustomer.push(guid);
    }
    function removeCheckedCustomer(guid)
    {
        var i = findCheckedCustomer(guid);
        if(i==-1) return;
        checkedCustomer.splice(i,1);
    }
    function f_isChecked(rowdata)
    {

        if (findCheckedCustomer(rowdata.guid) == -1)
            return false;
        return true;
    }
    function f_onCheckRow(checked, data)
    {
        if (checked) addCheckedCustomer(data.guid);
        else removeCheckedCustomer(data.guid);
    }
    function f_getChecked()
    {
        alert(checkedCustomer.join(','));
    }

    exports.checkCode = function(code, _bizCode, _guid) {
		var deferred = $.Deferred();// 创建一个延迟对象
		$.ajax({
			url : CONTEXT + "check/custom/checkCode.mvc?bizCode=" + _bizCode
					+ "&code=" + code + "&guid=" + _guid,
			async : false,// 要指定不能异步,必须等待后台服务校验完成再执行后续代码
			dataType : "json",
			success : function(datas) {
				if (datas) {
					deferred.resolve();
				} else {
					deferred.reject();
				}

			}
		});
		return deferred.state() == "resolved" ? true : false;
		// deferred.state()有3个状态:pending:还未结束,rejected:失败,resolved:成功
	};
	exports.del = function(code, datas, cGrid) {
		var data = {
			tName : code,
			guids : datas
		};
		utils.getJson("common/batchDel.mvc", {
			data : JSON.stringify(data),
		}, function(result) {
			if (result["code"] == "0") {
				Notyf.success("操作成功");
				cGrid.getGrid().loadData();
			} else {
				Notyf.tips(result.message);
			}
		});
	};
	exports.status = function() {
		var data = [ {
			text : "启用",
			id : 0
		}, {
			text : "停用",
			id : 1
		} ];
		return data;
	}
	exports.color = function() {
		var data = [ {
            text : "白色",
            id : "white"
        }, {
            text : "橙色",
            id : "orange"
        }, {
            text : "粉红",
            id : "pink"
        },{
            text : "黑色",
            id : "black"
        },{
			text : "红色",
			id : "red"
		},{
            text : "灰色",
            id : "gray"
        },{
            text : "军蓝",
            id : "cadetblue"
        },{
            text : "蓝色",
            id : "blue"
        }, {
            text : "绿色",
            id : "green"
        },  {
            text : "米色",
            id : "beige"
        },{
            text : "浅红",
            id : "lightred"
        },{
            text : "浅灰",
            id : "lightgray"
        },{
            text : "浅蓝",
            id : "lightblue"
        },{
            text : "浅绿",
            id : "lightgreen"
        },{
			text : "深红",
			id : "darkred"
		},{
            text : "深蓝",
            id : "darkblue"
        }, {
			text : "深绿",
			id : "darkgreen"
		}, {
            text : "深紫",
            id : "darkpurple"
        },{
			text : "紫色",
			id : "purple"
		},     ];
		return data;
	};
	exports.getComboboxData = function(code, guid) {
		var data = {
			"code" : code,
			"guid" : guid
		};
		utils.postJson(context + "webcontrol//combobox/data.mvc", {
			data : JSON.stringify(params),
		}, function(result) {
			if (result.code == 0) {
				_form.setData(result.message);
				cb();
			} else {
				Notyf.tips(result.message);
			}
		});
	}
	/**
	 * 获取当天时间
	 */
	exports.getNowFormatDate = function() {
		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1
				+ strDate;
		/*
		 * + " " + date.getHours() + seperator2 + date.getMinutes() + seperator2 +
		 * date.getSeconds();
		 */
		return currentdate;
	}
	// 添加遮罩层
	exports.coverDiv = function() {
		var div = document.createElement('div'); // 新增元素
		var diva = document.getElementById('myMask'); // 获取id为a的元素
		diva.parentNode.insertBefore(div, diva); // 在这个元素前面增加上去
		div.setAttribute("id", "mybg"); // 定义该div的id
		// div.style.background = "#777";
		div.style.width = "100%";
		div.style.height = "100%";
		div.style.position = "fixed";
		div.style.top = "0";
		div.style.left = "0";
		div.style.zIndex = "500";
		div.style.opacity = "0.6";
		div.style.filter = "Alpha(opacity=70)";
		diva.parentNode.insertBefore(div, diva); // 在这个元素前面增加上去
	};
	// 取消遮罩
	exports.hideCover = function() {
		var mybg = $("#mybg");// document.getElementById("mybg");
		$("#mybg").remove();

	};

    exports.changeStatus = function(code,type, cGrid) {
        var rows = cGrid.getGrid().getSelectedRows();
        var guids = [];
        $.each(rows, function () {
            guids.push(this.guid);
        });
        var data = {
            code : code,
            guids : guids,
			status : 1,
        };
        utils.getJson("order/status/changeStatus/"+type+".mvc", {
            data : JSON.stringify(data),
        }, function(result) {
            if (result["code"] == "0") {
                Notyf.success("操作成功");
                cGrid.getGrid().loadData();
            } else {
                Notyf.tips(result.message);
            }
        });
    };
});