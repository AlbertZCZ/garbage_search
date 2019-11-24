package yuyang.garbage.search.classification.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuyang.garbage.search.classification.mbg.mapper.CityGarbageRelationshipMapper;
import yuyang.garbage.search.classification.mbg.model.CityGarbageRelationship;
import yuyang.garbage.search.classification.mbg.model.CityGarbageRelationshipExample;
import yuyang.garbage.search.classification.service.CityGarbageRelationshipService;
import yuyang.garbage.search.classification.vo.CityParam;
import yuyang.garbage.search.classification.vo.GarbageParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: 城市与垃圾关联关系服务
 * @Date: 2019/11/23 下午9:37
 **/
@Service
public class CityGarbageRelationshipServiceImpl implements CityGarbageRelationshipService {
    @Autowired(required = false)
    private CityGarbageRelationshipMapper cityGarbageRelationshipMapper;

    @Override
    public int addRelationship(int cityId, int garbageId) {
        CityGarbageRelationship cityGarbageRelationship = new CityGarbageRelationship();
        cityGarbageRelationship.setCityId(cityId);
        cityGarbageRelationship.setGarbageId(garbageId);
        cityGarbageRelationship.setGmtCreate(new Date());
        cityGarbageRelationship.setGmtModify(new Date());
        cityGarbageRelationship.setIsValid(1);
        return cityGarbageRelationshipMapper.insert(cityGarbageRelationship);
    }

    @Override
    public int deleteRelationship(Integer cityId , GarbageParam garbageParam) {
        CityGarbageRelationship update = new CityGarbageRelationship();
        update.setIsValid(0);
        update.setGmtModify(new Date());
        CityGarbageRelationshipExample example = new CityGarbageRelationshipExample();
        CityGarbageRelationshipExample.Criteria criteria = example.createCriteria();
        if (cityId != null) {
            criteria.andCityIdEqualTo(cityId);
        }
        if (garbageParam.getGarbageId() != null) {
            criteria.andGarbageIdEqualTo(garbageParam.getGarbageId());
        }else if (garbageParam.getGarbageIdList() != null && !garbageParam.getGarbageIdList().isEmpty()) {
            criteria.andGarbageIdIn(garbageParam.getGarbageIdList());
        }

        return cityGarbageRelationshipMapper.updateByExampleSelective(update,example);
    }

    @Override
    public int deleteBatch(CityParam cityParam) {
        CityGarbageRelationship update = new CityGarbageRelationship();
        update.setIsValid(0);
        update.setGmtModify(new Date());
        CityGarbageRelationshipExample example = new CityGarbageRelationshipExample();
        CityGarbageRelationshipExample.Criteria criteria = example.createCriteria();
        if (cityParam.getCityId() != null) {
            criteria.andCityIdEqualTo(cityParam.getCityId());
        }else if (cityParam.getCityIdList() != null && cityParam.getCityIdList().size() > 0) {
            criteria.andCityIdIn(cityParam.getCityIdList());
        }
        return cityGarbageRelationshipMapper.updateByExampleSelective(update,example);
    }

    @Override
    public List<Integer> listRelationShip(Integer cityId) {
        CityGarbageRelationshipExample example = new CityGarbageRelationshipExample();
        CityGarbageRelationshipExample.Criteria criteria = example.createCriteria();
        criteria.andIsValidEqualTo(1);
        if (cityId != null) {
            criteria.andCityIdEqualTo(cityId);
        }
        List<CityGarbageRelationship> cityGarbageRelationships = cityGarbageRelationshipMapper.selectByExample(example);
        List<Integer> garbageIds = new ArrayList<>();
        cityGarbageRelationships.forEach(cityGarbageRelationship -> garbageIds.add(cityGarbageRelationship.getGarbageId()));
        return garbageIds;
    }

    @Override
    public int addAllCity(int garbageId,List<Integer> cityIdList) {

        return cityGarbageRelationshipMapper.addAllCity(garbageId,cityIdList);
    }
}
