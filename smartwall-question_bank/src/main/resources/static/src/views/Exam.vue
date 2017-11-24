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
        <router-view></router-view> 
    </div>        
</template>

<script>
    import { XHeader } from 'vux';
    
    export default {
        name: 'Exam',
        data() {
            return {

            };
        },
        props: {},
        computed: {
        },
        components: {
            XHeader
        },
        methods: {
        },
        created() {
        }
    };
</script>