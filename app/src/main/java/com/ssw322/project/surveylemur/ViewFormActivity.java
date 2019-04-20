package com.ssw322.project.surveylemur;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.ssw322.project.surveylemur.form.Form;
import com.ssw322.project.surveylemur.form.FormAdapter;
import com.ssw322.project.surveylemur.form.question.Choice;
import com.ssw322.project.surveylemur.form.question.Constants;
import com.ssw322.project.surveylemur.form.question.EssayQuestion;
import com.ssw322.project.surveylemur.form.question.GradedMultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.Question;
import com.ssw322.project.surveylemur.form.question.ShortAnswerQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.ToIntFunction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ViewFormActivity extends AppCompatActivity {
    FormAdapter adapter;
    ArrayList<Question> questions = new ArrayList<>();
    String title;
    String creatorId;
    Form form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        String codeEntered = getIntent().getStringExtra("code");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference(codeEntered);
        /*
        Ok so I'm sure there's probably a more efficient way to do this, but Firebase is actually disgusting when reading data that aren't primitive data types
        So we can get the strings for Title and creatorId pretty easily
        Questions don't work because Firebase doesn't know what type of Question each object is, so it tries to make a default Question, but Question is abstract
        So we need to make the actual question objects manually and add them to an ArrayList and make a new Form
        For whatever reason, Firebase is turning some of our integers into long when pulling them down from the database, so we need to cast the viewType to a long
        Somehow we can still compare it to the constants, which are ints? I feel like this shouldn't work but it does
        We start off by going to the Specific form in the database (myRef)
        Then we go through each of the children in the "questions" directory (go through each question)
        We can't use a switch statement because the viewType is returned as a long, so we have to just use a bunch of if-else
        For each view type, create a question object of that type
        For the multiple choice questions, we need to go through each choice in the database and construct a new choice and add it to the question
        At the end of every if we add the finished question to the ArrayList of questions
        Once there are no more questions, we create a new form using the collected data and then create and set our adapter
        TODO: add menuButtons for completing and submitting test/survey, add an additional if-else block determining whether the form is a test or a survey, currently it only creates survey questions
         */
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("reading data", dataSnapshot.getValue().toString());
                Log.d("reading data", dataSnapshot.child("creatorId").getValue().toString());
                Log.d("reading data", dataSnapshot.child("questions").getValue().toString());
//                System.out.println(something.get(0).getClass());
   //             System.out.println(something.size());
                for (DataSnapshot ds : dataSnapshot.child("questions").getChildren()) {
                    Question q = null;
                    String prompt = (String) ds.child("prompt").getValue();
                    long viewType = (long) ds.child("viewType").getValue();
                    if (viewType == Constants.VIEW_TYPE_MULTIPLE_CHOICE) {
                        q = new MultipleChoiceQuestion(prompt);
                        for ( DataSnapshot choiceSnapshot : ds.child("choices").getChildren()) {
                            Log.d("choices?", choiceSnapshot.getValue().toString());
                           // int id = (int) choiceSnapshot.child("id").getValue();
                            String text = (String) choiceSnapshot.child("text").getValue();
                            ((MultipleChoiceQuestion) q).addChoice(text);
                        }
                        questions.add(q);
                    } else if (viewType == Constants.VIEW_TYPE_MULTIPLE_ANSWER) {
                        q = new MultipleAnswerQuestion(prompt);
                        for ( DataSnapshot choiceSnapshot : ds.child("choices").getChildren()) {
                            Log.d("choices?", choiceSnapshot.getValue().toString());
                            // int id = (int) choiceSnapshot.child("id").getValue();
                            String text = (String) choiceSnapshot.child("text").getValue();
                            ((MultipleAnswerQuestion) q).addChoice(text);
                        }

                        questions.add(q);
                    } else if (viewType == Constants.VIEW_TYPE_ESSAY) {
                        q = new EssayQuestion(prompt);
                        questions.add (q);
                    } else if (viewType == Constants.VIEW_TYPE_SHORT_ANSWER) {
                        q = new ShortAnswerQuestion(prompt);
                        questions.add(q);
                    }

                }

                title = dataSnapshot.child("title").getValue().toString();
                creatorId = dataSnapshot.child("creatorId").getValue().toString();
                form = new Form(title, creatorId, questions);
                adapter = new FormAdapter(ViewFormActivity.this, questions);
                ListView questionList = (ListView)findViewById(R.id.list_view);
                questionList.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Log.d("form", creatorId);

    }
}
