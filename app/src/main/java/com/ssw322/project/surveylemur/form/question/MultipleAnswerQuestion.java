package com.ssw322.project.surveylemur.form.question;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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

    public int choiceNumber;

    public ArrayList<Choice> choices;
    public ArrayList<Integer> responseIds;

    public int getChoiceNumber() {
        return choiceNumber;
    }

    public void setChoiceNumber(int choiceNumber) {
        this.choiceNumber = choiceNumber;
    }

    public MultipleAnswerQuestion(String prompt) {
        this.prompt = prompt;
        this.choiceNumber = 0;
        this.choices = new ArrayList<>();
        this.responseIds = new ArrayList<>();
    }

    public MultipleAnswerQuestion setChoices(ArrayList<Choice> choices) {
        this.choices = choices;
        return this;
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

    private void addPrompt(View v) {
        TextView promptView = (TextView)v.findViewById(R.id.multiple_answer_prompt);
        promptView.setText(prompt);
    }

    private void addCheckboxes(View v) {
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

    @Override
    public View fillOutView(View v, ViewGroup container) {
        addPrompt(v);
        addCheckboxes(v);
        return v;
    }

    @Override
    public View fillCreationView(View v, ViewGroup container) {
        fillOutView(v, container);
        //now iterate over the checkboxes and set the checkboxes as clicked
        LinearLayout ll = v.findViewById(R.id.multiple_answer_choices);
        for(int i = 0; i < getChoiceNumber(); i++) {
            CheckBox cb = (CheckBox)ll.getChildAt(i);
            cb.setClickable(false);
            cb.setEnabled(false);
        }
        return v;
    }

    @Override
    public int getViewType() {
        return Constants.VIEW_TYPE_MULTIPLE_ANSWER;
    }
}
