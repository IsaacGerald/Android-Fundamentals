package com.example.questionanswerapp;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionAnswerRepo {
    private static final String TAG = "QuestionAnswerRepo";
    private QuestionADao mQuestionADao;
    LiveData<List<QuestionAnswer>> mAllQuestions;

    QuestionAnswerRepo(Application application){
        QuestionAnswerDatabase db = QuestionAnswerDatabase.getDatabase(application);
        mQuestionADao = db.mQuestionADao();
        mAllQuestions = mQuestionADao.getAllQuestions();
    }

    LiveData<List<QuestionAnswer>> getAllQuestions(){
        Log.d(TAG, "getAllQuestions: " + mAllQuestions);
        return mAllQuestions;
    }

    public void insert(QuestionAnswer questionAnswer){
        new insertAsyncTask(mQuestionADao).execute(questionAnswer);
    }
    public void delete(QuestionAnswer... questionAnswer){
        new deleteAsyncTask(mQuestionADao).execute(questionAnswer);
    }
    public void update(QuestionAnswer questionAnswer){
        new updateAsyncTask(mQuestionADao).execute(questionAnswer);
    }

    private static class insertAsyncTask extends AsyncTask<QuestionAnswer, Void, Void>{

        public insertAsyncTask(QuestionADao questionADao) {
            mQuestionADao = questionADao;
        }

        private QuestionADao mQuestionADao;

        @Override
        protected Void doInBackground(QuestionAnswer... questionAnswers) {
            mQuestionADao.insert(questionAnswers[0]);

            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<QuestionAnswer, Void, Void>{

        public deleteAsyncTask(QuestionADao questionADao) {
            mQuestionADao = questionADao;
        }

        private QuestionADao mQuestionADao;

        @Override
        protected Void doInBackground(QuestionAnswer... questionAnswers) {
            mQuestionADao.delete(questionAnswers[0]);

            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<QuestionAnswer, Void, Void>{

        public updateAsyncTask(QuestionADao questionADao) {
            mQuestionADao = questionADao;
        }

        private QuestionADao mQuestionADao;

        @Override
        protected Void doInBackground(QuestionAnswer... questionAnswers) {
            mQuestionADao.update(questionAnswers[0]);

            return null;
        }
    }

}
