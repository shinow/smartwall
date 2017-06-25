$(function() {
    $(".itekui-bar-tab .itekui-tab-item").tap(function() {
        $(this).addClass("itekui-tab-active").siblings().removeClass("itekui-tab-active");

        loadQB($(this).attr("data-type"));
    });

    $(".itekui-bar-tab .itekui-tab-item:first").addClass("itekui-tab-active");

    /*加载题库(ZZ|E1|E2|S1|S2|S3)*/
    function loadQB(type) {
        $.post("/sfa/mobi/exam/get_questions.mvc", {
            type: type
        }, function(result) {
            var c = $("#qb-type").empty();

            var html = "";
            $.each(result.message, function() {
                html += '<div class="itekui-tab-cell">' +
                    '<a class="itekui-tab-cell-item" data-type="' + type + '" data-guid="' + this["guid"] + '">' + this["caption"] + '</a>' +
                    '</div>';
            });

            c.html(html);
            c.find('.itekui-tab-cell').tap(function() {
                var a = $(this).find('a');
                var type = a.attr("data-type");
                var guid = a.attr("data-guid");

                window.location = "/sfa/mobi/exam/show_question.html?type=" + type + "&guid=" + guid + "&caption=" + a.text();
            });
        }, "json");
    };

    loadQB("ZZ");
});