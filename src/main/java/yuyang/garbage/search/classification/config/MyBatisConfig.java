package yuyang.garbage.search.classification.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhangchaozhen
 * @Description: mybatis配置
 * @Date: 2019/11/16 下午2:21
 **/
@Configuration
@MapperScan("yuyang.garbage.search.classification.mbg.mapper")
public class MyBatisConfig {
}
