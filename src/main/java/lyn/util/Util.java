package lyn.util;

import java.util.HashMap;
import java.util.Set;

public class Util {

	/**
	 * 
	 * @Title: formatSql4Mybatis 
	 * @Description: TODO 将hibernate的sql语句转化成mybatis的SQL语句
	 * @param @param sql
	 * @param @param hashMap
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @author lyn 
	 * @date 2017年5月23日 上午9:23:22 
	 * @throws
	 */
	public static String formatSql4Mybatis(String sql, HashMap<String,Object> hashMap){
		//update table set name=#{name} where id=#{id}
		//like :value '%%'   '%${value}%'
		Set<String> keySet = hashMap.keySet();
		for (String str : keySet) {
			sql = sql.replaceAll("like :"+str, "like "+"'%\\${map.+"+str+"}%'")
					.replaceAll(":"+str, "#{map."+str+"}");
		}
		return null;
	}
	
}
