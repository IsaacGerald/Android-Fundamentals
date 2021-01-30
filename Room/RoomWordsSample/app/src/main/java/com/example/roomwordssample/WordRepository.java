package com.example.roomwordssample;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private static final String TAG = "WordRepository";
    private WordDao mWordDao;
    public LiveData<List<Word>> mAllWords;

   WordRepository(Application application){
      WordRoomDatabase db = WordRoomDatabase.getDatabase(application.getApplicationContext());
      mWordDao = db.wordDao();
      //mAllWords = mWordDao.getAllWords();
  }
  LiveData<List<Word>> getAllWords(){
      Log.d(TAG, "getAllWords: " + mAllWords);
      return mAllWords;
  }

  public void insert(Word word){
      new insertAsyncTask(mWordDao).execute(word);
  }

  public void deleteAll(){
       new deleteAllWordsAsyncTask(mWordDao).execute();
  }

  public void delete(Word word){
       new deleteAsyncTask(mWordDao).execute(word);
  }
  public void update(Word word){
    new updateAsyncTask(mWordDao).execute(word);
  }

  private static class insertAsyncTask extends AsyncTask<Word, Void, Void>{

      private WordDao mAsyncTaskDao;

      public insertAsyncTask(WordDao dao) {
          mAsyncTaskDao = dao;
      }

      @Override
      protected Void doInBackground(Word... words) {
          mAsyncTaskDao.insert(words[0]);
          return null;
      }
  }
  private static class deleteAllWordsAsyncTask extends  AsyncTask<Void, Void, Void>{
   private WordDao mAsyncDao;

      private deleteAllWordsAsyncTask(WordDao asyncDao) {
          mAsyncDao = asyncDao;
      }

      @Override
      protected Void doInBackground(Void... voids) {
          mAsyncDao.deleteAll();

          return null;
      }
  }
  private static class deleteAsyncTask extends AsyncTask<Word, Void, Void>{

      private WordDao mDeleteAsyncDao;

      public deleteAsyncTask(WordDao deleteAsyncDao) {
          mDeleteAsyncDao = deleteAsyncDao;
      }

      @Override
      protected Void doInBackground(Word... words) {
          mDeleteAsyncDao.delete(words[0]);
          return null;
      }
  }
  private static class updateAsyncTask extends AsyncTask<Word, Void, Void>{

      private WordDao mUpdateDao;

      public updateAsyncTask(WordDao updateDao) {
          mUpdateDao = updateDao;
      }


      @Override
      protected Void doInBackground(Word... words) {
          mUpdateDao.update(words[0]);
          return null;
      }
  }
}
