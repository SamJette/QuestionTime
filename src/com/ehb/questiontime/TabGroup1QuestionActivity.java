package com.ehb.questiontime;

import android.content.Intent;
import android.os.Bundle;

public class TabGroup1QuestionActivity extends TabGroupQuestionActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startChildActivity("QuestionTab", new Intent(this, QuestionTab.class));

	}

}