<style  lang="less">
    .panel-question {
        position: absolute;
        top: 0;
        width: 100%;
        height: auto;
        bottom: 0;
    }
</style>

<template>
    <div class="panel-question">
        <x-header :left-options="{preventGoBack: true}" @on-click-back="backToChapter">{{currNo}}</x-header>
        <div id="question-type">
            <span id="type">{{currQ.type}}</span>
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
            select(answer) {
                this.currQ.select = answer;
            },

            backToChapter() {
                this.$router.push('/Chapter');
            }
        },
        created() {
            console.log(this.currQ);
        }
    };
</script>