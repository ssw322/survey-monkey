package com.ssw322.project.surveylemur.form;

/**
 * Created by Mark on 3/17/2019.
 */

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ssw322.project.surveylemur.R;
import com.ssw322.project.surveylemur.form.question.Constants;
import com.ssw322.project.surveylemur.form.question.MultipleAnswerQuestion;
import com.ssw322.project.surveylemur.form.question.MultipleChoiceQuestion;
import com.ssw322.project.surveylemur.form.question.Question;
import com.ssw322.project.surveylemur.form.question.ShortAnswerQuestion;

import java.util.ArrayList;

/**
 * Yes, we should be using a recycler view or something more advanced, but a standard listview will
 * work fine.
 */
public class FormAdapter extends ArrayAdapter<Question> {

    public FormAdapter(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup container) {
        if (convertView == null) {
            int viewType = getItemViewType(position);
            int layoutResource;
            switch(viewType) {
                case Constants.VIEW_TYPE_MULTIPLE_CHOICE:
                    layoutResource = R.layout.question_multiple_choice_template;
                    break;
                case Constants.VIEW_TYPE_MULTIPLE_ANSWER:
                    layoutResource = R.layout.question_multiple_answer_template;
                    break;
                case Constants.VIEW_TYPE_SHORT_ANSWER:
                    layoutResource = R.layout.question_short_answer_template;
                    break;
                default:
                    layoutResource = R.layout.question_essay_template;
            }
            //gimme some room for these views!
            convertView = LayoutInflater.from(getContext()).inflate(
                    layoutResource, container, false);
            Question q = getItem(position);
            //if you need to, wrap the view!
            convertView = q.fillOutView(convertView, container); //get polymorphic on 'em!
        }
        return convertView;
    }

    //this thing needs to support a whole bunch of layouts, namely multiple choice question,
    //multiple answer question, essay question, and short answer question -- so 4
    @Override
    public int getViewTypeCount() {
        return 4;
    }

    //let the adapter know which element is at which position and thus which layout to inflate.
    //each class displays differently after all
    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

}
