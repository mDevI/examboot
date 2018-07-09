package com.mdevi.examboot;


import com.mdevi.examboot.config.AppConfig;
import com.mdevi.examboot.service.TestProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExamSystem {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        TestProcessor testProcessor = ctx.getBean( TestProcessor.class);
        testProcessor.doTest();
        ctx.close();
    }
}
