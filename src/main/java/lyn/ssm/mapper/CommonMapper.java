package lyn.ssm.mapper;

import java.util.HashMap;
import java.util.List;

import lyn.ssm.po.ParameterPojo;

public interface CommonMapper {

	public void updateSql(HashMap<String,Object> hsahMap) throws Exception;
	
	public void insertSql(HashMap<String,Object> hsahMap) throws Exception;
	
	public void deleteSql(HashMap<String,Object> hsahMap) throws Exception;
	
	public List<HashMap<String,Object>> selectsqlForListMap(ParameterPojo parameterPojo) throws Exception;
	
	public HashMap<String,Object> selectSqlForMap(ParameterPojo parameterPojo) throws Exception;
	
}
