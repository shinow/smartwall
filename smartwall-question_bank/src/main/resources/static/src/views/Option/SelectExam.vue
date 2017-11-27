<style scoped lang="less">
</style>

<template>
    <div class="category">
        <x-header :left-options="{preventGoBack: true}" @on-click-back="backToChapter" title="关注的考试">
            <a slot="right" @click="save">完成</a>
        </x-header>
        <checklist label-position="left" :options="categoryList" v-model="category" :max="1" required>
        </checklist>
   </view-box>
    </div>        
</template>

<script>
    import examData from '../../data/exam_data';
    import { Cell, Checklist, Group, ViewBox, XHeader } from 'vux';
    import { mapState } from "vuex";
    export default {
        name: 'SelectExam',
        data() {
            return {
                categoryList: [],
                category: []
            };
        },
        props: {},
        components: {
            Cell,
            Checklist,
            Group,
            ViewBox,
            XHeader
        },
        computed: {
            ...mapState(['user'])
        },
        methods: {
            backToChapter() {
                this.$router.push('/Options');
            },

            save() {
                var that = this;
                examData.saveUserCategory(this.user, this.category[0])
                .then(function(req) {
                    that.$router.push('/Options');
                });
            }
        },
        created() {
            var that = this;
            examData.loadAllCategory()
                .then(function(req) {
                    for (let item of req) {
                        that.categoryList.push({
                            key: item['guid'],
                            value: item['name']
                        });
                    }
                });
            examData.getUserCategory(this.user)
                .then(function(req) {
                    that.category = [req];
                });
        }
    };
</script>