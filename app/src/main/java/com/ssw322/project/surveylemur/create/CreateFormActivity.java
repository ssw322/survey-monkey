package com.ssw322.project.surveylemur.create;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssw322.project.surveylemur.R;
import com.ssw322.project.surveylemur.form.FormAdapter;
import com.ssw322.project.surveylemur.form.FormCreationAdapter;
import com.ssw322.project.surveylemur.form.question.Question;

import java.util.ArrayList;

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

        //establish how we will display information in the list view
        adapter = new FormCreationAdapter(this, new ArrayList<Question>());
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

    //we won't know which activities we're getting into till we're there
    public abstract void showQuestionTypeSelection();

}
