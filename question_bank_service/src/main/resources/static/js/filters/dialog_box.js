/**
 * 对话框
 * Created by Yuffie on 2017/9/29.
 */
Vue.component('modal-dialog', {
    data: function (){
        return {
            isShow: false,
        };
    },
    props: {
        width: {
            type: Number,
        },
        height: {
            type: Number,
        },
        title: {
            type: String,
            default: "",
        },
    },
    watch: {},
    methods: {
        open: function (){
            this.isShow = true;
        },
        close: function (){
            this.isShow = false;
        },
        cancel: function (){
            this.close();
            this.$emit('cancel-close');
        }
    },
    computed: {
        styleHandler: function (){
            var width = this.width ? 'width:' + this.width + 'px;' : "";
            var height = this.height ? 'height:' + this.height + 'px' : "";
            return width + height;
        }
    },
    template:
        `<div class="c-dialog-wrapper" v-show="isShow">
        <div class="c-mask"></div>
        <div class="c-dialog">
            <div class="c-dialog-header">
                <div class="c-dialog-title" v-text="title"></div>
                <div class="c-dialog-close" @click="cancel"></div>
            </div>
            <div class="c-dialog-body" :style="styleHandler">
                <slot name="body"></slot>
            </div>
            <div class="c-dialog-footer">
                <slot name="footer"></slot>
            </div>
        </div>
    </div>`
});

Vue.component('modal-alert', {
    data: function (){
        return {
            minWidth: 200,
            minHeight: 50,
            value : "",
            cb: function (result){}
        };
    },
    props: {
        title: {
            type: String,
            default: "提示",
        },
        width: {
            type: Number,
        },
        height: {
            type: Number,
        },
    },
    watch: {
        value: {
            handler: function (val, oldVal) {
                this.isShow = val !== "";
            },
            deep: true
        },
    },
    methods: {
        ok: function (){
            this.value = "";
            this.cb(true);
        },
        cancel: function (){
            this.ok();
            this.cb(false);
            this.$emit('cancel-close');
        }
    },
    computed: {
        styleHandler: function (){
            var width = this.width ? 'width:' + this.width + 'px;' : 'min-width:' + this.minWidth + 'px;';
            var height = this.height ? 'height:' + this.height + 'px' : 'min-height:' + this.minHeight + 'px;';
            return width + height;
        }
    },
    template:
        `<div class="c-dialog-wrapper" v-show="value">
        <div class="c-mask"></div>
        <div class="c-dialog">
            <div class="c-dialog-header">
                <div class="c-dialog-title" v-text="title"></div>
                <div class="c-dialog-close" @click="cancel"></div>
            </div>
            <div class="c-dialog-body" :style="styleHandler">
                <div v-text="value"></div>
                <slot name="body"></slot>
            </div>
            <div class="c-dialog-footer">
                <button class="c-button" @click="ok">确定</button>
                <slot name="footer"></slot>
            </div>
        </div>
    </div>`
});

Vue.component('modal-confirm', {
    data: function (){
        return {
            minWidth: 200,
            minHeight: 50,
            value: "",
            cb: function (result){}
        };
    },
    props: {
        title: {
            type: String,
            default: "提示",
        },
        width: {
            type: Number,
        },
        height: {
            type: Number,
        },

    },
    watch: {
        value: {
            handler: function (val, oldVal) {
                this.isShow = val !== "";
            },
            deep: true
        },
    },
    methods: {
        ok: function (){
            this.value = "";
            this.cb(true);
        },
        cancel: function (){
            this.value = "";
            this.cb(false);
        }
    },
    computed: {
        styleHandler: function (){
            var width = this.width ? 'width:' + this.width + 'px;' : 'min-width:' + this.minWidth + 'px;';
            var height = this.height ? 'height:' + this.height + 'px' : 'min-height:' + this.minHeight + 'px;';
            return width + height;
        }
    },
    template:
        `<div class="c-dialog-wrapper" v-show="value">
        <div class="c-mask"></div>
        <div class="c-dialog">
            <div class="c-dialog-header">
                <div class="c-dialog-title" v-text="title"></div>
            </div>
            <div class="c-dialog-body" :style="styleHandler">
                <div v-text="value"></div>
                <slot name="body"></slot>
            </div>
            <div class="c-dialog-footer">
                <button class="c-button" @click="ok">确定</button>
                <button class="c-button" @click="cancel">取消</button>
                <slot name="footer"></slot>
            </div>
        </div>
    </div>`
});