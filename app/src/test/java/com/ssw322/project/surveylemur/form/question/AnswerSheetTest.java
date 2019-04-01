package com.ssw322.project.surveylemur.form.question;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AnswerSheetTest {
    String prompt = "This is a test";
    int correctAnswerId = 2;
    int maxPoints = 5;
    GradedMultipleChoiceQuestion questionOne = new GradedMultipleChoiceQuestion(prompt, correctAnswerId, maxPoints);
    GradedMultipleChoiceQuestion questionTwo = new GradedMultipleChoiceQuestion(prompt, correctAnswerId, maxPoints);
    AnswerSheet testAnswerSheet = new AnswerSheet();




    @Test
    public void grade() {
        //One of Two Correct, Max Possible Score: 10, Score Earned: 5, Expected Score: .5 or 50%
        questionOne.setChoiceNumber(2);
        questionTwo.setChoiceNumber(0);
        testAnswerSheet.addAnswer(questionOne);
        testAnswerSheet.addAnswer(questionTwo);
        assertEquals(.5,testAnswerSheet.grade(),0);
        //Two of Two Correct, Max Possible Score: 10, Score Earned: 10, Expected Score: 1.0 or 100%
        questionOne.setChoiceNumber(2);
        questionTwo.setChoiceNumber(2);
        assertEquals(1.0,testAnswerSheet.grade(),0);
        //Zero of Two Correct, Max Possible Score: 10, Score Earned: 0, Expected Score: 0.0 or 0%
        questionOne.setChoiceNumber(0);
        questionTwo.setChoiceNumber(0);
        assertEquals(0,testAnswerSheet.grade(),0);
    }

    @Test
    public void addAnswer() {
        assertEquals(0, testAnswerSheet.getNumQuestions());
        testAnswerSheet.addAnswer(questionOne);
        assertEquals(1, testAnswerSheet.getNumQuestions());
        testAnswerSheet.addAnswer(questionTwo);
        assertEquals(2, testAnswerSheet.getNumQuestions());
    }
}