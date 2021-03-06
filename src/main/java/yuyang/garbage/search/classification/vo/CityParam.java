package yuyang.garbage.search.classification.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: zhangchaozhen
 * @Description: 城市操作参数bean
 * @Date: 2019/11/24 下午9:44
 **/
@Data
public class CityParam {
    @ApiModelProperty(value = "城市id")
    private Integer cityId;

    @ApiModelProperty(value = "城市id集合(批量删除)")
    private List<Integer> cityIdList;

    @ApiModelProperty(value = "城市名称")
    private String city;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "页数")
    private Integer pageNum;
}
