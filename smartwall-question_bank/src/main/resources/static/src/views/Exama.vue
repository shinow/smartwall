<style  lang="less">
    .exam {
        font-size: 12px;
        height: 100%;
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
        <transition name="slide-forward">
            <router-view></router-view>
        </transition>   
    </v-touch>        
</template>

<script>
    import Vue from 'vue';
    import examData from '../data/exam_data';
    import { mapState, mapGetters, mapActions } from 'vuex';

    var VueTouch = require('vue-touch')
    Vue.use(VueTouch, {
        name: 'v-touch'
    })

    export default {
        name: 'Exama',
        data() {
            return {
                transitionName: null,
            };
        },
        props: {},
        computed: {
            ...mapGetters(['isFirst', 'isLast']),
            ...mapState(['chapter'])
        },
        components: {},
        methods: {
            ...mapActions(['next', 'prev']),
            onSwipeLeft() {
                if (this.isLast) {
                    alert("isLast");
                    return;
                }
                
                this.next();
                this.q();
            },
            onSwipeRight() {
                if (this.isFirst) {
                    alert("isFirst");
                    return;
                }

                this.prev();
                this.q();
            },
            /**
             * 到下一个页面
             */
            q() {
                var t = new Date().getTime();
                this.$router.push(`/Exama/q?t=${t}`);
            }
        },
        mounted() {
        }
    };
</script>