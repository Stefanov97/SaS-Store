package sas.web.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sas.web.interceptors.FaviconInterceptor;
import sas.web.interceptors.PageTitleInterceptor;

@Configuration
public class ApplicationWebConfiguration implements WebMvcConfigurer {
    private final FaviconInterceptor faviconInterceptor;
    private final PageTitleInterceptor pageTitleInterceptor;

    public ApplicationWebConfiguration(FaviconInterceptor faviconInterceptor, PageTitleInterceptor pageTitleInterceptor) {
        this.faviconInterceptor = faviconInterceptor;
        this.pageTitleInterceptor = pageTitleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.faviconInterceptor);
        registry.addInterceptor(this.pageTitleInterceptor);
    }
}
