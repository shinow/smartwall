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
        padding-left: 20px;
    }

    #options {
        padding-left: 20px;
        border-bottom: 1px solid #ddd;
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

    .answer-info {
    	border-bottom: 1px solid #ddd;
    	height: 26px;
    	line-height: 26px;
    	padding-left: 20px;

    	.right-answer {
    		color: #04be02;
    	}

    	.you-answer {
    		margin-left:20px;
    	}

    	.error-answer {
    		color: #f00;
    	}
    }

    .answer-analysis {
    	padding-left: 20px;
    }
</style>

<template>
    <div class="panel-question">
        <x-header title="结果解析" :left-options="{preventGoBack: true}" @on-click-back="backToChapter">
        </x-header>
        <div id="question-type">
            <span id="type">{{currQ.type}}</span><span id="info">{{currNo + 1}}/{{length}}</span>
        </div>
        <div id="stem">{{currQ.text}}</div>
        <div id="options">
            <div>
                <span class="option" :class="{'selected': currQ.select == 'A'}">A</span>{{currQ.opt_A}}
            </div>
            <div>
                <span class="option" :class="{'selected': currQ.select == 'B'}">B</span>{{currQ.opt_B}}
            </div>
            <div>
                <span class="option" :class="{'selected': currQ.select == 'C'}">C</span>{{currQ.opt_C}}
            </div>
            <div>
                <span class="option" :class="{'selected': currQ.select == 'D'}">D</span>{{currQ.opt_D}}
            </div>
            <div>
                <span class="option" :class="{'selected': currQ.select == 'E'}">E</span>{{currQ.opt_E}}
            </div>
        </div>
        <div class="answer-info">
            <span class="right-answer">【正确答案】{{currQ.answer}}</span>
            <span class="you-answer" :class="{'error-answer': isErrorAnswer}">您的答案:{{currQ.select}}</span>
        </div>
        <div class="answer-analysis">
            <div>答案解析</div>
            <div>{{currQ.analysis}}</div>
        </div>
    </div>        
</template>

<script>
    import { XHeader } from 'vux';
    import { mapState } from "vuex";

    export default {
        name: 'QuestionWithAnalysis',
        data() {
            return {
                current: {}
            }
        },
        props: {},
        computed: {
            ...mapState(['currNo', 'currQ', 'length']),
            isErrorAnswer() {
            	return this.currQ.select && (this.currQ.answer != this.currQ.selected)
            }
        },
        components: {
            XHeader
        },
        methods: {
            backToChapter() {
                this.$router.push('/Chapter');
            }
        },
        created() {

        }
    };
</script>