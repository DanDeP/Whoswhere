package com.depalma.whoswhere;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

public class AboutUsActivity extends Activity {
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus_page);
		context = this;
	}
}
