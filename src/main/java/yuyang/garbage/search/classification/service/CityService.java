package yuyang.garbage.search.classification.service;

import yuyang.garbage.search.classification.mbg.model.CityInfo;
import yuyang.garbage.search.classification.vo.CityParam;

import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: 搜索城市服务
 * @Date: 2019/11/16 下午4:55
 **/
public interface CityService {

    /**
     * 搜索城市
     * @param city
     * @return
     */
    List<CityInfo> search(String city);

    /**
     * 根据城市名称精确搜索
     * @param city
     * @return
     */
    CityInfo searchAccurate(String city);

    /**
     * 查询所有城市id
     * @return
     */
    List<Integer> getAllId();

    /**
     * 增加城市
     * @return
     */
    int add(CityInfo cityInfo);

    /**
     * 删除城市
     * @param cityParam
     * @return
     */
    int delete(CityParam cityParam);

    int update(CityInfo cityInfo);

    /**
     * 开放城市
     * @param cityParam
     * @return
     */
    int open(CityParam cityParam);
}
