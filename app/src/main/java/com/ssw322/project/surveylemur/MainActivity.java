package com.ssw322.project.surveylemur;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ssw322.project.surveylemur.form.FormRepo;
import com.ssw322.project.surveylemur.form.Survey;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FormRepo formRepo = new FormRepo(getApplication());
        formRepo.insert(new Survey("XXFG", "Hello World", "mark"));
        formRepo.insert(new Survey("GJKA", "Goodbye World", "mark"));

        Button button = findViewById(R.id.enterButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.code_field);
                String codeEntered = editText.getText().toString();

                if(codeEntered.equals("")) {
                    //let the user know that they must input something
                    Toast.makeText(getBaseContext(), "You must enter a valid 4-digit letter sequence", Toast.LENGTH_SHORT).show();
                }
                //make a call to the db somehow and show an alert dialogue if successful
            }
        });
    }
}
