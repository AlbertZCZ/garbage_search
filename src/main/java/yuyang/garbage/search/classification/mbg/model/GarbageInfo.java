package yuyang.garbage.search.classification.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GarbageInfo implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "垃圾名称")
    private String name;

    @ApiModelProperty(value = "垃圾类别（枚举值，可回收：RECYCLABLE,有害：HARMFUL,湿：WET,干：DRY）")
    private String type;

    @ApiModelProperty(value = "是否有效（1有效，0无效）")
    private Integer isValid;

    @ApiModelProperty(value = "冗余字段")
    private String note;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", isValid=").append(isValid);
        sb.append(", note=").append(note);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}