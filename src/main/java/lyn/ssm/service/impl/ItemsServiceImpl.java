package lyn.ssm.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import lyn.ssm.mapper.ItemsMapper;
import lyn.ssm.mapper.custom.ItemsMapperCustom;
import lyn.ssm.po.Items;
import lyn.ssm.po.custom.ItemsCustom;
import lyn.ssm.po.vo.ItemsQueryVo;
import lyn.ssm.service.ItemsService;

/**
 * 
 * @ClassName: ItemsServiceImpl 
 * @Description: TODO 商品管理的实现类
 * @author lyn 
 * @date 2017-4-12 上午11:15:50
 */
public class ItemsServiceImpl implements ItemsService {

	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	@Autowired
	private ItemsMapper itemsMapper;
	
	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)
			throws Exception {
		//通过ItemsCustomMapper来查询数据库
		return itemsMapperCustom.findItemsList(itemsQueryVo);
	}

	@Override
	public ItemsCustom findItemsById(Integer id) throws Exception {
		
		Items items = itemsMapper.selectByPrimaryKey(id);
		
		ItemsCustom itemsCustom = null;
		//将items中的属性值拷贝到itemsCustom
		if(items!=null){
			itemsCustom = new ItemsCustom();
			BeanUtils.copyProperties(items, itemsCustom);
		}
		
		return itemsCustom;
	}

	@Override
	public void updateTtems(Integer id, ItemsCustom itemsCustom) throws Exception {
		
		//校验Id是否为空，若为空抛出异常
		
		//更新商品信息 updateByPrimaryKeyWithBLOBs 根据id更新items表中的所有字段包括大文本
		//updateByPrimaryKeyWithBLOBs 要求必须传入id
		itemsCustom.setId(id);
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
	}

	@Override
	public void deleteItems(Integer[] items_id) throws Exception {
		//遍历items_id数组
		for (int i=0;i<items_id.length;i++){
			//删除商品信息
			itemsMapper.deleteByPrimaryKey(items_id[i]);
		}
	}

}
