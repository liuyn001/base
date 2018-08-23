package lyn.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @ClassName: HandlerInterceptor1 
 * @Description: TODO 测试拦截器1
 * @author lyn 
 * @date 2017-4-18 下午3:32:09
 */
public class HandlerInterceptor1 implements HandlerInterceptor {

	//进入Handler方法之前执行
	//用于身份认证、身份授权
	//身份认证不通过，表示当前用户为登录
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//return false 表示拦截不向下执行
		System.out.println("HandlerInterceptor1-----preHandle");
		//true 表示放行
		return true;
	}

	//进入Handler方法之后，返回modelAndView之前执行
	//应用场景从modelAndView出发：将公用的模型数据（菜单的导航）传到视图
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		System.out.println("HandlerInterceptor1-----postHandle");
		
	}

	//执行Handler完成之后执行
	//应用场景：统一的异常处理；统一的日志处理
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

		System.out.println("HandlerInterceptor1-----afterCompletion");
		
	}

}
