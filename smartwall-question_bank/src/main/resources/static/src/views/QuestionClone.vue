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
    <div class="panel-question">QuestionClone---{{$store.state.currQ}}</div>        
</template>

<script>
    export default {
        name: 'QuestionClone',
        data() {
            return {

            };
        },
        props: {},
        computed: {
        },
        components: {
        },
        methods: {
        },
        created() {
        }
    };
</script>