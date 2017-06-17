define(function(require, exports) {
	require("lib/umeditor/third-party/template.min");
	require("lib/umeditor/umeditor.config");
	require("lib/umeditor/umeditor.min");
	require("lib/umeditor/themes/default/css/umeditor.min.css");

	var initUE = {
		autoHeightEnabled: true,
		imageScaleEnabled: true,
		pasteplain: true,
		allowDivTransToP: false,
		enterTag: 'br'
	};
	exports.getEditor = function(domId) {
		return UM.getEditor(domId, initUE);
	};
});