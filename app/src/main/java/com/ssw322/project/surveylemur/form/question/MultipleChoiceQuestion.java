package com.ssw322.project.surveylemur.form.question;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ssw322.project.surveylemur.R;

import java.util.ArrayList;

/**
 * Created by Mark on 3/17/2019.
 */

public class MultipleChoiceQuestion extends Question {

    private int choiceNumber;
    private ArrayList<Choice> choices;

    public MultipleChoiceQuestion(String s) {
        this.prompt = s;
        this.choices = new ArrayList<>();
    }

    public MultipleChoiceQuestion addChoice(String s) {
        //automatically increment the choice id
        Choice c = new Choice(choiceNumber++, s);
        choices.add(c);
        return this;
    }

    @Override
    public void fillOutView(View v) {
        TextView promptView = (TextView)v.findViewById(R.id.multiple_choice_prompt);
        promptView.setText(prompt);

        Context context = v.getContext();
        RadioGroup choiceGroup = (RadioGroup)v.findViewById(R.id.multiple_choice_choices);
        for(Choice c : choices) {
            RadioButton newChoice = new RadioButton(context);
            //fill it out with the choices
            newChoice.setText(c.getText());
            newChoice.setId(c.getId());
            choiceGroup.addView(newChoice);
        }

    }
}
