package lyn.ssm.po.vo;

import java.util.List;

import lyn.ssm.po.Items;
import lyn.ssm.po.custom.ItemsCustom;

/**
 * 
 * @ClassName: ItemsQueryVo 
 * @Description: TODO 商品的包装对象
 * @author lyn 
 * @date 2017-4-12 上午10:36:43
 */
public class ItemsQueryVo {

	//商品信息
	private Items items;
	
	//批量商品信息 list参数绑定
	private List<ItemsCustom> itemsList;
	
	//map参数绑定
	/*private Map<String, Object> itemsInfo = new HashMap<String, Object>();
	//setter getter方法
*/	
	//为了系统的扩展性，对原始的po进行扩展
	private ItemsCustom itemsCustom;

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public ItemsCustom getItemsCustom() {
		return itemsCustom;
	}

	public void setItemsCustom(ItemsCustom itemsCustom) {
		this.itemsCustom = itemsCustom;
	}

	public List<ItemsCustom> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsCustom> itemsList) {
		this.itemsList = itemsList;
	}
	
}
