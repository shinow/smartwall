define(function(require, exports, module) {
	require("ztree_all");
	//require("ztree_exhide");
	require("zTreeStyle_css");
	// require("css/public/itfsm.css");/
	var $this;
	var $opts;
	var $dom;
	exports.init = function(dom, opts) {

		$dom = dom;
		$opts = opts;
		var opts = opts || {};
		$this = $(dom);
		$this.addClass("ztree");
		/*部门树参数*/
		/*参见服务的配置*/
		var treeId = opts['treeId'];
		/*注意应用名*/
		var url =CONTEXT + 'data/tree.mvc?treeId=' + treeId;
		var setting = {
			view: {
				selectedMulti: false
			},
			check: {
				enable: opts.check||false
			},
			async: {
				enable: true,
				contentType: "application/json",
				url: createUrl(url,opts['params']),
			},
			callback: {
				onAsyncSuccess: opts["onAsyncSuccess"]||function(event, treeId, treeNode, msg) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					var nodes = treeObj.getNodes();
					if (nodes.length > 0) {
						treeObj.selectNode(nodes[0]);
					}
					treeObj.expandAll(true);
					if(typeof(getFirstNode)!="undefined"){
						if(typeof(eval(getFirstNode)) == "function"){
							getFirstNode(nodes[0]);
						}
					}

				},
				beforeClick : opts["beforeClick"]||function(){},
				onClick: function(event, treeId, treeNode) {
						$(document).trigger("EVENT_TREE_USER_CLICK", treeNode);
				}
			},
			data: {
				simpleData: {
					enable: true,
					idKey: 'guid',
					pIdKey: 'parentguid',
					checked : true,
				}
			}
		};
        $.fn.zTree.init($this, setting, null);
       /* $(document).ready(function(){
            $.fn.zTree.init($this, setting, null);
        });*/

		initFilter(opts["filterDom"]);
	};
	
	initFilter = function(filterDom) {   
		var $this = $(filterDom);
		$this.addClass("nv-query-input");
		var placeHolder = "这里输入查询条件";
		var input = $('<input type="text"/>');
		input.attr("placeHolder", placeHolder);
		var inputWrapper = $('<div class="nv-query-input-wrapper"></div>')
		inputWrapper.append(input);

		var tips = "点击查询";
		var enter = $('<a class="nv-query-input-e"></a>');
		enter.attr("title", tips);

		$this.append('<a class="nv-query-input-q"></a>').append(enter).append(inputWrapper);
		
		input.keyup(function(event) {
			if (event.keyCode == 13) {
				if (typeof $opts.filterMenthod == "string") {
					var method = methods[$opts.filterMenthod];
					if (method) {
						return method($($dom), input.val().trim());
					}
				}
			}
		});
		enter.click(function() {
			if (typeof $opts.filterMenthod == "string") {
				var method = methods[$opts.filterMenthod];
				if (method) {
					return method($($dom), input.val().trim());
				}
			}
		});
	};
	
	methods = {
			/*过滤叶子节点*/
			filterLeaf: function(jq, param) {
				return methods._filter(jq, param, false);
			},

			/*过滤Folder节点*/
			filterFolder: function(jq, param) {
				return methods._filterF(jq, param, true);
			},

			/*过滤节点*/
			_filterF: function(jq, param, type) {
				var zTree = $.fn.zTree.getZTreeObj(jq.attr("id"));
				if (!!param) {
					var nodes = zTree.getNodesByParam("isParent", type);
					$.each(nodes, function(i, v) {
						if (v["name"].indexOf(param) == -1) {
							zTree.hideNode(v);
						} else {
							if (v["isHidden"]) {
								zTree.showNode(v);
							}
						}
					});
				} else {
					//显示所有节点
					var nodes = zTree.getNodesByParam("isHidden", true);
					zTree.showNodes(nodes);
				}

				return jq;
			},

			/*过滤节点*/
			_filter: function(jq, param, type) {
				var zTree = $.fn.zTree.getZTreeObj(jq.attr("id"));0
				if (!!param) {
					//查询所有子节点
					var nodes = zTree.getNodesByParam("isParent", false);
					//遍历节点,如果name中不包含搜索字段，则隐藏，如果包含，则显示
					$.each(nodes, function(i, v) {
						if (v["name"].indexOf(param) == -1) {
							zTree.hideNode(v);
						} else {
							if (v["isHidden"]) {
								zTree.showNode(v);
							}
						}
					});
					//查询所有的父节点
					var searchNodes = zTree.getNodesByParam("isParent", true);
					//显示父节点
					$.each(searchNodes, function() {
						zTree.showNode(this);
					});

					for (var i = 8; i >= 0; i--) {
						//获取未隐藏的父节点
						searchNodes = zTree.getNodesByFilter(function(node) {
							return (node.level = i && node.isParent && node.isHidden == false);
						});
						//遍历父节点下的子节点
						$.each(searchNodes, function() {
							var nodes = this.children;
							var hidden = true;
							$.each(nodes, function() {
								if (!!!this.isHidden) {
									hidden = false;

									return false;
								}
							});
							//如果该父节点不包含搜索名称 并且子节点下也都隐藏
							if (this["name"].indexOf(param) ==-1 && hidden) {
								zTree.hideNode(this);
							}
						});
					}
				} else {
					//显示所有节点
					var nodes = zTree.getNodesByParam("isHidden", true);
					zTree.showNodes(nodes);
				}

				return jq;
			}
		}



     /**
         * 拼接url参数
         * @param url
         * @param param  地址中的参数
         * @returns {string}
         */
        function createUrl(url, param) {
            var params = "";
            if (null != param) {
                // 开始遍历
                var f = false;
                for (var p in param) { // 方法
                    if (typeof(param[p]) == " function ") {
                        param[p]();
                    } else { // p 为属性名称，obj[p]为对应属性的值
                            params = params + "&" + p + "=" + param[p];
                        }
                    }
                }
           return url + params;
        }
});