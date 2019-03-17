package com.ssw322.project.surveylemur.form;

/**
 * Created by Mark on 3/17/2019.
 */

/**
 * A survey.  Does not provide much more useful state to the form, but does override some serialization
 * stuff and provide different display logic.
 */
public class Survey extends Form {

    public Survey(String code, String name) {
        super(code, name);
    }
}
