package com.example.questionanswerapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionADao {

    @Insert
    void insert(QuestionAnswer... question);

    @Delete
    void delete(QuestionAnswer... questionAnswer);

    @Update
    void update(QuestionAnswer... questionAnswer);

    @Query("SELECT * FROM question_table ORDER BY question ASC")
    LiveData<List<QuestionAnswer>> getAllQuestions();

    @Query("SELECT * FROM question_table LIMIT 1")
    QuestionAnswer[] getAnyQuestions();

    @Query("DELETE FROM question_table")
    void deleteAll();
}
