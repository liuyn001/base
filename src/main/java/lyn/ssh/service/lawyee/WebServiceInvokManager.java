package lyn.ssh.service.lawyee;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import lyn.ssh.service.lawyee.webservice.WebServiceFactory;
import lyn.ssh.service.lawyee.webservice.test.ILicenseService;

public class WebServiceInvokManager {

	private String login_test(ILicenseService testLicenseServiceService) {
		String account = "430000";
		String password = "430000";
		return testLicenseServiceService.login(account, password);
	}

	/**
	 * 许可律师（重新）执业
	 * 
	 * @param json
	 *            参数为：JSON字符串，格式为：{“name”:”value”}标号标注为必填项
	 * @param type
	 *            registerLawyer
	 * @throws JSONException
	 * @接口描述 主要用于登记许可律师执业（重新执业），获得律师的执业证号
	 */
	public JSONObject registerLawyer(String serviceId, String jsonstr) throws JSONException {
		JSONObject json = new JSONObject(jsonstr);
		String estr = "";
		String XM = json.optString("XM", "");// 中文名
		if ("".equals(XM)) {
			estr = estr + "中文名为空、";
		}

		String ZYJG = json.optString("ZYJG", "");// ZYJG执业机构
		if ("".equals(ZYJG)) {
			estr = estr + "执业机构为空、";
		}
		String ZYLB = json.optString("ZYLB", "");// 执业类别
		if ("".equals(ZYLB)) {
			estr = estr + "执业类别为空、";
		}
		String CSRQ = json.optString("CSRQ", "");// 出生日期
		//CSRQ = numToDateStr(CSRQ);
		if (null == CSRQ || "".equals(CSRQ) || CSRQ == "null") {
			estr = estr + "出生日期为空、";
		} else {
			CSRQ = CSRQ.substring(0, 10);
			if (CSRQ.length() != 10) {
				estr = estr + "出生日期格式错误、";
			} else {
				//json.put("CSRQ", CSRQ);
			}
		}
		String XB = json.optString("XB", "");// 性别
		if ("".equals(XB)) {
			estr = estr + "性别为空、";
		}
		String ZYZGZSBH = json.optString("ZYZGZSBH", "");// 职业资格证书编号
		if ("".equals(ZYZGZSBH)) {
			estr = estr + "职业资格证书编号为空、";
		}
		String ZYZGZSLB = json.optString("ZYZGZSLB", "");// 职业资格证书类别
		if ("".equals(ZYZGZSLB)) {
			estr = estr + "职业资格证书编号为空、";
		}
		String ZYZGZQDSJ = json.optString("ZYZGZQDSJ", "");// ZYZGZQDSJ职业资格证书取得时间
		//ZYZGZQDSJ = numToDateStr(ZYZGZQDSJ);
		if (null == ZYZGZQDSJ || "".equals(ZYZGZQDSJ) || ZYZGZQDSJ == "null") {
			estr = estr + "职业资格证书取得时间为空、";
		} else {
			ZYZGZQDSJ = ZYZGZQDSJ.substring(0, 10);
			if (ZYZGZQDSJ.length() != 10) {
				estr = estr + "职业资格证书取得时间格式错误、";
			} else {
				//json.put("ZYZGZQDSJ", ZYZGZQDSJ);
			}
		}
		String ZZMM = json.optString("ZZMM", "");// ZZMM 政治面貌
		if ("".equals(ZZMM)) {
			estr = estr + "政治面貌为空、";
		}
		String MZ = json.optString("MZ", "");//// MZ民族
		if ("".equals(MZ)) {
			estr = estr + "民族为空、";
		}
		String JG = json.optString("JG", "");// 籍贯
		if ("".equals(JG)) {
			estr = estr + "籍贯为空、";
		}
		String ZGXL = json.optString("ZGXL", "");// 最高学历
		if ("".equals(ZGXL)) {
			estr = estr + "最高学历为空、";
		}
		String ZGXW = json.optString("ZGXW", "");// 最高学位
		if ("".equals(ZGXW)) {
			estr = estr + "最高学位为空、";
		}
		String ZJLB = json.optString("ZJLB", "");// 身份证件类别
		if ("".equals(ZJLB)) {
			estr = estr + "身份证件类别为空、";
		}
		String ZJBH = json.optString("ZJBH", "");// 身份证件编号
		if ("".equals(ZJBH)) {
			estr = estr + "身份证件编号为空、";
		}
		String SJHM = json.optString("SJHM", "");// 手机号码
		if ("".equals(SJHM)) {
			estr = estr + "手机号码为空、";
		}
		String SCZYRQ = json.optString("SCZYRQ", "");// 首次执业日期
		//SCZYRQ = numToDateStr(SCZYRQ);
		if (null == SCZYRQ || "".equals(SCZYRQ) || SCZYRQ == "null") {
			estr = estr + "首次执业日期为空、";
		} else {
			SCZYRQ = SCZYRQ.substring(0, 10);
			if (SCZYRQ.length() != 10) {
				estr = estr + "首次执业日期格式错误、";
			} else {
				//json.put("SCZYRQ", SCZYRQ);
			}
		}
		String SCZYD = json.optString("SCZYD", "");// 首次执业地
		if ("".equals(SCZYD)) {
			estr = estr + "首次执业地为空、";
		}
		String SFPZ = json.optString("SFPZ", "");// 是否派驻
		if ("".equals(SFPZ)) {
			estr = estr + "是否派驻为空、";
		}
		String SFHHR = json.optString("SFHHR", "");// 是否合伙人
		if ("".equals(SFHHR)) {
			estr = estr + "是否合伙人为空、";
		}
		String SFFZR = json.optString("SFFZR", "");// 是否负责人
		if ("".equals(SFFZR)) {
			estr = estr + "是否负责人为空、";
		}
		String PZWH = json.optString("PZWH", "");// 批准文号
		if ("".equals(PZWH)) {
			estr = estr + "批准文号为空、";
		}
		String PZSJ = json.optString("PZSJ", "");// 批准日期
		//PZSJ = numToDateStr(PZSJ);
		if (null == PZSJ || "".equals(PZSJ) || PZSJ == "null") {
			estr = estr + "批准日期为空、";
		} else {
			PZSJ = PZSJ.substring(0, 10);
			if (PZSJ.length() != 10) {
				estr = estr + "批准日期格式错误、";
			} else {
				//json.put("PZSJ", PZSJ);
			}
		}

		if ("".equals(estr)) {// 异常字符串未拼接 证明数据正常
			// 获取webservice客户端调用工具----登录
			ILicenseService oaLicenseServiceService = WebServiceFactory.getTestLicenseServiceService();
			String loginresult = this.login_test(oaLicenseServiceService);

			// 测试环境
			/*
			 * com.lawyee.lawyeeCasSupport.manager.wsclient.test.ILicenseService
			 * testLicenseServiceService = WebServiceFactory.getTestLicenseServiceService();
			 * //String loginresult = this.login_test(testLicenseServiceService); String
			 * loginresult = this.login_test(testLicenseServiceService);
			 */

			JSONObject loginjson = new JSONObject(loginresult);
			if (!loginjson.optString("code").isEmpty() && "1".equals(loginjson.optString("code"))) {// 登录成功
				String registerLawyer = oaLicenseServiceService.registerLawyer(jsonstr);

				JSONObject registerLawyerjson = new JSONObject(registerLawyer);

				return registerLawyerjson;

			}

			return null;

		}
		return null;

	}

	private String numToDateStr(String str) {

		try {
			long unixstamp = Long.valueOf(str);
			Date dt = new Date(unixstamp);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			return sdf.format(dt);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "";
		}
	}
}