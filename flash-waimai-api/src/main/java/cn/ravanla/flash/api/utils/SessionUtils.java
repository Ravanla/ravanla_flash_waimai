package cn.ravanla.flash.api.utils;


import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 对Session的管理
 */
public class SessionUtils {

	// 获取session
	public static HttpSession getSession() {
		/*
		* 获取当前的HttpServletRequest对象，从而获取其中的Session对象，从而可以得到session中的数据。
		* 例如，可以通过该代码获取当前登录用户的信息：String username = request.getSession().getAttribute("username");
		* */

		// 检查 请求上下文持有者 中是否有 获取请求属性
		// 如果没有RequestAttributes则返回null
		if (RequestContextHolder.getRequestAttributes() == null) {
			return null;
		}
		// 获取HttpServletRequest
		// 返回request的session
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getSession();
	}

	/*
	* 可以用它来获取请求参数：String name = request.getParameter("name");
	* */
	// 获取request
	public static HttpServletRequest getRequest() {

		if (RequestContextHolder.getRequestAttributes() == null) {
			return null;
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/*
	* 可以使用以下代码获取ServletContext实例：ServletContext servletContext = getServletContext();
	* 然后，可以使用该ServletContext实例获取request对象：
	* request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	* */
	// 获取ServletContext
	public static ServletContext getServletContext() {

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		return context.getServletContext();
	}


}
