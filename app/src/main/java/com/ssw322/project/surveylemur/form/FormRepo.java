package com.ssw322.project.surveylemur.form;

import android.app.Application;
import android.os.AsyncTask;

import com.ssw322.project.surveylemur.form.question.QuestionDao;
import com.ssw322.project.surveylemur.user.User;
import com.ssw322.project.surveylemur.user.UserDao;

public class FormRepo {

    private UserDao userDao;
    private FormDao formDao;
    private QuestionDao questionDao;

    public FormRepo(Application app) {
        FormDatabase db = FormDatabase.getDatabase(app);
        userDao = db.userDao();
        formDao = db.formDao();
        questionDao = db.questionDao();
    }

    public void insert(User user) { new insertUserInBackground(userDao).execute(user); }

    public void insert(Form form) {
        new insertInBackground(formDao).execute(form);
    }

    private static class insertUserInBackground extends AsyncTask<User, Void, Void> {
        private UserDao dao;
        insertUserInBackground(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            dao.insert(users[0]);
            return null;
        }
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
