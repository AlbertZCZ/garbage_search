package yuyang.garbage.search.classification.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import yuyang.garbage.search.classification.mbg.mapper.HotTopMapper;
import yuyang.garbage.search.classification.mbg.model.HotTop;
import yuyang.garbage.search.classification.mbg.model.HotTopExample;
import yuyang.garbage.search.classification.service.HotTopService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: 地区垃圾热度服务
 * @Date: 2019/11/16 下午8:33
 **/
@Service
public class HotTopServiceImpl implements HotTopService {
    @Autowired(required = false)
    private HotTopMapper hotTopMapper;

    @Override
    public List<String> getHotByCity(String city) {
        HotTopExample example = new HotTopExample();
        example.setOrderByClause("num desc limit 9");
        HotTopExample.Criteria criteria = example.createCriteria();
        criteria.andCityEqualTo(city);
        List<HotTop> hotTops = hotTopMapper.selectByExample(example);
        List<String> garbageList = new ArrayList<>();
        hotTops.forEach(hotTop -> garbageList.add(hotTop.getGarbageName()));
        return garbageList;
    }

    @Override
    public int addHot(String city, String garbage) {
        HotTopExample example = new HotTopExample();
        HotTopExample.Criteria criteria = example.createCriteria();
        criteria.andCityEqualTo(city);
        criteria.andGarbageNameEqualTo(garbage);
        List<HotTop> hotTops = hotTopMapper.selectByExample(example);
        int result;
        if (hotTops.size() > 0) {
            //更新热度
            HotTop hotTop = hotTops.get(0);
            hotTop.setNum(hotTop.getNum() + 1);
            result = hotTopMapper.updateByPrimaryKey(hotTop);
        }else {
            //加入热度
            HotTop hotTop = new HotTop();
            hotTop.setNum(1);
            hotTop.setCity(city);
            hotTop.setGarbageName(garbage);
            result = hotTopMapper.insert(hotTop);
        }
        return result;
    }

    @Override
    public List<HotTop> listHotTop(String city) {
        HotTopExample example = new HotTopExample();
        example.setOrderByClause("num desc");
        HotTopExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(city)) {
            criteria.andCityEqualTo(city);
        }
        return hotTopMapper.selectByExample(example);
    }
}
