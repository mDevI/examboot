package com.mdevi.examboot.service;


import com.mdevi.examboot.model.Question;
import com.mdevi.examboot.model.Student;
import com.mdevi.examboot.model.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

@Service
public class TestProcessorImpl implements TestProcessor {

    private final Logger logger = LoggerFactory.getLogger(TestProcessorImpl.class);
    private final StudentEnrollment studentEnrollment;
    private Student theStudent;
    private Test test;
    private int result;
    private TestQuestionsLoader testQuestionsLoader;
    private MessageSource messageSource;
    private Locale locale;
    private Optional<String> localeString;

    @Autowired
    public TestProcessorImpl(StudentEnrollment studentEnrollment,
                             TestQuestionsLoader testQuestionsLoader,
                             MessageSource messageSource, Optional<String> localeString) {
        this.studentEnrollment = studentEnrollment;
        this.testQuestionsLoader = testQuestionsLoader;
        this.messageSource = messageSource;
        this.localeString = localeString;
        this.test = new Test();
    }

    @Override
    public void doTest() {
        result = 0;
        if(localeString.isPresent()) {
            locale = Locale.forLanguageTag(localeString.get());
        } else {
            locale = Locale.getDefault();
        }
        theStudent = studentEnrollment.enrollStudent();
        List<Question> questions = testQuestionsLoader.loadTestQuestions();
        test.setQuestionList(questions);

        if (test != null && theStudent != null) {
            printIntro();
            Scanner sc = new Scanner(System.in);
            for (Question question: test.getQuestionList()) {
                System.out.println(messageSource.getMessage("app.test.process.question.number",
                        new String[]{question.getNumber(), question.getText()}, locale));
                String testAnswer = sc.nextLine();
                if (testAnswer.equals(question.getAnswer().trim())) {
                    result++;
                }
            }
            printTestResult();
        } else {
            logger.error("There aren't sufficient conditions to process test.");
        }
    }

    private void printIntro() {
        System.out.println("---------------------------------");
        System.out.println(messageSource.getMessage("app.test.process.intro.begin", new String[]{}, locale));
        System.out.println(messageSource.getMessage("app.test.process.intro.answer", new String[]{}, locale));
    }

    private void printTestResult() {
        System.out.println("---------------------------------");
        System.out.println(messageSource.getMessage("app.test.process.result.test.end", new String[]{}, locale));
        System.out.println(messageSource.getMessage("app.test.process.result.total",
                new String[]{theStudent.getFirstName(), theStudent.getLastName(), Integer.toString(result)}, locale));
    }
}
