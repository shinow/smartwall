define(function(require, exports, module) {
	var form = require("pub/form_dms");

	var _grid;
	exports.setGrid = function(grid) {
		_grid = grid;
	};

	var _formConfig = {
		width: 400,
		height: 300,
		bizCode: 'kygj_exam_zz',
		columns: [{
			display: "试题名称",
			name: "caption",
			type: "text",
			validate: {
				maxLength: 100,
				required: true
			}
		}],
	};

	Exam = {};
	form.initDefMethod(Exam, _formConfig);


	Grid.showExamEditor = function() {
		return function(rowdata, index, value) {
			var href = CONTEXT + "exam/edit.mvc?type=S3&guid=" + rowdata["guid"];

			return '<a href="' + href + '">编辑</a>';
		};
	};
});