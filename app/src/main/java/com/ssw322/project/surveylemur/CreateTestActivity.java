package com.ssw322.project.surveylemur;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ssw322.project.surveylemur.form.FormAdapter;
import com.ssw322.project.surveylemur.form.question.Constants;
import com.ssw322.project.surveylemur.form.question.GradedEssayQuestion;
import com.ssw322.project.surveylemur.form.question.GradedMultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.GradedMultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.GradedShortAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.Question;

import java.util.ArrayList;

public class CreateTestActivity extends AppCompatActivity {

    private FormAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        ListView listView = findViewById(R.id.create_test_list);
        TextView emptyView = findViewById(R.id.empty_text);
        listView.setEmptyView(emptyView);

        //establish how we will display information in the list view
        adapter = new FormAdapter(this, new ArrayList<Question>());
        listView.setAdapter(adapter);

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
            //user is done, create a code, dump to the database, and return to the calling activity
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showQuestionTypeSelection() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Question Format");

        String[] questionTypes = {"Multiple Answer", "Multiple Choice", "True/False", "Short Answer", "Essay"};
        builder.setItems(questionTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                switch(i) {
                    case 0:
                        // start a multiple answer activity for a result and dump it into the list view
                        intent = new Intent(CreateTestActivity.this, EditTestMultipleAnswerActivity.class);
                        startActivityForResult(intent, Constants.DETAIL_TYPE_MULTIPLE_ANSWER);
                        break;
                    case 1:
                        // same with multiple choice
                        intent = new Intent(CreateTestActivity.this, EditTestMultipleChoiceActivity.class);
                        startActivityForResult(intent, Constants.DETAIL_TYPE_MULTIPLE_CHOICE);
                        break;
                    case 2:
                        //true/false
                        intent = new Intent(CreateTestActivity.this, EditTestMultipleChoiceActivity.class);
                        //we piggy back on the multiple choice activity and use an extra variable to distinguish
                        intent.putExtra("isTrueFalse", true);
                        startActivityForResult(intent, Constants.DETAIL_TYPE_TRUE_FALSE);
                        break;
                    case 3:
                        //short answer
                        intent = new Intent(CreateTestActivity.this, EditTestShortAnswerActivity.class);
                        startActivityForResult(intent, Constants.DETAIL_TYPE_SHORT_ANSWER);
                        break;
                    case 4:
                        //essay
                        intent = new Intent(CreateTestActivity.this, EditTestEssayActivity.class);
                        startActivityForResult(intent, Constants.DETAIL_TYPE_ESSAY);
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //retrieve the data back from the activity.  Use the request code to distinguish the type
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Gson gson = new Gson();
        if(resultCode == RESULT_OK) {
            Question q = null;
            switch(requestCode) {
                case Constants.DETAIL_TYPE_MULTIPLE_CHOICE:
                    q = gson.fromJson(data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION), GradedMultipleChoiceQuestion.class);
                    break;
                case Constants.DETAIL_TYPE_MULTIPLE_ANSWER:
                    q = gson.fromJson(data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION), GradedMultipleAnswerQuestion.class);
                    break;
                case Constants.DETAIL_TYPE_TRUE_FALSE:
                    q = gson.fromJson(data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION), GradedMultipleChoiceQuestion.class);
                    break;
                case Constants.DETAIL_TYPE_SHORT_ANSWER:
                    q = gson.fromJson(data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION), GradedShortAnswerQuestion.class);
                    break;
                case Constants.DETAIL_TYPE_ESSAY:
                    q = gson.fromJson(data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION), GradedEssayQuestion.class);
                    break;
            }
            adapter.add(q);
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
