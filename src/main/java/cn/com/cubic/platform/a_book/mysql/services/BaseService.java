package cn.com.cubic.platform.a_book.mysql.services;

import cn.com.cubic.platform.a_book.mysql.entity.BaseEntity;
import cn.com.cubic.platform.a_book.mysql.entity.BaseExample;
import cn.com.cubic.platform.a_book.mysql.vo.PageForm;
import cn.com.cubic.platform.a_book.mysql.vo.PageParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/6/22.
 **/

public interface BaseService<P extends BaseEntity,Q extends BaseExample> {

    int countByExample(Q example);

    int deleteByExample(Q example);

    int insert(P record);

    int insertSelective(P record);

    List<P> selectByExampleWithRowbounds(Q example, RowBounds rowBounds);

    List<P> selectByExample(Q example);

    int updateByExampleSelective(@Param("record") P record, @Param("example") Q example);

    int updateByExample(@Param("record") P record, @Param("example") Q example);


    /**
     * example 查询条件，pageForm分页数据
     * pageForm包含了查询条件
     * @param example
     * @param
     * @return
     */
    PageParams<P> listPage(Q example, PageParams pageParams);

}
