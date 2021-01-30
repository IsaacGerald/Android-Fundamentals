package com.example.questionanswerapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_table")
public class QuestionAnswer {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "question")
    private String mQuestion;

    @ColumnInfo(name = "answer")
    private String mAnswer;


    @Ignore
    public QuestionAnswer(String question, String answer, int id) {
        mQuestion = question;
        mAnswer = answer;
        this.id = id;
    }

    public QuestionAnswer(String question, String answer) {
        mQuestion = question;
        mAnswer = answer;
    }

    @Ignore
    public QuestionAnswer(int id, @NonNull String question) {
        mQuestion = question;
        this.id = id;
    }
    @Ignore
    public QuestionAnswer(@NonNull String question) {
        mQuestion = question;
    }

    public String getQuestion() {
        return mQuestion;
    }
    public void setQuestion(@NonNull String question) {
        mQuestion = question;
    }

    public String getAnswer() {
        return mAnswer;
    }
    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
