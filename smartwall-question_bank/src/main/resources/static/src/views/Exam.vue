<style  scoped lang="less">
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
        <!-- <x-header></x-header> -->
        <router-link to="/Exam/first">first</router-link>
        <router-link to="/Exam/second">second</router-link>
        <br/>
        <transition :name="transitionName">
            <router-view></router-view>
        </transition>   
    </v-touch>
<!--     <div class="exam" v-smart-gesture="options">

    </div> -->        
</template>

<script>
    import Vue from 'vue';
    // import XHeader from 'vux';
    // import VueTouch from 'vue-touch';
    //import smartGesture from 'vue-smart-gesture';
var VueTouch = require('vue-touch')
Vue.use(VueTouch, {name: 'v-touch'})

    export default {
        name: 'Exam',
        data() {
            return {
                transitionName: null
            };
        },
        props: {},
        computed: {
        },
        components: {
            // XHeader,
            // 'v-touch': VueTouch
        },
        methods: {
            onSwipeLeft() {
                this.$router.push('/Exam/first')
            },
            onSwipeRight() {
                this.$router.push('/Exam/second')
            }
        },
        created() {
        },
        watch: {  
            '$route' (to, from) { 
                // console.log(to);
                if(to.path == '/Exam/first'){
                    this.transitionName = 'slide-back'
                }else{
                    this.transitionName = 'slide-forward';
                }
                // if (!this.map[to.path]) {
                //     this.map[to.path] = +new Date() + 1;
                // }
                // if (!this.map[from.path]) {
                //     this.map[from.path] = +new Date();
                // }

                // if (this.map[to.path] > this.map[from.path]) {
                //     this.transitionName = 'slide-forward';
                // } else {
                //     this.transitionName = 'slide-back'
                // } 
            }  
        }
    };
</script>