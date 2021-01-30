package com.example.questionanswerapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

;
import java.util.List;

public class QuestionAnswerViewModel extends AndroidViewModel {
    private static final String TAG = "QuestionAnswerViewModel";
    private QuestionAnswerRepo mQuestionAnswerRepo;
    LiveData<List<QuestionAnswer>> mData;

   public QuestionAnswerViewModel(@NonNull Application application){
       super(application);
       mQuestionAnswerRepo = new QuestionAnswerRepo(application);
       mData = mQuestionAnswerRepo.getAllQuestions();
    }

    LiveData<List<QuestionAnswer>> getAllWords(){
        Log.d(TAG, "getAllWords: " + mData);
        return mData;
    }

    public void insert(QuestionAnswer questionAnswer){
        mQuestionAnswerRepo.insert(questionAnswer);
    }

    public  void delete(QuestionAnswer... questionAnswer){
        mQuestionAnswerRepo.delete(questionAnswer);
    }
    public  void update(QuestionAnswer questionAnswer){
        mQuestionAnswerRepo.update(questionAnswer);
    }
}
