package com.ssw322.project.surveylemur.create;

public enum QuestionType {

    MultipleAnswer("Multiple Answer"),
    MultipleChoice("Multiple Choice"),
    TrueFalse("True/False"),
    ShortAnswer("Short Answer"),
    Essay("Essay");

    private String displayName;

    QuestionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
