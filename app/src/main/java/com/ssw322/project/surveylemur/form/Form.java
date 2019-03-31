package com.ssw322.project.surveylemur.form;

import android.view.View;

import com.ssw322.project.surveylemur.form.question.Question;
import com.ssw322.project.surveylemur.user.User;

import java.util.ArrayList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Mark on 3/17/2019.
 */

//establish a link between this table and the user table
@Entity(foreignKeys = {
        @ForeignKey(entity= User.class,
            parentColumns = "id",
            childColumns = "creatorId")
})
public abstract class Form {

    @PrimaryKey
    private String code;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "creator")
    private String creatorId;

    private ArrayList<Question> questions;

    public Form(String code, String title, String creatorId) {
        this.code = code;
        this.title = title;
        this.creatorId = creatorId;
        this.questions = new ArrayList<>();
    }
}
