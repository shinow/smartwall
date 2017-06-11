define(function(require, exports) {
	require("jquery_validate_all");
	var utils = require("utils/utils");
	var tree = require("pub/tree");
	/* form 容器 */
	var _form$ = $('<form id="form"></form>').appendTo($("body"));
	$.metadata.setType("attr", "validate");

	/* 操作类型: add|edit */
	var _type;
	/* 业务代码 */
	var _bizCode;
	/* 当前数据guid */
	var _guid;
	/* form 实例 */
	var _form;
	/* 网格实例 */
	var _grid;
	/* 是否需要验证编码 */
	var _shouldCheck = false;
	/* 窗体 */
	var _win;

	function ensureForm(conf) {
		if (!_form) {
			_form = _form$.ligerForm({
				inputWidth: conf.labelWidth || 200,
				labelWidth: conf.labelWidth || 100,
				labelAlign: 'right',
				validate: true,
				allowClose: true,
				isHidden: true,
				fields: conf.columns
			});
		} else {
			resetForm(conf);
		}
	};

	function resetForm(conf) {
		var empty = {};
		$.each(conf.columns, function() {
			_form.hideFieldError(this["name"]);
			empty[this["name"]] = "";
		});

		_form.setData(empty);
		_guid = null;
	};

	function getFormData() {
		var values = _form.getData();
		for (var Key in values) {
			if (values[Key] == "") {
				delete values[Key];
			}
		}
		console.log(values);
		return values;
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
		if (!_form.valid()) {
			return;
		}

		var data = {
			code: _bizCode,
			values: getFormData()
		};

		var url;
		if (_type == "edit") {
			data["values"]["guid"] = _guid;
			url = 'common/update.mvc';
		} else {
			url = 'common/insert.mvc';
		}

		if (_shouldCheck && !checkCode(data["values"]["code"])) {
			Notyf.tips("编码已存在");
			return false;
		}
		utils.postJson(url, {
			data: JSON.stringify(data),
		}, function(result) {
			if (result.code == 0) {
				if (_win) {
					_win.hide();
				}
				Notyf.success("操作成功");
				_grid.getGrid().loadData();
			} else {
				if (_win) {
					_win.hide();
				}
				Notyf.tips(result.message);
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
				})
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
	exports.initDefMethod = function(obj, conf) {
		obj["add"] = function() {
			var newConf = conf;
			if (conf["beforeAdd"]) {
				newConf = conf["beforeAdd"]();
			}
			showAddForm(newConf);

			if (conf["afterAdd"]) {
				newConf["afterAdd"]();
			}
		};

		obj["edit"] = function() {
			if (conf["beforeEdit"]) {
				conf = conf["beforeEdit"]();
			}
			showEditForm(conf);
			if (conf["afterEdit"]) {
				conf["afterEdit"]();
			}
		};

		obj.del = function() {
			del(conf["bizCode"], conf["onBeforeDel"]);
		};
	};
});