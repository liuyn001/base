package lyn.ssm.po;

import java.io.Serializable;
import java.util.HashMap;

import lyn.util.Util;

public class ParameterPojo implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = -1767406621081278899L;

	private String sql;
	
	private HashMap<String,Object> map;

	public String getSql() {
		return sql;
	}

	/**
	 * 如果传入的手机号不为空则格式化手机号
	 */
	public void setSql(String sql,HashMap<String,Object> hashMap) {
		if(hashMap!=null && !hashMap.isEmpty()){
			this.sql = Util.formatSql4Mybatis(sql, hashMap);
		}else{
			this.sql = sql;
		}
	}

	public HashMap<String, Object> getMap() {
		return map;
	}

	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}
	
	
	
}
