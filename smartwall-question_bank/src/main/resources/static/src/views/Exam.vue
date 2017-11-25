<style  lang="less">
    .exam {
        font-size: 12px;
        height: 100%;
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

    .slide-forward-enter {
        transform: translate(100%);
    }
    .slide-forward-enter-active {
        transition: all 1s ease-in-out;
    }
    .slide-forward-leave-active {
        transform: translate(-100%);
        transition: all  1s ease-in-out;
    }


    .slide-back-enter {
        transform: translate(-100%);
    }
    .slide-back-enter-active {
        transition: all 1s ease-in-out;
    }
    .slide-back-leave-active {
        transform: translate(100%);
        transition: all  1s ease-in-out;
    }
</style>

<template>
    <v-touch class="exam" @swipeleft="onSwipeLeft" @swiperight="onSwipeRight">
        <transition name="transitionName">
            <router-view></router-view>
        </transition>   
    </v-touch>        
</template>

<script>
    import Vue from 'vue';
    import examData from '../data/exam_data';

    var VueTouch = require('vue-touch')
    Vue.use(VueTouch, {
        name: 'v-touch'
    })

    export default {
        name: 'Exam',
        data() {
            return {
                transitionName: null,
            };
        },
        props: {},
        computed: {},
        components: {},
        methods: {
            onSwipeLeft() {
                //this.transitionName = 'slide-forward';
                this.$store.commit('next');
                
                this.q();
            },
            onSwipeRight() {
                //this.transitionName = 'slide-back';
                this.$store.commit('prev');
                
                this.q();
            },
            /**
             * 到下一个页面
             */
            q() {
                let url = `${this.pathParent}/q`;
                this.$router.push(url);
            }

        },
        created() {
            let chapter = this.$route.params.chapter;
            this.$store.commit('reset');
            this.pathParent = `/Exam/${chapter}`;

            let that = this;
            examData.loadQeustions(chapter)
                .then(function(req) {
                    console.log(req);
                    that.$store.commit('setQuestions', req);
                });

            console.log("Exam init");
        }
        // watch: {
        //     '$route' (to, from) {
        //     }
        // }
    };
</script>