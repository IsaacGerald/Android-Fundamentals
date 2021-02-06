package com.sriyanksiddhartha.stylesandthemesdemo;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


/** 
 * Author : Sriyank Siddhartha 
 *
 * Module 2 : Styling Views
 *  
 * 		"BEFORE" demo project
 **/
public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        //Linear gradient
		TextView textView = (TextView)findViewById(R.id.txvGradient);
		LinearGradient linearGradient = new LinearGradient(0, 0,
				0, textView.getTextSize(),  Color.GREEN, Color.RED, Shader.TileMode.MIRROR);
		textView.getPaint().setShader(linearGradient);
	}
}
