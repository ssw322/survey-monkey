package com.ssw322.project.surveylemur.form.question;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssw322.project.surveylemur.R;

import androidx.room.Entity;

@Entity
public class GradedMultipleChoiceQuestion extends MultipleChoiceQuestion implements Gradable {

    private int correctAnswerId;
    private int maxPoints;

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
}
