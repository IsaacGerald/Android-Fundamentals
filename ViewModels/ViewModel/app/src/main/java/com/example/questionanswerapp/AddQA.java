package com.example.questionanswerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddQA extends AppCompatActivity {
    public static final String QUESTION_ONLY_REPLY = "com.example.questionanswerapp.QUESTION_ONLY_REPLY";
    public static final String QUESTION_REPLY = "com.example.questionanswerapp.QUESTION_REPLY";
    public static final String ANSWER_REPLY = "com.example.questionanswerapp.ANSWER_REPLY";
    public static final String EDITED_REPLY = "com.example.questionanswerapp.EDITED_REPLY";
    public static final String EDITED_ID = "com.example.questionanswerapp.EDITED_ID";
    private Button mAddQuestion;
    private Button mAddAll;
    private EditText mQuestion;
    private EditText mAnswer;
    private EditText mEditQuestion;
    private Button mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_q);

        mQuestion = findViewById(R.id.q_text);
        mAnswer = findViewById(R.id.ans_text);
        mAddQuestion = findViewById(R.id.add_question);
        mAddAll = findViewById(R.id.add_all);
        mEditQuestion = findViewById(R.id.q_editText);
        mSave = findViewById(R.id.save);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String question = extras.getString(MainActivity.QUESTION_REPLY);
            if (!question.isEmpty()){
                mEditQuestion.setText(question);
                mEditQuestion.requestFocus();
                mEditQuestion.setSelection(question.length());
            }
        }

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveIntent = new Intent(AddQA.this, MainActivity.class);
                if (TextUtils.isEmpty(mEditQuestion.getText())){
                    setResult(RESULT_CANCELED, saveIntent);
                }else {
                    String edited = mEditQuestion.getText().toString();
                    saveIntent.putExtra(EDITED_REPLY, edited);
                    if (extras != null && extras.containsKey(MainActivity.QUESTION_ID)){
                        int id = extras.getInt(MainActivity.QUESTION_ID, -1);
                        if (id != 1){
                            saveIntent.putExtra(EDITED_ID, id);
                        }
                    }
                    setResult(RESULT_OK, saveIntent);
                }
                finish();
            }
        });


        mAddQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent(AddQA.this, MainActivity.class);
                if (TextUtils.isEmpty(mQuestion.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }else {
                    String question = mQuestion.getText().toString();
                   replyIntent.putExtra(QUESTION_ONLY_REPLY, question);
                   setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        mAddAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQA.this, MainActivity.class);
                if (!TextUtils.isEmpty(mQuestion.getText())){
                    if (TextUtils.isEmpty(mAnswer.getText())){
                        setResult(RESULT_CANCELED, intent);
                    }else {
                       String quest = mQuestion.getText().toString();
                       String answer = mAnswer.getText().toString();
                        intent.putExtra(QUESTION_REPLY, quest);
                        intent.putExtra(ANSWER_REPLY, answer);
                        setResult(RESULT_OK, intent);
                    }
                    }
                else {
                    Toast.makeText(AddQA.this, "Enter a question!", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });

    }
}