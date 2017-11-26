<style scoped lang="less">
    .tab-item {
        white-space: nowrap;
        padding: 0 4px;
    }
</style>

<template>
    <div class="chapter">
        <view-box ref="viewBox">
            <x-header :left-options="{showBack: false}">{{category}}</x-header>
            <div style="width: 100%;overflow:scroll;-webkit-overflow-scrolling:touch;">
                <tab :animate="false">
                    <tab-item class="tab-item" :selected="index === subjectIndex" v-for="(subject, index) in subjects" :key="subject.guid" @on-item-click="selectSubject(index, subject)">{{subject.name}}</tab-item>
                </tab>
            </div>
            <group>
                <cell class="cell" :title="chapter.name" v-for="chapter in chapters" :key="chapter.guid" @click.native="onSelectChapter(chapter.guid)" is-link></cell>
            </group>
        </view-box>
   </view-box>
    </div>        
</template>

<script>
    import examData from '../data/exam_data';
    import { Cell, Group, Tab, TabItem, ViewBox, XHeader } from 'vux';
    import { mapState, mapActions } from "vuex";
    export default {
        name: 'Chapter',
        data() {
            return {
                category: '医疗执业医生',
                subjects: null,
                chapters: null,
                subjectIndex: 0,
                user: '5EE331015D332A99E050840A06390D03'
            };
        },
        props: {},
        components: {
            Cell,
            Group,
            Tab,
            TabItem,
            ViewBox,
            XHeader
        },
        computed: {
            ...mapState(['chapter'])
        },
        methods: {
            ...mapActions(['setChapter','setQuestions', 'reset']),
            loadSubjects: function(categoryGuid) {
                var that = this;

                examData.loadSubjects(categoryGuid)
                    .then(function(req) {
                        that.subjects = req;
                        that.selectSubject(0, that.subjects[0]);
                    })
                    .catch(function(error) {
                        console.log(error);
                    });
            },
            loadChapters: function(subjectGuid) {
                var that = this;

                examData.loadChapters(subjectGuid)
                    .then(function(req) {
                        that.chapters = req;
                    })
                    .catch(function(error) {
                        console.log(error);
                    });
            },
            selectSubject: function(index, subject) {
                this.subjectIndex  = index;

                this.loadChapters(subject.guid);
            },
            onSelectChapter: function(chapterGuid) {
                this.setChapter(chapterGuid);
                
                //清空试题数据
                this.reset();
                let that = this;
                
                examData.loadQuestions(this.chapter)
                    .then(function(req) {
                        that.setQuestions(req);

                        that.$router.push('/Exam/q');
                });
            }
        },
        created() {
            var that = this;
            examData.getUserCategory(this.user)
                .then(function(req) {
                    that.loadSubjects(req);
                });
        }
    };
</script>