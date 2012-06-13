package com.ehb.questiontime;

import android.content.Intent;
import android.os.Bundle;

public class TabGroup2SettingsActivity extends TabGroupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("SettingsTab", new Intent(this, SettingsTab.class));

	}

}
