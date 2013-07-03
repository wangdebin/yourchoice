package com.android.dice;

import com.android.dice.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.nduoa.synsdk.*;

public class DiceCloud extends Activity implements View.OnClickListener{
	/** Called when the activity is first created. */

	private Button doSth_button;
	private TextView view_result;
	private TextView view_affairs_cooking,
					 view_affairs_basketball,
					 view_affairs_washing,
					 view_affairs_cleaning,
					 view_affairs_sleeping,
					 view_affairs_shopping,
					 view_affairs_noting,
					 view_affairs_gaming,
					 view_affairs_watching_movie,
					 view_affairs_walking,
					 view_affairs_K,
					 view_affairs_surfing,
					 view_affairs_coding,
					 view_affairs_anthing,
					 view_affairs_chasing_after_girls,
					 view_affairs_climbing;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
		setListener();
		setClickListenr(view_affairs_cooking);
		setClickListenr(view_affairs_basketball);
		setClickListenr(view_affairs_washing);
		setClickListenr(view_affairs_cleaning);
		setClickListenr(view_affairs_sleeping);
		setClickListenr(view_affairs_shopping);
		setClickListenr(view_affairs_noting);
		setClickListenr(view_affairs_gaming);
		setClickListenr(view_affairs_watching_movie);
		setClickListenr(view_affairs_walking);
		setClickListenr(view_affairs_K);
		setClickListenr(view_affairs_surfing);
		setClickListenr(view_affairs_coding);
		setClickListenr(view_affairs_anthing);
		setClickListenr(view_affairs_chasing_after_girls);
		setClickListenr(view_affairs_climbing);
		//SynPayment.commit(getBaseContext(), 100, "rmb", System.currentTimeMillis(), "ndoo");
	}

	private void findViews() {
		doSth_button = (Button) findViewById(R.id.submit);
		view_result = (TextView) findViewById(R.id.result);
		view_affairs_cooking = (TextView) findViewById(R.id.affairs_cooking);
		view_affairs_basketball = (TextView) findViewById(R.id.affairs_basketball);
		view_affairs_washing = (TextView) findViewById(R.id.affairs_washing);
		view_affairs_cleaning = (TextView) findViewById(R.id.affairs_cleaning);
		view_affairs_sleeping = (TextView) findViewById(R.id.affairs_sleeping);
		view_affairs_shopping = (TextView) findViewById(R.id.affairs_shopping);
		view_affairs_noting = (TextView) findViewById(R.id.affairs_noting);
		view_affairs_gaming = (TextView) findViewById(R.id.affairs_gaming);
		view_affairs_watching_movie = (TextView) findViewById(R.id.affairs_watching_movie);
		view_affairs_walking = (TextView) findViewById(R.id.affairs_walking);
		view_affairs_K = (TextView) findViewById(R.id.affairs_K);
		view_affairs_surfing = (TextView) findViewById(R.id.affairs_surfing);
		view_affairs_coding = (TextView) findViewById(R.id.affairs_coding);
		view_affairs_anthing = (TextView) findViewById(R.id.affairs_anthing);
		view_affairs_chasing_after_girls = (TextView) findViewById(R.id.affairs_chasing_after_girls);
		view_affairs_climbing = (TextView) findViewById(R.id.affairs_climbing);
		
	}

	private void setListener() {
		doSth_button.setOnClickListener(doSth);
	}
	
	private void setClickListenr(TextView tv){
		tv.setOnClickListener(this);
	}
	
    public void onClick(View v) {
        Animation shake = AnimationUtils.loadAnimation(this, R.layout.shake);
        if(view_affairs_cooking.isPressed()){
        	findViewById(R.id.affairs_cooking).startAnimation(shake);
        }
        if(view_affairs_basketball.isPressed()){
        	findViewById(R.id.affairs_basketball).startAnimation(shake);
        }
        if(view_affairs_washing.isPressed()){
        	findViewById(R.id.affairs_washing).startAnimation(shake);
        }
        if(view_affairs_cleaning.isPressed()){
        	findViewById(R.id.affairs_cleaning).startAnimation(shake);
        }
        if(view_affairs_sleeping.isPressed()){
        	findViewById(R.id.affairs_sleeping).startAnimation(shake);
        }
        if(view_affairs_shopping.isPressed()){
        	findViewById(R.id.affairs_shopping).startAnimation(shake);
        }
        if(view_affairs_noting.isPressed()){
        	findViewById(R.id.affairs_noting).startAnimation(shake);
        }
        if(view_affairs_gaming.isPressed()){
        	findViewById(R.id.affairs_gaming).startAnimation(shake);
        }
        if(view_affairs_watching_movie.isPressed()){
        	findViewById(R.id.affairs_watching_movie).startAnimation(shake);
        }
        if(view_affairs_walking.isPressed()){
        	findViewById(R.id.affairs_walking).startAnimation(shake);
        }
        if(view_affairs_K.isPressed()){
        	findViewById(R.id.affairs_K).startAnimation(shake);
        }
        if(view_affairs_surfing.isPressed()){
        	findViewById(R.id.affairs_surfing).startAnimation(shake);
        }
        if(view_affairs_coding.isPressed()){
        	findViewById(R.id.affairs_coding).startAnimation(shake);
        }
        if(view_affairs_anthing.isPressed()){
        	findViewById(R.id.affairs_anthing).startAnimation(shake);
        }
        if(view_affairs_chasing_after_girls.isPressed()){
        	findViewById(R.id.affairs_chasing_after_girls).startAnimation(shake);
        }
        if(view_affairs_climbing.isPressed()){
        	findViewById(R.id.affairs_climbing).startAnimation(shake);
        }
        
    }
    
	private OnClickListener doSth = new OnClickListener() {
		public void onClick(View v) {
			try {
				char readomLetter = (char) (Math.random() * 26 + 'a');
				String tempString = String.valueOf(readomLetter);
				if (tempString.equals("a")) {
					view_result.setText(R.string.affairs_cooking);
				}
				if (tempString.equals("b")) {
					view_result.setText(R.string.affairs_washing);
				}
				if (tempString.equals("c")) {
					view_result.setText(R.string.affairs_cleaning);
				}
				if (tempString.equals("d")) {
					view_result.setText(R.string.affairs_sleeping);
				}
				if (tempString.equals("e")) {
					view_result.setText(R.string.affairs_cooking);
				}
				if (tempString.equals("f")) {
					view_result.setText(R.string.affairs_shopping);
				}
				if (tempString.equals("g")) {
					view_result.setText(R.string.affairs_noting);
				}
				if (tempString.equals("h")) {
					view_result.setText(R.string.affairs_gaming);
				}
				if (tempString.equals("i")) {
					view_result.setText(R.string.affairs_watching_movie);
				}
				if (tempString.equals("j")) {
					view_result.setText(R.string.affairs_walking);
				}
				if (tempString.equals("k")) {
					view_result.setText(R.string.affairs_basketball);
				}
				if (tempString.equals("l")) {
					view_result.setText(R.string.affairs_K);
				}
				if (tempString.equals("m")) {
					view_result.setText(R.string.affairs_surfing);
				}
				if (tempString.equals("n")) {
					view_result.setText(R.string.affairs_coding);
				}
				if (tempString.equals("o")) {
					view_result.setText(R.string.affairs_anthing);
				}
				if (tempString.equals("p")) {
					view_result.setText(R.string.affairs_chasing_after_girls);
				}
				if (tempString.equals("q")) {
					view_result.setText(R.string.affairs_climbing);
				}
				if (tempString.equals("r")) {
					view_result.setText(R.string.affairs_coding);
				}
				if (tempString.equals("s")) {
					view_result.setText(R.string.affairs_anthing);
				}
			} catch (Exception e) {

			}
		}
	};
}