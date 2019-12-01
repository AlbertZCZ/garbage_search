package yuyang.garbage.search.classification.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yuyang.garbage.search.classification.mbg.model.CityInfo;
import yuyang.garbage.search.classification.mbg.model.GarbageInfo;
import yuyang.garbage.search.classification.mbg.model.HotTop;
import yuyang.garbage.search.classification.service.CityService;
import yuyang.garbage.search.classification.service.GarbageService;
import yuyang.garbage.search.classification.service.HotTopService;
import yuyang.garbage.search.classification.util.ClassificationUtils;
import yuyang.garbage.search.classification.util.CommonResult;
import yuyang.garbage.search.classification.vo.GarbageAppVo;

import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: 主要api
 * @Date: 2019/11/16 下午3:40
 **/
@Controller
public class ApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    private GarbageService garbageService;
    @Autowired
    private CityService cityService;
    @Autowired
    private HotTopService hotTopService;


    @ApiOperation("模糊搜索垃圾名称 \n 参数示例 {\"city\": \"北京市\",\"garbageName\":\"碎玻璃\"}")
    @RequestMapping(value = "/api/search", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<GarbageInfo>> getBrandList(@RequestBody HotTop hotTop) {
        LOGGER.info("the garbage search request param is " + JSON.toJSONString(hotTop));
        int hotNum = hotTopService.addHot(hotTop.getCity(), hotTop.getGarbageName());
        LOGGER.info("the result of hotTop is " + hotNum);
        return CommonResult.success(garbageService.search(hotTop.getGarbageName()));
    }

    @ApiOperation("定位接口: https://restapi.amap.com/v3/ip?key=6fc9499b594a7a3929877dfb4efcd18f")
    @RequestMapping(value = "location", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<JSONObject> location() {
        String url = "https://restapi.amap.com/v3/ip?key=6fc9499b594a7a3929877dfb4efcd18f";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = ClassificationUtils.readJsonFromUrl(url);
        } catch (Exception e) {
            LOGGER.error("location exception ",e);
            return CommonResult.failed("接口异常！");
        }
        return CommonResult.success(jsonObject);
    }

    @ApiOperation("搜索城市")
    @RequestMapping(value = "/api/city/{city}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<CityInfo>> searchCity(@PathVariable("city") String city) {
        LOGGER.info("the city param is " + city);
        return CommonResult.success(cityService.search(city));
    }

    @ApiOperation("获取该地区垃圾搜索top9")
    @RequestMapping(value = "/api/topNine/{city}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<String>> topNine(@PathVariable("city") String city) {
        LOGGER.info("the top9 request is " + city);
        return CommonResult.success(hotTopService.getHotByCity(city));
    }

}
