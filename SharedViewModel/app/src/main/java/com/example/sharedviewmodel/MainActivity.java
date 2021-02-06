package com.example.sharedviewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sharedviewmodel.Fragments.FragmentA;
import com.example.sharedviewmodel.Fragments.FragmentB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, new FragmentA())
                .replace(R.id.container_b, new FragmentB())
                .commit();

    }



}