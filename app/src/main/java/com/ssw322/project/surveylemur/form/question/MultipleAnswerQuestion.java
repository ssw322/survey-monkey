package com.ssw322.project.surveylemur.form.question;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssw322.project.surveylemur.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mark on 3/17/2019.
 */

public class MultipleAnswerQuestion extends Question {

    private int choiceNumber;
    private ArrayList<Choice> choices;
    private Set<Integer> responseIds;

    public MultipleAnswerQuestion(String s) {
        this.prompt = s;
        this.choiceNumber = 0;
        this.choices = new ArrayList<>();
        this.responseIds = new HashSet<>();
    }

    public MultipleAnswerQuestion addChoice(String s) {
        //automatically increment the choice id
        Choice c = new Choice(choiceNumber++, s);
        choices.add(c);
        return this;
    }

    public boolean hasAnswer() {
        return !responseIds.isEmpty();
    }

    @Override
    public void fillOutView(View v) {

        TextView promptView = (TextView)v.findViewById(R.id.multiple_answer_prompt);
        promptView.setText(prompt);

        //make a bunch of CheckBoxes programmatically
        Context context = v.getContext();
        LinearLayout choiceGroup = (LinearLayout)v.findViewById(R.id.multiple_answer_choices);
        for(Choice c : choices) {
            CheckBox newChoice = new CheckBox(context);
            newChoice.setText(c.getText());
            newChoice.setId(c.getId());
            choiceGroup.addView(newChoice);
        }
    }
}
