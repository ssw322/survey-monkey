package com.ssw322.project.surveylemur;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ssw322.project.surveylemur.form.question.Constants;
import com.ssw322.project.surveylemur.form.question.GradedMultipleAnswerQuestion;

import java.util.HashSet;
import java.util.Set;

public class EditTestMultipleAnswerActivity extends AppCompatActivity {

    //TODO: Perhaps provide an expandable activity class?  Repeated logic with multiple choice
    private int numberofChoices = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test_multiple_answer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creation_menu_expandable, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.create_add_choice:
                //add a multiple choice
                showAlertDialogWithEditText();
                break;
            case R.id.create_remove_choice:
                if(numberofChoices == 0)
                    Toast.makeText(this, "No choices to remove", Toast.LENGTH_SHORT).show();
                else {
                    LinearLayout ll = findViewById(R.id.detail_multiple_answer_choices);
                    ll.removeViewAt(--numberofChoices);
                }
                break;
            case R.id.create_finished:
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                //create the object
                EditText promptText = findViewById(R.id.detail_multiple_answer_prompt);
                String prompt = promptText.getText().toString();

                LinearLayout choices = findViewById(R.id.detail_multiple_answer_choices);
                Set<Integer> correctAnswerIds = new HashSet<>();

                EditText pointsText = findViewById(R.id.detail_multiple_answer_points);
                int points = Integer.parseInt(pointsText.getText().toString());

                GradedMultipleAnswerQuestion gmaq = new GradedMultipleAnswerQuestion(prompt, correctAnswerIds, points); //need to get max points
                for(int i = 0; i < numberofChoices; i++) {
                    CheckBox cb = (CheckBox)choices.getChildAt(i);
                    if(cb.isChecked())
                        correctAnswerIds.add(cb.getId());
                    gmaq.addChoice(cb.getText().toString());
                }

                //use gson to serialize
                Gson gson = new Gson();
                String json = gson.toJson(gmaq);

                //make the intent
                Intent intent = new Intent();
                intent.putExtra(Constants.KEY_SERIALIZED_QUESTION, json);
                setResult(RESULT_OK, intent);
                finish();
                break;
            //keep list view data when back button is pressed
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialogWithEditText() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText entryText = new EditText(this);
        //TODO: give the edit text a slight left and right margin
        alert.setTitle("Add an Option");
        alert.setMessage("What would you like your choice to say?");

        alert.setPositiveButton("Add Choice", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //add item
                LinearLayout ll = findViewById(R.id.detail_multiple_answer_choices);
                CheckBox cb = new CheckBox(EditTestMultipleAnswerActivity.this);
                cb.setText(entryText.getText().toString());
                cb.setId(numberofChoices++);
                ll.addView(cb);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //nothing, just exit
            }
        });
        alert.setView(entryText);
        alert.show();
    }
}
