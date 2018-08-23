package lyn.ssm.mapper.custom;

import java.util.List;

import lyn.ssm.po.custom.ItemsCustom;
import lyn.ssm.po.vo.ItemsQueryVo;


public interface ItemsMapperCustom {
    
	//商品列表查询
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
	
}