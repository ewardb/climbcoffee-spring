package spring.security2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  <p> MyProperties </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/8/19 9:07
 */
@Data
@ConfigurationProperties(prefix = "claimbspirng-sercurity", ignoreUnknownFields = false)
public class MyProperties {

    /**
     * SWAGGER参数
     */
    private final Swagger swagger = new Swagger();

    /**
     * SWAGGER接口文档参数
     */
    @Data
    public static class Swagger {
        private String title;
        private String description;
        private String version;
        private String termsOfServiceUrl;
        private String contactName;
        private String contactUrl;
        private String contactEmail;
        private String license;
        private String licenseUrl;
    }

}
