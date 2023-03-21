<template>
  <div>
    <div class="shopcart">
      <div class="content">
        <div class="content-left" @click="toggleShow">
          <div class="logo-wrapper">
            <div class="logo" :class="{highlight: totalCount}">
              <i class="iconfont icon-shopping" :class="{highlight: totalCount}"></i>
            </div>
            <div class="num" v-if="totalCount">{{totalCount}}</div>
          </div>
          <div class="price" :class="{highlight: totalCount}">￥{{totalPrice}}</div>
          <div class="desc">另需配送费￥{{info.deliveryPrice}}元</div>
        </div>

        <router-link :to='totalPrice?"/order":""' replace>
          <div class="content-right">
            <div class="pay" :class="payClass">
              {{payText}}
            </div>
          </div>
        </router-link>
      </div>
      <transition name="move">
        <div class="shopcart-list" v-show="isShow">
          <div class="list-header">
            <h1 class="title">购物车</h1>
            <span class="empty" @click="clearCart">清空</span>
          </div>
          <div class="list-content">
            <ul>
              <li class="food" v-for="(food, index) in cartFoods" :key="index">
                <span class="name">{{food.name}}
                  每份￥{{food.price}}</span>
                <div class="price">
<!--                  <span></span>-->
                  <span>总共￥{{food.price*food.count}} </span>
                </div>
                <div class="cartcontrol-wrapper">
                  <CartControl :food="food"/>

<!--                  <div class="cartcontrol">-->
<!--                    <transition-->
<!--                      @before-enter="beforeEnter"-->
<!--                      @enter="enter"-->
<!--                      @after-enter="afterEnter">-->
<!--                      <div class="ball" v-show='ballFlag' ref='refball'></div>-->
<!--                    </transition>-->
<!--                    <transition name="move">-->
<!--                      <div class="iconfont icon-removecircleoutline" v-if="food.count" @click.stop="updateFoodCount(false)"></div>-->
<!--                    </transition>-->
<!--                    <div class="cart-count" v-if="food.count">{{food.count}}</div>-->
<!--                    <div class="iconfont icon-addcircle" @click.stop="updateFoodCount(true)"></div>-->
<!--                  </div>-->

                </div>
              </li>
            </ul>
          </div>
        </div>
      </transition>

    </div>
    <div class="list-mask" v-show="isShow" @click="toggleShow"></div>
  </div>
</template>

<script>
import { MessageBox } from 'mint-ui'
import BScroll from 'better-scroll'
import {mapState, mapGetters} from 'vuex'
import CartControl from '../CartControl/CartControl.vue'

export default {
  data () {
    return {
      isShow: false,
      ballFlag: false,
    }
  },
  watch: {
    totalCount: function () {
      // 如果总数量为0, 直接不显示
      if (this.totalCount === 0) {
        this.isShow = false
        // return false
      }
    },
    isShow: function () {
      if (this.isShow) {
        this.$nextTick(() => {
          // 实现BScroll的实例是一个单例
          if (!this.scroll) {
            this.scroll = new BScroll('.list-content', {
              click: true
            })
          } else {
            // console.log(555)
            this.scroll.refresh() // 让滚动条刷新一下: 重新统计内容的高度
          }
        })
      }

      return this.isShow
    }
  },
  computed: {
    // 在购物车中获取到cartFoods的state 以及商家的info
    ...mapState(['cartFoods', 'info']),
    // 获取相应的Getters里的数据
    ...mapGetters(['totalCount', 'totalPrice']),

    // 通过计算已购食品来设置购物车不同的样式和提示文字
    payClass () {
      const {totalPrice} = this
      const {minPrice} = this.info

      return totalPrice >= minPrice ? 'enough' : 'not-enough'
    },
    payText () {
      const {totalPrice} = this
      const {minPrice} = this.info
      if (totalPrice === 0) {
        return `￥${minPrice}元起送`
      } else if (totalPrice < minPrice) {
        return `还差￥${minPrice - totalPrice}元起送`
      } else {
        return '去结算'
      }
    }
  },

  methods: {
    toggleShow () {
      // 只有当总数量大于0时切换
      if (this.totalCount > 0) {
        this.isShow = !this.isShow
      }
    },

    clearCart () {
      MessageBox.confirm('确定清空购物车吗?').then(action => {
        this.$store.dispatch('clearCart')
      }, () => {})
    },

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
      var box = document.getElementsByClassName('iconfont')[1].getBoundingClientRect()
      // this.$refs.ball.style.left = (16) + 'px'
      this.$refs.refball.style.left = (box.left + 60) + 'px'
      // this.$refs.ball.style.top = (16)  + 'px'
      this.$refs.refball.style.top = (box.top) + scrollY + 'px'
      el.style.transform = 'translate(0, 0)'
    },
    enter(el, done) {
      el.offsetWidth
      //   获取小球在页面中的位置

      const ballPosition = this.$refs.refball.getBoundingClientRect()
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
  components: {
    CartControl
  }
}
</script>

<!--<style lang="stylus" rel="stylesheet/stylus" scoped>-->
//  @import "../common/stylus/mixins.styl"
<style lang="scss" scoped>
@import 'src/style/mixin';

  .shopcart {
    position: fixed;
    left: 0;
    bottom: 0;
    z-index: 50;
    width: 100%;
    height: 48px;

    .content {
      display: flex;
      background: #141d27;
      font-size: 0;
      color: rgba(255, 255, 255, 0.4);

      .content-left {
        flex: 1;

        .logo-wrapper {
          display: inline-block;
          vertical-align: top;
          position: relative;
          top: -10px;
          margin: 0 12px;
          padding: 6px;
          width: 56px;
          height: 56px;
          box-sizing: border-box;
          border-radius: 50%;
          background: #141d27;

          .logo {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            text-align: center;
            background: #2b343c;

            &.highlight {
              background: $green;
            }

            .icon-shopping {
              line-height: 44px;
              font-size: 24px;
              color: #80858a;

              &.highlight {
                color: #fff;
              }
            }
          }

          .num {
            position: absolute;
            top: 0;
            right: 0;
            width: 24px;
            height: 16px;
            line-height: 16px;
            text-align: center;
            border-radius: 16px;
            font-size: 9px;
            font-weight: 700;
            color: #ffffff;
            background: rgb(240, 20, 20);
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.4);
          }
        }

        .price {
          display: inline-block;
          vertical-align: top;
          margin-top: 5px;
          line-height: 24px;
          padding-right: 12px;
          box-sizing: border-box;
          font-size: 16px;
          font-weight: 700;
          color: #fff;

          &.highlight {
            color: #fff;
          }
        }

        .desc {
          display: inline-block;
          vertical-align: bottom;
          margin-bottom: 15px;
          margin-left: -45px;
          font-size: 10px;
        }
      }

      .content-right {
        flex: 0 0 105px;
        width: 105px;

        .pay {
          height: 48px;
          line-height: 48px;
          text-align: center;
          font-size: 12px;
          font-weight: 700;
          color: #fff;

          &.not-enough {
            background: #2b333b;
          }

          &.enough {
            background: #00b43c;
            color: #fff;
          }
        }
      }


    }

    .shopcart-list {
      position: absolute;
      left: 0;
      top: 0;
      z-index: -1;
      width: 100%;
      transform: translateY(-100%); //向上移动高度100%

      &.move-enter-active,
      &.move-leave-active {
        transition: transform 0.3s;
      }

      &.move-enter,
      &.move-leave-to {
        transform: translateY(0);
      }

      .list-header {
        height: 40px;
        line-height: 40px;
        padding: 0 18px;
        background: #f3f5f7;
        border-bottom: 1px solid rgba(7, 17, 27, 0.1);

        .title {
          float: left;
          font-size: 14px;
          color: rgb(7, 17, 27);
        }

        .empty {
          float: right;
          font-size: 12px;
          color: rgb(0, 160, 220);
        }
      }

      .list-content {
        padding: 0 18px;
        max-height: 217px;
        overflow: hidden;
        background: #fff;

        .food {
          position: relative;
          padding: 12px 0;
          box-sizing: border-box;
          //@include bottom-border-1px(rgba(7, 17, 27, 0.1));

          .name {
            line-height: 24px;
            font-size: 14px;
            color: rgb(7, 17, 27);
          }

          .price {
            position: absolute;
            right: 90px;
            bottom: 12px;
            line-height: 24px;
            font-size: 14px;
            font-weight: 700;
            color: rgb(240, 20, 20);
          }

          .cartcontrol-wrapper {
            position: absolute;
            right: 0;
            bottom: 6px;

            .cartcontrol {
              font-size: 0;

              .ball {
                background: red;
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

              .icon-removecircleoutline {
                display: inline-block;
                padding: 6px;
                line-height: 24px;
                font-size: 24px;
                color: $green;

                &.move-enter-active, &.move-leave-active {
                  transition: all 0.3s;
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

              .icon-addcircle {
                display: inline-block;
                padding: 6px;
                line-height: 24px;
                font-size: 24px;
                color: $green;
              }
            }
          }
        }
      }

      .list-mask {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        z-index: 40;
        backdrop-filter: blur(10px);
        opacity: 1;
        background: rgba(7, 17, 27, 0.6);

        &.fade-enter-active,
        &.fade-leave-active {
          transition: all 0.5s;
        }

        &.fade-enter,
        &.fade-leave-to {
          opacity: 0;
          background: rgba(7, 17, 27, 0);
        }
      }
    }
  }
  //.shopcart
  //  position fixed
  //  left 0
  //  bottom 0
  //  z-index 50
  //  width 100%
  //  height 48px
  //  .content
  //    display flex
  //    background #141d27
  //    font-size 0
  //    color rgba(255, 255, 255, 0.4)
  //    .content-left
  //      flex 1
  //      .logo-wrapper
  //        display inline-block
  //        vertical-align top
  //        position relative
  //        top -10px
  //        margin 0 12px
  //        padding 6px
  //        width 56px
  //        height 56px
  //        box-sizing border-box
  //        border-radius 50%
  //        background #141d27
  //        .logo
  //          width 100%
  //          height 100%
  //          border-radius 50%
  //          text-align center
  //          background #2b343c
  //          &.highlight
  //            background $green
  //          .icon-shopping
  //            line-height 44px
  //            font-size 24px
  //            color #80858a
  //            &.highlight
  //              color #fff
  //        .num
  //          position absolute
  //          top 0
  //          right 0
  //          width 24px
  //          height 16px
  //          line-height 16px
  //          text-align center
  //          border-radius 16px
  //          font-size 9px
  //          font-weight 700
  //          color #ffffff
  //          background rgb(240, 20, 20)
  //          box-shadow 0 4px 8px 0 rgba(0, 0, 0, 0.4)
  //      .price
  //        display inline-block
  //        vertical-align top
  //        margin-top 5px
  //        line-height 24px
  //        padding-right 12px
  //        box-sizing border-box
  //        font-size 16px
  //        font-weight 700
  //        color #fff
  //        &.highlight
  //          color #fff
  //      .desc
  //        display inline-block
  //        vertical-align bottom
  //        margin-bottom 15px
  //        margin-left -45px
  //        font-size 10px
  //    .content-right
  //      flex 0 0 105px
  //      width 105px
  //      .pay
  //        height 48px
  //        line-height 48px
  //        text-align center
  //        font-size 12px
  //        font-weight 700
  //        color #fff
  //        &.not-enough
  //          background #2b333b
  //        &.enough
  //          background #00b43c
  //          color #fff
  //
  //  .shopcart-list
  //    position absolute
  //    left 0
  //    top 0
  //    z-index -1
  //    width 100%
  //    transform translateY(-100%)//向上移动高度100%
  //    &.move-enter-active, &.move-leave-active
  //      transition transform .3s
  //    &.move-enter, &.move-leave-to
  //      transform translateY(0)
  //    .list-header
  //      height 40px
  //      line-height 40px
  //      padding 0 18px
  //      background #f3f5f7
  //      border-bottom 1px solid rgba(7, 17, 27, 0.1)
  //      .title
  //        float left
  //        font-size 14px
  //        color rgb(7, 17, 27)
  //      .empty
  //        float right
  //        font-size 12px
  //        color rgb(0, 160, 220)
  //
  //    .list-content
  //      padding 0 18px
  //      max-height 217px
  //      overflow hidden
  //      background #fff
  //      .food
  //        position relative
  //        padding 12px 0
  //        box-sizing border-box
  //        bottom-border-1px(rgba(7, 17, 27, 0.1))
  //        .name
  //          line-height 24px
  //          font-size 14px
  //          color rgb(7, 17, 27)
  //        .price
  //          position absolute
  //          right 90px
  //          bottom 12px
  //          line-height 24px
  //          font-size 14px
  //          font-weight 700
  //          color rgb(240, 20, 20)
  //        .cartcontrol-wrapper
  //          position absolute
  //          right 0
  //          bottom 6px
  //          .cartcontrol
  //            font-size: 0
  //            .ball
  //              background red
  //              position absolute
  //              width 16px
  //              height 16px
  //              border-radius 50%
  //              z-index 99
  //              top 380px
  //              left 152px
  //
  //            .cart-decrease
  //              display: inline-block
  //              padding: 6px
  //              line-height: 24px
  //              font-size: 24px
  //              color: rgb(0, 160, 220)
  //
  //            .icon-removecircleoutline
  //              display: inline-block
  //              padding 6px
  //              line-height 24px
  //              font-size 24px
  //              color $green
  //
  //              &.move-enter-active, &.move-leave-active
  //                transition all .3s
  //
  //              &.move-enter, &.move-leave-to
  //                opacity 0
  //                transform translateX(15px) rotate(180deg)
  //
  //            .cart-count
  //              display: inline-block
  //              vertical-align: top
  //              width: 12px
  //              padding-top: 6px
  //              line-height: 24px
  //              text-align: center
  //              font-size: 10px
  //              color: rgb(147, 153, 159)
  //
  //            .icon-addcircle
  //              display: inline-block
  //              padding: 6px
  //              line-height: 24px
  //              font-size: 24px
  //              color $green
  //
  //.list-mask
  //  position fixed
  //  top 0
  //  left 0
  //  width 100%
  //  height 100%
  //  z-index 40
  //  backdrop-filter blur(10px)
  //  opacity 1
  //  background rgba(7, 17, 27, 0.6)
  //  &.fade-enter-active, &.fade-leave-active
  //    transition all 0.5s
  //  &.fade-enter, &.fade-leave-to
  //    opacity 0
  //    background rgba(7, 17, 27, 0)
</style>
