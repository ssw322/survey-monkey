package com.ssw322.project.surveylemur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.ssw322.project.surveylemur.create.CreateFormActivity;
import com.ssw322.project.surveylemur.create.CreateSurveyActivity;
import com.ssw322.project.surveylemur.create.CreateTestActivity;
import com.ssw322.project.surveylemur.form.Form;
import com.ssw322.project.surveylemur.form.Test;
import com.ssw322.project.surveylemur.form.question.Choice;
import com.ssw322.project.surveylemur.form.question.Constants;
import com.ssw322.project.surveylemur.form.question.EssayQuestion;
import com.ssw322.project.surveylemur.form.question.GradedEssayQuestion;
import com.ssw322.project.surveylemur.form.question.GradedMultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.GradedMultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.GradedShortAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.Parser;
import com.ssw322.project.surveylemur.form.question.Question;
import com.ssw322.project.surveylemur.form.question.ShortAnswerQuestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean isEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        Button button = findViewById(R.id.enterButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.code_field);
                String codeEntered = editText.getText().toString();
                if(codeEntered.trim().length() != 4) {
                    Toast.makeText(getBaseContext(), "You must enter a valid 4-digit code", Toast.LENGTH_SHORT).show();
                } else {
                    //if it's a syntactically valid code, then reach out to the db
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = db.getReference(codeEntered);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Select Action");
                                builder.setMessage("Would you like to take or edit this form?");
                                //add buttons
                                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //open an activity to edit a form
                                        String formType = (String)dataSnapshot.child("formType").getValue();
                                        if(formType.equals("Test")) {
                                            Intent intent = new Intent(MainActivity.this, CreateTestActivity.class);
                                            intent.putExtra("code", codeEntered);
                                            intent.putExtra("isEditing", true);
                                            startActivity(intent);
                                        } else if(formType.equals("Survey")) {
                                            Intent intent = new Intent(MainActivity.this, CreateSurveyActivity.class);
                                            intent.putExtra("code", codeEntered);
                                            intent.putExtra("isEditing", true);
                                            startActivity(intent);
                                        }

                                    }
                                });
                                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //do nothing, just exit the dialog
                                    }
                                });

                                builder.setNegativeButton("Take", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(MainActivity.this, ViewFormActivity.class);
                                        intent.putExtra("code", codeEntered);
                                        startActivity(intent);

                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                            else {
                                Toast.makeText(getBaseContext(), "No form found!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //no-op
                        }
                    });
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showThreeOptionAlertDialog();
            }
        });
    }

    private void showThreeOptionAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Format");
        builder.setMessage("Would you like to make a survey or a test?");

        //add buttons
        builder.setPositiveButton("Test", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //open an activity to create a test
                Intent intent = new Intent(MainActivity.this, CreateTestActivity.class);
                startActivity(intent);
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing, just exit the dialog
            }
        });

        builder.setNegativeButton("Survey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //open an activity to create a survey
                Intent intent = new Intent(MainActivity.this, CreateSurveyActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
