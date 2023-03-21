import {
  RECORD_ADDRESS,
  ADD_CART,
  REDUCE_CART,
  INIT_BUYCART,
  // CLEAR_CART,
  RECORD_SHOPDETAIL,
  RECORD_USERINFO,
  GET_USERINFO,
  CONFIRM_REMARK,
  CONFIRM_INVOICE,
  CHOOSE_SEARCH_ADDRESS,
  SAVE_GEOHASH,
  CONFIRM_ADDRESS,
  CHOOSE_ADDRESS,
  NEED_VALIDATION,
  SAVE_CART_ID_SIG,
  SAVE_ORDER_PARAM,
  CHANGE_ORDER_PARAM,
  ORDER_SUCCESS,
  SAVE_SHOPID,
  SAVE_ORDER,
  OUT_LOGIN,
  RETSET_NAME,
  SAVE_AVANDER,
  SAVE_ADDRESS,
  SAVE_ADDDETAIL,
  SAVE_QUESTION,
  ADD_ADDRESS,
  BUY_CART,

  // ###############################################start

  RECEIVE_ADDRESS,
  RECEIVE_CATEGORYS,
  RECEIVE_SHOPS,
  RECEIVE_USER_INFO,
  RESET_USER_INFO,
  RECEIVE_INFO,
  RECEIVE_RATINGS,
  RECEIVE_GOODS,
  INCREMENT_FOOD_COUNT,
  DECREMENT_FOOD_COUNT,
  CLEAR_CART,
  RECEIVE_CAPTCHA,
  RECEIVE_SEARCH_SHOPS,
  CHANGE_ALL,
  CHANGE_SELECTED,
  CHANGE_DELETE,
  ADD_TO_CARTFOODS,

  // ###############################################end

} from './mutation-types.js'

import {setStore, getStore} from '../config/mUtils'

import {localapi, proapi} from 'src/config/env'

export default {

  // ###############################################start

  [RECEIVE_ADDRESS](state, {address}) {
    state.address = address
  },
  [RECEIVE_CATEGORYS](state, {categorys}) {
    state.categorys = categorys
  },
  [RECEIVE_SHOPS](state, {shops}) {
    state.shops = shops
  },
  [RECEIVE_USER_INFO](state, {userInfo}) {
    state.userInfo = userInfo
  },
  [RESET_USER_INFO](state) {
    state.userInfo = {}
  },
  [RECEIVE_INFO](state, {info}) {
    state.info = info
  },

  [RECEIVE_RATINGS](state, {ratings}) {
    state.ratings = ratings
  },

  [RECEIVE_GOODS](state, {goods}) {
    state.goods = goods
  },
  [INCREMENT_FOOD_COUNT](state, {food}) {
    var flag = true;
    state.cartFoods.forEach(foods => {// 遍历购物车
      if (foods.id === food.id) {// 购物车里有同样的商品
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
  [DECREMENT_FOOD_COUNT](state, {food}) {
    if (food.count) { // 只有有值才去减
      food.count--
      if (food.count === 0) {
        // 将food从cartFoods中移除
        state.cartFoods.splice(state.cartFoods.indexOf(food), 1)
      }
    }
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  [CLEAR_CART](state) {
    // 清除food中的count
    state.cartFoods.forEach(food => {
      if (food.count !== 0) {
        state.cartFoods.splice(state.cartFoods.indexOf(food), 1)
      }
      // food.count = 0
    })
    // 移除购物车中所有购物项
    state.cartFoods = []
    // 当购物车删除商品后，把car数组保存到 本地存储中
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  // ClearCar(state) {
  //   state.car = []
  //   localStorage.setItem('car', JSON.stringify(state.car))
  // },
  [RECEIVE_SEARCH_SHOPS](state, {searchShops}) {
    state.searchShops = searchShops
  },
  [RECEIVE_CAPTCHA](state, {captcha}) {
    state.captcha = captcha
  },
  // 全选和全不选
  [CHANGE_ALL](state, flag) {
    state.cartFoods.forEach(item => {
      item.selected = flag
    })
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  [CHANGE_SELECTED](state, info) {
    state.cartFoods.forEach(item => {
      if (item.id === info.id) {
        item.selected = info.selected
      }
    })
    // 当购物车商品选择改变后，把car数组保存到 本地存储中
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  // 购物车中删除商品
  [CHANGE_DELETE](state, id) {
    state.cartFoods.some((item, index) => {
      if (item.id === id) {
        state.cartFoods.splice(index, 1)
        return true
      }
    })
    // 当购物车删除商品后，把car数组保存到 本地存储中
    localStorage.setItem('cartFoods', JSON.stringify(state.cartFoods))
  },
  // 商品加入购物车
  [ADD_TO_CARTFOODS](state, goodsInfo) {
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

  // ###############################################end

  // 记录当前经度纬度
  [RECORD_ADDRESS](state, {
    latitude,
    longitude
  }) {
    state.latitude = latitude;
    state.longitude = longitude;
  },

  [RECORD_SHOPDETAIL](state, detail) {
    state.shopDetail = detail;
  },
  // 加入购物车
  [ADD_CART](state, {
    shopid,
    category_id,
    item_id,
    food_id,
    name,
    price,
    specs,
    packing_fee,
    sku_id,
    stock
  }) {
    let cart = state.cartList;
    let shop = cart[shopid] = (cart[shopid] || {});
    let category = shop[category_id] = (shop[category_id] || {});
    let item = category[item_id] = (category[item_id] || {});
    if (item[food_id]) {
      item[food_id]['num']++;
    } else {
      item[food_id] = {
        "num": 1,
        "id": food_id,
        "name": name,
        "price": price,
        "specs": specs,
        "packing_fee": packing_fee,
        "sku_id": sku_id,
        "stock": stock
      };
    }
    state.cartList = {...cart};
    //存入localStorage
    setStore('buyCart', state.cartList);
  },
  // 移出购物车
  [REDUCE_CART](state, {
    shopid,
    category_id,
    item_id,
    food_id,
    name,
    price,
    specs,
  }) {
    let cart = state.cartList;
    let shop = (cart[shopid] || {});
    let category = (shop[category_id] || {});
    let item = (category[item_id] || {});
    if (item && item[food_id]) {
      if (item[food_id]['num'] > 0) {
        item[food_id]['num']--;
        state.cartList = {...cart};
        // 存入localStorage
        setStore('buyCart', state.cartList);
      } else {
        // 商品数量为0，则清空当前商品的信息
        item[food_id] = null;
      }
    }
  },
  // 网页初始化时从本地缓存获取购物车数据
  [INIT_BUYCART](state) {
    let initCart = getStore('buyCart');
    if (initCart) {
      state.cartList = JSON.parse(initCart);
    }
  },
  // // 清空当前商品的购物车信息
  // [CLEAR_CART](state, shopid) {
  // 	state.cartList[shopid] = null;
  // 	state.cartList = {...state.cartList};
  // 	setStore('buyCart', state.cartList);
  // },
  // 记录用户信息
  [RECORD_USERINFO](state, info) {
    state.userInfo = info;
    state.login = true;
    setStore('user_id', info.user_id);
  },
  // 获取用户信息存入vuex
  [GET_USERINFO](state, info) {
    if (state.userInfo && (state.userInfo.username !== info.username)) {
      return;
    }
    ;
    if (!state.login) {
      return
    }
    if (!info.message) {
      state.userInfo = {...info};
    } else {
      state.userInfo = null;
    }
  },
  // 修改用户名
  [RETSET_NAME](state, username) {
    state.userInfo = Object.assign({}, state.userInfo, {username})
  },
  // 保存商铺id
  [SAVE_SHOPID](state, shopid) {
    state.shopid = shopid;
  },
  // 记录订单页面用户选择的备注, 传递给订单确认页面
  [CONFIRM_REMARK](state, {
    remarkText,
    inputText
  }) {
    state.remarkText = remarkText;
    state.inputText = inputText;
  },
  // 是否开发票
  [CONFIRM_INVOICE](state, invoice) {
    state.invoice = invoice;
  },
  // 选择搜索的地址
  [CHOOSE_SEARCH_ADDRESS](state, place) {
    state.searchAddress = place;
  },
  // 保存geohash
  [SAVE_GEOHASH](state, geohash) {
    state.geohash = geohash;

  },
  // 确认订单页添加新的的地址
  [CONFIRM_ADDRESS](state, newAddress) {
    state.newAddress.push(newAddress);
  },
  // 选择的地址
  [CHOOSE_ADDRESS](state, {
    address,
    index
  }) {
    state.choosedAddress = address;
    state.addressIndex = index;
  },
  // 保存下单需要验证的返回值
  [NEED_VALIDATION](state, needValidation) {
    state.needValidation = needValidation;
  },
  // 保存下单后购物id 和 sig
  [SAVE_CART_ID_SIG](state, {
    cart_id,
    sig
  }) {
    state.cart_id = cart_id;
    state.sig = sig;
  },
  // 保存下单参数，用户验证页面调用
  [SAVE_ORDER_PARAM](state, orderParam) {
    state.orderParam = orderParam;
  },
  // 修改下单参数
  [CHANGE_ORDER_PARAM](state, newParam) {
    state.orderParam = Object.assign({}, state.orderParam, newParam);
  },
  // 下单成功，保存订单返回信息
  [ORDER_SUCCESS](state, order) {
    state.cartPrice = null;
    state.orderMessage = order;
  },
  // 进入订单详情页前保存该订单信息
  [SAVE_ORDER](state, orderDetail) {
    state.orderDetail = orderDetail;
  },
  // 退出登录
  [OUT_LOGIN](state) {
    state.userInfo = {};
    state.login = false;
  },
  // 保存图片
  [SAVE_AVANDER](state, imgPath) {
    state.imgPath = imgPath;
  },
  // 删除地址列表
  [SAVE_ADDRESS](state, newAdress) {
    state.removeAddress = newAdress
  },
  // 添加地址name
  [SAVE_ADDDETAIL](state, addAddress) {
    state.addAddress = addAddress;
  },
  // 保存所选问题标题和详情
  [SAVE_QUESTION](state, question) {
    state.question = {...question};
  },
  // 增加地址
  [ADD_ADDRESS](state, obj) {
    state.removeAddress = [obj, ...state.removeAddress];
  },
  // 会员卡价格纪录
  [BUY_CART](state, price) {
    state.cartPrice = price;
  },

}
