package com.ssw322.project.surveylemur.create;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssw322.project.surveylemur.R;
import com.ssw322.project.surveylemur.ViewFormActivity;
import com.ssw322.project.surveylemur.form.Form;
import com.ssw322.project.surveylemur.form.FormAdapter;
import com.ssw322.project.surveylemur.form.FormCreationAdapter;
import com.ssw322.project.surveylemur.form.question.Parser;
import com.ssw322.project.surveylemur.form.question.Question;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class CreateFormActivity extends AppCompatActivity {

    private FormAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        ListView listView = findViewById(R.id.create_test_list);
        TextView emptyView = findViewById(R.id.empty_text);
        listView.setEmptyView(emptyView);

        if (getIntent().getBooleanExtra("isEditing", false )) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference ref = db.getReference(getIntent().getStringExtra("code"));

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Form f = Parser.parseFirebaseData(dataSnapshot);
                    adapter = new FormCreationAdapter(CreateFormActivity.this, f.questions);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            //establish how we will display information in the list view
            adapter = new FormCreationAdapter(this, new ArrayList<Question>());
            listView.setAdapter(adapter);
        }


        FloatingActionButton fab = findViewById(R.id.create_add_question);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showQuestionTypeSelection();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.create_finished) {
            if(getIntent().getBooleanExtra("isEditing", false)) {
                constructForm(getIntent().getStringExtra("code"));
            }
            else {
                getCode(); //create a code, dump to the database, and return to the calling activity
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void passActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected AlertDialog.Builder getAlertDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Question Format");
        return builder;
    }

    protected String[] getQuestionTypes() {
        String[] questionTypes = new String[QuestionType.values().length];
        for(int i = 0; i < questionTypes.length; i++) {
            questionTypes[i] = QuestionType.values()[i].getDisplayName();
        }
        return questionTypes;
    }

    public abstract void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

    protected void addToAdapter(Question q) {
        adapter.add(q);
        adapter.notifyDataSetChanged();
    }

    public String generateCode() {
        StringBuilder sb = new StringBuilder(4);
        Random rand = new Random();
        for(int i = 0; i < 4; i++)
            sb.append(getCharForNumber(rand.nextInt(26))); //0 to 25
        return sb.toString();
    }

    // get uppercase letter from 0-25 inclusive number
    private char getCharForNumber(int i) {
        return (char)(i + 'A');
    }

    private void constructForm(String code) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference(code);
        ArrayList<Question> questions = new ArrayList<>();
        for(int i = 0; i < adapter.getCount(); i++) {
            questions.add(adapter.getItem(i).setId(i));
        }
        Form form = createForm(questions);

        ref.setValue(form);
        finish();
    }

    private void getCode() {
        String codeAttempt = generateCode();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        db.getReference(codeAttempt).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) //retry if we collide
                    getCode();
                else
                    onCodeGenerated(codeAttempt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void onCodeGenerated(String code) {
        constructForm(code);
    }

    //we won't know which activities we're getting into till we're there
    public abstract void showQuestionTypeSelection();

    public abstract Form createForm(ArrayList<Question> questions);
}
