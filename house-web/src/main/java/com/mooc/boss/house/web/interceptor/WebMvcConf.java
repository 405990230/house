package com.mooc.boss.house.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthActionInterceptor authActionInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
        registry.addInterceptor(authActionInterceptor).addPathPatterns("/house/toAdd")
                .addPathPatterns("/accounts/profile").addPathPatterns("/accounts/profileSubmit")
                .addPathPatterns("/house/bookmarked").addPathPatterns("/house/del")
                .addPathPatterns("/house/ownlist").addPathPatterns("/house/add")
                .addPathPatterns("/house/toAdd").addPathPatterns("/agency/agentMsg")
                .addPathPatterns("/comment/leaveComment").addPathPatterns("/comment/leaveBlogComment");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
    }

//	@Override
//	public void configureViewResolvers(ViewResolverRegistry registry) {
//		registry.jsp("/WEB-INF/view/", ".jsp");
//	}


    /**
     * 跨域支持
     * allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
