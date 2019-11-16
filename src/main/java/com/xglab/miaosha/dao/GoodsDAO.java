package com.xglab.miaosha.dao;

import com.xglab.miaosha.domain.Goods;
import com.xglab.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/14
 */
@Mapper
public interface GoodsDAO {

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price " +
            "from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price " +
            "from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update miaosha_goods set stock_count = stock_count -1 where goods_id = #{id}")
    int reduceStock(Goods good);
}
