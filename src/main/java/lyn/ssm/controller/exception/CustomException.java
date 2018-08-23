package lyn.ssm.controller.exception;

/**
 * 
 * @ClassName: CustomException 
 * @Description: TODO 系统自定义异常类，针对预期的异常，需要在程序中抛出此类异常
 * @author lyn 
 * @date 2017-4-18 上午9:51:22
 */
@SuppressWarnings("serial")
public class CustomException extends Exception{
	
	//异常信息
	public String message;
	
	public CustomException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
