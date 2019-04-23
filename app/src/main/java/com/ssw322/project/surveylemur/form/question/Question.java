package com.ssw322.project.surveylemur.form.question;

import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Mark on 3/17/2019.
 */

public abstract class Question {

    public int id;
    public String prompt;

    public abstract View fillOutView(View v, ViewGroup container);
    public abstract View fillCreationView(View v, ViewGroup container);
    public abstract int getViewType();
    public abstract String getAnswer(View v);
    public abstract boolean hasAnswer(View v);

    public Question setId(int id) {
        this.id = id;
        return this;
    }
}
