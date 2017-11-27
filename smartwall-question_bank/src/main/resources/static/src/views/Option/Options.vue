<style scoped lang="less">
    .weui-cell__ft {
        font-size: 12px;
    }
</style>

<template>
    <div class="options">
        <view-box ref="viewBox">
            <x-header title="设置" :left-options="{showBack: false}"></x-header>
            <group>
                <cell title="lexloo" value="修改图像" @click.native="onChangeImage" is-link></cell>
            </group>
            <group>
                <cell title="关注考试" :value="category.name" @click.native="onChangeExam(category.guid)" is-link></cell>
            </group>
            <group>
                <cell title="关于" is-link></cell>
            </group>
            <group>
                <cell title="退出当前账号"></cell>
            </group>
        </view-box>
   </view-box>
    </div>        
</template>

<script>
    import examData from '../../data/exam_data';
    import { Cell, Group, ViewBox, XHeader } from 'vux';
    import { mapState, mapActions } from "vuex";
    export default {
        name: 'Options',
        data() {
            return {
                category: ''
            };
        },
        props: {},
        components: {
            Cell,
            Group,
            ViewBox,
            XHeader
        },
        computed: {
            ...mapState(['user'])
        },
        methods: {
            onChangeImage() {
                //alert('change iamge');
            },

            onChangeExam() {
                this.$router.push('/SelectExam');
            }
        },
        created() {
            var that = this;
            examData.getUserCategoryObject(this.user)
                .then(function(req) {
                    that.category = req;
                });
        }
    };
</script>