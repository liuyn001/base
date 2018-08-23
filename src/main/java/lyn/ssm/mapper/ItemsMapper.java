package lyn.ssm.mapper;

import java.util.List;

import lyn.ssm.po.Items;
import lyn.ssm.po.ItemsExample;

public interface ItemsMapper {
    int countByExample(ItemsExample example);

    int deleteByExample(ItemsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Items record);

    int insertSelective(Items record);

    List<Items> selectByExampleWithBLOBs(ItemsExample example);

    List<Items> selectByExample(ItemsExample example);

    Items selectByPrimaryKey(Integer id);

    int updateByExampleSelective(Items record, ItemsExample example);

    int updateByExampleWithBLOBs(Items record, ItemsExample example);

    int updateByExample(Items record, ItemsExample example);

    int updateByPrimaryKeySelective(Items record);

    int updateByPrimaryKeyWithBLOBs(Items record);

    int updateByPrimaryKey(Items record);
}