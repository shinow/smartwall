<style  lang="less">
    .panel-question {
        position: absolute;
        top: 140px;
        width: 99%;
        height: auto;
        bottom: 0;
        border: 1px solid red;
    }
</style>

<template>
    <div class="panel-question">
        <x-header>{{$store.state.currNo}}</x-header>
        <div id="question-type">
            <span id="type">{{$store.state.currQ.type}}</span>
        </div>
        <div id="stem">{{$store.state.currQ.text}}</div>
        <div id="options">
            <div @click="select('A')">
                <span class="option" :class="{'selected': $store.state.currQ.select == 'A'}">A</span>{{$store.state.currQ.opt_A}}
            </div>
            <div @click="select('B')">
                <span class="option" :class="{'selected': $store.state.currQ.select == 'B'}">B</span>{{$store.state.currQ.opt_B}}
            </div>
            <div @click="select('C')">
                <span class="option" :class="{'selected': $store.state.currQ.select == 'C'}">C</span>{{$store.state.currQ.opt_C}}
            </div>
            <div @click="select('D')">
                <span class="option" :class="{'selected': $store.state.currQ.select == 'D'}">D</span>{{$store.state.currQ.opt_D}}
            </div>
            <div @click="select('E')">
                <span class="option" :class="{'selected': $store.state.currQ.select == 'E'}">E</span>{{$store.state.currQ.opt_E}}
            </div>
        </div>
    </div>        
</template>

<script>
    import { XHeader } from 'vux';

    export default {
        name: 'Question',
        data() {
            return {
            }
        },
        props: {},
        computed: {
            count() {
                return this.$store.state.questions.length;
            }
        },
        components: {
            XHeader
        },
        methods: {
            select(answer) {
                this.$store.state.currQ.select = answer;

                //this.next();
            },
        },
        created() {
        }
    };
</script>