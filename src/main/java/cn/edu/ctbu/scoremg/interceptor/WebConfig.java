package cn.edu.ctbu.scoremg.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")    // 拦截所以请求
                .excludePathPatterns("/login","/api/student/validateUser",
                        "/css/**","/admin/**","/component/**"
                ,"/pagejs/**","/config/**","/view/**"); // 放行登录页面和静态资源
    }
}
