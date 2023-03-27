import {mapMutations} from 'vuex'
import headTop from '../../components/header/head'
import footGuide from '../../components/footer/footGuide'
import shopList from '../../components/common/shoplist'
import {msiteAddress, msiteFoodTypes, cityGuess} from '../../service/getData'
import 'src/plugins/swiper.min.js'
import 'src/style/swiper.min.css'

// #################################################################################################start

// import ShopList from '../../components/ShopList/ShopList.vue'
// import ShopList from '../../components/ShopList/ShopList'
// 利用mapState语法糖去读取state对象
import {mapState} from 'vuex'
// #################################################################################################end

export default {
  data() {
    return {
      geohash: '', // city页面传递过来的地址geohash
      msiteTitle: '请选择地址...', // msite页面头部标题
      foodTypes: [], // 食品分类列表
      hasGetData: false, // 是否已经获取地理位置数据，成功之后再获取商铺列表信息
      imgBaseUrl: 'https://fuss10.elemecdn.com', // 图片域名地址
    }
  },
  async beforeMount() {
    let address = '1'
    if (!this.$route.query.geohash) {
      const address = await cityGuess();
      this.geohash = address.latitude + ',' + address.longitude;
    } else {
      this.geohash = this.$route.query.geohash
      address = this.$route.query.address
    }
    console.log('geohash', this.geohash)
    // 保存geohash 到vuex
    this.SAVE_GEOHASH(this.geohash);
    // 获取位置信息
    let res = await msiteAddress(this.geohash);
    this.msiteTitle = address;
    // 记录当前经度纬度
    const latAndLng = this.geohash.split(',')
    this.RECORD_ADDRESS({latitude: latAndLng[0], longitude: latAndLng[1]});

    this.hasGetData = true;
  },
  mounted() {
    //获取导航食品类型列表
    msiteFoodTypes(this.geohash).then(res => {
      let resLength = res.length;
      let resArr = [...res]; // 返回一个新的数组
      let foodArr = [];
      for (let i = 0, j = 0; i < resLength; i += 8, j++) {
        foodArr[j] = resArr.splice(0, 8);
      }
      this.foodTypes = foodArr;
    }).then(() => {
      //初始化swiper
      new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        loop: true
      });
    })
    // ################################################################################################start
    // this.$store.dispatch('getCategorys')
    // this.$store.dispatch('getShops')
    // ################################################################################################end
  },
  components: {
    headTop,
    shopList,
    // ShopList,
    footGuide,
  },
  computed: {
    // ################################################################################################start
    // ...mapState(['address', 'categorys', 'userInfo']),
    // categorysArr () {
    //   // 1.先从当前组件中得到所有食品分类的一维数组
    //   const {categorys} = this
    //   // 2.准备一个空的二维数组--categorysArr
    //   const arr = []
    //   // for (let i = 0, len = categorys.length; i < len; i += 8) {
    //   //   arr.push(categorys.slice(i, i + 8))
    //   // }
    //   // 3.准备一个小数组--pages(最大长度为8)
    //   let minArr = []
    //   // 4.遍历categorys得到处理后的二维数组catagorysArr
    //   categorys.forEach(data => {
    //     // 如果当前小数组(pages)已经满了, 创建一个新的
    //     if (minArr.length === 8) {
    //       minArr = []
    //     }
    //     // 如果minArr是空的, 将小数组(pages)保存到大数组(categorysArr)中
    //     if (minArr.length === 0) {
    //       arr.push(minArr)
    //     }
    //     // 将当前分类信息保存到小数组(pages)中
    //     minArr.push(data)
    //   })
    //   return arr
    // }
    // #################################################################################################end
  },
  methods: {
    ...mapMutations([
      'RECORD_ADDRESS', 'SAVE_GEOHASH'
    ]),
    // 解码url地址，求去restaurant_category_id值
    getCategoryId(url) {
      let urlData = decodeURIComponent(url.split('=')[1].replace('&target_name', ''));
      if (/restaurant_category_id/gi.test(urlData)) {
        return JSON.parse(urlData).restaurant_category_id.id
      } else {
        return ''
      }
    }
  },
  watch: {
    // #################################################################################################start
    // categorys (value) { // categorys数组中有数据了 但界面还没有异步更新
      // 使用setTimeout可以实现效果, 但是时机不准确
      /*
      setTimeout(() => {
        // 创建一个Swiper实例对象来实现轮播
        new Swiper('.swiper-container', {
          autoplay: true,
          // 如果需要分页器
          pagination: {
            el: '.swiper-pagination',
            clickable: true
          }
        })
      }, 100) */

      // // 在修改数据之后立即使用它，然后等待 DOM 更新。
      // this.$nextTick(() => {
      //   // 一旦完成界面更新, 立即执行回调
      //   new Swiper('.swiper-container', {
      //     autoplay: true,
      //     pagination: {
      //       el: '.swiper-pagination',
      //       clickable: true
      //     }
      //   })

        // new BScroll('.miste-content-wrapper', {
        //   click: true
        // })
      // })
    // }
    // #################################################################################################end
  }
}
