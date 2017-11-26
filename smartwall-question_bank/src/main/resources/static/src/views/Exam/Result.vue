<style scoped lang="less">
    #summary {
        font-size: 12px;
        padding-left: 20px;
        border-bottom: 1px solid #ddd;
        height: 28px;
        line-height: 28px;
    }

    #sheet{
        padding: 10px;
        border-bottom: 1px solid #ddd;
        font-size: 12px;
    }

    .circle {
        text-align: center;
        border: 1px solid #ddd;
        height: 24px;
        width: 24px;
        display: inline-block;
        line-height: 24px;
        border-radius: 12px;
        margin-left: 8px;
    }

    .right {
        border: 1px solid #04be02;
        background-color: #04be02;
        color: #fff;
    }

    .error {
        border: 1px solid #f00;
        background-color: #f00;
        color: #fff;
    }
</style>

<template>
    <div class="answer-sheet">
        <x-header :left-options="{preventGoBack: true}" @on-click-back="backToExam" title="答题结果">
            <div slot="right">
                <a @click="showAnalysis">查看解析</a>
            </div>
        </x-header>
        <div id="summary">
            <span>共答题&nbsp{{count}}</span>
            <span>正确率&nbsp{{rightPercent()}}</span>
        </div>
        <div id="sheet">
            <div>答题情况</div>
            <span v-for='(item, index) in questions' class="circle" :class="{right: item.select == item.answer, error: item.select && item.select != item.answer}">{{index + 1}}</span>
        </div>
    </div>
</template>

<script>
    import { Cell, Group, ViewBox, XHeader } from 'vux';
    import { mapState, mapActions } from "vuex";
    export default {
        name: 'Result',
        data() {
            return {
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
            ...mapState(['questions', 'length']),
            count() {
                let c = 0;
                for (let item of this.questions) {
                    if(item.select) {
                        c++;
                    }
                }

                return c;
            },
        },
        methods: {
            ...mapActions(['reset']),
            rightPercent() {
                let c = 0;
                for (let item of this.questions) {
                    if(item.select && item.select == item.answer) {
                        c++;
                    }
                }

                return c / this.length;
            },
            backToExam() {
                this.$router.push('/Chapter');
            },

            showAnalysis() {
                this.reset();
                this.$router.push('/Exama/q');
            }
        },
        created() {}
    };
</script>