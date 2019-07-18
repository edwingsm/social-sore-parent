package ie.newwhip.social.score.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("social-score")
        .select()
        .apis(RequestHandlerSelectors.basePackage("ie.newwhip.social"))
        .paths(PathSelectors.ant("/**"))
        .build()
        .apiInfo(new ApiInfoBuilder().version("1").title("Social Score API Version 1")
            .description("Documentation SocialScore  API ").build());
  }
}
