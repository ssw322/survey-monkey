package com.ssw322.project.surveylemur.form;

import android.content.Context;
import android.os.AsyncTask;

import com.ssw322.project.surveylemur.form.question.Choice;
import com.ssw322.project.surveylemur.form.question.Converters;
import com.ssw322.project.surveylemur.form.question.EssayQuestion;
import com.ssw322.project.surveylemur.form.question.GradedEssayQuestion;
import com.ssw322.project.surveylemur.form.question.GradedMultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.GradedMultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.GradedShortAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.Question;
import com.ssw322.project.surveylemur.form.question.QuestionDao;
import com.ssw322.project.surveylemur.form.question.ShortAnswerQuestion;
import com.ssw322.project.surveylemur.user.User;
import com.ssw322.project.surveylemur.user.UserDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {
        Form.class,
        Choice.class,
        MultipleAnswerQuestion.class,
        MultipleChoiceQuestion.class,
        ShortAnswerQuestion.class,
        EssayQuestion.class,
        GradedMultipleAnswerQuestion.class,
        GradedMultipleChoiceQuestion.class,
        GradedShortAnswerQuestion.class,
        GradedEssayQuestion.class,
        Question.class,
        User.class
}, version = 1)
@TypeConverters({Converters.class}) //allow sets to be serialized directly into the tables
public abstract class FormDatabase extends RoomDatabase {

    private static FormDatabase DB;

    public abstract UserDao userDao();
    public abstract FormDao formDao();
    public abstract QuestionDao questionDao();

    //make the db a singleton
    public static FormDatabase getDatabase(final Context context) {
        if(DB == null)
            DB = Room.databaseBuilder(context.getApplicationContext(),
                    FormDatabase.class, "form_database")
                    .build();
        return DB;
    }
}
