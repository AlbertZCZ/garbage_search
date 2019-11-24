package yuyang.garbage.search.classification.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import yuyang.garbage.search.classification.mbg.model.HotTop;
import yuyang.garbage.search.classification.mbg.model.HotTopExample;

public interface HotTopMapper {
    long countByExample(HotTopExample example);

    int deleteByExample(HotTopExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HotTop record);

    int insertSelective(HotTop record);

    List<HotTop> selectByExample(HotTopExample example);

    HotTop selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HotTop record, @Param("example") HotTopExample example);

    int updateByExample(@Param("record") HotTop record, @Param("example") HotTopExample example);

    int updateByPrimaryKeySelective(HotTop record);

    int updateByPrimaryKey(HotTop record);
}