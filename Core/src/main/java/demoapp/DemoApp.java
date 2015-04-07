package demoapp;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableSwagger
public class DemoApp  {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApp.class, args);
    }

    @Bean
    public SwaggerSpringMvcPlugin swagger(SpringSwaggerConfig config) {
        SwaggerSpringMvcPlugin swagger = new SwaggerSpringMvcPlugin(config);
        swagger.apiInfo(new ApiInfo(
                "Elasticsearch Demo App",
                "Sample app to demonstrate the Java API of Elasticsearch",
                "https://github.com/feaster83/elasticsearch-demo-app",
                 null,
                "Apache License 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"));
        return swagger;
    }
}
