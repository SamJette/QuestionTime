package com.ehb.questiontime;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/** action bar **/
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Question Time");
		actionBar.hide();

		/** tabs **/

		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		intent = new Intent().setClass(this, StudentTab.class);
		spec = tabHost.newTabSpec("Leerlingen")
				.setIndicator(myTabTextView("Leerlingen")).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, TabGroup1QuestionActivity.class);
		spec = tabHost.newTabSpec("Vragen")
				.setIndicator(myTabTextView("Vragen")).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, ResultTab.class);
		spec = tabHost.newTabSpec("Resultaten")
				.setIndicator(myTabTextView("Resultaten")).setContent(intent);

		tabHost.addTab(spec);

		intent = new Intent().setClass(this, TabGroup2SettingsActivity.class);
		spec = tabHost.newTabSpec("Instellingen")
				.setIndicator(myTabTextView("Instellingen")).setContent(intent);
		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);

	}

	/** textView in the tabs **/

	public TextView myTabTextView(String myTabTitle) {
		TextView txtTab = new TextView(this);
		txtTab.setTextColor(Color.WHITE);
		txtTab.setPadding(8, 9, 8, 9);
		txtTab.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		txtTab.setBackgroundResource(R.drawable.list_tab_selector);
		txtTab.setTypeface(Typeface.DEFAULT_BOLD);
		txtTab.setTextSize(22);
		txtTab.setShadowLayer(1, 1, 1, Color.DKGRAY);
		txtTab.setText(myTabTitle);
		return txtTab;

	}

	/** menu **/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, Menu.NONE, "Refresh");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Activity myCurrentActivity = this.getCurrentActivity();
			if (StudentTab.class.isInstance(myCurrentActivity) == true) {
				((StudentTab) myCurrentActivity).refreshStudentList();
			}
			return true;
		}
		return false;
	}

}