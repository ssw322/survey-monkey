package com.ssw322.project.surveylemur;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.ssw322.project.surveylemur.form.Form;
import com.ssw322.project.surveylemur.form.FormAdapter;
import com.ssw322.project.surveylemur.form.Survey;
import com.ssw322.project.surveylemur.form.Test;
import com.ssw322.project.surveylemur.form.question.Constants;
import com.ssw322.project.surveylemur.form.question.EssayQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.Parser;
import com.ssw322.project.surveylemur.form.question.Question;
import com.ssw322.project.surveylemur.form.question.ShortAnswerQuestion;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ViewFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference(getIntent().getStringExtra("code"));

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Form f = Parser.parseFirebaseData(dataSnapshot);
                FormAdapter adapter = new FormAdapter(ViewFormActivity.this, f.questions);
                ListView listView = findViewById(R.id.list_view);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
