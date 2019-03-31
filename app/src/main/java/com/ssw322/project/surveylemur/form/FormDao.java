package com.ssw322.project.surveylemur.form;

import com.ssw322.project.surveylemur.form.question.Question;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FormDao {

    //just generate enough information to display a preview
    @Query("SELECT * FROM Form WHERE code = :code")
    Form getFormInfoByCode(String code);

    //gather up all of the forms for a given user
    @Query("SELECT * FROM Form WHERE creatorId = :username")
    List<Form> getFormsForUser(String username);

    @Insert
    void insert(Form form);
}
