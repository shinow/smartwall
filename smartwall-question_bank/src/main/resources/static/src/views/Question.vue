<style  lang="less">
    .panel-question {
        position: absolute;
        top: 0;
        width: 100%;
        height: auto;
        bottom: 0;
    }

    .selected {
        color: #fff !important;
        background-color: #04be02;
    }

    #question-type {
        border: 1px solid #ddd;
    }

    #type {
        height: 32px;
        line-height: 32px;
        margin-left: 20px;
    }

    #info {
        height: 32px;
        line-height: 32px;
        margin-right: 20px;
        float: right;
    }

    #stem {
        height: 32px;
        line-height: 32px;
        margin-left: 20px;
    }

    #options {
        margin-left: 20px;
    }
    
    .option {
        margin: 4px 8px;
        color: #04be02;
        border: 1px solid #04be02;
        width: 24px;
        height: 24px;
        display: inline-block;
        line-height: 24px;
        text-align: center;
        border-radius: 12px;
    }
</style>

<template>
    <div class="panel-question">
        <x-header :left-options="{preventGoBack: true}" @on-click-back="backToChapter">
            <div slot="right">
                <a @click="showCard">答题卡</a>
                <a @click="showSubmit">交卷</a>
            </div>
        </x-header>
        <div id="question-type">
            <span id="type">{{currQ.type}}</span><span id="info">{{currNo + 1}}/{{length}}</span>
        </div>
        <div id="stem">{{currQ.text}}</div>
        <div id="options">
            <div @click="select('A')">
                <span class="option" :class="{'selected': currQ.select == 'A'}">A</span>{{currQ.opt_A}}
            </div>
            <div @click="select('B')">
                <span class="option" :class="{'selected': currQ.select == 'B'}">B</span>{{currQ.opt_B}}
            </div>
            <div @click="select('C')">
                <span class="option" :class="{'selected': currQ.select == 'C'}">C</span>{{currQ.opt_C}}
            </div>
            <div @click="select('D')">
                <span class="option" :class="{'selected': currQ.select == 'D'}">D</span>{{currQ.opt_D}}
            </div>
            <div @click="select('E')">
                <span class="option" :class="{'selected': currQ.select == 'E'}">E</span>{{currQ.opt_E}}
            </div>
        </div>
    </div>        
</template>

<script>
    import { XHeader } from 'vux';
    import { mapState } from "vuex";

    export default {
        name: 'Question',
        data() {
            return {
                current: {}
            }
        },
        props: {},
        computed: {
            ...mapState(['currNo', 'currQ', 'length']),
        },
        components: {
            XHeader
        },
        methods: {
            showCard() {
                alert('答题卡');
            },
            showSubmit() {
                alert('提交');
            },
            select(answer) {
                this.currQ.select = answer;
            },

            backToChapter() {
                this.$router.push('/Chapter');
            }
        },
        created() {

        }
    };
</script>