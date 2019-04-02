package com.ssw322.project.surveylemur.form.question;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ssw322.project.surveylemur.R;

import androidx.room.Entity;

/**
 * Created by Mark on 3/17/2019.
 */

@Entity
public class ShortAnswerQuestion extends Question {

    public ShortAnswerQuestion(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public View fillOutView(View v, ViewGroup container) {
        TextView promptView = (TextView)v.findViewById(R.id.short_answer_prompt);
        promptView.setText(prompt);
        return v;
    }

    @Override
    public View fillCreationView(View v, ViewGroup container) {
        fillOutView(v, container);
        EditText editText = v.findViewById(R.id.short_answer_response);
        editText.setClickable(false);
        editText.setEnabled(false);
        return v;
    }

    @Override
    public int getViewType() {
        return Constants.VIEW_TYPE_SHORT_ANSWER;
    }
}
