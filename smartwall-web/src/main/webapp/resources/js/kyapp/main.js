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
                name: '政治题库',
                func: 'grid/show/49B2ECD92D104BE8B5FEF5F7FEDB42B8.mvc'
            }, {
                name: '英语一题库',
                func: 'grid/show/60CD9199B915462085E16846A0EB1F41.mvc'
            }, {
                name: '英语二题库',
                func: 'grid/show/37433153D2B045648F63B062850E3E08.mvc'
            }, {
                name: '数学一题库',
                func: 'grid/show/3CE5976B6C8643B7B7B87CAB73DA586F.mvc'
            }, {
                name: '数学二题库',
                func: 'grid/show/2D036D6946F24CC98CF5DD06F5719839.mvc'
            }, {
                name: '数学三题库',
                func: 'grid/show/91A4B54A90AF4848A6648C3493EE13BE.mvc'
            }]
        }, {
            name: '基础信息',
            children: [{
                name: '基础信息',
                func: '/question-bank/base_info'
            }, {
                name: '题库',
                func: '/question-bank/designer'
            }]
        }];

        loadMenu(menus);
    });
});