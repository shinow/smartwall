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
                <cell class="cell" :title="chapter.name" v-for="chapter in chapters" :key="chapter.guid" :link="getChapterLink(chapter)" is-link></cell>
            </group>
        </view-box>
   </view-box>
    </div>        
</template>

<script>
    import examData from '../data/exam_data';
    import { Cell, Group, Tab, TabItem, ViewBox, XHeader } from 'vux';

    export default {
        name: 'Chapter',
        data() {
            return {
                category: '医疗执业医生',
                subjects: null,
                chapters: null,
                subjectIndex: 0
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
        computed: {},
        methods: {
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
            getChapterLink(chapter) {
                return `/Exam/${chapter.guid}/q`;
            }
        },
        created() {
            this.loadSubjects('5DCA16610870507BE050840A06394546');
        }
    };
</script>