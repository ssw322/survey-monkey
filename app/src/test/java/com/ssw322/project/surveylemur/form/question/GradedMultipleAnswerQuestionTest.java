package com.ssw322.project.surveylemur.form.question;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class GradedMultipleAnswerQuestionTest {

    Set<Integer> correctIds = new HashSet<Integer>(){{add(0); add(1);}};
    Set<Integer> matchingIds = new HashSet<Integer>() {{add(0); add(1);}};
    Set<Integer> partiallyCorrectOne = new HashSet<Integer>() {{add(0);}};
    Set<Integer> partiallyCorrectTwo = new HashSet<Integer>() {{add(1);}};
    String prompt = "This is a test";
    int maxPoints = 5;
    GradedMultipleAnswerQuestion testQuestion = new GradedMultipleAnswerQuestion(prompt, correctIds, maxPoints);



    @Test
    public void getPoints() {
        assertEquals(0, testQuestion.getPoints());
        testQuestion.setResponseIds(matchingIds);
        assertEquals(5, testQuestion.getPoints());
        testQuestion.setResponseIds(partiallyCorrectOne);
        assertEquals(0, testQuestion.getPoints());
        testQuestion.setResponseIds(partiallyCorrectTwo);
        assertEquals(0, testQuestion.getPoints());
    }

    @Test
    public void getMaxPoints() {
        assertEquals(5, testQuestion.getMaxPoints());
    }
}