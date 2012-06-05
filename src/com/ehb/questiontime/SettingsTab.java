package com.ehb.questiontime;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SettingsTab extends Activity implements OnClickListener {

	private Button buttonTeachers;
	private Button buttonEditDataStudent;
	private Button buttonSendDataStudent;
	private Button buttonAfmelden;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_tab);

		buttonTeachers = (Button) findViewById(R.id.buttonLeerkracht);
		buttonEditDataStudent = (Button) findViewById(R.id.buttonGegevensWijzigen);
		buttonSendDataStudent = (Button) findViewById(R.id.buttonGegevensVerzenden);
		buttonAfmelden = (Button) findViewById(R.id.buttonAfmelden);

		buttonEditDataStudent.setOnClickListener(this);
		buttonSendDataStudent.setOnClickListener(this);
		buttonTeachers.setOnClickListener(this);
		buttonAfmelden.setOnClickListener(this);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonLeerkracht:
			final Dialog dialog = new Dialog(getParent());

			dialog.setContentView(R.layout.edit_login_teacher);
			dialog.setTitle("U gegevens wijzigen: ");
			dialog.setCancelable(false);

			Button buttonBewaren = (Button) dialog
					.findViewById(R.id.buttonBewaren);
			buttonBewaren.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			Button buttonCancel = (Button) dialog
					.findViewById(R.id.buttonCancel);
			buttonCancel.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			dialog.show();

			break;
		case R.id.buttonGegevensWijzigen:
			Log.d("demo", "onClickChangeDataStudents - buttonGegevensWijzigen");
			Intent intent = new Intent(getParent(), SettingsStudentEdit.class);
			TabGroupActivity parentactivity = (TabGroupActivity) getParent();
			parentactivity.startChildActivity("SettingsStudentEdit", intent);

			break;

		case R.id.buttonGegevensVerzenden:
			Log.d("demo", "onClickChangeDataStudents - buttonGegevensVerzenden");
			Intent i = new Intent(getParent(), SettingsStudentSend.class);
			TabGroupActivity pactivity = (TabGroupActivity) getParent();
			pactivity.startChildActivity("SettingsStudentSend", i);

		case R.id.buttonAfmelden:
			Toast.makeText(this, "Afmelden", Toast.LENGTH_LONG);

		}
	}
}
