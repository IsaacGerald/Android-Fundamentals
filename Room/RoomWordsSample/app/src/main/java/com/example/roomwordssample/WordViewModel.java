package com.example.roomwordssample;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private static final String TAG = "WordViewModel";
    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;


    public WordViewModel(@NonNull Application application, WordRepository repository) {
        super(application);
        mRepository = repository;
    }



    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();

    }

    public LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }
    public void insert(Word word){
        mRepository.insert(word);
    }
    public void deleteAll(){
        mRepository.deleteAll();
    }
    public void delete(Word word){
        mRepository.delete(word);
    }
    public void update(Word word){
        mRepository.update(word);
    }


}
