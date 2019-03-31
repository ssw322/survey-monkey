package com.ssw322.project.surveylemur.form.question;

import androidx.room.Entity;

@Entity
public class GradedShortAnswerQuestion extends ShortAnswerQuestion implements Gradable {

    private int maxPoints;

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public GradedShortAnswerQuestion(String prompt, int maxPoints) {
        super(prompt);
        this.maxPoints = maxPoints;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public int getMaxPoints() {
        return maxPoints;
    }
}
