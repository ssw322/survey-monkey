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

/**
 * Created by Mark on 3/17/2019.
 */

@Entity
public class MultipleChoiceQuestion extends Question {

    protected int choiceNumber;
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
    public View fillOutView(View v, ViewGroup container) {
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
        return v;
    }

    @Override
    public int getViewType() {
        return Constants.VIEW_TYPE_MULTIPLE_CHOICE;
    }
}
