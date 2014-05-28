package com.depalma.whoswhere.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.depalma.whoswhere.Communicator;
import com.depalma.whoswhere.R;
import com.depalma.whoswhere.R.array;
import com.depalma.whoswhere.R.id;
import com.depalma.whoswhere.R.layout;
import com.depalma.whoswhere.shared.Gender;
import com.depalma.whoswhere.shared.messages.JoinMessage;

public class LoginActivity extends Activity {

	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_page);
		genderSpinner();
		context = this;
	}

	public void genderSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.genderSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.gender, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}
	public void joinClick(View view) 
	{
	    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
	    //JoinMessage join = new JoinMessage("name", Gender.Male, 22);
	    //Communicator.getCommunicator().queueMessage(join);
	    startActivity(intent);
	}

}
