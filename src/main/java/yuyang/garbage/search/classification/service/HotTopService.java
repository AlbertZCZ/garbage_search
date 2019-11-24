package yuyang.garbage.search.classification.service;

import yuyang.garbage.search.classification.mbg.model.HotTop;

import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: 搜索热度服务
 * @Date: 2019/11/16 下午8:28
 **/
public interface HotTopService {
    /**
     * 查询地区搜索垃圾top9
     * @param city
     * @return
     */
    List<String> getHotByCity(String city);

    /**
     * 搜索加入top
     * @param city
     * @param garbage
     * @return
     */
    int addHot(String city,String garbage);

    /**
     * 根据城市名称获取热门垃圾信息
     * @param city null为查所有城市
     * @return
     */
    List<HotTop> listHotTop(String city);
}
