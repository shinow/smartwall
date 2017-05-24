define(function(require, exports) {
	require("lib/umeditor/third-party/template.min");
	require("lib/umeditor/umeditor.config");
	require("lib/umeditor/umeditor.min");
	require("lib/umeditor/themes/default/css/umeditor.min.css");

	exports.getEditor = function(domId) {
		return UM.getEditor(domId);
	};
});