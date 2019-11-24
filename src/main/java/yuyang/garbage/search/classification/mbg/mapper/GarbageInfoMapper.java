package yuyang.garbage.search.classification.mbg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yuyang.garbage.search.classification.mbg.model.GarbageInfo;
import yuyang.garbage.search.classification.mbg.model.GarbageInfoExample;


public interface GarbageInfoMapper {
    long countByExample(GarbageInfoExample example);

    int deleteByExample(GarbageInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GarbageInfo record);

    int insertSelective(GarbageInfo record);

    List<GarbageInfo> selectByExample(GarbageInfoExample example);

    GarbageInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GarbageInfo record, @Param("example") GarbageInfoExample example);

    int updateByExample(@Param("record") GarbageInfo record, @Param("example") GarbageInfoExample example);

    int updateByPrimaryKeySelective(GarbageInfo record);

    int updateByPrimaryKey(GarbageInfo record);
}