package lyn.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lyn.ssm.po.custom.ItemsCustom;

/**
 * 
 * @ClassName: JsonTest 
 * @Description: TODO 测试json交互
 * @author lyn 
 * @date 2017-4-18 下午2:15:53
 */
@Controller
public class JsonTest {

	//请求json串（商品信息），返回json串（商品信息）
	//@RequestBody将请求的商品信息的json串转成itemsCustom对象
	//@ResponseBody将itemsCustom对象 转成json 串
	@RequestMapping("/requestJson")
	public @ResponseBody ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom){
		
		return itemsCustom;
	}
	
	//请求key/value（商品信息），返回json串（商品信息）
	@RequestMapping("/responseJson")
	public @ResponseBody ItemsCustom responseJson(ItemsCustom itemsCustom){
		
		return itemsCustom;
	}
	
}
