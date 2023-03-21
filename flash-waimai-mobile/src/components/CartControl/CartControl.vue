<template>

  <div class="cart_control">
    <transition
      @before-enter="beforeEnter"
      @enter="enter"
      @after-enter="afterEnter">
      >
      <div class="ball" v-show='ballFlag' ref='ball'></div>
    </transition>
    <transition name="move">
      <div class="iconfont icon-remove_circle_outline" v-if="food.count" @click.stop="updateFoodCount(false)"></div>
    </transition>
    <div class="cart-count" v-if="food.count">{{food.count}}</div>
    <div class="iconfont icon-add_circle" @click.stop="updateFoodCount(true)"></div>

    <!--    <p>-->
    <!--      <mt-button type="primary" Nsize="small">立即购买</mt-button>-->
    <!--      <mt-button type="danger" size="small" @click='addGoods'>加入购物车</mt-button>-->
    <!--    </p>-->

  </div>
</template>

<script>
  import Vue from "vue";

  export default {

    props: {
      food: Object
    },

    data() {
      return {
        ballFlag: false,
      }
    },

    methods: {
      updateFoodCount(isAdd) {
        // this.ballFlag = !this.ballFlag
        // var goodsinfo = {
        //   id: parseInt(this.id),
        //   count: this.selectNum,
        //   price: this.goodsInfos.sell_price,
        //   selected: true
        // }
        // console.log(goodsinfo)
        // this.$store.commit('add_to_cartfoods', goodsinfo)
        // if (!this.food.count){
        //   Vue.set(food, 'id', parseInt(this.id))
        // }
        this.$store.dispatch('updateFoodCount', {isAdd, food: this.food})
      },
      beforeEnter(el) {
        var box = document.getElementsByClassName('iconfont')[0].getBoundingClientRect()
        // this.$refs.ball.style.left = (16) + 'px'
        this.$refs.ball.style.left = (box.left + 60) + 'px'
        // this.$refs.ball.style.top = (16)  + 'px'
        this.$refs.ball.style.top = (box.top) + scrollY + 'px'
        el.style.transform = 'translate(0, 0)'
      },
      enter(el, done) {
        el.offsetWidth
        //   获取小球在页面中的位置

        const ballPosition = this.$refs.ball.getBoundingClientRect()
        // //   获取图标在页面中的位置
        // const badgePosition = document.getElementById('num').getBoundingClientRect()
        //
        const x = -70
        // const x = badgePosition.left - ballPosition.left
        const y = -100
        // const y = badgePosition.top - ballPosition.top

        // const x = 18
        // const y = 18

        el.style.transform = `translate(${x}px, ${y}px)`
        el.style.transition = 'all 3s cubic-bezier(.4,-0.3,1,.68)'
        done()

      },
      afterEnter(el) {
        this.ballFlag = !this.ballFlag
      },
    },
  }
</script>

<!-- <style lang="stylus" rel="stylesheet/stylus">-->
//  @import "../common/stylus/mixins.styl"
<style lang="scss" scoped>
@import 'src/style/mixin';
  .cart_control {
    font-size: 0;

    .ball {
      background-color: red;
      position: absolute;
      width: 16px;
      height: 16px;
      border-radius: 50%;
      z-index: 99;
      top: 380px;
      left: 152px;
    }

    .cart-decrease {
      display: inline-block;
      padding: 6px;
      line-height: 24px;
      font-size: 24px;
      color: rgb(0, 160, 220);
    }

    .icon-remove_circle_outline {
      display: inline-block;
      padding: 6px;
      line-height: 24px;
      font-size: 24px;
      color: $green;

      &.move-enter-active, &.move-leave-active {
        transition: all .3s;
      }

      &.move-enter, &.move-leave-to {
        opacity: 0;
        transform: translateX(15px) rotate(180deg);
      }
    }

    .cart-count {
      display: inline-block;
      vertical-align: top;
      width: 12px;
      padding-top: 6px;
      line-height: 24px;
      text-align: center;
      font-size: 10px;
      color: rgb(147, 153, 159);
    }

    .icon-add_circle {
      display: inline-block;
      padding: 6px;
      line-height: 24px;
      font-size: 24px;
      color: $green;
    }
  }

</style>
