$(function() {
    function getUrlVars() {
        var vars = [],
            hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for (var i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    };

    function getUrlVar(name) {
        return getUrlVars()[name];
    };

    function init() {
        var vars = getUrlVars();

        $("#title").text(decodeURIComponent(vars["caption"]));
        $(".itekui-action-back").tap(function() {
            history.back();
        });

        loadQuestions(vars["type"], vars["guid"]);
        $(".question-nav #prev").tap(function() {
            if (Q.ptr > 1) {
                Q.ptr--;
                Q.showItem();
            }
        });
        $(".question-nav #next").tap(function() {
            Q.ptr++;
            if (Q.hasQuestion()) {
                Q.showItem();
            } else {
                Q.ptr--;
            }
        });
    };

    function loadQuestions(type, guid) {
        $.post("/sfa/mobi/exam/get.mvc", {
            type: type,
            guid: guid
        }, function(result) {
            var message = result.message;
            Q = new Questions(JSON.parse(message));
        }, "json");
    };

    init();

    var Q;
    Questions = function(template) {
        alert(JSON.stringify(template));
        this.template = template;
        this.ptr = 1;
    };

    Questions.prototype.showItem = function() {
        alert(this.ptr);
    }

    Questions.prototype.hasQuestion = function() {
        return this.template["Q" + this.ptr] != undefined;
    }
});