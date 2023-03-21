import Vue from 'vue'
import Vuex from 'vuex'
import mutations from './mutations'
import actions from './action'
import getters from './getters'


// ################################################################start

// 引入四个基本模块
import state from './state'
import mutations01 from './mutations01'
import actions01 from './actions01'
import getters01 from './getters01'

// ################################################################end

// 一定要声明使用插件
Vue.use(Vuex)


// const state = {
	// // latitude: '39.542637', // 当前位置纬度
	// // longitude: '116.232922', // 当前位置经度
  // latiude: '',
  // longitude: '',
	// geohash: '',// 地址geohash值 31.22299,121.36025
	// sig: null,// 购物车sig
	// cartList: {}, // 加入购物车的商品列表
	// cartId: null, // 购物车id
	// shopDetail: null, // 商家详情信息
	// shopid: null,// 商铺id
  //
  // // ################################################################start
  //
  // shops: [], // 商家数组
  // // latitude: 30.10038, // 纬度
  // // longitude: 116.36867, // 经度
  // // address: {}, // 地址相关信息对象
  // // categorys: [], // 食品分类数组
  // // userInfo: {}, // 用户信息
  // goods: [], // 商品列表
  // ratings: [], // 商家评价列表
  // info: {}, // 商家信息
  // cartFoods: cartFoods, // 购物车中食物的列表
  // // captcha: [], // 验证码
  // // searchShops: [], // 搜索得到的商家列表
  // car: car,
  //
  // // ################################################################end
  //
	// remarkText: null,// 可选备注内容
	// inputText: '',// 输入备注内容
	// invoice: false,// 开发票
	// newAddress: [], // 确认订单页新的地址
	// searchAddress: null,// 搜索并选择的地址
	// choosedAddress: null,// 选择地址
	// addressIndex: null,// 选择地址的索引值
	// removeAddress:[],// 移除地址
	// addAddress:'',		// 新增地址
	// needValidation: null,// 确认订单时是否需要验证
	// orderParam: null,// 订单的参数
	// orderMessage: null, // 订单返回的信息
	// orderDetail: null, // 订单详情
	// login: true,// 是否登录
	// userInfo: null, // 用户信息
	// imgPath:null,// 头像地址
	// question: null,// 问题详情
	// cartPrice: null, // 会员卡价格
// }

// 把 store 对象提供给 “store” 选项，这可以把 store 的实例注入所有的子组件
export default new Vuex.Store({
	getters,
  actions,
  mutations,
  state,
  // mutations01,
  // getters01,
  // actions01,
})

// export default new Vuex.Store({
//   state,
//   mutations,
//   actions,
//   getters
// })
