package com.mdevi.examboot.service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mdevi.examboot.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TestQuestionsLoaderImpl implements TestQuestionsLoader {

    private final Logger LOGGER = LoggerFactory.getLogger(TestQuestionsLoaderImpl.class);

    private String fileName;
    private Class<?> type;
    private Optional<String> localeString;
    private Locale locale;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void setLocaleString(String localeString) {
        this.localeString = Optional.ofNullable(localeString);
    }

    @Autowired
    public TestQuestionsLoaderImpl(Optional<String> localeString) {
        this.localeString = localeString;
    }

    @Override
    public List<Question> loadTestQuestions() {
       if(localeString.isPresent()) {
           locale = Locale.forLanguageTag(localeString.get());
       } else {
           locale = Locale.getDefault();
       }

       try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            List list = mapper.readerFor(type)
                    .with(bootstrapSchema.withColumnSeparator(bootstrapSchema.DEFAULT_COLUMN_SEPARATOR))
                    .readValues(TestQuestionsLoaderImpl.class.getClassLoader().getResourceAsStream(fileName)).readAll();
            List<Question> all = list;
            List<Question> testQuestionsByLocale  = all.stream()
                                                        .filter(question -> question.getLocale().equals(locale.toString()))
                                                        .collect(Collectors.toList());
            return testQuestionsByLocale;
        } catch (Exception e) {
            LOGGER.error("Error occurred while loading object list from file " + fileName, e);
            return Collections.emptyList();
        }
    }
}
