/*
包含多个基于state的getter计算属性的对象
 */
export default {
  totalCount (state) {
    return state.cartFoods.reduce((preTotal, food) => preTotal + food.count, 0)
  },

  totalPrice (state) {
    return state.cartFoods.reduce((preTotal, food) => preTotal + food.count * food.price, 0)
  },

  positiveSize (state) {
    return state.ratings.reduce((preTotal, rating) => preTotal + (rating.rateType === 0 ? 1 : 0), 0)
  },

  // 购物车总数
  getShopcarCount (state) {
    var c = 0
    state.cartFoods.forEach(element => {
      c += element.count
    })
    return c
  },
  // 购物车中每个商品购买的初始值
  getGoodsInitCount (state) {
    var o = {}
    state.cartFoods.forEach(item => {
      o[item.id] = item.count
    })
    return o
  },
  // 购物车中商品的选择情况
  getGoodsSelected (state) {
    var b = {}
    state.cartFoods.forEach(item => {
      b[item.id] = item.selected
    })
    return b
  },
  // 计算购物车中商品总数和总价
  getGoodsCountAndAmount (state) {
    var o = {
      count: 0, // 勾选的商品件数
      amount: 0 // 总价
    }
    state.cartFoods.forEach(item => {
      if (item.selected === true) {
        o.count += item.count
        o.amount += item.price * item.count
      }
    })
    return o
  }
}
