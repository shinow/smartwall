<style scoped lang="less">
    .weui-cell__ft {
        font-size: 12px;
    }
</style>

<template>
    <div class="options">
        <view-box ref="viewBox">
            <x-header title="设置"></x-header>
            <group>
                <cell title="lexloo" value="修改图像" @click.native="onChangeImage" is-link></cell>
            </group>
            <group>
                <cell title="关注考试" :value="category" @click.native="onChangeExam" is-link></cell>
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
    // import examData from '../../data/exam_data';
    import { Cell, Group, ViewBox, XHeader } from 'vux';
    import { mapState, mapActions } from "vuex";
    export default {
        name: 'Options',
        data() {
            return {
                category: '医疗执业医生',
                // subjects: null,
                // chapters: null,
                // subjectIndex: 0
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
            // ...mapState(['chapter'])
        },
        methods: {
            onChangeImage() {
                alert('change iamge');
            },

            onChangeExam() {
                this.$router.push('/SelectExam');
            }
            // ...mapActions(['setChapter','setQuestions', 'reset']),
            // loadSubjects: function(categoryGuid) {
            //     var that = this;

            //     examData.loadSubjects(categoryGuid)
            //         .then(function(req) {
            //             that.subjects = req;
            //             that.selectSubject(0, that.subjects[0]);
            //         })
            //         .catch(function(error) {
            //             console.log(error);
            //         });
            // },
            // loadChapters: function(subjectGuid) {
            //     var that = this;

            //     examData.loadChapters(subjectGuid)
            //         .then(function(req) {
            //             that.chapters = req;
            //         })
            //         .catch(function(error) {
            //             console.log(error);
            //         });
            // },
            // selectSubject: function(index, subject) {
            //     this.subjectIndex  = index;

            //     this.loadChapters(subject.guid);
            // },
            // onSelectChapter: function(chapterGuid) {
            //     this.setChapter(chapterGuid);
                
            //     //清空试题数据
            //     this.reset();
            //     let that = this;
                
            //     examData.loadQeustions(this.chapter)
            //         .then(function(req) {
            //             that.setQuestions(req);

            //             that.$router.push('/Exam/q');
            //     });
            // }
        },
        created() {
            // this.loadSubjects('5DCA16610870507BE050840A06394546');
        }
    };
</script>