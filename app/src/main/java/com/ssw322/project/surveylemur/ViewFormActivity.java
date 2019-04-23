package com.ssw322.project.surveylemur;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssw322.project.surveylemur.form.Form;
import com.ssw322.project.surveylemur.form.FormAdapter;
import com.ssw322.project.surveylemur.form.question.AnswerSheet;
import com.ssw322.project.surveylemur.form.question.Parser;
import com.ssw322.project.surveylemur.form.question.Question;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewFormActivity extends AppCompatActivity {

    Form f;
    String code;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);

        code = getIntent().getStringExtra("code");
        listView = findViewById(R.id.list_view);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference(code);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                f = Parser.parseFirebaseData(dataSnapshot);
                FormAdapter adapter = new FormAdapter(ViewFormActivity.this, f.questions);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            if(allQuestionsHaveAnswers(listView)) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference("Responses").child(f.formType).child(code);
                DatabaseReference newFormResponse = ref.push();
                newFormResponse.setValue(getAnswers(listView), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(ViewFormActivity.this, "Response recorded!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
            else {
                Toast.makeText(this, "You must answer all questions", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean allQuestionsHaveAnswers(ListView listView) {
        FormAdapter a = (FormAdapter)listView.getAdapter();
        for(int i = 0; i < listView.getChildCount(); i++) {
            View v = listView.getChildAt(i);
            Question q = a.getItem(i);
            if(!q.hasAnswer(v)) {
                return false;
            }
        }
        return true;
    }

    public AnswerSheet getAnswers(ListView listView) {
        AnswerSheet answerSheet = new AnswerSheet();
        FormAdapter a = (FormAdapter)listView.getAdapter();
        for(int i = 0; i < listView.getChildCount(); i++) {
            View v = listView.getChildAt(i);
            Question q = a.getItem(i);
            answerSheet.addAnswer(q, q.getAnswer(v));
        }
        return answerSheet;
    }
}
