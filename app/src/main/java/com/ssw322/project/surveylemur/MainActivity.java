package com.ssw322.project.surveylemur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ssw322.project.surveylemur.form.FormAdapter;
import com.ssw322.project.surveylemur.form.question.EssayQuestion;
import com.ssw322.project.surveylemur.form.question.GradedMultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.GradedShortAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.Question;
import com.ssw322.project.surveylemur.form.question.ShortAnswerQuestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make one of each question just for testing
        MultipleAnswerQuestion maq = new MultipleAnswerQuestion("How do you say hello?")
                .addChoice("Hello")
                .addChoice("World");

        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion("Now say it backwards!")
                .addChoice("World")
                .addChoice("Hello");

        EssayQuestion eq = new EssayQuestion("What is the air-speed velocity of an unladen swallow?");

        ShortAnswerQuestion saq = new ShortAnswerQuestion("So whatcha want?");

        GradedMultipleAnswerQuestion gmaq = new GradedMultipleAnswerQuestion("I'm graded!", new HashSet<Integer>(){{add(0);}}, 10);
        gmaq.addChoice("Hello!")
                .addChoice("Goodbye!");

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(maq);
        questions.add(mcq);
        questions.add(eq);
        questions.add(saq);
        questions.add(gmaq);
        FormAdapter adapter = new FormAdapter(this, questions);
        ListView questionList = (ListView)findViewById(R.id.list_view);
        questionList.setAdapter(adapter);
    }
}
