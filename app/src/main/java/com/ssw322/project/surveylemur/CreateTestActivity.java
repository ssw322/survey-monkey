package com.ssw322.project.surveylemur;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
                switch(i) {
                    case 0:
                        // start a multiple answer activity for a result and dump it into the list view
                        break;
                    case 1:
                        // same with multiple choice
                        break;
                    case 2:
                        //true/false
                        break;
                    case 3:
                        //short answer
                        break;
                    case 4:
                        //essay
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
