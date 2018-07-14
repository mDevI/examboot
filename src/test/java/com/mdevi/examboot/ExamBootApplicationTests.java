package com.mdevi.examboot;

import com.mdevi.examboot.model.Question;
import com.mdevi.examboot.service.TestQuestionsLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExamBootApplicationTests {

    @Autowired
    TestQuestionsLoader loader;

	@Test
	public void contextLoads() {
	}


    @Test
    public void shouldLoadFiveQuestionsOnly() {

        List<Question> questions = loader.loadTestQuestions();
        assertEquals(5, questions.size());

    }
}
