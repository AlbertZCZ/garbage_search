package yuyang.garbage.search.classification.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import yuyang.garbage.search.classification.mbg.model.CityInfo;
import yuyang.garbage.search.classification.mbg.model.GarbageInfo;
import yuyang.garbage.search.classification.mbg.model.HotTop;
import yuyang.garbage.search.classification.service.CityGarbageRelationshipService;
import yuyang.garbage.search.classification.service.CityService;
import yuyang.garbage.search.classification.service.GarbageService;
import yuyang.garbage.search.classification.service.HotTopService;
import yuyang.garbage.search.classification.util.CommonResult;
import yuyang.garbage.search.classification.util.TestFileUtil;
import yuyang.garbage.search.classification.vo.CityParam;
import yuyang.garbage.search.classification.vo.GarbageParam;
import yuyang.garbage.search.classification.vo.GarbageVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * @Author: zhangchaozhen
 * @Description: 管理后台相关接口
 * @Date: 2019/11/23 下午9:11
 **/
@Controller
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private CityGarbageRelationshipService cityGarbageRelationshipService;
    @Autowired
    private GarbageService garbageService;
    @Autowired
    private CityService cityService;
    @Autowired
    private HotTopService hotTopService;

    @ApiOperation("增加垃圾分类 参数示例，不传city为全选 {\"city\": \"北京市\",\"garbageName\":\"碎玻璃\",\"type\":\"RECYCLABLE\"}")
    @RequestMapping(value = "/admin/addGarbage", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> addGarbage(@RequestBody GarbageVo garbageVo) {
        log.info("admin add garbage parms is " + JSON.toJSONString(garbageVo));
        if (garbageVo == null) {
            return CommonResult.failed("参数获取异常");
        }
        if (StringUtils.isEmpty(garbageVo.getGarbageName())) {
            return CommonResult.failed("垃圾名称不能为空");
        }
        if (StringUtils.isEmpty(garbageVo.getType())) {
            return CommonResult.failed("垃圾类别不能为空");
        }
        GarbageInfo garbageInfo = new GarbageInfo();
        garbageInfo.setIsValid(1);
        garbageInfo.setName(garbageVo.getGarbageName());
        garbageInfo.setType(garbageVo.getType());
        if (garbageService.add(garbageInfo) > 0) {

            CityInfo city;
            if (!StringUtils.isEmpty(garbageVo.getCity())) {
                //选择城市，查询城市id
                city = cityService.searchAccurate(garbageVo.getCity());
                if (city == null) {
                    return CommonResult.failed("未找到该城市" + garbageVo.getCity());
                }
                return CommonResult.success(cityGarbageRelationshipService.addRelationship(city.getId(),garbageInfo.getId()));
            }else {
                //没选城市添加到全部城市
                List<Integer> allId = cityService.getAllId();
                return CommonResult.success(cityGarbageRelationshipService.addAllCity(garbageInfo.getId(),allId));
            }

        }
        return CommonResult.failed("垃圾添加失败");
    }

    @ApiOperation("搜索垃圾 不传city为全选")
    @RequestMapping(value = "/admin/garbageList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<PageInfo<GarbageVo>> garbageList(@RequestBody GarbageVo garbageVo) {
        log.info("admin list garbage parms is " + JSON.toJSONString(garbageVo));
        if (garbageVo == null) {
            return CommonResult.failed("参数获取异常");
        }

        //查询城市id
        CityInfo city;
        List<Integer> garbageIdList;
        if (!StringUtils.isEmpty(garbageVo.getCity())) {
            city = cityService.searchAccurate(garbageVo.getCity());
            if (city == null) {
                return CommonResult.failed("未找到该城市" + garbageVo.getCity());
            }
            garbageIdList = cityGarbageRelationshipService.listRelationShip(city.getId());
        }else {
            //城市为全选
            garbageIdList = cityGarbageRelationshipService.listRelationShip(null);
        }

        //查询垃圾
        int pageNum = 1;
        int pageSize = 15;
        if (garbageVo.getPageSize() != null) {
            pageSize = garbageVo.getPageSize();
        }
        if (garbageVo.getPageNum() != null) {
            pageNum = garbageVo.getPageNum();
        }
        PageHelper.startPage(pageNum,pageSize);
        List<GarbageInfo> garbageInfos = garbageService.listGarbage(garbageVo,garbageIdList);
        PageInfo<GarbageInfo> garbageInfoPageInfo = new PageInfo<>(garbageInfos);

        //根据城市查询垃圾的搜索次数
        List<HotTop> hotTops = hotTopService.listHotTop(garbageVo.getCity());
        List<GarbageVo> garbageVoList = new ArrayList<>();
        garbageInfos.forEach(garbageInfo -> {
            GarbageVo vo = new GarbageVo();
            vo.setCity(garbageVo.getCity());
            vo.setGarbageName(garbageInfo.getName());
            vo.setType(garbageInfo.getType());
            vo.setNote(garbageInfo.getNote());
            vo.setGarbageId(garbageInfo.getId());
            hotTops.forEach(hotTop -> {
                if (garbageInfo.getName().equals(hotTop.getGarbageName())) {
                    vo.setSearchNum(hotTop.getNum());
                }
            });
            if (vo.getSearchNum() == null) {
                vo.setSearchNum(0);
            }
            garbageVoList.add(vo);
        });
        //搜索次数降序排序
        garbageVoList.sort(Comparator.comparing(GarbageVo::getSearchNum).reversed());
        PageInfo<GarbageVo> garbageVoPageInfo = new PageInfo<>(garbageVoList);
        garbageVoPageInfo.setPageSize(garbageInfoPageInfo.getPageSize());
        garbageVoPageInfo.setPageNum(garbageInfoPageInfo.getPageNum());
        garbageVoPageInfo.setEndRow(garbageInfoPageInfo.getEndRow());
        garbageVoPageInfo.setIsFirstPage(garbageInfoPageInfo.isIsFirstPage());
        garbageVoPageInfo.setIsLastPage(garbageInfoPageInfo.isIsLastPage());
        garbageVoPageInfo.setHasNextPage(garbageInfoPageInfo.isHasNextPage());
        garbageVoPageInfo.setHasPreviousPage(garbageInfoPageInfo.isHasPreviousPage());
        garbageVoPageInfo.setNavigateFirstPage(garbageInfoPageInfo.getNavigateFirstPage());
        garbageVoPageInfo.setNavigateLastPage(garbageInfoPageInfo.getNavigateLastPage());
        garbageVoPageInfo.setNavigatepageNums(garbageInfoPageInfo.getNavigatepageNums());
        garbageVoPageInfo.setNavigatePages(garbageInfoPageInfo.getNavigatePages());
        garbageVoPageInfo.setNextPage(garbageInfoPageInfo.getNextPage());
        garbageVoPageInfo.setPages(garbageInfoPageInfo.getPages());
        garbageVoPageInfo.setPrePage(garbageInfoPageInfo.getPrePage());
        garbageVoPageInfo.setSize(garbageInfoPageInfo.getSize());
        garbageVoPageInfo.setStartRow(garbageInfoPageInfo.getStartRow());
        garbageVoPageInfo.setTotal(garbageInfoPageInfo.getTotal());
        return CommonResult.success(garbageVoPageInfo);
    }

    @ApiOperation("更新垃圾 参数示例  {\"garbageId\": \"267\",\"garbageName\":\"碎玻璃片片\"}")
    @RequestMapping(value = "/admin/updateGarbage", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> update(@RequestBody GarbageVo garbageVo) {
        log.info("admin update garbage parms is " + JSON.toJSONString(garbageVo));
        return CommonResult.success(garbageService.update(garbageVo));
    }

    @ApiOperation("删除垃圾，批量删除传garbageIdList 单个删除传garbageId，城市名称不传为所有城市")
    @RequestMapping(value = "/admin/deleteGarbage", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> delete(@RequestBody GarbageParam garbageParam) {
        log.info("admin delete garbage parms is " + JSON.toJSONString(garbageParam));
        Integer cityId = null;
        if (!StringUtils.isEmpty(garbageParam.getCity())) {
            //传城市查询城市id
            CityInfo cityInfo = cityService.searchAccurate(garbageParam.getCity());
            if (cityInfo == null) {
                return CommonResult.failed("未找到该城市" + garbageParam.getCity());
            }
            cityId = cityInfo.getId();
        }
        return CommonResult.success(cityGarbageRelationshipService.deleteRelationship(cityId,garbageParam));
    }

    @ApiOperation("增加城市")
    @RequestMapping(value = "/admin/addCity", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> addCity(@RequestBody CityInfo cityInfo) {
        log.info("admin add city parms is " + JSON.toJSONString(cityInfo));
        if (cityInfo == null) {
            return CommonResult.failed("获取参数失败");
        }
        if (StringUtils.isEmpty(cityInfo.getCity())) {
            return CommonResult.failed("城市名称不能为空");
        }
        return CommonResult.success(cityService.add(cityInfo));
    }

    @ApiOperation("搜索城市")
    @RequestMapping(value = "/admin/cityList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<PageInfo<CityInfo>> cityList(@RequestBody CityParam cityParam) {
        log.info("admin list city parms is " + JSON.toJSONString(cityParam));
        if (cityParam == null) {
            return CommonResult.failed("获取参数错误");
        }
        int pageSize = 15;
        int pageNum = 1;
        if (cityParam.getPageSize() != null) {
            pageSize = cityParam.getPageSize();
        }
        if (cityParam.getPageNum() != null) {
            pageNum = cityParam.getPageNum();
        }
        PageHelper.startPage(pageNum,pageSize);
        List<CityInfo> search = cityService.search(cityParam.getCity());
        return CommonResult.success(new PageInfo<>(search));
    }


    @ApiOperation("删除城市，批量删除传cityIdList示例{ \"cityIdList\": [ 2859,2860] }； 单个删除传cityId,示例 { \"cityId\": 2859 }")
    @RequestMapping(value = "/admin/deleteCity", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> deleteCity(@RequestBody CityParam cityParam) {
        log.info("admin delete city parms is " + JSON.toJSONString(cityParam));
        //删除城市
        int num;
        if ((num = cityService.delete(cityParam)) > 0) {
            //删除城市的关联
            cityGarbageRelationshipService.deleteBatch(cityParam);
        }
        return CommonResult.success(num);
    }

    @ApiOperation("更新城市")
    @RequestMapping(value = "/admin/updateCity", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> update(@RequestBody CityInfo cityInfo) {
        log.info("admin update city parms is " + JSON.toJSONString(cityInfo));
        if (cityInfo.getId() == null) {
            return CommonResult.failed("城市id不能为空");
        }
        return CommonResult.success(cityService.update(cityInfo));
    }

    @ApiOperation("开放城市，批量开放传cityIdList 单个开放传cityId")
    @RequestMapping(value = "/admin/openCity", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> openCity(@RequestBody CityParam cityParam) {
        log.info("admin open city parms is " + JSON.toJSONString(cityParam));
        return CommonResult.success(cityService.open(cityParam));
    }

    @ApiOperation("城市导出 /admin/cityDownload?cityName=")
    @RequestMapping(value = "/admin/cityDownload", method = RequestMethod.GET)
    @ResponseBody
    public void cityDownload(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String cityName = request.getParameter("cityName");
        log.info("admin download city parms is " + cityName);
        List<CityInfo> search = cityService.search(cityName);
        search.forEach(cityInfo -> cityInfo.setDistrict(cityInfo.getOpend() == 1 ? "是" : "否"));
        // 写法1
        /*String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, CityInfo.class).sheet("城市信息").doWrite(search);*/
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("城市信息", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), CityInfo.class).sheet("城市信息").doWrite(search);
    }
}
