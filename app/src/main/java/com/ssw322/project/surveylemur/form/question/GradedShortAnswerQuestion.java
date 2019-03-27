package com.ssw322.project.surveylemur.form.question;

public class GradedShortAnswerQuestion extends ShortAnswerQuestion implements Gradable {

    private int maxPoints;

    public GradedShortAnswerQuestion(String s, int maxPoints) {
        super(s);
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
