package com.ssw322.project.surveylemur.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ssw322.project.surveylemur.R;
import com.ssw322.project.surveylemur.form.question.GradedMultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleChoiceQuestion;

public class EditSurveyMultipleChoiceActivity extends AppCompatActivity {

    private int numberofChoices = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_survey_multiple_choice);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            boolean isTrueFalse = bundle.getBoolean("isTrueFalse");
            //true/false is just a special case of multiple choice
            if(isTrueFalse) {
                addOption("True");
                addOption("False");
            }
        }
    }

    public void addOption(String s) {
        RadioGroup rg = findViewById(R.id.detail_survey_multiple_choice_choices);
        RadioButton rb = new RadioButton(EditSurveyMultipleChoiceActivity.this);
        rb.setText(s);
        rb.setId(numberofChoices++);
        rb.setEnabled(false);
        rb.setClickable(false);
        rg.addView(rb);
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
                    RadioGroup rg = findViewById(R.id.detail_survey_multiple_choice_choices);
                    rg.removeViewAt(--numberofChoices);
                }
                break;
            case R.id.create_finished:
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                //create the object
                EditText promptText = findViewById(R.id.detail_survey_multiple_choice_prompt);
                String prompt = promptText.getText().toString();

                RadioGroup choices = findViewById(R.id.detail_survey_multiple_choice_choices);

                MultipleChoiceQuestion gmcq = new MultipleChoiceQuestion(prompt); //need to get max points
                for(int i = 0; i < numberofChoices; i++) {
                    RadioButton rb = (RadioButton)choices.getChildAt(i);
                    gmcq.addChoice(rb.getText().toString());
                }

                //use gson to serialize
                Gson gson = new Gson();
                String json = gson.toJson(gmcq);

                //make the intent
                Intent intent = new Intent();
                intent.putExtra("SerializedQuestion", json);
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
                addOption(entryText.getText().toString());
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
