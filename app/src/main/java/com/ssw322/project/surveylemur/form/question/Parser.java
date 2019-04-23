package com.ssw322.project.surveylemur.form.question;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.ssw322.project.surveylemur.form.Form;
import com.ssw322.project.surveylemur.form.Survey;
import com.ssw322.project.surveylemur.form.Test;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Parser {

    //Firebase is really bad at this, I don't understand how it's this hard
    public static Form parseFirebaseData(@NonNull DataSnapshot dataSnapshot) {
        String formTitle = (String)dataSnapshot.child("title").getValue();
        String creatorId = (String)dataSnapshot.child("creatorId").getValue();
        ArrayList<Question> questions = new ArrayList<>();
        if(dataSnapshot.child("formType").getValue().equals("Test")) {
            for(DataSnapshot ds : dataSnapshot.child("questions").getChildren()) {
                String prompt = getPrompt(ds);
                int questionType = getQuestionType(ds);
                int questionId = getId(ds);
                switch(questionType) {
                    case Constants.VIEW_TYPE_MULTIPLE_CHOICE:
                        GradedMultipleChoiceQuestion gmcq = new GradedMultipleChoiceQuestion(prompt,
                                getAsInteger(ds, "correctAnswerId"),
                                getAsInteger(ds, "maxPoints"));
                        gmcq.setChoices(gatherUpChoices(ds));
                        questions.add(gmcq.setId(questionId));
                        break;
                    case Constants.VIEW_TYPE_MULTIPLE_ANSWER:
                        ArrayList<Integer> correctAnswerIds = new ArrayList<>();
                        for(DataSnapshot answers : ds.child("correctAnswerIds").getChildren()) {
                            correctAnswerIds.add(((Long)answers.getValue()).intValue());
                        }
                        GradedMultipleAnswerQuestion gmaq = new GradedMultipleAnswerQuestion(prompt,
                                correctAnswerIds,
                                getAsInteger(ds, "maxPoints"));
                        gmaq.setChoices(gatherUpChoices(ds));
                        questions.add(gmaq.setId(questionId));
                        break;
                    //TODO: both of these are exactly the same aside from the type
                    case Constants.VIEW_TYPE_SHORT_ANSWER:
                        GradedShortAnswerQuestion gsaq = new GradedShortAnswerQuestion(prompt,
                                getAsInteger(ds, "maxPoints"),
                                getAsString(ds, "correctAnswer"));
                        questions.add(gsaq.setId(questionId));
                        break;
                    case Constants.VIEW_TYPE_ESSAY:
                        GradedEssayQuestion geq = new GradedEssayQuestion(prompt,
                                getAsInteger(ds, "maxPoints"),
                                getAsString(ds,"correctAnswer"));
                        questions.add(geq.setId(questionId));
                        break;
                }
            }
            return new Test(formTitle, creatorId, questions);
        }
        //must be a survey
        else {
            for(DataSnapshot ds : dataSnapshot.child("questions").getChildren()) {
                String prompt = getPrompt(ds);
                int questionType = getQuestionType(ds);
                int questionId = getId(ds);
                switch(questionType) {
                    case Constants.VIEW_TYPE_MULTIPLE_CHOICE:
                        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(prompt);
                        mcq.setChoices(gatherUpChoices(ds));
                        questions.add(mcq.setId(questionId));
                        break;
                    case Constants.VIEW_TYPE_MULTIPLE_ANSWER:
                        MultipleAnswerQuestion maq = new MultipleAnswerQuestion(prompt);
                        maq.setChoices(gatherUpChoices(ds));
                        questions.add(maq.setId(questionId));
                        break;
                    case Constants.VIEW_TYPE_SHORT_ANSWER:
                        ShortAnswerQuestion saq = new ShortAnswerQuestion(prompt);
                        questions.add(saq.setId(questionId));
                        break;
                    case Constants.VIEW_TYPE_ESSAY:
                        EssayQuestion eq = new EssayQuestion(prompt);
                        questions.add(eq.setId(questionId));
                        break;
                }
            }
            return new Survey(formTitle, creatorId, questions);
        }
    }

    private static int getId(DataSnapshot ds) {
        return getAsInteger(ds, "id");
    }

    private static String getAsString(DataSnapshot ds, String key) {
        return (String)ds.child(key).getValue();
    }

    private static int getAsInteger(DataSnapshot ds, String key) {
        return ((Long)ds.child(key).getValue()).intValue();
    }

    private static String getPrompt(DataSnapshot ds) {
        return (String)ds.child("prompt").getValue();
    }

    private static int getQuestionType(DataSnapshot ds) {
        return ((Long)ds.child("viewType").getValue()).intValue();
    }

    private static ArrayList<Choice> gatherUpChoices(DataSnapshot ds) {
        ArrayList<Choice> lst = new ArrayList<>();
        for(DataSnapshot choice : ds.child("choices").getChildren()) {
            lst.add(new Choice(((Long)choice.child("id").getValue()).intValue(),
                    (String)choice.child("text").getValue()));
        }
        return lst;
    }
}
