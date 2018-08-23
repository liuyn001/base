package lyn.ssm.service;

import java.util.List;

import lyn.ssm.po.custom.ItemsCustom;
import lyn.ssm.po.vo.ItemsQueryVo;

/**
 * 
 * @ClassName: ItemsService 
 * @Description: TODO 商品管理Service
 * @author lyn 
 * @date 2017-4-12 上午11:11:33
 */
public interface ItemsService {

	/**
	 * 
	 * @Title: findItemsList 
	 * @Description: TODO 商品列表查询 
	 * @param @param itemsQueryVo
	 * @param @return
	 * @param @throws Exception 设定文件 
	 * @return List<ItemsCustom> 返回类型 
	 * @author lyn 
	 * @date 2017-4-12 下午3:43:25 
	 * @throws
	 */
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
	
	/**
	 * 
	 * @Title: findItemsById 
	 * @Description: TODO 根据商品id查询商品信息
	 * @param @param id 查询商品的id
	 * @param @return
	 * @param @throws Exception 设定文件 
	 * @return ItemsCustom 返回类型 
	 * @author lyn 
	 * @date 2017-4-12 下午3:43:03 
	 * @throws
	 */
	public ItemsCustom findItemsById(Integer id) throws Exception;
	
	/**
	 * 
	 * @Title: updateTtems 
	 * @Description: TODO 修改商品信息
	 * @param @param id 修改商品的id
	 * @param @param itemsCustom 修改的商品的信息
	 * @param @throws Exception 设定文件 
	 * @return void 返回类型 
	 * @author lyn 
	 * @date 2017-4-12 下午3:42:22 
	 * @throws
	 */
	public void updateTtems(Integer id, ItemsCustom itemsCustom) throws Exception;
	
	/**
	 * 
	 * @Title: deleteItems 
	 * @Description: TODO 商品的批量删除 
	 * @param @throws Exception 设定文件 
	 * @return void 返回类型 
	 * @author lyn 
	 * @date 2017-4-13 下午4:50:58 
	 * @throws
	 */
	public void deleteItems(Integer[] items_id) throws Exception;
}
