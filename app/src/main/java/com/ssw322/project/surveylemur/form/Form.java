package com.ssw322.project.surveylemur.form;

import com.ssw322.project.surveylemur.form.question.Question;
import java.util.ArrayList;

/**
 * Created by Mark on 3/17/2019.
 */

public abstract class Form {

    public String title;
    public String creatorId;
    public String formType;
    public ArrayList<Question> questions;

    public Form(String title, String creatorId, ArrayList<Question> questions) {
        this.title = title;
        this.creatorId = creatorId;
        this.questions = questions;
    }
}
