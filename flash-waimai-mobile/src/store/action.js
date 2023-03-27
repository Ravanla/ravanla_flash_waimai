import {
	getUser,
	getAddressList,
} from '../service/getData'
import {
	GET_USERINFO,
	SAVE_ADDRESS,
} from './mutation-types.js'
// ##############################################################start

// 注意要引入api接口函数
// import {
//   reqAddress,
//   reqUserInfo,
//   reqLogout,
//   reqCaptcha,
//   reqCategorys,
//   reqShops,
//   reqShopRatings,
//   reqShopGoods,
//   reqShopInfo,
//   reqSearchShop
// } from '../api2'
// import {
// //   RECEIVE_ADDRESS,
// //   RECEIVE_USER_INFO,
// //   RESET_USER_INFO,
// //   RECEIVE_CAPTCHA,
//   RECEIVE_SEARCH_SHOPS,
//   RECEIVE_CATEGORYS,
//   RECEIVE_SHOPS,
//   RECEIVE_GOODS,
//   RECEIVE_RATINGS,
//   RECEIVE_INFO,
//   INCREMENT_FOOD_COUNT,
//   DECREMENT_FOOD_COUNT,
//   CLEAR_CART,
// } from './mutation-types'

// ##############################################################end

export default {

	async getUserInfo({
		commit,
		state
	}) {
		let res = await getUser();
		commit(GET_USERINFO, res)
	},
	async saveAddress({
		commit,
		state
	}) {

		if(state.removeAddress.length > 0) return;

		let addres = await getAddressList(state.userInfo.user_id);
		commit(SAVE_ADDRESS, addres);
	},
// ##############################################################start

  // // 异步获取地址
  // async getAddress({commit, state}) {
  //   // 从state状态中获取到经纬度用来设置reqAddress的参数（看接口文档）
  //   const geohash = state.latitude + ',' + state.longitude
  //   // 1. 发送异步ajax请求
  //   const result = await reqAddress(geohash)
  //   // 2. 提交一个mutation
  //   if (result.code === 0) {
  //     const address = result.data
  //     commit(RECEIVE_ADDRESS, {address})
  //   }
  // },
  //
  // // 异步获取食品分类列表
  // async getCategorys({commit}) {
  //   // 发送异步ajax请求
  //   const result = await reqCategorys()
  //   // 提交一个mutation
  //   if (result.code === 0) {
  //     const categorys = result.data
  //     commit(RECEIVE_CATEGORYS, {categorys})
  //   }
  // },
  // //
  // // 异步获取商家列表
  // async getShops({commit, state}) {
  //   // 对象的结构赋值
  //   const {longitude, latitude} = state
  //   // 发送异步ajax请求
  //   const result = await reqShops(longitude, latitude)
  //   // 提交一个mutation
  //   if (result.code === 0) {
  //     const shops = result.data
  //     commit(RECEIVE_SHOPS, {shops})
  //   }
  // },
  // // 同步记录用户信息
  // recordUser({commit}, userInfo) {
  //   commit(RECEIVE_USER_INFO, {userInfo})
  // },
  //
  // // 异步获取用户信息
  // // async getUserInfo({commit}) {
  // //   const result = await reqUserInfo()
  // //   if (result.code === 0) {
  // //     const userInfo = result.data
  // //     commit(RECEIVE_USER_INFO, {userInfo})
  // //   }
  // // },
  //
  // // 异步登出
  // async logout({commit}) {
  //   const result = await reqLogout()
  //   if (result.code === 0) {
  //     commit(RESET_USER_INFO)
  //   }
  // },
  // // 异步获取商家信息
  // async getShopInfo({commit}) {
  //   const result = await reqShopInfo()
  //   if (result.code === 0) {
  //     const info = result.data
  //     commit(RECEIVE_INFO, {info})
  //   }
  // },
  // //
  // // 异步获取商家评价列表  shoprating
  // async getShopRatings({commit}, callback) {
  //   const result = await reqShopRatings()
  //   if (result.code === 0) {
  //     const ratings = result.data
  //     commit(RECEIVE_RATINGS, {ratings})
  //     // 数据更新了, 通知一下组件
  //     callback && callback()
  //   }
  // },
  // //
  // // 异步获取商家商品列表  shoplist
  // async getShopGoods({commit}, callback) {
  //   const result = await reqShopGoods()
  //   if (result.code === 0) {
  //     const goods = result.data
  //     commit(RECEIVE_GOODS, {goods})
  //     // 数据更新了, 通知一下组件
  //     callback && callback()
  //   }
  // },
  // // 同步更新food中的count值
  // updateFoodCount({commit}, {isAdd, food}) {
  //   if (isAdd) {
  //     commit(INCREMENT_FOOD_COUNT, {food})
  //   } else {
  //     commit(DECREMENT_FOOD_COUNT, {food})
  //   }
  // },
  // //
  // // 同步清空购物车
  // clearCart({commit}) {
  //   commit(CLEAR_CART)
  // },
  //
  //
  //
  // // 验证码
  // async reqCaptcha({commit}) {
  //   const result = await reqCaptcha()
  //   if (result.code === 0) {
  //     const captcha = result.data
  //     commit(RECEIVE_CAPTCHA, {captcha})
  //   }
  // },
  //
  // 异步获取商家商品列表
  // async searchShops({commit, state}, keyword) {
  //   const geohash = state.latitude + ',' + state.longitude
  //   const result = await reqSearchShop(geohash, keyword)
  //   if (result.code === 0) {
  //     const searchShops = result.data
  //     commit(RECEIVE_SEARCH_SHOPS, {searchShops})
  //   }
  // },

// ##############################################################end
}
