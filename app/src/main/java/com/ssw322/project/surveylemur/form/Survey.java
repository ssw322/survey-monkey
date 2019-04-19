package com.ssw322.project.surveylemur.form;

/**
 * Created by Mark on 3/17/2019.
 */

import android.view.View;

import com.ssw322.project.surveylemur.form.question.Question;

import java.util.ArrayList;

/**
 * A survey.  Does not provide much more useful state to the form, but does override some serialization
 * stuff and provide different display logic.
 */
public class Survey extends Form {

    public Survey(String name, String creatorId) {
        super(name, creatorId);
    }

    public Survey(String name, String creatorId, ArrayList<Question> questions) { super(name, creatorId, questions); }

}
