package com.ssw322.project.surveylemur.form.question;

import org.junit.Test;

import static org.junit.Assert.*;

public class GradedShortAnswerQuestionTest {
    String prompt = "This is a test";
    int maxPoints = 5;
    GradedShortAnswerQuestion testQuestion = new GradedShortAnswerQuestion(prompt, maxPoints);

    @Test
    public void getPoints() {
        assertEquals(0, testQuestion.getPoints());
    }

    @Test
    public void getMaxPoints() {
        assertEquals(5, testQuestion.getMaxPoints());
    }
}