package com.mdevi.examboot;

import com.mdevi.examboot.service.TestProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ExamBootApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ExamBootApplication.class, args);
        TestProcessor testProcessor = context.getBean(TestProcessor.class);
        testProcessor.doTest();

	}
}
