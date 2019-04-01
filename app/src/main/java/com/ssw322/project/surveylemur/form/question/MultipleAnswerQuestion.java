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

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverter;

/**
 * Created by Mark on 3/17/2019.
 */

@Entity
public class MultipleAnswerQuestion extends Question {

    private int choiceNumber;
    @Ignore
    private ArrayList<Choice> choices;
    protected Set<Integer> responseIds;

    public int getChoiceNumber() {
        return choiceNumber;
    }

    public void setChoiceNumber(int choiceNumber) {
        this.choiceNumber = choiceNumber;
    }

    public void setResponseIds (Set<Integer> responseIds) { this.responseIds = responseIds; }

    public MultipleAnswerQuestion(String prompt) {
        this.prompt = prompt;
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
    public View fillOutView(View v, ViewGroup container) {

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
        return v;
    }

    @Override
    public int getViewType() {
        return Constants.VIEW_TYPE_MULTIPLE_ANSWER;
    }
}
