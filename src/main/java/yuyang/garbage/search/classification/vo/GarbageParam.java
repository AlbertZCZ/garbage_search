package yuyang.garbage.search.classification.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: 删除垃圾传参
 * @Date: 2019/11/24 下午9:06
 **/
@Data
public class GarbageParam {
    @ApiModelProperty(value = "垃圾id(单独删除)")
    private Integer garbageId;
    @ApiModelProperty(value = "垃圾id集合(批量删除)")
    private List<Integer> garbageIdList;
    @ApiModelProperty(value = "城市名称(不传为全选)")
    private String city;
}
