package com.ssw322.project.surveylemur.form.question;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

/**
 * This must be an abstract class because we are implementing transaction methods here.  We could
 * do this as an interface using the transaction as a static method if we were to set the API higher,
 * but since current min is 15, we are stuck with this.
 */
@Dao
public abstract class QuestionDao {

    private static class IdComparator implements Comparator<Question> {
        @Override
        public int compare(Question a, Question b) {
            return Integer.compare(a.id, b.id);
        }
    }

    @Query("SELECT * FROM MultipleAnswerQuestion WHERE formId = :formId")
    abstract List<MultipleAnswerQuestion> getMultipleAnswerQuestionsForForm(String formId);

    @Query("SELECT * FROM MultipleChoiceQuestion WHERE formId = :formId")
    abstract List<MultipleChoiceQuestion> getMultipleChoiceQuestionsForForm(String formId);

    @Query("SELECT * FROM ShortAnswerQuestion WHERE formId = :formId")
    abstract List<ShortAnswerQuestion> getShortAnswerQuestionsForForm(String formId);

    @Query("SELECT * FROM EssayQuestion WHERE formId = :formId")
    abstract List<EssayQuestion> getEssayQuestionsForForm(String formId);

    //pull all of the questions for a survey
    @Transaction
    List<Question> getQuestionsForSurvey(String code) {
        List<Question> questions = new ArrayList<>();
        questions.addAll(getMultipleAnswerQuestionsForForm(code));
        questions.addAll(getMultipleChoiceQuestionsForForm(code));
        questions.addAll(getShortAnswerQuestionsForForm(code));
        questions.addAll(getEssayQuestionsForForm(code));
        //we want questions to sort based on id from bottom to top for now, no shuffling
        questions.sort(new IdComparator());
        return questions;
    }

    @Query("")
    abstract List<GradedMultipleAnswerQuestion> getGradedMultipleAnswerQuestionsForForm(String formId);

    @Query("")
    abstract List<GradedMultipleChoiceQuestion> getGradedMultipleChoiceQuestionForForm(String formId);

    @Query("")
    abstract List<GradedShortAnswerQuestion> getGradedShortAnswerQuestionForForm(String formId);

    @Query("")
    abstract List<GradedEssayQuestion> getGradedEssayQuestionForForm(String formId);

    @Transaction
    List<Question> getQuestionsForTest(String code) {
        List<Question> questions = new ArrayList<>();
        questions.addAll(getGradedMultipleAnswerQuestionsForForm(code));
        questions.addAll(getGradedMultipleChoiceQuestionForForm(code));
        questions.addAll(getGradedShortAnswerQuestionForForm(code));
        questions.addAll(getGradedEssayQuestionForForm(code));
        questions.sort(new IdComparator());
        return questions;
    }
}
