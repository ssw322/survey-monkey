package com.ssw322.project.surveylemur.form.question;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssw322.project.surveylemur.R;

import java.util.Set;

public class GradedMultipleAnswerQuestion extends MultipleAnswerQuestion implements Gradable {

    private Set<Integer> correctAnswerIds;
    private int maxPoints;

    public GradedMultipleAnswerQuestion(String s, Set<Integer> correctAnswerIds, int maxPoints) {
        super(s);
        this.correctAnswerIds = correctAnswerIds;
        this.maxPoints = maxPoints;
    }

    public void addCorrectAnswerId(int id) {
        correctAnswerIds.add(id);
    }

    /**
     * May want to have different strategies for this.  For now, it's all-or-nothing
     * @return an int representing how many points the answer earns on this question
     */
    @Override
    public int getPoints() {
        if(correctAnswerIds.equals(responseIds))
            return maxPoints;
        return 0;
    }

    @Override
    public int getMaxPoints() {
        return maxPoints;
    }

    /**
     * This is just characteristic of all graded views, perhaps
     * a static method on the interface?
     * @param v
     */
    @Override
    public View fillOutView(View v, ViewGroup container) {
        //go populate the view for the question within
        super.fillOutView(v, container);
        //now, take that view and wrap it within a different layout
        View wrapperView = (LinearLayout)LayoutInflater.from(v.getContext()).inflate(R.layout.question_graded_wrapper, container, false);
        LinearLayout viewToBeReplaced = (LinearLayout)wrapperView.findViewById(R.id.item_to_be_wrapped);
        viewToBeReplaced.addView(v);

        TextView pointsView = (TextView)wrapperView.findViewById(R.id.pointsView);
        pointsView.setText(maxPoints + " points");
        return wrapperView;
    }
}
