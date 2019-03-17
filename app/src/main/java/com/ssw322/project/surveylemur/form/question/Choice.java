package com.ssw322.project.surveylemur.form.question;

/**
 * Created by Mark on 3/17/2019.
 */

public class Choice {

    private int id;
    private String text;

    public Choice(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
