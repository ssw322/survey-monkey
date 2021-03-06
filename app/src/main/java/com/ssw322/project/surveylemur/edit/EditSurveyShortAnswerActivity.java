package com.ssw322.project.surveylemur.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ssw322.project.surveylemur.R;
import com.ssw322.project.surveylemur.form.question.Constants;
import com.ssw322.project.surveylemur.form.question.GradedShortAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.ShortAnswerQuestion;

public class EditSurveyShortAnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_survey_short_answer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.create_finished:
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                //create the object
                EditText promptText = findViewById(R.id.detail_survey_short_answer_prompt);
                String prompt = promptText.getText().toString();

                ShortAnswerQuestion saq = new ShortAnswerQuestion(prompt);

                //use gson to serialize
                Gson gson = new Gson();
                String json = gson.toJson(saq);

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
}
