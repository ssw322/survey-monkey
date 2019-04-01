package com.ssw322.project.surveylemur;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssw322.project.surveylemur.form.question.Constants;

public class CreateTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        ListView listView = findViewById(R.id.create_test_list);
        TextView emptyView = findViewById(R.id.empty_text);
        listView.setEmptyView(emptyView);

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
                        intent = new Intent(CreateTestActivity.this, EditTestQuestionActivity.class);
                        intent.putExtra("QuestionType", Constants.DETAIL_TYPE_MULTIPLE_ANSWER); //reusing view type, can probably rename
                        startActivity(intent);
                        break;
                    case 1:
                        // same with multiple choice
                        intent = new Intent(CreateTestActivity.this, EditTestQuestionActivity.class);
                        intent.putExtra("QuestionType", Constants.DETAIL_TYPE_MULTIPLE_CHOICE); //reusing view type, can probably rename
                        startActivity(intent);
                        break;
                    case 2:
                        //true/false
                        intent = new Intent(CreateTestActivity.this, EditTestQuestionActivity.class);
                        intent.putExtra("QuestionType", Constants.DETAIL_TYPE_TRUE_FALSE); //reusing view type, can probably rename
                        startActivity(intent);
                        break;
                    case 3:
                        //short answer
                        intent = new Intent(CreateTestActivity.this, EditTestQuestionActivity.class);
                        intent.putExtra("QuestionType", Constants.DETAIL_TYPE_SHORT_ANSWER); //reusing view type, can probably rename
                        startActivity(intent);
                        break;
                    case 4:
                        //essay
                        intent = new Intent(CreateTestActivity.this, EditTestQuestionActivity.class);
                        intent.putExtra("QuestionType", Constants.DETAIL_TYPE_ESSAY); //reusing view type, can probably rename
                        startActivity(intent);
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
