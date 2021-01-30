package com.example.roomwordssample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordNewListAdapter extends ListAdapter<Word, WordNewListAdapter.WordListViewHolder> {
    List<Word> mWords;

    private  WordClickInterface mClickInterface;
    protected WordNewListAdapter(@NonNull DiffUtil.ItemCallback<Word> diffCallback, WordClickInterface mWordClickInterface) {
        super(diffCallback);
        this.mClickInterface = mWordClickInterface;
    }

    @NonNull
    @Override
    public WordListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false), mClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListViewHolder holder, int position) {
      Word word = getItem(position);
      holder.bind(word);
    }

    public class WordListViewHolder extends RecyclerView.ViewHolder {

        private TextView wordItemTextView;
        private WordClickInterface mWordClickInterface;

        public WordListViewHolder(@NonNull View itemView, WordClickInterface wordClickInterface) {
            super(itemView);
            this.mWordClickInterface = wordClickInterface;
            wordItemTextView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickInterface.onClick(getAdapterPosition());
                }
            });
        }

        private void bind(Word word){
            wordItemTextView.setText(word.getWord());
        }
    }
    interface WordClickInterface {
         void onClick(int position);
    }
}
