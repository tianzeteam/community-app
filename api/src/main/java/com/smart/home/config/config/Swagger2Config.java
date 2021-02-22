package com.smart.home.config.config;

import com.smart.home.common.contants.SecurityConsts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	private static final String BASE_VERSION = "1.0.0";
	private static final int NAME_INDEX = 0;
	private static final int PACKAGE_INDEX = 1;
	private static final int VERSION_INDEX = 2;

	private static final String[] ADMIN_API = {"后台接口","com.smart.home.controller.pc",BASE_VERSION};

	private static final String[] APP_API = {"APP接口","com.smart.home.controller.app",BASE_VERSION};

	private static final String[] COMMON_API = {"通用接口","com.smart.home.controller.common",BASE_VERSION};

	@Bean
	public Docket adminApi(){
		return restApi(ADMIN_API);
	}

	@Bean
	public Docket appApi(){
		return restApi(APP_API);
	}

	@Bean
	public Docket commonApi(){
		return restApi(COMMON_API);
	}

	private Docket restApi(String[] infos) {
		// 定义全局header参数
		ParameterBuilder useridPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<>();
		useridPar.name(SecurityConsts.SECURITY_HEAD_NAME).defaultValue("").description("访问令牌").modelRef(new ModelRef("string"))
				.parameterType("header").required(false).build();
		pars.add(useridPar.build());

		return new Docket(DocumentationType.SWAGGER_2)
				.groupName(infos[NAME_INDEX])
				.apiInfo(apiInfo(infos))
				.select()
				.apis(RequestHandlerSelectors.basePackage(infos[PACKAGE_INDEX]))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars);
	}

	private ApiInfo apiInfo(String[] infos) {
		ApiInfoBuilder aipInfo = new ApiInfoBuilder();
		aipInfo.title(infos[NAME_INDEX]);
		aipInfo.version(infos[VERSION_INDEX]);
		return aipInfo.build();
	}
	
}
