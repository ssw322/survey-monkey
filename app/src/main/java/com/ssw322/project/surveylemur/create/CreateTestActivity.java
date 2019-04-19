package com.ssw322.project.surveylemur.create;

import androidx.annotation.Nullable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import com.ssw322.project.surveylemur.edit.EditTestEssayActivity;
import com.ssw322.project.surveylemur.edit.EditTestMultipleAnswerActivity;
import com.ssw322.project.surveylemur.edit.EditTestMultipleChoiceActivity;
import com.ssw322.project.surveylemur.edit.EditTestShortAnswerActivity;
import com.ssw322.project.surveylemur.form.Form;
import com.ssw322.project.surveylemur.form.Test;
import com.ssw322.project.surveylemur.form.question.Constants;
import com.ssw322.project.surveylemur.form.question.GradedEssayQuestion;
import com.ssw322.project.surveylemur.form.question.GradedMultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.GradedMultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.GradedShortAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.Question;

import java.util.ArrayList;

public class CreateTestActivity extends CreateFormActivity {

    public void showQuestionTypeSelection() {

        AlertDialog.Builder builder = getAlertDialogBuilder();
        builder.setTitle("Choose a Question Format");

        builder.setItems(getQuestionTypes(), new DialogInterface.OnClickListener() {
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Gson gson = new Gson();
        if(resultCode == RESULT_OK) {
            Question q = null;
            Log.d("Serialized Question", data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION));
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
            addToAdapter(q);
        }
        passActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Form createForm(ArrayList<Question> questions) {
        return new Test("Sample Test", "Test", questions);
    }
}
