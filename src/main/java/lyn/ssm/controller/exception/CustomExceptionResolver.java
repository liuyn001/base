package lyn.ssm.controller.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @ClassName: CustomExceptionResolver 
 * @Description: TODO 全局异常处理器
 * @author lyn 
 * @date 2017-4-18 上午9:58:38
 */
public class CustomExceptionResolver implements HandlerExceptionResolver{

	/*
	 * (非 Javadoc) 
	 * <p>Title:resolveException</p> 
	 * <p>Description: </p> 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex 系统抛出的异常
	 * @return 
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception ex) {
		/*String message = null;
		if(ex instanceof CustomException){
			message = ((CustomException)ex).getMessage();
		} else{
			message = "未知错误";
		}*/
		
		//上边代码可变为
		CustomException customException = null;
		if(ex instanceof CustomException){
			customException = (CustomException)ex;
		} else{
			customException = new CustomException("未知错误");
		}
		//获取异常信息
		String message = customException.getMessage();
		//将异常信息放入ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", message);
		//设置返回错误页面
		modelAndView.setViewName("error");
		return modelAndView;
	}

	
}
