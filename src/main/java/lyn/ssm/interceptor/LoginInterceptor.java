package lyn.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @ClassName: HandlerInterceptor1 
 * @Description: TODO 登录认证拦截器
 * @author lyn 
 * @date 2017-4-18 下午3:32:09
 */
public class LoginInterceptor implements HandlerInterceptor {

	//进入Handler方法之前执行
	//用于身份认证、身份授权
	//身份认证不通过，表示当前用户为登录
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//获取请求的url
		String url = request.getRequestURI();
		//判定url是不是公开地址（实际开发中配置在配置文件中）
		//这里公开地址是登录提交的地址
		if(url.indexOf("login.action")>0){
			//如果进行登录提交，放行
			return true;
		}
		
		//从session取出用户信息,如果不为空，证明已登录 放行
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		if(username!=null){
			return true;
		}
		
		//若代码执行到这里说明以上都没有放行，则跳转登录页面
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		//return false 表示拦截不向下执行
		System.out.println("HandlerInterceptor1-----preHandle");
		//true 表示放行
		return false;
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
