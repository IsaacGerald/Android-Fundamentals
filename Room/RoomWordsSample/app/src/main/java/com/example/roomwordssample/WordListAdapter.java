package com.example.roomwordssample;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomwordssample.appUtil.MyDiffUtilCallback;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private static final String TAG = "WordListAdapter";
    private final LayoutInflater mInflater;
    private List<Word> mWords;
    private List<Word> newWords;
    private static ClickListener clickListener;


    public WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        if (mWords != null){
            Word current = mWords.get(position);
            holder.wordItemTextView.setText(current.getWord());
        }else {
            holder.wordItemTextView.setText("No Word");
        }

    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position, @NonNull List<Object> payloads) {

        if (!payloads.isEmpty()){
            Bundle bundle = (Bundle)payloads.get(0);
            for (String key : bundle.keySet()){
                if (key.equals("name")){
                    holder.wordItemTextView.setText(bundle.getString("name"));
                }
            }
        }else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    void setWords(List<Word> words){
        mWords = words;
        Log.d(TAG, "setWords: " + mWords);

    }
    public Word getWordAtPosition(int position){
        return mWords.get(position);
    }

    @Override
    public int getItemCount() {
        if (mWords != null){
            return mWords.size();
        }else {
            return 0;
        }
    }

    public void updateWords (List<Word> newWords){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffUtilCallback(mWords, newWords ));
        if (mWords != null){
            mWords.clear();
            mWords.addAll(newWords);
            diffResult.dispatchUpdatesTo(this);
        }




    }


    class WordViewHolder extends RecyclerView.ViewHolder{
        private final TextView wordItemTextView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItemTextView = itemView.findViewById(R.id.textView);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  clickListener.onItemClick(v, getAdapterPosition());
               }
           });
        }

    }
    public void setOnItemClickListener(ClickListener clickListener){
        WordListAdapter.clickListener = clickListener;

    }
   public interface ClickListener{
        void onItemClick(View v, int position);
   }
}
