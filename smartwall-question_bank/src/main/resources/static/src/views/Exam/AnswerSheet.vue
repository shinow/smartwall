<style scoped lang="less">
    .weui-cell__ft {
        font-size: 12px;
    }

    #summary {
        font-size: 12px;
        text-align: right;
        padding-right: 20px;
        border-bottom: 1px solid #ddd;
        height: 28px;
        line-height: 28px;
    }

    .undoCircle {
        border: 1px solid #ddd;
        height: 18px;
        width: 18px;
        display: inline-block;
        line-height: 18px;
        border-radius: 9px;
        margin-left: 4px;
        margin-right: 4px;
    }

    .haveDoneCircle {
        border: 1px solid #04be02;
        height: 18px;
        width: 18px;
        display: inline-block;
        line-height: 18px;
        border-radius: 9px;
        margin-left: 4px;
        margin-right: 4px;
    }
</style>

<template>
    <div class="answer-sheet">
        <x-header :left-options="{preventGoBack: true}" @on-click-back="backToExam" title="答题卡"></x-header>
        <div id="summary">
            <span>未做&nbsp{{undoCount()}}</span>
            <span class="undoCircle">&nbsp</span>
            <span>已做&nbsp{{haveDoneCount()}}</span>
            <span class="haveDoneCircle">&nbsp</span>
        </div>
    </div>        
</template>

<script>
    import { Cell, Group, ViewBox, XHeader } from 'vux';
    import { mapState, mapActions } from "vuex";
    export default {
        name: 'AnswerSheet',
        data() {
            return {
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
            ...mapState(['questions'])
        },
        methods: {
            undoCount() {
                let c = 0;
                for (let item of this.questions) {
                    if(!item.selectVal) {
                        c++;
                    }
                }

                return c;
            },
            haveDoneCount() {
                let c = 0;
                for (let item of this.questions) {
                    if(item.selectVal) {
                        c++;
                    }
                }

                return c;
            },
            backToExam() {
                this.$router.push('/Exam/q');
            }
        },
        created() {}
    };
</script>