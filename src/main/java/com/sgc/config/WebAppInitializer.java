package com.sgc.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * Spring IoC环境配置
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //配置Spring IoC资源
        return new Class<?>[]{RootConfig.class};
    }

    /**
     * DispatcherServlet环境配置
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    /**
     * DispatcherServlet拦截请求配置
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.do"};
    }
}
