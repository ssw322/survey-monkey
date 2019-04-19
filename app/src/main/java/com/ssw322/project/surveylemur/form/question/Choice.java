package com.ssw322.project.surveylemur.form.question;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Mark on 3/17/2019.
 */

@Entity(foreignKeys = {
        @ForeignKey(entity = Question.class,
            parentColumns = "id",
            childColumns = "questionId")
})
public class Choice {

    @PrimaryKey
    public int id;

    //TODO: remove foreign keys, we don't need these on a document db
    @ColumnInfo(name = "questionId")
    public String questionId;

    @ColumnInfo(name = "text")
    private String text;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Choice(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
