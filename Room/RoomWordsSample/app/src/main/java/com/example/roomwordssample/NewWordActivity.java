package com.example.roomwordssample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.roomwordssample.REPLY";
    public static final String EXTRA_REPLY_ID = "com.android.example.roomwordssample.REPLY_ID";
    private Button mSave;
    private EditText mEditText;
    private EditText mEditSavedText;
    private Button mSaveEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditText = findViewById(R.id.edit_word);
        mEditSavedText = findViewById(R.id.edit_saved_word);
        mSaveEdit = findViewById(R.id.button_edit);
        mSave = findViewById(R.id.button_save);

        int id = -1;
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditText.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }else {
                    String word = mEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String word = extras.getString(MainActivity.EXTRA_WORD_TO_BE_UPDATED, "");
            if (!word.isEmpty()){
                mEditSavedText.setText(word);
                mEditSavedText.setSelection(word.length());
                mEditSavedText.requestFocus();
            }
        }
        mSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditSavedText.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }else {
                    String word = mEditSavedText.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    if (extras != null && extras.containsKey(MainActivity.EXTRA_DATA_ID)){
                        int id = extras.getInt(MainActivity.EXTRA_DATA_ID, -1);
                        if (id != 1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }

}