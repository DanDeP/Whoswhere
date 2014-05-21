package com.depalma.whoswhere;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class DeRegisterActivity extends Activity {
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deregister_page);
		context = this;
	}
}
