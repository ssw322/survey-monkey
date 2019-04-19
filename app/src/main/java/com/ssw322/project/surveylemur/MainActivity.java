package com.ssw322.project.surveylemur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.ssw322.project.surveylemur.create.CreateSurveyActivity;
import com.ssw322.project.surveylemur.create.CreateTestActivity;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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
                    //let the user know that they must input something
                    Toast.makeText(getBaseContext(), "You must enter a valid 4-digit code", Toast.LENGTH_SHORT).show();
                }
                //make a call to the db somehow and show an alert dialogue if successful
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
