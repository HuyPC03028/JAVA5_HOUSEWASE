package com.poly.main.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ResourceConfig implements WebMvcConfigurer{
    @Bean("messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource ms
        = new ReloadableResourceBundleMessageSource();
        ms.setBasenames("classpath:messages/messages"); 
        ms.setDefaultEncoding("utf-8");
        return ms;
    }
    @Bean
    public LocaleResolver localeResolver() {
    	SessionLocaleResolver resolver = new SessionLocaleResolver();
    	resolver.setDefaultLocale(new Locale("en"));
    	return resolver;
    }
    @Bean
    public LocaleChangeInterceptor changeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(changeInterceptor());
    }
}
