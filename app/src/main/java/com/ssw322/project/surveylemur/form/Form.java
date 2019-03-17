package com.ssw322.project.surveylemur.form;

import com.ssw322.project.surveylemur.form.question.Question;

import java.util.ArrayList;

/**
 * Created by Mark on 3/17/2019.
 */

public class Form {

    private String code;
    private String name;
    private ArrayList<Question> questions;

    public Form(String code, String name) {
        this.code = code;
        this.name = name;
        this.questions = new ArrayList<>();
    }
}
