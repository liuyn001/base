package lyn.ssh.service.lawyee.webservice;

import lyn.ssh.service.lawyee.webservice.test.ILicenseService;
import lyn.ssh.service.lawyee.webservice.test.LicenseServiceService;

/**
 * 生成webservice客户端步骤 ：在jdk bin目录下用命令行打开
 * wsimport -keep -p lyn.ssh.service.lawyee.webservice.test -verbose http://test.oa.acla.org.cn/ws/licenseService?wsdl
 * @ClassName: WebServiceFactory 
 * @Description: TODO 
 * @author lyn 
 * @date 2018年8月30日 上午11:32:20
 */

public class WebServiceFactory {

	private static LicenseServiceService testLicenseServiceService = null;
	
	/**
	 * 获取测试环境调用客户端
	 * @return
	 */
	public static ILicenseService getTestLicenseServiceService(){
		testLicenseServiceService = new LicenseServiceService();
		return testLicenseServiceService.getLicenseServicePort();
	}
	
}
