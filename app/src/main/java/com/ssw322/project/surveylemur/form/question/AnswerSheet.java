package com.ssw322.project.surveylemur.form.question;

import java.util.ArrayList;

public class AnswerSheet {

    private ArrayList<Gradable> questions;

    public AnswerSheet() {
        this.questions = new ArrayList<>();
    }

    public AnswerSheet addAnswer(Gradable g) {
        questions.add(g);
        return this;
    }

    public double grade() {
        double total = 0, maxTotal = 0;
        for(Gradable q : questions) {
            total += q.getPoints();
            maxTotal += q.getMaxPoints();
        }
        return total / maxTotal;
    }
}
