package com.example;

import org.neo4j.cypher.internal.compiler.v2_1.ast.rewriters.useAliasesInSortSkipAndLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
 
@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig {
 
    private SpringSwaggerConfig  springSwaggerConfig;
 
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig ) {
        this.springSwaggerConfig = springSwaggerConfig;
    }
 
    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).
        		apiInfo(new com.mangofactory.swagger.models.dto.ApiInfo
        				("SWAGGER DEMO WITH SPRING BOOT REST API", 
        						"This API is for swagger demo Employee analysis.",
        								 null, null, null, null)
                .useDefaultResponseMessages(false)                        
                .includePatterns("/swagger-demo/.*"));
    }
 
}