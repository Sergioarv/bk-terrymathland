package co.com.sergio.bkterrymathmand;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 4/06/2022 9:41
 **/

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("co.com.sergio.bkterrymathmand.controller"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo getApiInfo() {
        Contact contact = new Contact("Sergio Abelardo Rodríguez Vásquez", "#", "ingsergiorodriguezv@gmail.com");
        return new ApiInfoBuilder().title("API REST para gestionar la Plataforma TerryMathLand").description(
                        "Esta api describe la funcionalidad para gestionar la Plataforma TerryMathLand. ")
                .version("1.0.0").license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact).build();
    }
}