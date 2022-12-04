package com.spring.learning;

import com.spring.learning.annotation.based.config.AppConfig;
import com.spring.learning.annotation.based.config.Samsung;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigTest
{
    public static void main(String[] args)
    {
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);

        Samsung s7=applicationContext.getBean(Samsung.class);
        s7.config();
    }
}
