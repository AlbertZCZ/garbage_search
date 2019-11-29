package yuyang.garbage.search.classification.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import yuyang.garbage.search.classification.mbg.model.GarbageInfo;

import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: app端搜索垃圾返回内容
 * @Date: 2019/11/29 下午9:55
 **/
@Data
public class GarbageAppVo {
    @ApiModelProperty(value = "垃圾列表")
    private List<GarbageInfo> garbageInfoList;
    @ApiModelProperty(value = "搜索关键字")
    private String searchKey;
}
