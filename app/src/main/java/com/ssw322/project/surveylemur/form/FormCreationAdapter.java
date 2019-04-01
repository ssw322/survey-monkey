package com.ssw322.project.surveylemur.form;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssw322.project.surveylemur.R;
import com.ssw322.project.surveylemur.form.question.Constants;
import com.ssw322.project.surveylemur.form.question.Question;

import java.util.ArrayList;

public class FormCreationAdapter extends FormAdapter {

    public FormCreationAdapter(Context context, ArrayList<Question> questions) {
        super(context, questions);
    }

    //when you go to get the view, make sure that you display the correct answer and
    //prevent any further input
    @Override
    protected View populateView(int position, View convertView, ViewGroup container) {
        convertView = inflateView(position, container);
        Question q = getItem(position);
        return q.fillCreationView(convertView, container);
    }
}
