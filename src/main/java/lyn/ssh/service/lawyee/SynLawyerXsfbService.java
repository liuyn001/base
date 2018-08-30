package lyn.ssh.service.lawyee;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


@Service("synLawyerXsfbService")
public class SynLawyerXsfbService {

	private static final Logger log = Logger.getLogger(SynLawyerXsfbService.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	/**
	 * 同步律师新执业发证 (新接口)
	 * @param oid 
	 * @return serviceResult
	 * @author liuyn
	 * @throws ParseException 
	 */
	public Object registerLawyer() {
		Object sr="";
		String oid = "3ba3922e4fd74db8b0bcdac6c05ccbe8";
		WebServiceInvokManager webServiceInvokManager = new WebServiceInvokManager();
		StringBuffer seSql = new StringBuffer();
		seSql.append("SELECT pcid,XM,YWXM,ZYJG,ZYLB,CSRQ,XB,ZYZGZSBH,ZYZGZSLB,ZYZGZQDSJ,ZZMM,MZ,JG,ZGXL,ZGXW,ZJLB,ZJBH,LXDH,SJHM,DZYX,SCZYRQ,SCZYD,SFPZ,SFHHR,SFFZR,PZWH,PZSJ,lawyerId FROM jalawadmin_syninfo_xjk WHERE oid=':oid'");
		String value = seSql.toString();
		value = value.replace(":oid", oid);
		Map<String, Object> queryForMap = namedParameterJdbcTemplate.queryForMap(value,new HashMap<>());
		try {
			Map<String, Object> theques = queryForMap;
			//String pcid = (String)theques.get("pcid");
			theques.remove("pcid");
			//String lawyerId = (String)theques.get("lawyerId");
			//执业类别
			String ZYLB=(String) theques.get("ZYLB");
			if ("practice_type_sole".equals(ZYLB)) {
				theques.put("ZYLB", "01");
			}
			if ("practice_type_part".equals(ZYLB)) {
				theques.put("ZYLB", "02");
			}
			if ("practice_type_aid".equals(ZYLB)) {
				theques.put("ZYLB", "09");
			}
			if ("practice_type_company".equals(ZYLB)) {
				theques.put("ZYLB", "04");
			}
			if ("practice_type_puboffice".equals(ZYLB)) {
				theques.put("ZYLB", "03");
			}
			
			//职业资格证书类别
			String ZYZGZSLB=(String) theques.get("ZYZGZSLB");
			if ("root_xsfbzd_zyzgzslb_flzyzgzs".equals(ZYZGZSLB)) {
				theques.put("ZYZGZSLB", "01");
			}
			if ("root_xsfbzd_zyzgzslb_lszgz".equals(ZYZGZSLB)) {
				theques.put("ZYZGZSLB", "02");
			}
			if ("root_xsfbzd_zyzgzslb_txlszy".equals(ZYZGZSLB)) {
				theques.put("ZYZGZSLB", "03");
			}
			//性别
			String XB=(String) theques.get("XB");
			if ("sex_male".equals(XB)) {
				theques.put("XB", "01");
			}
			if ("sex_female".equals(XB)) {
				theques.put("XB", "02");
			}
			//学历
			String ZGXL=(String) theques.get("ZGXL");
			if ("staff_education_special".equals(ZGXL)) {
				theques.put("ZGXL", "中专");
			}
			if ("staff_education_doctorh".equals(XB)) {
				theques.put("ZGXL", "其它");
			}
			//学位
			String ZGXW=(String) theques.get("ZGXW");
			if ("staff_degree_fbachelor".equals(ZGXW)) {
				theques.put("ZGXW", "双学士");
			}
			
			//身份证类别
			String ZJLB=(String) theques.get("ZJLB");
			if ("papers_type_identity".equals(ZJLB)) {
				theques.put("ZJLB", "01");
			}
			if ("papers_type_passport".equals(ZJLB)) {
				theques.put("ZJLB", "04");
			}else {
				theques.put("ZJLB", "99");
			}
			
			theques.remove("lawyerId");
			if (webServiceInvokManager != null) {
				//处理字典
				//处理“是否”的字典问题;司法部0否1是，本地1否0是
				String SFPZ=(String) theques.get("SFPZ");
				if("0".equals(SFPZ)){
					theques.put("SFPZ", "1");
				} else if("1".equals(SFPZ)){
					theques.put("SFPZ", "0");
				} else {
					theques.put("SFPZ", "0");
				}
				
				String SFHHR=(String) theques.get("SFHHR");
				if("0".equals(SFHHR)){
					theques.put("SFHHR", "1");
				} else if("1".equals(SFHHR)){
					theques.put("SFHHR", "0");
				} else {
					theques.put("SFHHR", "0");
				}
				
				String SFFZR=(String) theques.get("SFFZR");
				if("0".equals(SFFZR)){
					theques.put("SFFZR", "1");
				} else if("1".equals(SFFZR)){
					theques.put("SFFZR", "0");
				} else {
					theques.put("SFFZR", "0");
				}
				
				JSONObject json = new JSONObject(theques);
				sr = webServiceInvokManager.registerLawyer(oid,json.toString());
			}
		} catch (Exception e) {
			String excepDesc = e.getMessage();
			log.error("时间处理异常或webService接口调用异常："+ excepDesc,e);
			e.printStackTrace();
			return sr;
		}

		return sr;
	}
	
}
