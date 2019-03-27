package com.ssw322.project.surveylemur.form;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 3/17/2019.
 */

public class Test extends Form {

    private Map<Integer, Integer> points;

    public Test(String code, String name) {
        super(code, name);
        this.points = new HashMap<>();
    }
}
