package com.ead.authuser.config;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class ResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        var specificationArgumentResolver = new SpecificationArgumentResolver();
        resolvers.add(specificationArgumentResolver);

        var pageableResolver = new PageableHandlerMethodArgumentResolver();
        pageableResolver.setFallbackPageable(PageRequest.of(0, 10));
        resolvers.add(pageableResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//       registry.addMapping("/users/**").allowedOrigins("http://example.com").maxAge(3600L);
//       registry.addMapping("/auth/**").allowedOrigins("http://abc.com").maxAge(3600L);
        registry.addMapping("/**").maxAge(3600L);
    }


}
