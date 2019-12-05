package yuyang.garbage.search.classification.mbg.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class CityInfo extends BaseRowModel implements Serializable {
    @ExcelIgnore
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ExcelProperty(value = {"行政区域代码"}, index = 0)
    @ApiModelProperty(value = "行政区域代码")
    private Integer cityCode;

    @ExcelProperty(value = {"省份"}, index = 1)
    @ApiModelProperty(value = "省份")
    private String province;

    @ExcelProperty(value = {"城市"}, index = 2)
    @ApiModelProperty(value = "城市")
    private String city;

    @ExcelProperty(value = {"是否已开放"}, index = 3)
    @ApiModelProperty(value = "区县")
    private String district;

    @ExcelIgnore
    @ApiModelProperty(value = "是否已开放（0：否，1：是）")
    private Integer opend;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getOpend() {
        return opend;
    }

    public void setOpend(Integer opend) {
        this.opend = opend;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", district=").append(district);
        sb.append(", opend=").append(opend);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}