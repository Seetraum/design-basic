package com.test.context;

import com.test.context.service.CalculatingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;

@Configuration
@ComponentScan(basePackageClasses = CalculatingService.class)
public class CalculatingServiceBootstrap {
    static {
        //通过Java系统属性设置Spring Profile
        //以下语句等效于ConfigurableEnvironment.setActiveProfiles("Java8");
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"Java8");
        //以下语句等效于ConfigurableEnvironment.setDefaultProfiles("Java7")
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME,"Java7");
    }

    public static void main(String[] args) {
        //构建Annotation配置驱动Spring上下文
        AnnotationConfigApplicationContext context = new
                                        AnnotationConfigApplicationContext();
        //注册当前Bean到Spring上下文
        context.register(CalculatingServiceBootstrap.class);
        //启动上下文
        context.refresh();
        //获取Bean
        CalculatingService calculatingService = context.getBean(CalculatingService.class);
        //输出累加结果
        calculatingService.sum(1,2,3,4,5);
        //关闭上下文
        context.close();
    }
}
