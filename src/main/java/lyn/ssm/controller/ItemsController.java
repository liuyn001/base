package lyn.ssm.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lyn.ssm.controller.exception.CustomException;
import lyn.ssm.controller.validation.ValidationGroup1;
import lyn.ssm.po.custom.ItemsCustom;
import lyn.ssm.po.vo.ItemsQueryVo;
import lyn.ssm.service.ItemsService;

/**
 * 
 * @ClassName: ItemsController 
 * @Description: TODO 商品的controller
 * @author lyn 
 * @date 2017-4-12 上午11:51:59
 */
@Controller
//为了对URL进行分类管理，可以在这里定义根路径(窄化请求映射)，最终访问路径是跟路径+子路径（/items/queryItems）
@RequestMapping("/items")
public class ItemsController {

	
	@Autowired
	private ItemsService itemsService;
	
	//商品分类
	//itemtypes:表示将方法的返回值放到request域中的key
	@ModelAttribute("itemtypes")
	public Map<String, String> getItemsTypes() {
		
		Map<String, String> itemsTypes = new HashMap<String, String>();
		itemsTypes.put("101", "数码");
		itemsTypes.put("102", "文具");
		
		return itemsTypes;
		
	}
	
	//商品列表查询
	//@RequestMapping实现queryItems和url进行映射 一个方法 对应一个url
	//一般建议将url和方法名写成一致
	//public ModelAndView queryItems(HttpServletRequest request) throws Exception{//forward转发共享的request
	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest request,ItemsQueryVo itemsQueryVo) throws Exception{
		
		//forward转发共享的request
		//System.out.println(request.getParameter("id"));
		
		//调用service层查找数据
		List<ItemsCustom> itlist = itemsService.findItemsList(itemsQueryVo);
		
		
		//返回ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		//相当于request中的setAttribute，在jsp页面中通过itlist获取数据
		modelAndView.addObject("itlist", itlist);
		//指定视图
		//下面的路径，如果在视图解析器中配置了jsp路径前缀和后缀
		//modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		modelAndView.setViewName("items/itemsList");
		
		return modelAndView;
	}
	
	//1.商品信息修改页面的展示 返回modelAndView
	//@RequestMapping("/editItems")
	//限制http请求方法 可以是post 和 get
	/*@RequestMapping(value="/editItems",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView editItems() throws Exception {
		
		//调用service更加id查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsById(1);
		
		//返回ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		
		//将商品信息放入modelAndView中
		modelAndView.addObject("itemsCustom", itemsCustom);
		
		//返回商品修改页面
		modelAndView.setViewName("items/editItems");
		
		return modelAndView;
	}*/
	
	//2.返回逻辑视图名
	//@RequestParam 对简单类型的参数进行绑定，如果不使用，要求request传入参数名称和controller方法的形参名称一致，方可绑定成功
	//@RequestParam(value="id",required="true",defaultValue="") required:表示必须传入的参数 defaultValue:默认值
	//public String editItems(Model model,@RequestParam(value="id") Integr items_id) throws Exception {
	
	@RequestMapping(value="/editItems",method={RequestMethod.POST,RequestMethod.GET})
	public String editItems(Model model,@RequestParam(value="id") Integer items_id) throws Exception {
		
		//调用service更加id查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);
		
		//判断商品信息是否为空
		if(itemsCustom==null){
			throw new CustomException("修改的商品信息不存在！");
		}
		
		//通过形参的model将model的数据传送到页面
		//相当于modelAndView.addObject方法
		model.addAttribute("items", itemsCustom);
		return "items/editItems";
	}
	
	//1.修改商品信息的提交 返回modelAndView
	//@RequestMapping("/editItemsSubmit")
	//限制http请求方法 可以是post 和 get
	/*@RequestMapping(value="/editItemsSubmit",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView editItemsSubmit() throws Exception{
		
		//调用service更新商品信息
		
		//返回ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		
		//返回商品修改页面
		modelAndView.setViewName("success");
		
		return modelAndView;
	}*/
	
	//2.重定向：浏览器像是的url会变化，修改提交的request数据无法传到重定向的地址，request无法共享
	/*@RequestMapping(value="/editItemsSubmit",method={RequestMethod.POST,RequestMethod.GET})
	public String editItemsSubmit() throws Exception{
		
		//调用service更新商品信息
		
		//重定向
		return "redirect:queryItems.action";
	}*/
	
	//2.转发：浏览器像是的url不变化，request可以共享
	//页面中input的name和controller中的pojo形参的属性名称一致就能将页面中的参数绑定到pojo中
	/*在需要校验的pojo前面添加@Validated，在需要校验的pojo后边添加BindingResult接收校验出错的信息
	注意@Validated和BindingResult配对出现，并且形参顺序固定一前一后
	value={ValidationGroup1.class}指定使用ValidationGroup.class中的校验*/
	/*springmvc默认提供一种数据回显，会把pojo放入request域中，属性名首字母小写，不要要使用任何注解
	另一种方式：使用@ModelAttribute注解 value中的值和jsp中一直即可
	@ModelAttribute注解还用一个作用见getItemsTypes方法
	还有一种方式是：直接利用model返回model.addAttribute("items", itemsCustom);简单数据类型的回显只能使用这种方法
	*/
	@RequestMapping(value="/editItemsSubmit",method={RequestMethod.POST,RequestMethod.GET})
	public String editItemsSubmit(Model model,HttpServletRequest request,Integer id,
			@ModelAttribute("items") @Validated(value={ValidationGroup1.class}) ItemsCustom itemsCustom, 
			BindingResult bindingResult,
			MultipartFile items_pic//接收商品图片
			) throws Exception{
		
		//获取校验信息
		if(bindingResult.hasErrors()){
			//输出错误信息
			List<ObjectError> listError = bindingResult.getAllErrors();
			for(ObjectError errorv : listError){
				//输出错误信息
				System.out.println(errorv.getDefaultMessage());
			}
			//将错误信息传入页面
			model.addAttribute("listError", listError);
			//转发返回到商品修改页面
			return "items/editItems";
		}
		
		//获取图片的原始名称
		String originalFilename = items_pic.getOriginalFilename();
		//上传图片
		if(items_pic!=null && originalFilename!=null && originalFilename.length()>0){
			
			//存储图片的位置
			String pic_th="D:\\Java\\tomcat7\\apache-tomcat-7.0.53-1\\webapps\\pic\\";
			//生成新的图片名称
			String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
			//新的图片
			File newFile = new File(pic_th+newFileName);
			
			//将内存中的数据写入磁盘
			items_pic.transferTo(newFile);
			//图片上传成功，将图片的名称写到itemsCustom
			itemsCustom.setPic(newFileName);
		}
		
		//调用service更新商品信息
		itemsService.updateTtems(id, itemsCustom);
		
		//转发
		return "forward:queryItems.action";
	}
	
	//3.返回void：浏览器像是的url不变化，request可以共享
	/*@RequestMapping(value="/editItemsSubmit",method={RequestMethod.POST,RequestMethod.GET})
	public void editItemsSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//调用service更新商品信息
		
		//通过response指定响应结果，例如json数据
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;chartset=utf-8");
		response.getWriter().write("json串");
		
		//转发
		request.getRequestDispatcher("").forward(request, response);
		
		//或者通过response重定向
		response.sendRedirect("url");
		
	}*/
	
	//批量商品的删除
	@RequestMapping("/deleteItems")
	public String deleteItems(Integer[] items_id) throws Exception{
		
		//调用service删除数据
		itemsService.deleteItems(items_id);
		
		return "redirect:queryItems.action";
	}
	
	//批量修改商品查询
	@RequestMapping("/editItemsQuery")
	public ModelAndView editItemsQuery(HttpServletRequest request,ItemsQueryVo itemsQueryVo) throws Exception{
		
		//调用service层查找数据
		List<ItemsCustom> itlist = itemsService.findItemsList(itemsQueryVo);
		
		
		//返回ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		//相当于request中的setAttribute，在jsp页面中通过itlist获取数据
		modelAndView.addObject("itlist", itlist);
		
		modelAndView.setViewName("items/editItemsQuery");
		
		return modelAndView;
	}
	
	//批量修修改商品提交
	@RequestMapping("/editItemsAllSubmit")
	public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo) throws Exception{
		
		List<ItemsCustom> list = itemsQueryVo.getItemsList();
		ItemsCustom itemsCustom = null;
		for(int i=0;i<list.size();i++){
			itemsCustom = list.get(i);
			if(itemsCustom.getId()!=null){
				itemsService.updateTtems(itemsCustom.getId(), itemsCustom);
			}
		}
		
		return "redirect:queryItems.action";
	}
	
	//查询商品信息，输出json
	///itemsView/{id}:{id}表示将这个位置的参数传入@PathVariable("id")指定的名称中
	@RequestMapping("/itemsView/{id}")
	public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id) throws Exception {
		//调用service查询商品信息
		ItemsCustom itemsCustom =itemsService.findItemsById(id);
		return itemsCustom;
	}
}
