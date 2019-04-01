package com.ssw322.project.surveylemur.form.question;

import org.junit.Test;

import static org.junit.Assert.*;

public class GradedMultipleChoiceQuestionTest {
    String prompt = "This is a test";
    int correctAnswerId = 2;
    int maxPoints = 5;
    GradedMultipleChoiceQuestion testQuestion = new GradedMultipleChoiceQuestion(prompt, correctAnswerId, maxPoints);



    @Test
    public void getPoints() {

        for (int i = 0; i < 4; i++) {
            testQuestion.setChoiceNumber(i);
            if (i == 2) {
                assertEquals(5, testQuestion.getPoints());
            } else {
                assertEquals(0, testQuestion.getPoints());
            }
        }
    }

    @Test
    public void getMaxPoints() {
        assertEquals(5, testQuestion.getMaxPoints());
    }
}