<style lang="less">
</style>

<template>
    <div class="chapter" style="padding: 20px">
        <div class="aui-header">
            <h4>{{category}}</h4>
        </div>
        <div class="aui-nav">
            <a v-for="subject in subjects" data-guid="" class="aui-tab-item"  :class="{'aui-tab-item-active': subject.active}"  @click="selectSubject(subject)">{{subject.name}}</a>
        </div>
        <div v-for="chapter in chapters">
            <div class="chapter" @click="selectChapter(chapter.guid)">{{chapter.name}}</div>
        </div>
    </div>        
</template>

<script>
    import examData from '../data/exam_data';

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