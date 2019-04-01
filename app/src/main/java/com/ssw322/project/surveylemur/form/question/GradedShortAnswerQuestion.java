package com.ssw322.project.surveylemur.form.question;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssw322.project.surveylemur.R;

import androidx.room.Entity;

@Entity
public class GradedShortAnswerQuestion extends ShortAnswerQuestion implements Gradable {

    private int maxPoints;

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public GradedShortAnswerQuestion(String prompt, int maxPoints) {
        super(prompt);
        this.maxPoints = maxPoints;
    }

    @Override
    public int getPoints() {
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
