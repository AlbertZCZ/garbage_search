package yuyang.garbage.search.classification.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import yuyang.garbage.search.classification.mbg.model.CityGarbageRelationship;
import yuyang.garbage.search.classification.mbg.model.CityGarbageRelationshipExample;

public interface CityGarbageRelationshipMapper {
    long countByExample(CityGarbageRelationshipExample example);

    int deleteByExample(CityGarbageRelationshipExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CityGarbageRelationship record);

    int insertSelective(CityGarbageRelationship record);

    List<CityGarbageRelationship> selectByExample(CityGarbageRelationshipExample example);

    CityGarbageRelationship selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CityGarbageRelationship record, @Param("example") CityGarbageRelationshipExample example);

    int updateByExample(@Param("record") CityGarbageRelationship record, @Param("example") CityGarbageRelationshipExample example);

    int updateByPrimaryKeySelective(CityGarbageRelationship record);

    int updateByPrimaryKey(CityGarbageRelationship record);

    int addAllCity(@Param("garbageId") int garbageId,@Param("cityIdList") List<Integer> cityIdList);
}