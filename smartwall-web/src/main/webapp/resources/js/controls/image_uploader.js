define(function(require, exports) {
    require("lib/webuploader/webuploader");
    require("lib/webuploader/webuploader.css");
    var utils = require("utils/utils");
    // // 优化retina, 在retina下这个值是2
    // var ratio = window.devicePixelRatio || 1;
    // // 缩略图大小
    // var thumbnailWidth = 100 * ratio,
    //     thumbnailHeight = 100 * ratio;

    /**
     * 图片上传对象
     */
    ImageUploader = function() {

    };

    /**
     * 初始化对象
     * opts:
     *   container: 父级容器Id
     */
    ImageUploader.prototype.init = function(opts) {
        var container = $("#" + opts["container"]);
        var handerDom = $('<div class="itek-img-uploader"></div>');

        var imgPicker = $('<div class="itek-img-picker"/>').appendTo(handerDom);
        var imgShower = $('<div class="itek-img-shower"><img class="itek-img-ctl" style="width:100%;height:100%"/><a class="itek-img-toolbar"><span class="itek-img-del l-icon l-icon-delete" title="删除图片"/></a>').appendTo(handerDom);
        imgShower.find(".itek-img-del").click(function() {
            var shower = handerDom.find(".itek-img-shower");
            var id = shower.attr("file-id");
            if (id) {
                var file = uploader.getFile(id);
                uploader.removeFile(file);
            }
            shower.attr('file-name', '').removeClass("upload-curr");
            imgPicker.show();
        });
        container.append(handerDom);

        var h = container.height();
        var w = container.width();
        imgShower.width(w).height(h).css({
            border: "1px solid #999",
            position: "relative"
        }).hide();

        this.formDataFunc = opts["formDataFunc"] || function() {
            return "images/" + utils.guid()
        };

        var uploader = WebUploader.create({
            // 自动上传。
            auto: true,
            sendAsBinary: true,
            // swf文件路径
            swf: 'resources/js/lib/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: CONTEXT + 'image/uploader.mvc',
            // [默认值：'file']  设置文件上传域的name。
            fileVal: 'image',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {
                id: imgPicker,
                innerHTML: '选择图片'
            },
            // 只允许选择文件，可选。
            accept: {
                title: 'Images',
                extensions: 'jpg,jpeg,png',
                mimeTypes: 'image/jpg'
            },
            thumb: {
                crop: false
            },
            formData: {
                fileGuid: this.formDataFunc(),
            }
        });

        $(".webuploader-pick", handerDom).width(w).height(h).css({
            "padding": 0,
            "line-height": h + "px"
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function(file, percentage) {
            $percent = handerDom.find(".progress");
            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress"><span class="text"></span> <span class="percentage"></span></div>')
                    .appendTo(handerDom)
                    .find('div');
            }
            $percent.find('.text').html(Math.round(percentage * 100) + '%');
            $percent.find('.percentage').css('width', percentage * 100 + '%');
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function(file, data) {
            // 创建缩略图
            uploader.makeThumb(file, function(error, src) {
                if (error) {
                    return;
                }
                handerDom.find(".itek-img-ctl").attr("src", src);
            });

            handerDom.find(".itek-img-picker").hide();
            handerDom.find(".itek-img-shower").addClass("upload-curr").attr("file-id", file.id).attr("file-name", file.name);
        });

        // 文件上传失败，现实上传出错。
        uploader.on('uploadError', function(file) {
            alert("上传文件失败")
        });

        // 完成上传完了，成功或者失败
        uploader.on('uploadComplete', function(file) {
            $(".progress").remove();
        });

        this.handerDom = handerDom;
    };

    ImageUploader.prototype.setImage = function(image) {
        var image = image;
        if (!!image) {
            var imageUrl;

            if (-1 != image.indexOf(".")) {
                //有后缀,web端上传图片
                imageUrl = CONTEXT + 'oss/web/image.mvc?key=' + image;
            } else {
                //无后缀，手机端上传图片
                imageUrl = CONTEXT + 'oss/web/image.mvc?key=imgcache/' + image + '.jpg';
            }

            this.handerDom.find(".itek-img-ctl").attr("src", imageUrl);
            this.handerDom.find(".itek-img-picker").hide();

            var fileName = image.substr(image.lastIndexOf("/") + 1, image.length);
            this.handerDom.find(".itek-img-shower").addClass("upload-curr").attr("file-name", fileName);
        }
    };

    ImageUploader.prototype.getImage = function() {
        var fileName = this.handerDom.find(".itek-img-shower").attr("file-name");
        if (fileName){
            if (fileName.indexOf(".") > -1) {
                //有后缀,web端上传图片
                return this.formDataFunc() + '/' + fileName;
            } else {
                //无后缀，手机端上传图片
                return fileName;
            }
        }
        return "";

    };
});