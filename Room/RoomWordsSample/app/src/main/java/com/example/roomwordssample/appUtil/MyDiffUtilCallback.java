package com.example.roomwordssample.appUtil;

import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.roomwordssample.Word;

import java.util.ArrayList;
import java.util.List;

public class MyDiffUtilCallback extends DiffUtil.Callback {

    private List<Word> oldWords = new ArrayList<>();
    private List<Word> newWords = new ArrayList<>();

    public MyDiffUtilCallback(List<Word> oldWords, List<Word> newWords) {
        this.oldWords = oldWords;
        this.newWords = newWords;
    }

    @Override
    public int getOldListSize() {
        return oldWords != null ? oldWords.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newWords != null ? newWords.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldWords.get(oldItemPosition).getId() == newWords.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (newWords.get(newItemPosition).equals(oldWords.get(oldItemPosition))){
            return true;
        }else {
            return false;
        }

    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Word newWord = newWords.get(newItemPosition);
        Word oldWord = oldWords.get(oldItemPosition);
        Bundle bundle = new Bundle();

        if (!newWord.getWord().equals(oldWord.getWord())){
            bundle.putString("word", newWord.getWord());
        }

        if (bundle.size() == 0){
            return null;
        }else {
            return bundle;
        }

    }
}
