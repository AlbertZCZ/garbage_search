package yuyang.garbage.search.classification.service;

import yuyang.garbage.search.classification.mbg.model.GarbageInfo;
import yuyang.garbage.search.classification.vo.GarbageVo;

import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: 搜索垃圾服务
 * @Date: 2019/11/16 下午3:44
 **/
public interface GarbageService {
    /**
     * 根据垃圾名称查询垃圾信息
     * @param name
     * @return
     */
    List<GarbageInfo> search(String name);

    /**
     * 增加垃圾
     * @param garbageInfo
     * @return
     */
    int add(GarbageInfo garbageInfo);

    /**
     * 后台查询垃圾信息
     * @param garbageVo
     * @return
     */
    List<GarbageInfo> listGarbage(GarbageVo garbageVo,List<Integer> garbageIdList);

    int update(GarbageVo garbageVo);
}
