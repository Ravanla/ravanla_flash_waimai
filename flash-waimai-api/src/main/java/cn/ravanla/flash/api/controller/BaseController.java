package cn.ravanla.flash.api.controller;


import cn.ravanla.flash.api.utils.ApiConstants;
import cn.ravanla.flash.security.JwtUtil;
import cn.ravanla.flash.utils.Constants;
import cn.ravanla.flash.utils.HttpKit;
import cn.ravanla.flash.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Map;

/**
 * 基础controller
 *
 *@Author ravanla
 * @version 2020-07-25
 */
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 根据token获取用户id，如果不存在则抛出异常
     *
     * @param request
     * @return
     */
    public Long getIdUser(HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN_NAME);

        Long idUser = JwtUtil.getUserId(token);
        if (idUser == null) {
            throw new RuntimeException("用户不存在");
        }
        return idUser;
    }

    /**
     * 获取客户端token
     *
     * @param request
     * @return
     */

    // 第一个方法是通过HttpServletRequest参数获取token，
    // 第一种情况适用于在 服务器端 处理请求时，从HttpServletRequest对象中获取用户令牌；
    public String getToken(HttpServletRequest request) {
        return request.getHeader(Constants.TOKEN_NAME);
    }

    // 第二个方法是通过HttpKit的getRequest()方法获取token。
    // 第二种情况适用于 客户端 发起请求时，从HttpKit工具类中获取用户令牌。
    public String getToken() {
        return HttpKit.getRequest().getHeader(Constants.TOKEN_NAME);
    }

    /**
     * 获取客户端ip
     *
     * @param req
     * @return
     */
    public String getRealIp(HttpServletRequest req) {
        String ip = req.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || ApiConstants.IP_UNKNOW.equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ApiConstants.IP_UNKNOW.equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ApiConstants.IP_UNKNOW.equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || ApiConstants.IPV6_LOCALHOST.equals(ip)) {
            ip =ApiConstants.IPV4_LOCALHOST;
        }
        return ip;
    }

    /**
     * 获取前端传递过来的json字符串<br>
     * 如果前端使用axios的data方式传参则使用改方法接收参数
     *
     * @return
     */
//    public String getjsonReq() {
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(HttpKit.getRequest().getInputStream()));
//            String line = null;
//            StringBuilder sb = new StringBuilder();
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//
//            }
//            br.close();
//            if (sb.length() < 1) {
//                return "";
//            }
//            String reqBody = URLDecoder.decode(sb.toString(), "UTF-8");
//            reqBody = reqBody.substring(reqBody.indexOf("{"));
//            return reqBody;
//
//        } catch (IOException e) {
//
//            logger.error("获取json参数错误！{}", e.getMessage());
//
//            return "";
//
//        }
//
//    }
    // 此方法用于获取json格式的请求参数
    public String getjsonReq() {
        try{
            // 创建缓冲字符输入流，用于读取请求参数
            BufferedReader br = new BufferedReader(new InputStreamReader(HttpKit.getRequest().getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();

            // 循环读取
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            // 如果没有参数，则返回空字符串
            if (sb.length() < 1) {
                return "";
            }

            // 解码参数
            // 根据左花括号截取出json格式字符串
            String reqBody = URLDecoder.decode(sb.toString(), "UTF-8");
            reqBody = reqBody.substring(reqBody.indexOf("{"));
            return reqBody;
        } catch (IOException e) {

            logger.error("获取json参数错误！{}", e.getMessage());

            return "";
        }
    }

    // 从json中获取对应的实例
    public <T> T getFromJson(Class<T> klass) {
        String jsonStr = getjsonReq();
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        JSONObject json = JSONObject.parseObject(jsonStr);
        return JSON.toJavaObject(json, klass);
    }

    // 获取请求中的负载
    protected String getRequestPayload(){
        // 创建一个StringBuilder对象
        // 从请求中获取BufferedReader对象
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = getRequest().getReader();) {

            // 创建一个字符数组
            char[] buff = new char[1024];
            int len;

            // 循环读取
            while ((len = reader.read(buff)) != -1) {
                // 将读取到的字节添加到StringBuilder对象中
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            // 捕获异常
            e.printStackTrace();
        }
        // 返回StringBuilder对象的字符串
        return sb.toString();
    }

    // 从负载中获取指定类型的实例
    protected  <T>T getRequestPayload(  Class<T> klass)     {
        String json = getRequestPayload();
        try {
            T result = null;
            if(klass== Map.class||klass==null){
                result = (T) Json.fromJson(json);
            }else {
                result = Json.fromJson( klass,json);
            }
            return result;
        }catch (Exception e){

        }
        return null;
    }

    // 获取请求
    protected HttpServletRequest getRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        return sra.getRequest();
    }

    // 获取Session
    protected Object getSession(String key){
        return getRequest().getSession().getAttribute(key);
    }

    // 设置Session
    protected  void setSession(  String key,Object val){
        getRequest().getSession().setAttribute(key,val);
    }

    // 获取ip
    public String getIp(){
        String ip = getRequest().getHeader("x-forwarded-for");
        if(Strings.isNullOrEmpty(ip)){
            //测试ip
            ip = "101.81.121.39";
        }
        return ip;

    }


}
