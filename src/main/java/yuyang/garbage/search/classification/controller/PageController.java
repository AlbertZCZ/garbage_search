package yuyang.garbage.search.classification.controller;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: zhangchaozhen
 * @Description: 页面控制层
 * @Date: 2019/12/3 下午9:29
 **/
@Controller
public class PageController {
    private static final String INDEX_PAGE = "index";

    @ApiModelProperty(value = "首页显示")
    @RequestMapping("/index")
    public String index() {
        return INDEX_PAGE;
    }
}
