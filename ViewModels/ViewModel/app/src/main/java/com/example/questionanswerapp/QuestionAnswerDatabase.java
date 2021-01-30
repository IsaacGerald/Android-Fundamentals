package com.example.questionanswerapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = QuestionAnswer.class, version = 2, exportSchema = false)
public abstract class QuestionAnswerDatabase extends RoomDatabase {

    public abstract QuestionADao mQuestionADao();

    private static QuestionAnswerDatabase INSTANCE;

    //creating a singleton QuestionAnswerDatabase
    static QuestionAnswerDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (QuestionAnswerDatabase.class){
                if (INSTANCE == null){
                    //create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            QuestionAnswerDatabase.class, "QuestionAnswer_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new populateDbAsync(INSTANCE).execute();
        }
    };

    private static class populateDbAsync extends AsyncTask<Void, Void, Void>{
       private final QuestionADao mQuestionADao;
        String str1 = "What is your name.";
        String str2 = "The name of your country.";
        String str3 = "Tell us your age.";

        String [] strings = {str1, str2, str3};

        private populateDbAsync(QuestionAnswerDatabase db) {
            mQuestionADao = db.mQuestionADao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (mQuestionADao.getAnyQuestions().length < 1){
                for (int i = 0; i < strings.length; i++){
                    QuestionAnswer question = new QuestionAnswer(strings[i]);
                    mQuestionADao.insert(question);
                }
            }


            return null;
        }
    }
}