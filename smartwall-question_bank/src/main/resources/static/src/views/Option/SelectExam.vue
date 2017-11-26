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
    import { mapState, mapActions } from "vuex";
    export default {
        name: 'SelectExam',
        data() {
            return {
                categoryList: [],
                category: ['医疗执业医生'],
                // subjects: null,
                // chapters: null,
                // subjectIndex: 0
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
        },
        methods: {
            onChangeImage() {
                alert('change iamge');
            },

            onChangeExam() {
                this.$router.push('/Options/SelectExam');
            },

            backToChapter() {
                this.$router.push('/Options');
            },

            save() {
                alert(this.category);
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
        }
    };
</script>