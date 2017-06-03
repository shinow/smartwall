define(function(require) {
    require("ligerui");
    require("ligerui-css");
    require("./css/main.css");
    var utils = require("utils/utils");

    $(function() {
        var accordion;
        //布局
        $("#layout").ligerLayout({
            topHeight: 0,
            leftWidth: 170,
            height: '100%',
            space: -1,
            allowLeftResize: false,
            onHeightChanged: f_heightChanged
        });

        var height = $(".l-layout-center").height();

        $("#framecenter").ligerTab({
            height: height
        });


        var tab = $("#framecenter").ligerGetTabManager();


        $("#pageloading").hide();

        function f_heightChanged(options) {
            if (tab) {
                tab.addHeight(options.diff);
            }

            if (accordion && options.middleHeight - 26 > 0) {
                accordion.setHeight(options.middleHeight - 26);
            }
        }

        function f_addTab(tabid, text, url) {
            tab.addTabItem({
                tabid: tabid,
                text: text,
                url: url
            });
        }


        var loadMenu = function(menus) {
            var accordionList = [];

            $.each(menus, function() {
                var module = {
                    title: this["name"]
                };

                if (this["children"]) {
                    var items = module["items"] = [];
                    $.each(this["children"], function() {
                        items.push({
                            title: this["name"],
                            url: this["func"]
                        })
                    });
                }

                accordionList.push(module);
            });

            f_createAccordionList();

            function f_createAccordionList() {
                var divText = '';
                $(accordionList).each(function() {
                    divText += '<div title="' + this.title + '" class="l-scroll"></div>';
                });

                var c = $("<div></div>");
                c.append(divText);
                $('#accordion').empty().append(c);

                height = $(".l-layout-center").height();
                //面板
                c.ligerAccordion({
                    height: height + 3,
                    speed: null,
                    heightDiff: 0,
                    changeHeightOnResize: true
                });
                accordion = c.ligerGetAccordionManager();

                $(accordionList).each(function(index) {
                    var liText = '';
                    $(this.items).each(function() {
                        liText += '<li url="' + this.url + '">' + this.title + '</li>';
                    });
                    $('.l-accordion-content', c).eq(index).html(liText);
                });

                $('li', c).each(function() {
                    $(this).click(function() {
                        var url = $(this).attr('url');
                        var text = $(this).html();
                        if (url.indexOf("?") > 0) {
                            f_addTab(text, text, url + "&_name_=" + text);
                        } else {
                            f_addTab(text, text, url + "?_name_=" + text);
                        }
                    })
                });
            }

            $(window).resize();
        };

        var menus = [{
            name: '注册用户管理',
            children: [{
                name: '注册信息管理',
                func: ''
            }, {
                name: '注册用户统计',
                func: ''
            }]
        }, {
            name: '购书会员管理',
            children: [{
                name: '会员特权码生成管理',
                func: ''
            }, {
                name: '特权码设置',
                func: ''
            }, {
                name: '会员特权码生成统计',
                func: ''
            }, {
                name: '会员特权码使用管理',
                func: ''
            }, {
                name: '会员特权码使用统计',
                func: ''
            }]
        }, {
            name: '全局参数设置',
            children: [{
                name: '全屏图片管理',
                func: ''
            }, {
                name: '倒计时设置',
                func: ''
            }, {
                name: '励志语句管理',
                func: ''
            }, {
                name: '行政区划管理',
                func: ''
            }, {
                name: '会员等级管理',
                func: ''
            }]
        }, {
            name: '专业管理',
            children: [{
                name: '专业分类管理',
                func: ''
            }, {
                name: '专业管理',
                func: ''
            }, {
                name: '专业排名',
                func: ''
            }, {
                name: '专业经验',
                func: ''
            }, {
                name: '专业书籍',
                func: ''
            }]
        }, {
            name: '院校管理',
            children: [{
                name: '基本信息',
                func: ''
            }, {
                name: '专业介绍',
                func: ''
            }, {
                name: '招生信息',
                func: ''
            }, {
                name: '报录分析',
                func: ''
            }, {
                name: '分数线',
                func: ''
            }, {
                name: '成绩查询',
                func: ''
            }, {
                name: '导师信息',
                func: ''
            }, {
                name: '学校资讯',
                func: ''
            }, {
                name: '复试调剂',
                func: ''
            }]
        }, {
            name: '论坛管理',
            children: [{
                name: '论坛设置',
                func: ''
            }, {
                name: '帖子管理',
                func: ''
            }, {
                name: '发帖',
                func: ''
            }, {
                name: '违规行为处罚',
                func: ''
            }]
        }, {
            name: '最新资讯管理',
            children: [{
                name: '推送',
                func: ''
            }, {
                name: '测试推送',
                func: ''
            }]
        }, {
            name: '题库管理',
            children: [{
                name: '题库分类',
                func: ''
            }, {
                name: '试卷管理',
                func: ''
            }, {
                name: '试题管理',
                func: ''
            }, {
                name: '错题统计',
                func: ''
            }]
        }, {
            name: '订单管理',
            children: [{
                name: '新订单处理',
                func: ''
            }, {
                name: '已发货订单跟踪',
                func: ''
            }, {
                name: '订单退货管理',
                func: ''
            }]
        }];

        loadMenu(menus);
    });
});