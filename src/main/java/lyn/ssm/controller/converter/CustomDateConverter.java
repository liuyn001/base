package lyn.ssm.controller.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @ClassName: CustomDateConverter 
 * @Description: TODO 自定义日期类型转换器
 * @author lyn 
 * @date 2017-4-13 上午11:54:38
 */
public class CustomDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		
		//实现日期转换，转换格式（yyyy-MM-dd HH:mm:ss）
		SimpleDateFormat simDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simDateFormat.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
