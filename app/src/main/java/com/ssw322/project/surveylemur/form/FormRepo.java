package com.ssw322.project.surveylemur.form;

import android.app.Application;
import android.os.AsyncTask;

import com.ssw322.project.surveylemur.form.question.QuestionDao;

public class FormRepo {

    private FormDao formDao;
    private QuestionDao questionDao;

    public FormRepo(Application app) {
        FormDatabase db = FormDatabase.getDatabase(app);
        formDao = db.formDao();
        questionDao = db.questionDao();
    }

    public void insert(Form form) {
        new insertInBackground(formDao).execute(form);
    }

    private static class insertInBackground extends AsyncTask<Form, Void, Void> {
        private FormDao dao;
        insertInBackground(FormDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Form... forms) {
            dao.insert(forms[0]);
            return null;
        }
    }
}
