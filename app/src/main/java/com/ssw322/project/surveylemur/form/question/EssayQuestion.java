package com.ssw322.project.surveylemur.form.question;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.ssw322.project.surveylemur.R;

/**
 * Created by Mark on 3/17/2019.
 */

public class EssayQuestion extends Question {

    public EssayQuestion(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public View fillOutView(View v, ViewGroup container) {
        TextView promptView = (TextView)v.findViewById(R.id.essay_prompt);
        promptView.setText(prompt);
        return v;
    }

    @Override
    public View fillCreationView(View v, ViewGroup container) {
        //fill the edit text with the correct answer text then set it to
        //non-clickable
        fillOutView(v, container);
        EditText editText = v.findViewById(R.id.essay_response);
        editText.setClickable(false);
        editText.setEnabled(false);
        return v;
    }

    @Override
    public int getViewType() {
        return Constants.VIEW_TYPE_ESSAY;
    }

    @Override
    public String getAnswer(View v) {
        EditText editText = v.findViewById(R.id.essay_response);
        return editText.getText().toString();
    }

    @Override
    public boolean hasAnswer(View v) {
        EditText editText = v.findViewById(R.id.essay_response);
        return editText.getText().toString().trim().length() != 0;
    }
}
