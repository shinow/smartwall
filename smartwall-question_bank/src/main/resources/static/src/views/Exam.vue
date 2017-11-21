<style  lang="less">
</style>

<template>
    <div class="exam" style="padding: 20px">
        <div>
            <router-link :to="{path:'/Chapter'}" class="return" title="">返回</router-link>
        </div>
        <div class="aui-header">
            <h4></h4>
        </div>
        <div class="question-type">
            <span>{{current.type}}</span>
        </div>
        <div>{{current.text}}</div>
        <div>
            <div @click="select('A')"><span :class="{'selected': current.select == 'A'}">A</span>{{current.opt_A}}</div>
            <div @click="select('B')"><span :class="{'selected': current.select == 'B'}">B</span>{{current.opt_B}}</div>
            <div @click="select('C')"><span :class="{'selected': current.select == 'C'}">C</span>{{current.opt_C}}</div>
            <div @click="select('D')"><span :class="{'selected': current.select == 'D'}">D</span>{{current.opt_D}}</div>
            <div @click="select('E')"><span :class="{'selected': current.select == 'E'}">E</span>{{current.opt_E}}</div>
        </div>
    </div>        
</template>

<script>
    import examData from '../data/exam_data';

    export default {
        name: 'Exam',
        data() {
            return {
                current: {}
            };
        },
        props: {},
        computed: {},
        methods: {
            select: function(answer) {
                this.current.select = answer;
            },
            next: function() {
                if (this.no == this.count() - 1) {
                    alert('已经最后');
                } else {
                    this.no++;
                    this.current = this.questions[this.no];
                }
            },
            prev: function() {
                if (this.no == 0) {
                    alert('已经最前');
                } else {
                    this.no--;
                    this.current = this.questions[this.no];
                }
            }
        },
        created() {
            let that = this;
            this.chapterGuid = this.$route.query.chapter_guid;
            examData.loadQeustions(this.chapterGuid)
                .then(function(req) {
                    that.questions = req;

                    that.no = 0;
                    that.current = that.questions[0];
                });
        }
    };
</script>