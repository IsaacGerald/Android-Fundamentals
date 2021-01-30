package com.example.questionanswerapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.QuestionsViewHolder>{
    private static final String TAG = "QuestionAnswerAdapter";
    private static ClickListener clickListener;
    QuestionAnswerAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }
    LayoutInflater mInflater;
private List<QuestionAnswer> mQuestionAnswers;
    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View item = mInflater.inflate(R.layout.list_item, parent, false);
        return new QuestionsViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
      if (mQuestionAnswers != null){
          QuestionAnswer current = mQuestionAnswers.get(position);
          holder.mQuestionText.setText(current.getQuestion());
          holder.mEditText.setText(current.getAnswer());
          Log.d(TAG, "onBindViewHolder: " + current.getQuestion());
      }else {
          holder.mQuestionText.setText(R.string.no_questions);
      }
    }

    @Override
    public int getItemCount() {
        if (mQuestionAnswers != null){
           return mQuestionAnswers.size();
        }else return 0;
    }

    class QuestionsViewHolder extends RecyclerView.ViewHolder{
        private TextView mQuestionText;
        private EditText mEditText;

         public QuestionsViewHolder(@NonNull View itemView) {
             super(itemView);
             mQuestionText = itemView.findViewById(R.id.tv_question);
             mEditText = itemView.findViewById(R.id.et_answer);
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     clickListener.onItemClick(v, getAdapterPosition());
                 }
             });
         }
     }
     void setWords(List<QuestionAnswer> questionAnswers){
        mQuestionAnswers = questionAnswers;
         Log.d(TAG, "setWords: " + mQuestionAnswers);
         notifyDataSetChanged();
     }

     public QuestionAnswer getQuestionAtPosition(int position){
        return mQuestionAnswers.get(position);
    }
     public void setOnclickListener(ClickListener clickListener){
        QuestionAnswerAdapter.clickListener = clickListener;
     }
     public interface ClickListener{
        void onItemClick(View v, int position);
     }
}
