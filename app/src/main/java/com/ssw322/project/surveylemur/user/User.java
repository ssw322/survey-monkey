package com.ssw322.project.surveylemur.user;

import com.ssw322.project.surveylemur.form.Form;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Mark on 3/17/2019.
 */

@Entity
public class User {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "username")
    public String username;
}
