(function() {
    if (typeof iTek == 'undefined') {
        iTek = {};
    }

    var _native = {
        uuid: 1,
        invokeCBMap: {}
    };

    var _invoke = function(module, funcName, params, cbSucc, cbErr) {
        var data = {
            options: params
        };

        if (cbSucc) {
            var cbSuccId = "cb_" + (_native.uuid++) + "_" + new Date().getTime();
            data["cbSuccId"] = cbSuccId;
            _native.invokeCBMap[cbSuccId] = cbSucc;
        }

        if (cbErr) {
            var cbErrId = "cb_" + (_native.uuid++) + "_" + new Date().getTime();
            data["cbErrId"] = cbErrId;
            _native.invokeCBMap[cbErrId] = cbErrId;
        }

        if (typeof __Native__ == "undefined") {
            window.webkit.messageHandlers["invoke"].postMessage({
                module: module,
                funcName: funcName,
                data: JSON.stringify(data)
            });
        } else {
            __Native__.invoke(module, funcName, JSON.stringify(data));
        };
    };

    iTek.__html5_reg = function(module, methods) {
        var m = methods.split(",");
        m.forEach(function(func) {
            var c = iTek[module];
            if (!c) {
                c = (iTek[module] = {});
            }

            if (c[func]) {
                return;
            }

            c[func] = function() {
                if (arguments.length == 0) {
                    return _invoke(module, func);
                }

                var p1 = arguments[0];
                if (typeof p1 == 'function') {
                    return _invoke(module, func, null, p1, arguments[1]);
                } else {
                    return _invoke(module, func, p1, arguments[1], arguments[2]);
                }
            };
        });
    };

    iTek.__html5_cb = function(message) {
        var cbSuccId = message["cbSuccId"];
        var cbErrId = message["cbErrId"];
        if (message.status == 1) {
            if (cbErrId) {
                _native.invokeCBMap[cbErrId](message.body);
            } else {
                alert(message.body);
            }
        } else {
            if (cbSuccId) {
                _native.invokeCBMap[cbSuccId](message.body);
            }
        }

        cbSuccId && (delete iTek.invokeCBMap[cbSuccId]);
        cbErrId && (delete iTek.invokeCBMap[cbErrId]);
    };

    iTek.__html5_evt = function(eventName) {
        var events = this.__events[eventName],
            args = Array.prototype.slice.call(arguments, 1),
            i, m;

        if (!events) {
            return;
        }
        for (i = 0, m = events.length; i < m; i++) {
            events[i].apply(null, args);
        }
    };

    iTek.__html5_reg("ui", "close");
    iTek.__html5_reg("ui", "goURL")
})();