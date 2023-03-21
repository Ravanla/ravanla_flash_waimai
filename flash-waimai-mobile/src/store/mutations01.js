/*
vuex 的 mutations 模块
*/
import Vue from 'vue'
import {
  // RECEIVE_ADDRESS,
  RECEIVE_CATEGORYS,
  RECEIVE_SHOPS,
  // RECEIVE_USER_INFO,
  // RESET_USER_INFO,
  RECEIVE_INFO,
  RECEIVE_RATINGS,
  RECEIVE_GOODS,
  INCREMENT_FOOD_COUNT,
  DECREMENT_FOOD_COUNT,
  CLEAR_CART,
  // RECEIVE_CAPTCHA,
  RECEIVE_SEARCH_SHOPS,
  CHANGE_ALL,
  CHANGE_SELECTED,
  CHANGE_DELETE,
  ADD_TO_CARTFOODS,
} from './mutation-types'


// [方法名](state,{param}){}
export default {
  // [RECEIVE_ADDRESS] (state, {address}) {
  //   state.address = address
  // },
  [RECEIVE_CATEGORYS] (state, {categorys}) {
    state.categorys = categorys
  },
  [RECEIVE_SHOPS] (state, {shops}) {
    state.shops = shops
  },
  // [RECEIVE_USER_INFO] (state, {userInfo}) {
  //   state.userInfo = userInfo
  // },
  // [RESET_USER_INFO] (state) {
  //   state.userInfo = {}
  // },
  [RECEIVE_INFO] (state, {info}) {
    state.info = info
  },
  //
  [RECEIVE_RATINGS] (state, {ratings}) {
    state.ratings = ratings
  },

  [RECEIVE_GOODS] (state, {goods}) {
    state.goods = goods
  },
  [INCREMENT_FOOD_COUNT] (state, {food}) {
    var flag = true;
    state.cartFoods.forEach(foods => {// 遍历购物车
      if (foods.id === food.id){// 购物车里有同样的商品
        flag = false;
      }
    })
    // if (!food.count) { // 第一次增加
    if (flag) { // 第一次增加
      // if (!state.cartFoods.find(this.food._id)) { // 第一次增加
      // food.count = 1  // 新增属性(没有数据绑定)
      /*
      对象
      属性名
      属性值
       */
      Vue.set(food, 'count', 1) // 让新增的属性也有数据绑定
      // Vue.set(food, 'count', 1) // 让新增的属性也有数据绑定
      Vue.set(food, 'selected', true) // 让新增的属性也有数据绑定
      // 将food添加到cartFoods中
      state.cartFoods.push(food)
    } else {
      food.count++
    }
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  [DECREMENT_FOOD_COUNT] (state, {food}) {
    if (food.count) { // 只有有值才去减
      food.count--
      if (food.count === 0) {
        // 将food从cartFoods中移除
        state.cartFoods.splice(state.cartFoods.indexOf(food), 1)
      }
    }
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  [CLEAR_CART] (state) {
    // 清除food中的count
    state.cartFoods.forEach(food => {
      if (food.count !== 0){
        state.cartFoods.splice(state.cartFoods.indexOf(food), 1)
      }
      // food.count = 0
    })
    // 移除购物车中所有购物项
    state.cartFoods = []
    //当购物车删除商品后，把car数组保存到 本地存储中
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  ClearCar(state) {
    state.car = []
    localStorage.setItem('car', JSON.stringify(state.car))
  },
  [RECEIVE_SEARCH_SHOPS] (state, {searchShops}) {
    state.searchShops = searchShops
  },
  // [RECEIVE_CAPTCHA] (state, {captcha}) {
  //   state.captcha = captcha
  // },
  //全选和全不选
  [CHANGE_ALL] (state, flag) {
    state.cartFoods.forEach(item => {
      item.selected = flag
    })
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  [CHANGE_SELECTED] (state, info) {
    state.cartFoods.forEach(item => {
      if (item.id === info.id) {
        item.selected = info.selected
      }
    })
    //当购物车商品选择改变后，把car数组保存到 本地存储中
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  //购物车中删除商品
  [CHANGE_DELETE](state, id) {
    state.cartFoods.some((item, index) => {
      if (item.id === id) {
        state.cartFoods.splice(index, 1)
        return true
      }
    })
    //当购物车删除商品后，把car数组保存到 本地存储中
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  //商品加入购物车
  [ADD_TO_CARTFOODS] (state, goodsInfo) {
    var flag = false
    state.cartFoods.some(item => {
      if (item.id === goodsInfo.id) {
        item.count += parseInt(goodsInfo.count)
        flag = true
        return true
      }
    })
    if (!flag) {
      state.cartFoods.push(goodsInfo)
    }
    // 当更新car之后，把car数组保存到 本地存储中
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
}

