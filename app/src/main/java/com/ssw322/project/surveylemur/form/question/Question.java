package com.ssw322.project.surveylemur.form.question;

import android.view.View;
import android.view.ViewGroup;

import com.ssw322.project.surveylemur.form.Form;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Mark on 3/17/2019.
 */

@Entity(foreignKeys = {
        @ForeignKey(entity = Form.class,
            parentColumns = "code",
            childColumns = "formId")
})
public abstract class Question {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "formId")
    public String formId;

    @ColumnInfo(name = "prompt")
    public String prompt;

    public abstract View fillOutView(View v, ViewGroup container);
    public abstract View fillCreationView(View v, ViewGroup container);
    public abstract int getViewType();
}
