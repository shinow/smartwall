$(function() {
    $(".itekui-bar-tab .itekui-tab-item").tap(function() {
        $(this).addClass("itekui-tab-active").siblings().removeClass("itekui-tab-active");

        loadQB($(this).attr("data-type"));
    });

    $(".itekui-bar-tab .itekui-tab-item:first").addClass("itekui-tab-active");

    /*加载题库(ZZ|E1|E2|S1|S2|S3)*/
    function loadQB(type) {
        $.post("http://192.168.1.5:8787/sfa/exam/get_questions.mvc", {
            type: type
        }, function(result) {
            alert(JSON.stringify(result));
        }, "json");
    };

    loadQB("ZZ");
});