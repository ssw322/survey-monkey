package com.ssw322.project.surveylemur.form;

import android.view.View;

import com.ssw322.project.surveylemur.form.question.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 3/17/2019.
 */

public class Test extends Form {

    public Test(String name, String creatorId) {
        super(name, creatorId);
    }

    public Test(String name, String creatorId, ArrayList<Question> questions) { super(name, creatorId, questions); }
}
