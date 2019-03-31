package com.ssw322.project.surveylemur.form.question;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssw322.project.surveylemur.R;

import androidx.room.Entity;

@Entity
public class GradedEssayQuestion extends Question implements Gradable {

    private int maxPoints;

    public GradedEssayQuestion(String s, int maxPoints) {
        this.prompt = s;
        this.maxPoints = maxPoints;
    }

    @Override
    public View fillOutView(View v, ViewGroup container) {
        TextView promptView = (TextView)v.findViewById(R.id.essay_prompt);
        promptView.setText(prompt);
        return v;
    }

    @Override
    public int getViewType() {
        return Constants.VIEW_TYPE_ESSAY;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public int getMaxPoints() {
        return maxPoints;
    }
}
