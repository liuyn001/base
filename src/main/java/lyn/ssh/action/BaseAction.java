package lyn.ssh.action;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import com.alibaba.fastjson.JSON;
/**
 * 
 * @ClassName: BaseAction 
 * @Description: 基础action 
 * @author A18ccms a18ccms_gmail_com 
 * @date 2016-8-10 下午4:51:28
 */
@ParentPackage("basePackage")
@Namespace("/")
public class BaseAction {

	private static final Logger logger = Logger.getLogger(BaseAction.class);
	
	/**
	 * 
	 * @Title: writeJson 
	 * @Description: 传入对象,进行编码过滤,写入前台
	 * @param @param obj 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	public void writeJson(Object obj) {
		try {
			// ajax请求时设置返回的格式
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			// 设置编码格式
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			String json = JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
}
