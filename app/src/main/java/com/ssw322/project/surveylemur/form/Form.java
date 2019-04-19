package com.ssw322.project.surveylemur.form;

import android.view.View;

import com.ssw322.project.surveylemur.form.question.Question;
import com.ssw322.project.surveylemur.user.User;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
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
public class Form {

    @NonNull
    @PrimaryKey
    public String code;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "creatorId")
    public String creatorId;

    //room will mandate that you provide getters and setters for certain things
    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Ignore
    public ArrayList<Question> questions;

    public Form(String title, String creatorId, ArrayList<Question> questions) {
        this.title = title;
        this.creatorId = creatorId;
        this.questions = questions;
    }

    public Form(String title, String creatorId) {
        this.title = title;
        this.creatorId = creatorId;
        this.questions = new ArrayList<>();
    }
}
