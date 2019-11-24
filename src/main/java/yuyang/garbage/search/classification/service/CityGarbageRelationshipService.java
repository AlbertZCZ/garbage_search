package yuyang.garbage.search.classification.service;

import yuyang.garbage.search.classification.vo.CityParam;
import yuyang.garbage.search.classification.vo.GarbageParam;

import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: 城市垃圾关联接口
 * @Date: 2019/11/23 下午9:13
 **/
public interface CityGarbageRelationshipService {
    /**
     * 增加关联
     * @param cityId
     * @param garbageId
     * @return
     */
    int addRelationship(int cityId ,int garbageId);

    /**
     * 删除关联
     * @param cityId
     * @param garbageParam
     * @return
     */
    int deleteRelationship(Integer cityId , GarbageParam garbageParam);

    /**
     * 根据城市id删除关联
     * @param cityParam
     * @return
     */
    int deleteBatch(CityParam cityParam);

    /**
     * 根据城市id获取该城市下的垃圾id
     * @param cityId null为查全部
     * @return
     */
    List<Integer> listRelationShip(Integer cityId);

    /**
     * 将垃圾添加到所有城市
     * @param garbageId
     * @return
     */
    int addAllCity(int garbageId,List<Integer> cityIdList);


}
