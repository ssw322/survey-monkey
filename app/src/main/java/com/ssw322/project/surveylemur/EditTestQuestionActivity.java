package com.ssw322.project.surveylemur;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ssw322.project.surveylemur.form.question.Constants;

import androidx.appcompat.app.AppCompatActivity;

public class EditTestQuestionActivity extends AppCompatActivity {

    private static int detailType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test_question);
        //this can only be created via a test creation activity, must check the bundle to see which
        //type of question we are making
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
            detailType = bundle.getInt("QuestionType");

        //let's get this bread
        switch(detailType) {
            case Constants.DETAIL_TYPE_MULTIPLE_CHOICE:
                setContentView(R.layout.detail_multiple_choice);
                break;
            case Constants.DETAIL_TYPE_MULTIPLE_ANSWER:
                setContentView(R.layout.detail_multiple_answer);
                break;
            case Constants.DETAIL_TYPE_SHORT_ANSWER:
                setContentView(R.layout.detail_short_answer);
                break;
            case Constants.DETAIL_TYPE_ESSAY:
                setContentView(R.layout.detail_essay);
                break;
            case Constants.DETAIL_TYPE_TRUE_FALSE:
                setContentView(R.layout.detail_true_false);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //we also need to check the detail type here -- only multiple choice and multiple answer will inflate the plus and minus buttons
        if(detailType == Constants.DETAIL_TYPE_MULTIPLE_CHOICE || detailType == Constants.DETAIL_TYPE_MULTIPLE_ANSWER)
            getMenuInflater().inflate(R.menu.creation_menu_expandable, menu);
        else
            getMenuInflater().inflate(R.menu.creation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.create_finished) {
            Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
