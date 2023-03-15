import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import store from '../store'
import {getToken} from '@/utils/auth'
// axios.defaults.withCredentials=true
// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 10000

})

// request拦截器
// 它会将当前用户的认证 token 加入请求的头部，以确保每个请求都是经过授权的。
// 具体来说，它使用 Axios 库提供的 interceptors API 在请求发送前进行拦截，

service.interceptors.request.use(
  config => {
    // getToken() 是一个自定义的函数，用于获取当前用户的 token。
    var token = getToken()

    if (token) {
      // 将 config.headers['Authorization'] 设置为 getToken() 的返回值，
      config.headers['Authorization'] = token
    }
    // config.headers.common['Content-Type'] = 'application/x-www-form-urlencoded'
    // config.data = true
    return config
  },
  error => {
    // 此外，还包含了一个错误处理函数，用于在请求出错时打印错误信息并返回一个 rejected 的 Promise，
    // 以便调用方进一步处理错误。注释部分的代码是对请求头部的 Content-Type 做了一些设置，但被注释掉了。
    console.log('error', error)
    Promise.reject(error)
  }
)

// respone拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    const res = response.data

    // 判断响应数据的code字段是否为20000，如果不是，就会抛出错误并显示错误信息。
    if (res.code !== 20000) {
      // 可能是token失效或被其他客户端登录
      // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了; 这里我直接忽略了这些情况
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
      }
      return Promise.reject(res.msg)
    } else {
      // 如果响应数据的code为20000，则会返回响应数据，否则就会抛出错误并返回错误信息。
      return response.data
    }
  },
  error => {
    // 在请求错误时，这段代码会首先判断错误响应对象的errors字段是否存在，
    if (error.response && error.response.data.errors) {
      // 如果存在，则将errors[0].defaultMessage作为错误信息进行显示，
      Message({
        message: error.response.data.errors[0].defaultMessage,
        type: 'error',
        duration: 5 * 1000
      })
    } else {
      // 否则再判断响应对象的message字段是否存在，
      if (error.response && error.response.data.message) {
        // 如果存在则将其作为错误信息进行显示，
        Message({
          message: error.response.data.message,
          type: 'error',
          duration: 5 * 1000
        })
      } else {
        // 否则就将错误本身的message作为错误信息进行显示。
        Message({
          message: error.message,
          type: 'error',
          duration: 5 * 1000
        })
      }
    }
    // 最后，将错误信息作为错误对象的拒绝值返回。
    return Promise.reject(error)
  }
)

export default service
