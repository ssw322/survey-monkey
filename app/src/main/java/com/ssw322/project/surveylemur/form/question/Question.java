package com.ssw322.project.surveylemur.form.question;

import android.view.View;

/**
 * Created by Mark on 3/17/2019.
 */

public abstract class Question {

    String prompt;
    int id;

    public abstract void fillOutView(View v);
}
