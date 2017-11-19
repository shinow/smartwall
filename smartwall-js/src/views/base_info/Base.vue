<style lang="less">
    @import './Base.less';

    .main {
        width: 100%;
        height: 100%;
        border: 0;
        padding: 0;
        margin: 0;
        border-collapse: collapse;

        .panel {
            padding: 10px;

            >div {
                height: 100%;
            }
        }
    }
</style>

<template>
    <table class="main">
        <tbody>
            <tr>
                <td class="panel"><Kind></Kind></td>
                <td class="panel"><Category></Category></td>
                <td class="panel"><Subject></Subject></td>
                <td class="panel"><Chapter></Chapter></td>
            </tr>
        </tbody>
    </table>      
</template>

<script>
    import Category from './Category';
    import Kind from './Kind';
    import Subject from './Subject';
    import Chapter from './Chapter';

    export default {
        name: 'BaseInfo',
        data () {
            return {
            };
        },
        props: {
        },
        computed: {
        },
        components:{
            Kind,
            Category,
            Subject,
            Chapter
        },
        methods: {
        },
        created () {
        }
    };
</script>
