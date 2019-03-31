package com.ssw322.project.surveylemur.form.question;

import androidx.room.Entity;

@Entity
public class GradedMultipleChoiceQuestion extends MultipleChoiceQuestion implements Gradable {

    private int correctAnswerId;
    private int maxPoints;

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public GradedMultipleChoiceQuestion(String prompt, int correctAnswerId, int maxPoints) {
        super(prompt);
        this.correctAnswerId = correctAnswerId;
        this.maxPoints = maxPoints;
    }

    /**
     * For this, we may want to have some kind of strategy to allow for specialized ways of
     * grading a multiple choice question
     * @return an integer representing whether or not they got the question right
     */
    @Override
    public int getPoints() {
        if(correctAnswerId == choiceNumber)
            return maxPoints;
        return 0;
    }

    @Override
    public int getMaxPoints() {
        return maxPoints;
    }
}
