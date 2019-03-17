package com.ssw322.project.surveylemur.form.question;

import android.view.View;
import android.widget.TextView;

import com.ssw322.project.surveylemur.R;

/**
 * Created by Mark on 3/17/2019.
 */

public class ShortAnswerQuestion extends Question {

    public ShortAnswerQuestion(String s) {
        this.prompt = s;
    }

    @Override
    public void fillOutView(View v) {
        TextView promptView = (TextView)v.findViewById(R.id.short_answer_prompt);
        promptView.setText(prompt);
    }
}
