package com.ssw322.project.surveylemur.form.question;

import org.junit.Test;

import static org.junit.Assert.*;

public class GradedEssayQuestionTest {
    String prompt = "This is a test";
    int points = 5;
    GradedEssayQuestion testQuestion = new GradedEssayQuestion(prompt, points);

    @Test
    public void getPoints() throws Exception {
        assertEquals(0, testQuestion.getPoints());
    }

    @Test
    public void getMaxPoints() {
        assertEquals(5, testQuestion.getMaxPoints());
    }
}