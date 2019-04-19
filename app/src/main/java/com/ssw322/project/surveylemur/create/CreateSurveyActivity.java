package com.ssw322.project.surveylemur.create;

import androidx.annotation.Nullable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.google.gson.Gson;
import com.ssw322.project.surveylemur.edit.EditSurveyEssayActivity;
import com.ssw322.project.surveylemur.edit.EditSurveyMultipleAnswerActivity;
import com.ssw322.project.surveylemur.edit.EditSurveyMultipleChoiceActivity;
import com.ssw322.project.surveylemur.edit.EditSurveyShortAnswerActivity;
import com.ssw322.project.surveylemur.form.Form;
import com.ssw322.project.surveylemur.form.Survey;
import com.ssw322.project.surveylemur.form.question.Constants;
import com.ssw322.project.surveylemur.form.question.EssayQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.Question;
import com.ssw322.project.surveylemur.form.question.ShortAnswerQuestion;

import java.util.ArrayList;

public class CreateSurveyActivity extends CreateFormActivity {

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Gson gson = new Gson();
        if(resultCode == RESULT_OK) {
            Question q = null;
            switch(requestCode) {
                case Constants.DETAIL_TYPE_MULTIPLE_CHOICE:
                    q = gson.fromJson(data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION), MultipleChoiceQuestion.class);
                    break;
                case Constants.DETAIL_TYPE_MULTIPLE_ANSWER:
                    q = gson.fromJson(data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION), MultipleAnswerQuestion.class);
                    break;
                case Constants.DETAIL_TYPE_TRUE_FALSE:
                    q = gson.fromJson(data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION), MultipleChoiceQuestion.class);
                    break;
                case Constants.DETAIL_TYPE_SHORT_ANSWER:
                    q = gson.fromJson(data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION), ShortAnswerQuestion.class);
                    break;
                case Constants.DETAIL_TYPE_ESSAY:
                    q = gson.fromJson(data.getStringExtra(Constants.KEY_SERIALIZED_QUESTION), EssayQuestion.class);
                    break;
            }
            addToAdapter(q);
        }
        passActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showQuestionTypeSelection() {
        AlertDialog.Builder builder = getAlertDialogBuilder();
        builder.setItems(getQuestionTypes(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                switch(i) {
                    case 0:
                        // start a multiple answer activity for a result and dump it into the list view
                        intent = new Intent(CreateSurveyActivity.this, EditSurveyMultipleAnswerActivity.class);
                        startActivityForResult(intent, Constants.DETAIL_TYPE_MULTIPLE_ANSWER);
                        break;
                    case 1:
                        // same with multiple choice
                        intent = new Intent(CreateSurveyActivity.this, EditSurveyMultipleChoiceActivity.class);
                        startActivityForResult(intent, Constants.DETAIL_TYPE_MULTIPLE_CHOICE);
                        break;
                    case 2:
                        //true/false
                        intent = new Intent(CreateSurveyActivity.this, EditSurveyMultipleChoiceActivity.class);
                        //we piggy back on the multiple choice activity and use an extra variable to distinguish
                        intent.putExtra("isTrueFalse", true);
                        startActivityForResult(intent, Constants.DETAIL_TYPE_TRUE_FALSE);
                        break;
                    case 3:
                        //short answer
                        intent = new Intent(CreateSurveyActivity.this, EditSurveyShortAnswerActivity.class);
                        startActivityForResult(intent, Constants.DETAIL_TYPE_SHORT_ANSWER);
                        break;
                    case 4:
                        //essay
                        intent = new Intent(CreateSurveyActivity.this, EditSurveyEssayActivity.class);
                        startActivityForResult(intent, Constants.DETAIL_TYPE_ESSAY);
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public Form createForm(ArrayList<Question> questions) {
        return new Survey("Sample Survey", "Test User", questions);
    }
}
