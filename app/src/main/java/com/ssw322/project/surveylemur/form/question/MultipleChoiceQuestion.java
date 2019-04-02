package com.ssw322.project.surveylemur.form.question;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ssw322.project.surveylemur.R;

import java.util.ArrayList;

import androidx.room.Entity;
import androidx.room.Ignore;

/**
 * Created by Mark on 3/17/2019.
 */

@Entity
public class MultipleChoiceQuestion extends Question {

    //TODO: rename to numberOfChoices
    protected int choiceNumber;

    @Ignore
    private ArrayList<Choice> choices;

    public MultipleChoiceQuestion(String prompt) {
        this.prompt = prompt;
        this.choices = new ArrayList<>();
    }

    public MultipleChoiceQuestion addChoice(String s) {
        //automatically increment the choice id
        Choice c = new Choice(choiceNumber++, s);
        choices.add(c);
        return this;
    }

    private void addPrompt(View v) {
        TextView promptView = (TextView)v.findViewById(R.id.multiple_choice_prompt);
        promptView.setText(prompt);
    }

    private void addRadioButtons(View v) {
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

    @Override
    public View fillOutView(View v, ViewGroup container) {
        addPrompt(v);
        addRadioButtons(v);
        return v;
    }

    //for survey questions, this is the same as filling it out and blocking clicks
    @Override
    public View fillCreationView(View v, ViewGroup container) {
        //we don't wrap it, so we don't need to reassign
        fillOutView(v, container);
        RadioGroup rg = v.findViewById(R.id.multiple_choice_choices);
        for(int i = 0; i < choiceNumber; i++) {
            RadioButton rb = (RadioButton)rg.getChildAt(i);
            rb.setClickable(false);
            rb.setEnabled(false);
        }
        return v;
    }

    @Override
    public int getViewType() {
        return Constants.VIEW_TYPE_MULTIPLE_CHOICE;
    }
}
