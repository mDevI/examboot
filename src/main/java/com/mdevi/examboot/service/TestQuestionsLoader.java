package com.mdevi.examboot.service;


import com.mdevi.examboot.model.Question;

import java.util.List;

public interface TestQuestionsLoader {
    List<Question> loadTestQuestions();
}
