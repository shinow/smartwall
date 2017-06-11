define(function(require, exports, module) {
	var utils = require("utils/utils");
	Grid = {};
	Grid.showUrl = function(cellStr, url, title) {
		return function(rowdata, index, value) {
			var u = url + "?guid=" + rowdata["guid"];
			var href = "javascript:Grid._showUrl('" + title + "','" + u + "')";
			return '<a href="' + href + '">' + cellStr + "</a>";
		};
	};
    Grid.statusColor = function () {
        return function (rowdata, index, value) {
            if (value == '有效') {
                return '<span style="padding:3px 12px;border-radius:3px;background-color:#009933; color: #fafafa">有效</span>';
            } else if (value == '无效'){
                return '<span style="padding:3px 12px;border-radius:3px;background-color:#FF3300; color: #fafafa ">无效</span>';
            }
            else if (value == '启用'){
                return '<span style="padding:3px 12px;border-radius:3px;background-color:#009933; color: #fafafa ">启用</span>';
            }
            else if (value == '停用'){
                return '<span style="padding:3px 12px;border-radius:3px;background-color:#FF3300; color: #fafafa ">停用</span>';
            }
        }
    };

    Grid.approval = function () {
        return function (rowdata, index, value) {
        	if (value){
                if (value == '审批通过') {
                    return '<span style="padding:3px 12px;border-radius:3px;background-color:#009933; color: #fafafa">审批通过</span>';
                } else if (value == '审批中'){
                    return '<span style="padding:3px 12px;border-radius:3px;background-color:#FFB35B; color: #fafafa ">审批中</span>';
                }else if (value == '撤销'){
                    return '<span style="padding:3px 12px;border-radius:3px;background-color:#aaaaaa; color: #fafafa ">撤销</span>';
                }else if (value == '拒绝'){
                    return '<span style="padding:3px 12px;border-radius:3px;background-color:#FF3300; color: #fafafa ">拒绝</span>';
                }else {
                    return '<span style="padding:3px 12px;border-radius:3px;background-color:#61CAD0; color: #fafafa">'+value+'</span>';
                }
			}

        }
    };

    //页面网格上色
    Grid.commonColor = function () {
        return function (rowdata, index, value) {
        	if (rowdata.type == "valiad"){
                if (value == 0) {
                    return colorText(0, '无效');
                } else if(value == 1){
                    return  colorText(1, '有效');
                }
			}else if (rowdata.type == "plan"){
                if (value == 0) {
                    return colorText(0, '未开始');
                } else if(value == 1){
                    return  colorText(2, '进行中');
                } else if(value == 2){
                    return  colorText(1, '已完成');
                }
			}else if (rowdata.type == "status"){
                if (value == '启用') {
                    return colorText(1, '启用');
                   // return '<span style="padding:3px 12px;border-radius:3px;background-color:#009933; color: #fafafa">启用</span>';
                } else {
                    return colorText(0, '停用');
                   // return '<span style="padding:3px 12px;border-radius:3px;background-color:#FF3300; color: #fafafa ">停用</span>';
                }
			}else if (rowdata.type == "complete"){
                if (value == 0) {
                    return colorText(0, '未完成');
                } else if(value == 1){
                    return colorText(1, '完成');
                }else if(value == '审核'){
                    return colorText(1, '审核');
				}else if (value == '新建'){
                    return colorText(0, '新建');
				}
			}else if (rowdata.type == "if"){
                if (value == 0) {
                    return colorText(0, '否');
                } else if(value == 1){
                    return  colorText(1, '是');
                }
            }else if (rowdata.type == "pd"){
                if (value == 1) {
                    return colorText(2, '进行中');
                } else if(value == 2){
                    return  colorText(1, '已完成');
                }
            }else if (rowdata.type == "bfDetail"){
                if (value == 0) {
                    return colorText(1, '计划内');
                } else if(value == 1){
                    return colorText(0, '计划外');
                }else if(value == '未完成'){
                    return colorText(0, '未完成');
                }else if (value == '完成'){
                    return colorText(1, '完成');
                }
            }

        }
    };

    function colorText(type, name) {
    	var text = '';
		if (type === 0){
            text =  '<span style="padding:3px 12px;border-radius:3px;background-color:#FF3300; color: #fafafa ">'+ name+ '</span>';
		}else if (type === 1){
            text =  '<span style="padding:3px 12px;border-radius:3px;background-color:#009933; color: #fafafa">'+ name+ '</span>';
		}else if (type === 2){
            text = '<span style="padding:3px 12px;border-radius:3px;background-color:#FFB35B; color: #fafafa">'+ name+ '</span>'
		}
		return text;
    }

    Grid.color = function() {
        return function(rowdata, index, value) {
            var colorName = {
                "darkred" : "深红",
                "red" : "红色",
                "lightred" : "浅红",
                "orange" : "橙色",
                "beige" : "米色",
                "green" : "绿色",
                "darkgreen" : "深绿",
                "lightgreen" : "浅绿",
                "blue" : "蓝色",
                "darkblue" : "深蓝",
                "lightblue" : "浅蓝",
                "cadetblue" : "军蓝",
                "purple" : "紫色",
                "darkpurple" : "深紫",
                "pink" : "粉红",
                "white" : "白色",
                "gray" : "灰色",
                "lightgray" : "浅灰",
                "black" : "黑色",
                "default":""
            };
            var colors = {
                "darkred" : "#8B0000",
                "red" : "#FF0000",
                "lightred" : "#eb7d7f",
                "orange" : "#f69730",
                "beige" : "#ffcb92",
                "green" : "#72b026",
                "darkgreen" : "#728224",
                "lightgreen" : "#bbf970",
                "blue" : "#38aadd",
                "darkblue" : "#0067a3",
                "lightblue" : "#8adaff",
                "cadetblue" : "#436978",
                "purple" : "#ff91ea",
                "darkpurple" : "#d252b9",
                "pink" : "#FFC0CB",
                "white" : "#ffffff",
                "gray" : "#808080",
                "lightgray" : "#D3D3D3",
                "black" : "#000000",
                "default":""
            };
            if(!value){
                value = "default";
            }
            return '<span style="color: #fafafa ;padding:3px 12px;border-radius:3px;background-color:'
                + colors[value] + '">'+colorName[value]+'</span>';
        }
    };

	/**
	 * 显示URL页面
	 */
	Grid._showUrl = function(title, url, width, height) {
		$.ligerDialog.open({
			width: width || 800,
			height: height || 500,
			title: title,
			url: url,
			buttons: [{
				text: "关闭",
				onclick: function() {
					$.ligerDialog.hide();
				}
			}]
		});
	};

	/**
	 * 显示图片
	 */
	Grid.showImage = function() {
		return function(rowdata, index, value) {
			var imgstr = "";
			if (!!value) {
				imgstr += '<div class="itekui-img-cell" style="width:100%;height:100%;text-align:center;vertical-align:middle;">';
				var vs = value.split(",");

				if (vs.length > 1) {
					$.each(vs, function() {
						var pic;
						if (-1 != this.indexOf(".")) {
							//有后缀,web端上传图片
							pic = CONTEXT + 'oss/web/image.mvc?key=' + this;
						} else {
							//无后缀，手机端上传图片
							pic = CONTEXT + 'oss/web/image.mvc?key=imgcache/' + this + ".jpg";
						}
						imgstr += '<img original="' + pic + '" style="width:40px;height:40px;display:block;margin:2px;float:left;cursor:pointer;" src="' + pic + '&thumbnail=true"/>';
					});
				} else {
					var pic;
					if (-1 != value.indexOf(".")) {
						//有后缀,web端上传图片
						pic = CONTEXT + 'oss/web/image.mvc?key=' + value;
					} else {
						//无后缀，手机端上传图片
						pic = CONTEXT + 'oss/web/image.mvc?key=imgcache/' + value + ".jpg";
					}

					imgstr += '<img original="' + pic + '" style="width:84px;height:84px;display:block;margin:auto;cursor:pointer;" src="' + pic + '&thumbnail=true"/>';
				}

				imgstr += "</div>";
			}
			return imgstr;
		};
	};

	Grid.exp = function(grid) {
		var options = grid.options;
		var columns = grid.options.columns;
		var data = [];
		var value = [];

		$.each(columns, function() {
			data.push({
				text: this["display"],
				id: this["name"]
			});

			if (!this["_hide"]) {
				value.push(this["name"]);
			}
		});

		var listBox = $("<div></div>").appendTo($("body"));
		var select = listBox.ligerListBox({
			isShowCheckBox: true,
			isMultiSelect: true,
			width: 250,
			height: 185,
			data: data,
			value: value.join(";")
		});

		$.ligerDialog.open({
			target: listBox,
			title: "请选择导出信息",
			width: 280,
			height: 260,
			buttons: [{
				text: '确定',
				onclick: function(item, dialog) {
					var p = $.extend({}, options.parms);

					if (options.sortName) {
						p = $.extend({
							"sortname": options.sortName,
							"sortorder": options.sortOrder
						});
					}

					expToXls(options._code, select.getValue(), p);
					dialog.close();
				}
			}, {
				text: '取消',
				onclick: function(item, dialog) {
					dialog.close();
				}
			}]
		});

		/* Excel导出 */
		function expToXls(gridCode, values, params) {
			var params = $.extend({
				gridCode: gridCode,
				values: values
			}, params);
			var url = utils.context + "grid/data/expToXls.mvc?" + $.param(params);
			var fra = $("#nv-grid-exp-fra");
			if (fra.length == 0) {
				fra = $('<iframe id="nv-grid-exp-fra" src="" style="display:none"></iframe>').appendTo($('body'));
			}

			fra.attr("src", url);
		};
	};

	Grid.imp = function(grid) {
		var dlg = $('<div id="_global_imp_div" style="width:100%;height:100%;"><iframe frameborder="0" scrolling="auto" id="_global_imp_fra" style="width:100%;height:100%;"></iframe></div>').appendTo($("body"));
		var fra = $("#_global_imp_fra", dlg);
		fra.attr('src', utils.context + 'excel/import.mvc?model=' + grid);

		$.ligerDialog.open({
			target: dlg,
			title: "导入信息",
			width: 600,
			height: 460,
			buttons: [{
				text: '取消',
				onclick: function(item, dialog) {
					dialog.close();
				}
			}]
		});
	};


	Grid.impXls = function(model,bizCode){
        $.ligerDialog.open({
            id: 'impDialog',
            height : 500,
            width : 650,
            title : 'Excel导入',
            url :  CONTEXT + 'excel/import2.mvc?model=' + model + '&bizCode=' + bizCode,
            showMax : false,
            showToggle : false,
            showMin : false,
            isResize : true,
            slide : false,
            myDataName : null,
            buttons: [
                 { text: '关闭', onclick: function (item, dialog) {
                     $.ligerDialog.hide();
                 }
                 }
             ],
        });
    }
});