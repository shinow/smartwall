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
                    <tab-item class="tab-item" v-for="subject in subjects" :key="subject.guid" @on-item-click="selectSubject(subject)">{{subject.name}}</tab-item>
                </tab>
            </div>
            <group>
                <cell class="cell" :title="chapter.name"v-for="chapter in chapters" :key="chapter.guid" @click="selectChapter(chapter.guid)" is-link></cell>
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
                chapters: null
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
                        // addProperty(that.subjects, 'active', false);
                        that.selectSubject(that.subjects[0]);
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
            clearActive: function() {
                for (let subject of this.subjects) {
                    subject.active = false;
                }
            },
            selectSubject: function(subject) {
                this.clearActive();

                subject.active = true;
                this.loadChapters(subject.guid);
            },
            selectChapter: function(chapterGuid) {
                this.$router.push({
                    name: 'Exam',
                    query: {
                        chapter_guid: chapterGuid
                    }
                });
            }
        },
        created() {
            this.loadSubjects('5DCA16610870507BE050840A06394546');
        }
    };
</script>