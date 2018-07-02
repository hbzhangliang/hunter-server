package cn.com.cubic.platform.hunter.mysql.mapper;

import cn.com.cubic.platform.hunter.mysql.entity.BaseEntity;
import cn.com.cubic.platform.hunter.mysql.entity.BaseExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/6/22.
 **/

public interface BaseMapper<P extends BaseEntity,Q extends BaseExample> {

    int countByExample(Q example);

    int deleteByExample(Q example);

    int insert(P record);

    int insertSelective(P record);

    List<P> selectByExampleWithRowbounds(Q example, RowBounds rowBounds);

    List<P> selectByExample(Q example);

    int updateByExampleSelective(@Param("record") P record, @Param("example") Q example);

    int updateByExample(@Param("record") P record, @Param("example") Q example);

}
