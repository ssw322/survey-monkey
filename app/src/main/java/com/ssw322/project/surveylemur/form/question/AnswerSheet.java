package com.ssw322.project.surveylemur.form.question;

import java.util.ArrayList;

public class AnswerSheet {

    private static class Answer {
        public int questionId;
        public String answer;

        public Answer(int questionId, String answer) {
            this.questionId = questionId;
            this.answer = answer;
        }
    }

    private ArrayList<Answer> answers;

    public AnswerSheet() {
        this.answers = new ArrayList<>();
    }

    public AnswerSheet addAnswer(Question q, String answer) {
        answers.add(new Answer(q.id, answer));
        return this;
    }

    public ArrayList<Answer> getAnswers() {
        return this.answers;
    }

//    public double grade() {
//        double total = 0, maxTotal = 0;
//        for(Gradable q : questions) {
//            total += q.getPoints();
//            maxTotal += q.getMaxPoints();
//        }
//        return total / maxTotal;
//    }
}
