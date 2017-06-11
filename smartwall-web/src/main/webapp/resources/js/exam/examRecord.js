define(function(require, exports, module) {
  var utils = require("utils/utils");
	var cGrid;
	exports.setGrid = function (grid) {
		cGrid = grid;
	};

    ExamRecord = {};
      /**
         * 预览
         * @returns {Function}
         */
        Grid.showRecord = function () {
            return function (rowdata, index, value) {
                var text = '<a href="javascript:ExamRecord.showDetail(\'{guid}\',\'{content}\',\'{wf_guid}\')">显示明细</a>';
                return utils.format(text, rowdata);
            }
        };

    ExamRecord.showDetail = function(guid){
        var url = 'exam/getRecordDetail.mvc';
                utils.postJson(url, {guid: guid}, function (result) {
                    if (result.code == 0) {
                        var formData = {
                            Rows: result.message
                        };
                        liger.PopupSelect({
                            width: 800,
                            height: 420,
                            grid: getGridOptions(formData),
                        })();
                    } else {
                        console.log(result);
                    }
                });
    }

    function getGridOptions(empData) {
            console.log(empData);
            var options = {
                columns: [{
                    display: '销售代表',
                    name: 'name',
                    align: 'left',
                    width: 120,
                    minWidth: 120
                }, {
                    display: '员工编号',
                    name: 'code',
                    minWidth: 100,
                    width: 100
                }, {
                    display: '题号',
                    name: 'qid',
                    minWidth: 50,
                    width: 50
                }, {
                    display: '正确答案',
                    name: 'answer',
                    minWidth: 100,
                    width: 100
                }, {
                    display: '提交答案',
                    name: 'option_selected',
                    minWidth: 100,
                    width: 100
                 },{
                    display: '是否正确',
                    name: 'result',
                    minWidth: 50,
                    width: 50
                 },{
                    display: '答题时间',
                    name: 'exam_time_start',
                    minWidth: 140,
                    width: 140
                 },],
                switchPageSizeApplyComboBox: false,
                data: $.extend({}, empData),
                pageSize: 10,
                checkbox: false,
            };

            return options;
        };

});