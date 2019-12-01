package yuyang.garbage.search.classification.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import yuyang.garbage.search.classification.mbg.mapper.GarbageInfoMapper;
import yuyang.garbage.search.classification.mbg.model.GarbageInfo;
import yuyang.garbage.search.classification.mbg.model.GarbageInfoExample;
import yuyang.garbage.search.classification.service.GarbageService;
import yuyang.garbage.search.classification.vo.GarbageAppVo;
import yuyang.garbage.search.classification.vo.GarbageVo;

import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: TODO
 * @Date: 2019/11/16 下午3:46
 **/
@Service
public class GarbageServiceImpl implements GarbageService {
    @Autowired
    private GarbageInfoMapper garbageInfoMapper;

    @Override
    public List<GarbageInfo> search(String name) {
        GarbageInfoExample example = new GarbageInfoExample();
        GarbageInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIsValidEqualTo(1);
        criteria.andNameLike("%" + name + "%");
        return garbageInfoMapper.selectByExample(example);
    }

    @Override
    public int add(GarbageInfo garbageInfo) {
        return garbageInfoMapper.insert(garbageInfo);
    }

    @Override
    public List<GarbageInfo> listGarbage(GarbageVo garbageVo,List<Integer> garbageIdList) {
        GarbageInfoExample example = new GarbageInfoExample();
        GarbageInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIsValidEqualTo(1);
        if (!StringUtils.isEmpty(garbageVo.getGarbageName())) {
            criteria.andNameLike("%" + garbageVo.getGarbageName() + "%");
        }
        if (!StringUtils.isEmpty(garbageVo.getType())) {
            criteria.andTypeEqualTo(garbageVo.getType());
        }
        if (garbageIdList.size() > 0) {
            criteria.andIdIn(garbageIdList);
        }
        return garbageInfoMapper.selectByExample(example);
    }

    @Override
    public int update(GarbageVo garbageVo) {
        GarbageInfo update = new GarbageInfo();
        update.setName(garbageVo.getGarbageName());
        GarbageInfoExample example = new GarbageInfoExample();
        GarbageInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIsValidEqualTo(1);
        criteria.andIdEqualTo(garbageVo.getGarbageId());
        return garbageInfoMapper.updateByExample(update,example);
    }
}
