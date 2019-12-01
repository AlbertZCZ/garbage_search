package yuyang.garbage.search.classification.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import yuyang.garbage.search.classification.mbg.mapper.CityInfoMapper;
import yuyang.garbage.search.classification.mbg.model.CityInfo;
import yuyang.garbage.search.classification.mbg.model.CityInfoExample;
import yuyang.garbage.search.classification.service.CityService;
import yuyang.garbage.search.classification.vo.CityParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: 搜索城市
 * @Date: 2019/11/16 下午4:56
 **/
@Service
public class CityServiceImpl implements CityService {
    @Autowired(required = false)
    private CityInfoMapper cityInfoMapper;

    @Override
    public List<CityInfo> search(String city) {
        CityInfoExample example = new CityInfoExample();
        example.setDistinct(true);
        CityInfoExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(city)) {
            criteria.andCityLike("%" + city + "%");
        }
        List<CityInfo> cityInfoList = cityInfoMapper.selectByExample(example);
        List<String> cityName = new ArrayList<>();
        Iterator<CityInfo> iterator = cityInfoList.iterator();
        while (iterator.hasNext()) {
            CityInfo next = iterator.next();
            if (!cityName.contains(next.getCity())) {
                cityName.add(next.getCity());
                next.setDistrict(null);
            }else {
                iterator.remove();
            }
        }
        return cityInfoList;
    }

    @Override
    public CityInfo searchAccurate(String city) {
        CityInfoExample example = new CityInfoExample();
        example.setOrderByClause("id");
        CityInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCityEqualTo(city);
        List<CityInfo> cityInfoList = cityInfoMapper.selectByExample(example);
        if (cityInfoList.size() > 0) {
            return cityInfoList.get(0);
        }
        return null;
    }

    @Override
    public List<Integer> getAllId() {
        CityInfoExample example = new CityInfoExample();
        example.setOrderByClause("id");
        List<CityInfo> cityInfoList = cityInfoMapper.selectByExample(example);
        List<String> cityName = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        for (CityInfo next : cityInfoList) {
            if (!cityName.contains(next.getCity())) {
                cityName.add(next.getCity());
                idList.add(next.getId());
            }
        }
        return idList;
    }

    @Override
    public int add(CityInfo cityInfo) {

        return cityInfoMapper.insert(cityInfo);
    }

    @Override
    public int delete(CityParam cityParam) {
        if (cityParam.getCityId() != null) {
            return cityInfoMapper.deleteByPrimaryKey(cityParam.getCityId());
        }
        List<Integer> cityIdList = cityParam.getCityIdList();
        if (cityIdList != null && cityIdList.size() > 0) {
            CityInfoExample example = new CityInfoExample();
            CityInfoExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(cityIdList);
            return cityInfoMapper.deleteByExample(example);
        }
        return 0;
    }

    @Override
    public int update(CityInfo cityInfo) {

        return cityInfoMapper.updateByPrimaryKey(cityInfo);
    }

    @Override
    public int open(CityParam cityParam) {
        List<Integer> cityIdList = new ArrayList<>();
        List<String> cityList = new ArrayList<>();
        CityInfoExample example = new CityInfoExample();
        if (cityParam.getCityId() != null) {
            CityInfo cityInfo = cityInfoMapper.selectByPrimaryKey(cityParam.getCityId());
            cityList.add(cityInfo.getCity());
        }else if ((cityIdList = cityParam.getCityIdList()).size() > 0){

            CityInfoExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(cityIdList);
            List<CityInfo> cityInfos = cityInfoMapper.selectByExample(example);
            cityInfos.forEach(city -> cityList.add(city.getCity()));
        }
        if (cityList.size() > 0) {
            CityInfo update = new CityInfo();
            update.setOpend(1);

            example.clear();
            CityInfoExample.Criteria updateCriteria = example.createCriteria();
            updateCriteria.andCityIn(cityList);
            return cityInfoMapper.updateByExample(update,example);
        }
        return 0;
    }
}
