package pl.zielinski.kamil.stockmarket.common.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.zielinski.kamil.stockmarket.common.interceptors.authentication.NoAuthorizationInterceptor;
import pl.zielinski.kamil.stockmarket.common.interceptors.ifmatch.IfMatchInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private IfMatchInterceptor ifMatchInterceptor;
    private NoAuthorizationInterceptor noAuthorizationInterceptor;

    @Autowired
    AppConfig(IfMatchInterceptor ifMatchInterceptor, NoAuthorizationInterceptor noAuthorizationInterceptor) {
        this.ifMatchInterceptor = ifMatchInterceptor;
        this.noAuthorizationInterceptor = noAuthorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(noAuthorizationInterceptor)
                .addPathPatterns("/api/users/{id}")
                .addPathPatterns("/api/stocks/**");

        registry.addInterceptor(ifMatchInterceptor)
                .excludePathPatterns("/api/users/register")
                .excludePathPatterns("/api/users/login")
                .excludePathPatterns("/api/users/{id}")
                .excludePathPatterns("/swagger-ui.html")
                .addPathPatterns("/api/users/**")
                .addPathPatterns("/api/userstocks/**")
                .addPathPatterns("/api/stocksubscriptions/**");


    }
}
