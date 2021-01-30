package com.example.questionanswerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int NEW_QUESTION_REQUEST_CODE = 1;
    public static final int UPDATE_QUESTION_RESULT_CODE = 2;
    public static final String QUESTION_REPLY = "com.example.questionanswerapp.QUESTION_REPLY";
    public static final String QUESTION_ID = "com.example.questionanswerapp.QUESTION_ID";
    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private QuestionAnswerViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = findViewById(R.id.tv_question);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AddQA.class);
                startActivityForResult(intent, NEW_QUESTION_REQUEST_CODE);
            }
        });

        mRecyclerView = findViewById(R.id.rv_layout);
        QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(QuestionAnswerViewModel.class);
        Log.d(TAG, "onCreate: " + mViewModel.getAllWords());
        mViewModel.getAllWords().observe(this, new Observer<List<QuestionAnswer>>() {
            @Override
            public void onChanged(List<QuestionAnswer> questionAnswers) {
                 adapter.setWords(questionAnswers);
            }
        });

        adapter.setOnclickListener(new QuestionAnswerAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                QuestionAnswer questionAnswer = adapter.getQuestionAtPosition(position);
                launchUpdateQuestion(questionAnswer);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                       int position = viewHolder.getAdapterPosition();
                       QuestionAnswer questionAnswer = adapter.getQuestionAtPosition(position);

                        Toast.makeText(MainActivity.this, "Deleting " + questionAnswer.getQuestion(), Toast.LENGTH_SHORT).show();
                        mViewModel.delete(questionAnswer);
                    }

                }
        );

        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    private void launchUpdateQuestion(QuestionAnswer question) {
        Intent updateIntent = new Intent(MainActivity.this, AddQA.class);
        updateIntent.putExtra(QUESTION_REPLY, question.getQuestion());
        updateIntent.putExtra(QUESTION_ID, question.getId());
        startActivityForResult(updateIntent, UPDATE_QUESTION_RESULT_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_QUESTION_REQUEST_CODE && resultCode == RESULT_OK){
           if (data.hasExtra(AddQA.QUESTION_ONLY_REPLY)){
               QuestionAnswer question = new QuestionAnswer(data.getStringExtra(AddQA.QUESTION_ONLY_REPLY));
               mViewModel.insert(question);
           }else {
               QuestionAnswer questionAnswer = new QuestionAnswer(data.getStringExtra(AddQA.QUESTION_REPLY), data.getStringExtra(AddQA.ANSWER_REPLY));
               mViewModel.insert(questionAnswer);
           }
        } else if(requestCode == UPDATE_QUESTION_RESULT_CODE && resultCode == RESULT_OK){
            String editedText = data.getStringExtra(AddQA.EDITED_REPLY);
            int id = data.getIntExtra(AddQA.EDITED_ID, -1);
            if (id != -1){
                QuestionAnswer questionAnswer = new QuestionAnswer(id, editedText);
                mViewModel.update(questionAnswer);

            }}
        else {
            Toast.makeText(this, "Question not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}