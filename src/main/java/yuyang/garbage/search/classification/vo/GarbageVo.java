package yuyang.garbage.search.classification.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhangchaozhen
 * @Description: 增加垃圾bean
 * @Date: 2019/11/23 下午9:55
 **/
@Data
public class GarbageVo {

    @ApiModelProperty(value = "垃圾id(删除更新用)")
    private Integer garbageId;

    @ApiModelProperty(value = "城市名称（精确）")
    private String city;

    @ApiModelProperty(value = "垃圾名称")
    private String garbageName;

    @ApiModelProperty(value = "垃圾类别（枚举值，可回收：RECYCLABLE,有害：HARMFUL,湿：WET,干：DRY）")
    private String type;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "搜索次数（查询用）")
    private Integer searchNum;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "页数")
    private Integer pageNum;
}
