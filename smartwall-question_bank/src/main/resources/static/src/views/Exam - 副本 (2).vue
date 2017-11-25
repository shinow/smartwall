<style  scoped lang="less">
    .exam {
        font-size: 12px;
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
    <div class="exam">
        <x-header></x-header>
        <div id="question-type">
            <span id="type">{{current.type}}</span>
        </div>
        <div id="stem">{{current.text}}</div>
        <div id="options">
            <div @click="select('A')"><span class="option" :class="{'selected': current.select == 'A'}">A</span>{{current.opt_A}}</div>
            <div @click="select('B')"><span class="option" :class="{'selected': current.select == 'B'}">B</span>{{current.opt_B}}</div>
            <div @click="select('C')"><span class="option" :class="{'selected': current.select == 'C'}">C</span>{{current.opt_C}}</div>
            <div @click="select('D')"><span class="option" :class="{'selected': current.select == 'D'}">D</span>{{current.opt_D}}</div>
            <div @click="select('E')"><span class="option" :class="{'selected': current.select == 'E'}">E</span>{{current.opt_E}}</div>
        </div>
    </div>        
</template>

<script>
    import examData from '../data/exam_data';
    import { Divider, XHeader } from 'vux';
    
    export default {
        name: 'Exam',
        data() {
            return {
                current: {}
            };
        },
        props: {},
        computed: {
            count() {
                return this.questions.length;
            }
        },
        components: {
            Divider,
            XHeader
        },
        methods: {
            select: function(answer) {
                this.current.select = answer;

                this.next();
            },
            next: function() {
                if (this.no == this.count - 1) {
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
            this.chapterGuid = this.$route.params.chapter;
            examData.loadQeustions(this.chapterGuid)
                .then(function(req) {
                    that.questions = req;

                    that.no = 0;
                    that.current = that.questions[0];
                });
        }
    };
</script>