/*
  路由模块
*/
import Vue from 'vue'
import VueRouter from 'vue-router'
// 引入路由组件文件夹下的组件
// import Msite from '../pages/Msite/Msite.vue'
// import Search from '../pages/Search/Search.vue'
// import Order from '../pages/Order/Order.vue'
// import Profile from '../pages/Profile/Profile.vue'

// import Login from '../pages/Login/Login'
// import Register from '../pages/Register/Register.vue'
// import Userinfo from '../pages/Userinfo/Userinfo.vue'
import Shop from '../page/Shop01/Shop01'
import ShopGoods from '../page/Shop01/ShopGoods/ShopGoods'
import ShopRatings from '../page/Shop01/ShopRatings/ShopRatings'
import ShopInfo from '../page/Shop01/ShopInfo/ShopInfo'

// 路由组件懒加载
// const Msite = () => import('../pages/Msite/Msite.vue')
// const Search = () => import('../pages/Search/Search.vue')
const Order = () => import('../page/Order01/Order')
// const Profile = () => import('../pages/Profile/Profile.vue')
// 全局注册Vue-router组件
Vue.use(VueRouter)

// 配置路由表并导出
export default new VueRouter({
  //  去掉地址中的哈希#
  mode: 'history',
  routes: [
    // {
    //   path: '/',
    //   redirect: '/msite'
    // },
    // {
    //   path: '/msite',
    //   component: Msite,
    //   // 此时的Msite等都是返回路由组件的函数，只有请求对应的路由路径时(第一次)才会执行此函数并加载路由组件
    //   // 标识此路由是否显示FooterGuide
    //   meta: {
    //     showFooter: true
    //   }
    // },
    // {
    //   path: '/search',
    //   component: Search,
    //   meta: {
    //     showFooter: true
    //   }
    // },
    {
      path: '/order',
      component: Order,
      meta: {
        showFooter: false
      }
    },
    // {
    //   path: '/profile',
    //   component: Profile,
    //   meta: {
    //     showFooter: true
    //   }
    // },
    // {
    //   path: '/userinfo',
    //   component: Userinfo,
    //   meta: {
    //     showFooter: false
    //   }
    // },
    // {
    //   path: '/Login',
    //   component: Login
    // },
    // {
    //   path: '/Register',
    //   component: Register
    // },
    {
      path: '/shop',
      component: Shop,
      children: [{
        path: '/shop/goods',
        component: ShopGoods
      },
      {
        path: '/shop/ratings',
        component: ShopRatings
      },
      {
        path: '/shop/info',
        component: ShopInfo
      },
      {
        path: '',
        redirect: '/shop/goods'
      }]
    }
  ]
})
