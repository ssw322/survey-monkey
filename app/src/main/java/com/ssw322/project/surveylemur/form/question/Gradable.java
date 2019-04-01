package com.ssw322.project.surveylemur.form.question;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssw322.project.surveylemur.R;

public interface Gradable {

    int getPoints();
    int getMaxPoints();
}
