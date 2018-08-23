package lyn.util;

import org.apache.commons.configuration2.CompositeConfiguration;

public class PropertiesUtil {
    
	private static final CompositeConfiguration config = new CompositeConfiguration();
	
	/**
	 * 初始化，类首先加载静态代码块，把配置文件加载到环境中
	 */
	/*static{
		config = new CompositeConfiguration();
		try {
			//config.addConfiguration(new PropertiesConfiguration("test.properties"));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}*/
	
	
	/**
	 * 
	 * @Title: getString 
	 * @Description: TODO 获取属性值 
	 * @param @param key
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @author lyn 
	 * @date 2017年5月31日 上午10:40:01 
	 * @throws
	 */
	public static String getString(String key){
		return config.getString(key);
	}
	
	/**
	 * 
	 * @Title: getString 
	 * @Description: TODO 获取属性值
	 * @param @param key
	 * @param @param defaultValue 默认值
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @author lyn 
	 * @date 2017年5月31日 下午3:07:54 
	 * @throws
	 */
	public static String getString(String key, String defaultValue){
		return config.getString(key, defaultValue);
	}
	
	/**
	 * 
	 * @Title: setString 
	 * @Description: TODO 设置属性值 
	 * @param @param key
	 * @param @param value 设定文件 
	 * @return void 返回类型 
	 * @author lyn 
	 * @date 2017年5月31日 下午3:08:39 
	 * @throws
	 */
	public static void setString(String key, String value){
		config.setProperty(key, value);
	}
	
	public static int getInt(String key){
		return config.getInt(key);
	}
	
	public static int getInt(String key, int defaultValue){
		return config.getInt(key, defaultValue);
	}
	
	public static Integer getInteger(String key, Integer defaultValue){
		return config.getInteger(key, defaultValue);
	}
	
	public static Long getLong(String key){
		return config.getLong(key);
	}
	
	public static Long getLong(String key, Long defaultValue){
		return config.getLong(key, defaultValue);
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getString("start"));
	}
	
}
