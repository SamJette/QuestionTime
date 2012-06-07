package com.ehb.questiontime;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class SettingsTab extends Activity implements OnClickListener {

	private Button buttonServer;

	private Button buttonEditDataStudent;

	// private Button buttonSendDataStudent;

	private Button buttonAfmelden;

	public static final String PREFS_NAME = "LoginPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings_tab);

		buttonServer = (Button) findViewById(R.id.buttonServer);

		buttonEditDataStudent = (Button) findViewById(R.id.buttonGegevensWijzigen);

		// buttonSendDataStudent = (Button)
		// findViewById(R.id.buttonGegevensVerzenden);

		buttonAfmelden = (Button) findViewById(R.id.buttonAfmelden);

		buttonEditDataStudent.setOnClickListener(this);

		// buttonSendDataStudent.setOnClickListener(this);

		buttonServer.setOnClickListener(this);

		buttonAfmelden.setOnClickListener(this);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,

	Intent intent) {

		super.onActivityResult(requestCode, resultCode, intent);

	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.buttonServer:

			final Dialog dialog = new Dialog(getParent());

			dialog.setContentView(R.layout.server_dialog);

			dialog.setTitle("Serveradres instellen.");

			dialog.setCancelable(false);

			Button buttonCancel = (Button) dialog

			.findViewById(R.id.buttonCancel);

			buttonCancel.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					dialog.dismiss();

				}

			});

			Button buttonBewaren = (Button) dialog

			.findViewById(R.id.buttonBewaren);

			buttonBewaren.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					dialog.dismiss();

				}

			});

			dialog.show();

			break;

		/*
		 * case R.id.buttonLeerkracht:
		 * 
		 * final Dialog dialog = new Dialog(getParent());
		 * 
		 * 
		 * dialog.setContentView(R.layout.edit_login_teacher);
		 * 
		 * dialog.setTitle("U gegevens wijzigen: ");
		 * 
		 * dialog.setCancelable(false);
		 * 
		 * 
		 * Button buttonBewaren = (Button) dialog
		 * 
		 * .findViewById(R.id.buttonBewaren);
		 * 
		 * buttonBewaren.setOnClickListener(new OnClickListener() {
		 * 
		 * 
		 * public void onClick(View v) {
		 * 
		 * dialog.dismiss();
		 * 
		 * } });
		 * 
		 * 
		 * Button buttonCancel = (Button) dialog
		 * 
		 * .findViewById(R.id.buttonCancel);
		 * 
		 * buttonCancel.setOnClickListener(new OnClickListener() {
		 * 
		 * 
		 * public void onClick(View v) {
		 * 
		 * dialog.dismiss();
		 * 
		 * } });
		 * 
		 * 
		 * dialog.show();
		 * 
		 * 
		 * break;
		 */

		case R.id.buttonGegevensWijzigen:

			Log.d("demo", "onClickChangeDataStudents - buttonGegevensWijzigen");

			Intent intent = new Intent(getParent(), SettingsStudentEdit.class);

			TabGroupActivity parentactivity = (TabGroupActivity) getParent();

			parentactivity.startChildActivity("SettingsStudentEdit", intent);

			break;

		/*
		 * case R.id.buttonGegevensVerzenden:
		 * 
		 * Log.d("demo", "onClickChangeDataStudents - buttonGegevensVerzenden");
		 * 
		 * Intent i = new Intent(getParent(), SettingsStudentSend.class);
		 * 
		 * TabGroupActivity pactivity = (TabGroupActivity) getParent();
		 * 
		 * pactivity.startChildActivity("SettingsStudentSend", i);
		 */

		case R.id.buttonAfmelden:
			final Dialog dialog2 = new Dialog(getParent());

			dialog2.setContentView(R.layout.logout_dialog);
			dialog2.setTitle("Bent u zeker ? afmelden ? ");
			dialog2.setCancelable(false);

			Button buttonBewaren2 = (Button) dialog2
					.findViewById(R.id.buttonBewaren);
			buttonBewaren2.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Log.d("demo", "onClickChangeDataStudents - buttonAfmelden");

					SharedPreferences settings = getSharedPreferences(
							PREFS_NAME, 0);

					SharedPreferences.Editor editor = settings.edit();

					editor.remove("teacher[email]");

					editor.remove("teacher[password]");

					editor.remove("teacher[classid]");

					editor.commit();

					Logout();
					finish();

					dialog2.dismiss();

				}
			});

			Button buttonCancel2 = (Button) dialog2
					.findViewById(R.id.buttonCancel);
			buttonCancel2.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					dialog2.dismiss();

				}
			});

			dialog2.show();

		}

	}

	public void Logout() {

		RestClient.get("logout", null, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {

				Log.d("logout", "started");

			}

			public void onSuccess() {

				// go the resultspage

				Log.d("logout", "logout");

				finish();

			}

			public void onFailure() {

				// go the resultspage

				Log.d("logout", "failed");

				finish();

			}

		});

	}
}
