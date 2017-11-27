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
        <toast v-model="showPositionValue" width="12em" type="text" :time="800" is-show-mask :text="toastText" position="middle"></toast>
    </v-touch>        
</template>

<script>
    import Vue from 'vue';
    import { Toast } from 'vux';
    import examData from '../data/exam_data';
    import { mapState, mapGetters, mapActions } from 'vuex';

    var VueTouch = require('vue-touch')
    Vue.use(VueTouch, {
        name: 'v-touch'
    })

    export default {
        name: 'Exam',
        data() {
            return {
                transitionName: null,
                showPositionValue: false,
                toastText: ''
            };
        },
        props: {},
        computed: {
            ...mapGetters(['isFirst', 'isLast']),
            ...mapState(['chapter'])
        },
        components: {
            Toast
        },
        methods: {
            ...mapActions(['next', 'prev']),
            onSwipeLeft() {
                if (this.isLast) {
                    this.showToast("已经到最后一题了");
                    return;
                }
                
                this.next();
                this.q();
            },
            onSwipeRight() {
                if (this.isFirst) {
                    this.showToast("已经到第一题了");
                    return;
                }

                this.prev();
                this.q();
            },

            showToast(text) {
                this.toastText = text;
                this.showPositionValue = true;
            },
            /**
             * 到下一个页面
             */
            q() {
                var t = new Date().getTime();
                this.$router.push(`/Exam/q?t=${t}`);
            }
        },
        mounted() {
        }
    };
</script>