package com.ssw322.project.surveylemur.form.question;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.ssw322.project.surveylemur.R;

public class GradedMultipleChoiceQuestion extends MultipleChoiceQuestion implements Gradable {

    public int correctAnswerId;
    public int maxPoints;

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public GradedMultipleChoiceQuestion(String prompt, int correctAnswerId, int maxPoints) {
        super(prompt);
        this.correctAnswerId = correctAnswerId;
        this.maxPoints = maxPoints;
    }

    /**
     * For this, we may want to have some kind of strategy to allow for specialized ways of
     * grading a multiple choice question
     * @return an integer representing whether or not they got the question right
     */
    @Override
    public int getPoints() {
        if(correctAnswerId == choiceNumber)
            return maxPoints;
        return 0;
    }

    @Override
    public int getMaxPoints() {
        return maxPoints;
    }

    @Override
    public View fillOutView(View v, ViewGroup container) {
        //go populate the view for the question within
        super.fillOutView(v, container);
        //now, take that view and wrap it within a different layout
        View wrapperView = (LinearLayout) LayoutInflater.from(v.getContext()).inflate(R.layout.question_graded_wrapper, container, false);
        LinearLayout viewToBeReplaced = (LinearLayout)wrapperView.findViewById(R.id.item_to_be_wrapped);
        viewToBeReplaced.addView(v);

        TextView pointsView = (TextView)wrapperView.findViewById(R.id.pointsView);
        pointsView.setText(maxPoints + " points");
        return wrapperView;
    }

    //force the correct answer to show and prevent it from being modified
    @Override
    public View fillCreationView(View v, ViewGroup container) {
        v = fillOutView(v, container);
        RadioGroup rg = v.findViewById(R.id.multiple_choice_choices);
        for(int i = 0; i < choiceNumber; i++) {
            RadioButton rb = (RadioButton)rg.getChildAt(i);
            rb.setClickable(false);
            rb.setEnabled(false);
        }
        ((RadioButton)rg.getChildAt(correctAnswerId)).setChecked(true);
        rg.setEnabled(false);
        return v;
    }
}
