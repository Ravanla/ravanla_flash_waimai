<template>
<!--  <transition name="fade">-->
<!--    -->
<!--  </transition>-->
  <section class="order">
    <HeaderTop title="订单列表"></HeaderTop>
<!--    <section class="order_no_login" v-if="!userInfo.phone && !userInfo._id">-->
<!--      <div v-if="!userInfo.phone && !userInfo._id">-->
<!--        <img src="./images/order/person.png">-->
<!--        <h3>登录后查看外卖订单</h3>-->
<!--      </div>-->
<!--      <div v-else>-->
<!--        {{userInfo.name}}-->
<!--      </div>-->
<!--      &lt;!&ndash;        <button>立即登陆</button>&ndash;&gt;-->
<!--      &lt;!&ndash;        <router-link :to="userInfo._id ? '/userinfo': '/login'" class="profile-link" >&ndash;&gt;-->
<!--      <router-link :to='userInfo._id?"/useinfo":"/login"' class="profile-link">-->
<!--        <div class="user-info">-->
<!--          <p class="user-info-top" v-if='!userInfo.phone'>{{userInfo.name || '立即登陆'}}</p>-->
<!--        </div>-->
<!--      </router-link>-->
<!--    </section>-->
<!--    <section class="order_yes_login" v-else>-->
    <section class="order_yes_login" >
      <div class="shopcar-container">
        <!-- 列表头区域 -->
        <div class="mui-card">
          <div class="mui-card-content">
            <div class="mui-card-content-inner card-top">
              <mt-switch @change="SelectedAll" v-model='btnAll'>
                全选/全不选
              </mt-switch>

              <mt-button size="small" type='primary' @click='clearCart'>清空购物车</mt-button>
            </div>
          </div>
          <!-- 列表区域 -->
          <div class="foods-wrapper" ref="foodsWrapper">
            <ul ref="foodsUl">
              <li class="food-list-hook">
                <h1 class="title">购物车</h1>

                <ul>
                  <li class="food-item bottom-border-1px" v-for="(food, index) in cartFoods"
                      :key="food.id">

                    <mt-switch
                      v-model=" $store.getters.getGoodsSelected[food.id]"
                      @change='SelectedChange(food.id,$store.getters.getGoodsSelected[food.id])'>
                    </mt-switch>

                    <div class="icon">
                      <img width="57" height="57" :src="food.icon">
                    </div>
                    <div class="food-item-content">
                      <h2 class="name">{{food.name}}</h2>
                      <p class="desc">{{food.description}}</p>
                      <div class="extra">
                      </div>
                      <div class="price">
                        <span class="now">￥{{food.price}}</span>
                        <span class="old" v-if="food.oldPrice">￥{{food.oldPrice}}</span>
                      </div>
                      <a href="#" @click.prevent="deleteGoods(food.id,index)">删除 </a>
                      <div class="cartcontrol-wrapper">
                        <CartControl :food="food"/>
                      </div>
                    </div>
                  </li>
                </ul>

              </li>
            </ul>
          </div>
          <!-- 结算区域 -->
          <div class="mui-card-content">
            <div class="mui-card-content-inner countAll">
              <div class="left">
                <p class="left-top">总计（不含运费）</p>
                <p>已勾选商品
                  <span class="red">{{$store.getters.getGoodsCountAndAmount.count}}</span>
                  件， 总价
                  <span class="red">￥ {{$store.getters.getGoodsCountAndAmount.amount}} </span>
                </p>
              </div>

              <mt-button class="right" siz="small" type="danger" @click="$router.push({ path: '/shop/goods' })">
                <router-link class="Settlement" to="/shop/goods" replace>
                  去结算
                </router-link>
              </mt-button>
            </div>
          </div>
        </div>
      </div>

    </section>
    <FooterGuide/>
  </section>
</template>

<script>/* eslint-disable */
import {mapState, mapGetters} from 'vuex'
import ShopGoods from "../Shop01/ShopGoods/ShopGoods";
// import HeaderTop from '../../components/HeaderTop/HeaderTop.vue';
import HeaderTop from "../../components/header/head";
import ShopCart from "../../components/ShopCart/ShopCart";
import CartControl from "../../components/CartControl/CartControl";
// import FooterGuide from "../../components/FooterGuide/FooterGuide";
import FooterGuide from "../../components/footer/footGuide";
import BScroll from 'better-scroll'
import {Toast} from 'mint-ui'
import {MessageBox} from 'mint-ui'

export default {
  data() {
    return {
      // goodslist: [],
      btnAll: true
    }
  },
  created() {
  },
  watch: {
    totalCount: function () {
      // 如果总数量为0, 直接不显示
      if (this.totalCount === 0) {
        // this.isShow = false
        // return false
      }
    },
  },
  computed: {
    // 在购物车中获取到cartFoods的state 以及商家的info
    ...mapState(['cartFoods', 'info', 'userInfo']),
    // 获取相应的Getters里的数据
    ...mapGetters(['totalPrice', 'totalCount']),
  },
  methods: {
    clearCart() {
      MessageBox.confirm('确定清空购物车吗?').then(action => {
        this.$store.dispatch('clearCart')
      }, () => {
      })
    },
    deleteGoods(id, index) {
      MessageBox.confirm('确认删除该商品吗？').then(action => {
        this.cartFoods.splice(index, 0)
        this.$store.commit('change_delete', id)
        Toast({
          message: '删除成功',
          position: 'middle',
          duration: 2000
        })
      })

    },
    SelectedChange(id, val) {
      this.$store.commit('change_selected', {
        id: id,
        selected: val
      })
    },
    SelectedAll() {
      this.$store.commit('change_all', this.btnAll)
    },
    // 初始化滚动
    _initScroll() {
      // 列表显示之后创建
      new BScroll('.menu-wrapper', {
        click: true
      })
      this.foodsScroll = new BScroll('.foods-wrapper', {
        probeType: 2, // 因为惯性滑动不会触发
        click: true
      })

      // 给右侧列表绑定scroll监听
      this.foodsScroll.on('scroll', ({x, y}) => {
        // console.log(x, y) 绝对值
        this.scrollY = Math.abs(y)
      })
      // 给右侧列表绑定scroll结束的监听
      this.foodsScroll.on('scrollEnd', ({x, y}) => {
        // console.log('scrollEnd', x, y)
        this.scrollY = Math.abs(y)
      })
    },

  },
  components: {
    HeaderTop,
    ShopCart,
    CartControl,
    FooterGuide,
  },
}
</script>

<!--<style lang="stylus">-->
//  @import "../../components/common/stylus/mixins.styl"
<style lang="scss" scoped>
@import 'src/style/mixin';

.order { //订单
  width: 100%;

  &.fade-enter-active, &.fade-leave-active {
    transition: opacity 0.3s;
  }

  &.fade-enter, &.fade-leave-to {
    opacity: 0;
  }

  .header { //头部公共css
    background-color: #02a774;
    position: fixed;
    z-index: 100;
    left: 0;
    top: 0;
    width: 100%;
    height: 45px;

    .header_search {
      position: absolute;
      left: 15px;
      top: 50%;
      transform: translateY(-50%);
      width: 10%;
      height: 50%;

      .iconfont {
        font-size: 22px;
        color: #fff;
      }
    }

    .header_title {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      width: 30%;
      color: #fff;
    }
  }

  .order_no_login {
    padding-top: 100px;
    width: 70%;
    margin: 0 auto;
    text-align: center;

    > img {
      display: block;
      width: 100%;
      height: 10%;
    }

    > h3 {
      padding: 10px 0;
      font-size: 17px;
      color: #6a6a6a;
    }

    .profile-link {
      .user-info {
        p {
          font-weight: 700;
          font-size: 18px;
          color: #fff;

          &.user-info-top {
            display: inline-block;
            background: #02a774;
            font-size: 14px;
            color: #fff;
            border: 0;
            outline: none;
            border-radius: 5px;
            padding: 10px 20px;
          }
        }
      }
    }
  }

  .order_yes_login {
    padding-top: 50px;
    width: 100%;
    margin: 0 auto;
    /*text-align: center;*/

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

    .foods-wrapper {
      flex: 1;

      .title {
        padding-left: 14px;
        height: 26px;
        line-height: 26px;
        border-left: 2px solid #d9dde1;
        font-size: 12px;
        color: rgb(147, 153, 159);
        background: #f3f5f7;
      }

      .food-item {
        display: flex;
        margin: 18px;
        padding-bottom: 18px;
        //bottom-border-1px(rgba(7, 17, 27, 0.1));

        &:last-child {
          //border-none();
          margin-bottom: 0;
        }

        .icon {
          flex: 0 0 57px;
          margin-right: 10px;
        }

        .food-item-content {
          flex: 1;

          .name {
            margin: 2px 0 8px 0;
            height: 14px;
            line-height: 14px;
            font-size: 14px;
            color: rgb(7, 17, 27);
          }

          .desc,
          .extra {
            line-height: 10px;
            font-size: 10px;
            color: rgb(147, 153, 159);

            .desc {
              line-height: 12px;
              margin-bottom: 8px;
            }

            .extra {
              .count {
                margin-right: 12px;
              }
            }

            .price {
              font-weight: 700;
              line-height: 24px;

              .now {
                margin-right: 8px;
                font-size: 14px;
                color: rgb(240, 20, 20);
              }

              .old {
                text-decoration: line-through;
                font-size: 10px;
                color: rgb(147, 153, 159);
              }
            }

            .cartcontrol-wrapper {
              position: absolute;
              right: 0;
              bottom: 12px;
            }
          }
        }
      }
    }

    .shopcar-container {
      background-color: #eee;
      overflow: hidden;

      .card-top {
        display: flex;
        justify-content: space-between;

        .mui-card-content-inner {
          display: flex;
          align-items: center;

          .mt-switch:before {
            color: #8a6de9;
            content: '关';
          }

          .mt-switch.mt-active:before {
            color: #ffff00;
            content: '开';
          }
        }
      }

      .goods-list img {
        width: 60px;
        height: 60px;
        position: fixed;
        left: 0;
        bottom: 0;
        z-index: 50;
        width: 100%;
        height: 48px;
      }

      .countAll {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding-bottom: 50px;
        margin-bottom: 50px;

        .red {
          color: red;
          font-weight: bold;
          font-size: 16px;
        }

        .left .left-top {
          margin: 0.1rem 0;
        }

        .right {
          .Settlement {
            color: whitesmoke;
          }
        }
      }
    }
  }
}



</style>
