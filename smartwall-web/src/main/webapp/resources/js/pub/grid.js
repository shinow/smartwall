define(function(require, exports, module) {
	require("ligerui");
	require("ligerui-css");
	require("ligerui-css-icons");
	require("viewer");
	require("viewer-css");
	require("pub/grid.extends");
	require("itekui-css");

	var utils = require("utils/utils");
	var _code;
    var _dataUrl;
	var _grid;
	var _initParams;
	var colObject = [];
	var _filterOps = {};
	var _filterType = {};
	var _gridOpts = {
		domGrid: "#grid",
		pageSize: 20,
		pageSizeOptions: [10, 20, 30, 40, 50, 100, 200, 400],
		height: "100%",
		allowHideColumn: true,
		fixedCellHeight: false,
		rownumbers: true,
		colDraggable: true,
		heightDiff: -5,
		onDblClickRow: function(data, rowindex, rowobj) {},
		onAfterShowData: function(data) {
			$(".itekui-img-cell").viewer({
				url: "original"
			});
		}
	};

	exports.create = function(code) {
		_code = code;
        _dataUrl = utils.context + "grid/data/" + _code + ".mvc"
	};

	exports.initByParams = function(params) {
		_initParams = params;
		utils.getJson("grid/config.mvc", {
			code: _code
		}, function(conf) {
			showGrid(conf, params);
		});
	};

    exports.initMongoose = function (code, opts) {

        _code = code;
        _dataUrl = utils.context + "research/mongoose/data/" + _code + ".mvc";

        $.extend(_gridOpts, opts);


        utils.getJson("grid/config.mvc", {
            code: code
        }, function(conf) {
            showGrid(conf);
        });
    }
	/**
	 * @param code 网格编码
	 * @param params 额外参数
	 */
	exports.init = function(code, opts) {
		_code = code;
        _dataUrl = utils.context + "grid/data/" + _code + ".mvc"
		$.extend(_gridOpts, opts);


		utils.getJson("grid/config.mvc", {
			code: code
		}, function(conf) {
			showGrid(conf);
		});
	};



	exports.getGrid = function() {
		return _grid;
	}

	exports.reloadData = function(param) {
		_initParams = param;

		if (null != param) {
			var con = [];
			for (var p in param) {
				con.push({
					field: p,
					op: 'equal',
					value: param[p],
					type: 'string'
				});
			}

			_grid.setParm("condition", JSON.stringify(con));
			_grid.loadData(true);
		}
	}

	/**
	 * 通过参数刷新，不写入到条件中
	 */
	exports.refreshByParam = function(param) {
		$.each(param, function(k, v) {
			_grid.setParm(k, v);
		});

		_grid.loadData(true);
	};

	function showGrid(conf, params) {
		var columns = toGridColumns(conf.columns);
		var parms = $.extend({}, utils.getArgs(), params);
		if (params) {
			var con = [];
			for (var p in params) {
				con.push({
					field: p,
					op: 'equal',
					value: params[p],
					type: 'string'
				});
			}

			parms = {
				"condition": JSON.stringify(con)
			};
		}
		_grid = $(_gridOpts.domGrid).ligerGrid(
			$.extend({}, _gridOpts, {
				parms: parms,
				columns: columns,
				url: _dataUrl,//utils.context + "grid/data/" + _code + ".mvc",
				_code: _code,
				checkbox: conf.base.selectable
			})
		);

		var actions = toGridActions(conf.buttons);
		if (actions.length != 0) {
			$("#toptoolbar").ligerToolBar({
				items: actions
			});
		}
		initFilters(conf.filters, conf.base.showFilter);
	};

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
					if (this["type"] == "IMAGE") {
						/*图片宽度固定为100*/
						column.width = 100;
					}
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
						case "DATETIME": //dataTimeRender
							renderFn = _RENDERS["dataTimeRender"];
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
				columns.push(column);
			}
		});

		return columns;
	};

	function toGridActions(conf) {
		var items = [];
		$.each(conf, function(n, v) {
			items.push({
				text: v.caption,
				click: function() {
					exec(v.action);
				},
				icon: v.icon || ""
			});
		});

		return items;
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

	/* 初始化过滤面板 */
	function initFilters(filters, showFilter) {

		var psearch = $(".l-panel-search");
		psearch.css("overflow", "hidden");
		var html = "<form >";
		$.each(filters, function() {
			_filterOps[this["code"]] = this["op"];
			_filterType[this["code"]] = this["type"];
			var editor = this["editor"];
			if (editor && _Factory[editor]) {
				html += _Factory[editor](this);
			} else {
				html += _Factory["INPUT"](this);
			}
		});

		html += "</form>";
		var fis = $(html).css({
			"float": "left",
			"margin": 0
		});
		psearch.append(fis);
		var sf = fis.ligerForm();
		//弹出框搜索，待优化
		$.each(colObject, function() {
			$("#popTxt").ligerPopupEdit({
				condition: {
					prefixID: 'condtion_',
					fields: [{
						name: 'code',
						type: 'text',
						label: '编码'
					}, {
						name: 'name',
						type: 'text',
						label: '名称',
						newline: false
					}]
				},
				grid: {
					columns: this, //$(".popTxt").attr("data-options"),//JSON.parse($(".popTxt").attr("data-options")) ,
					switchPageSizeApplyComboBox: false,
					url: $("#popTxt").attr("data-url"),
                    usePager : false,
					parms: {
						popup: JSON.stringify(this)
					},
					checkbox: false
				},
				valueField: 'name',
				textField: 'name',
				width: 120
			});
		})



		if (filters.length > 0) {
			var item = $('<div class="l-panel-search-item"></div>');
			var btn = $(
					'<button class="l-grid-btn l-btn-default l-btn-radius">搜索</button>')
				.click(function() {
					var fs = [];
					$.each(sf.getData(), function(k, v) {
						if (v) {
							fs.push({
								field: k,
								op: _filterOps[k], //||'like',
								value: v.replace(/(^\s*)|(\s*$)/g, ""),
								type: _filterType[k]
							});
						}
					});


					if (_initParams) {
						for (var p in _initParams) {
							fs.push({
								field: p,
								op: 'equal',
								value: _initParams[p],
								type: 'string'
							});
						}
					}

					_grid.setParm("condition", JSON.stringify(fs));

					if (_grid.options.page == 1) {
						_grid.loadData(true);
					} else {
						_grid.changePage("first");
					}
				});
			item.append(btn);
			if (showFilter) {
				var h = $('<a href="#" class="l-btn-spec-click">高级</a>');
				h.click(function() {
					_grid.showFilter();
				});
				item.append(h);
			}


			psearch.append(item);
		}
		document.onkeydown = function(event) {
			if (event && event.keyCode == 13) {
				$(".l-grid-btn").trigger("click");
			}
		}
	};

	var _Factory = {};
	_Factory.INPUT = function(cfg) {
		return utils
			.format(
				'<div class="l-panel-search-item">{caption}</div><div class="l-panel-search-item"><input class="ui-textbox" name="{code}" data-options="width:110"></input></div>',
				cfg);
	};

	_Factory.DATE = function(cfg) {
		if ("between" == cfg.op) {
			return utils
				.format(
					'<div class="l-panel-search-item">{caption} 从</div><div class="l-panel-search-item"><input class="ui-datepicker" name="{code}" data-options="width:110"></input></div>' +
					'<div class="l-panel-search-item">到</div><div class="l-panel-search-item"><input class="ui-datepicker" name="{code}_to" data-options="width:110"></input></div>',
					cfg);
		} else {
			return utils.format(
				'<div class="l-panel-search-item">{caption}</div><div class="l-panel-search-item"><input class="ui-datepicker" name="{code}" data-options="width:110"></input></div>',
				cfg);
		}
	};

	_Factory.SELECT = function(cfg) {
		var arr = cfg.ds.split("__");
		var dsUrl;
		switch (arr[0].toLowerCase()) {
			case "dict":
				dsUrl = CONTEXT + "webcontrol/combobox/data.mvc?code=dms_dict&mainCode=" + arr[1];
				break;
			case "url":
				dsUrl = CONTEXT + "webcontrol/data/combobox.mvc?guid=" + arr[1];
				break;
			default:
				return utils
					.format(
						'<div class="l-panel-search-item">{caption}</div><div class="l-panel-search-item"><input class="ui-combobox" data-options="width:110,data:' + arr[1] + '" name="{code}"></input></div>',
						cfg);
		}
		return utils
			.format(
				'<div class="l-panel-search-item" >{caption}</div><div class="l-panel-search-item" ><input class="ui-combobox"  data-options="width:110" data-url="' + dsUrl + '" name="{code}"></input></div>', cfg);
	};

	_Factory.TREE = function(cfg) {
		var dsUrl = CONTEXT + "webcontrol/combobox/queryNodeData.mvc?tName=" + cfg.ds;
		var opts = "width:110,selectBoxWidth:200,selectBoxHeight:200,treeLeafOnly : false,tree:{url:'" + dsUrl + "',idFieldName:'id',parentIDFieldName:'pid',checkbox:false}";

		return utils
			.format(
				'<div class="l-panel-search-item">{caption}</div><div class="l-panel-search-item"><input class = "ui-combobox" data-options="' + opts + '" name="{code}"></input></div>', cfg);
	};

	_Factory.popup = function(cfg) {
		var arr = JSON.parse(cfg.ds);
		colObject.push(arr.columns);
		var dsUrl = CONTEXT + "webcontrol/popup/fliter.mvc?tName=" + arr.tName;
		// JSON.parse(arr[1]);//[{ display: '编码', name: 'code'},{ display: '名称', name: 'name' }];
		return utils
			.format(
				'<div class="l-panel-search-item">{caption}</div><div class="l-panel-search-item"><input id = "popTxt"  name="{code}" data-url="' + dsUrl + '"></input></div>', cfg);
	};

	/* 列渲染 */
	var _RENDERS = {};
	_RENDERS.stringRender = function(rowdata, index, value) {
		return value;
	};
	/* 日期格式 */
	_RENDERS.dataTimeRender = function(rowdata, index, value) {
		return value;
	};
});