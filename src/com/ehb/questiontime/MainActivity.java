package com.ehb.questiontime;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

/**
 * 
 * @author Patricia Meeremans, Kristof Polleunis, Anderson Muela, Collin
 *         Koolenbrander<br>
 * @version 1.0<br>
 *          04-juni-2012<br>
 */

public class MainActivity extends TabActivity {
	/** Called when the activity is first created. */

	/** variables for the shared prefs */
	static final String KEY_USERNAME = "username";
	static final String KEY_PASSWORD = "password";
	static final String KEY_CLASSID = "classId";

	public static final String PREFS_NAME = "LoginPrefs";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
		RestClient.client.setCookieStore(myCookieStore);

		/** action bar **/

		ActionBar actionBar = getActionBar();
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
		txtTab.setPadding(20,0,20,0);
		txtTab.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		txtTab.setBackgroundResource(R.drawable.list_tab_selector);
		txtTab.setTypeface(Typeface.DEFAULT_BOLD);
		txtTab.setTextSize(21);
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

	/** login **/

	@Override
	protected void onStart() {
		super.onStart();
		showAccessPopUpDialog();
	}

	private void showAccessPopUpDialog() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle(R.string._loginscreen_title);
		helpBuilder.setMessage(R.string._login_message);
		helpBuilder.setIcon(R.drawable.key_stroke_32x32);

		final TextView LabelName = new TextView(this);
		LabelName.setWidth(100);
		LabelName.setText(R.string._email);

		final EditText inputName = new EditText(this);
		inputName.setSingleLine();
		inputName.setWidth(200);
		inputName.setInputType(InputType.TYPE_CLASS_TEXT);

		String emailLogin = settings.getString("teacher[email]", null);
		if (emailLogin == null)
			inputName.setText("");
		else
			inputName.setText(emailLogin);

		final TextView LabelClass = new TextView(this);
		LabelClass.setWidth(100);
		LabelClass.setText(R.string._class);

		final EditText inputClass = new EditText(this);
		inputClass.setSingleLine();
		inputClass.setWidth(200);
		inputClass.setInputType(InputType.TYPE_CLASS_TEXT);

		String classId = settings.getString("teacher[classid]", null);
		if (classId == null)
			inputClass.setText("");
		else
			inputClass.setText(classId);

		final TextView LabelPass = new TextView(this);
		LabelPass.setWidth(100);
		LabelPass.setText(R.string._password);

		final EditText inputPass = new EditText(this);
		inputPass.setSingleLine();
		inputPass.setWidth(200);
		inputPass.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);

		String password = settings.getString("teacher[password]", null);
		if (password == null)
			inputPass.setText("");
		else
			inputPass.setText(password);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		layout.setPadding(10, 5, 0, 5);

		LinearLayout layoutUser = new LinearLayout(this);

		layoutUser.setLayoutParams(params);
		layoutUser.addView(LabelName);
		layoutUser.addView(inputName);

		LinearLayout layoutPass = new LinearLayout(this);

		layoutPass.setLayoutParams(params);
		layoutPass.addView(LabelPass);
		layoutPass.addView(inputPass);

		LinearLayout layoutClass = new LinearLayout(this);

		layoutClass.setLayoutParams(params);
		layoutClass.addView(LabelClass);
		layoutClass.addView(inputClass);

		layout.addView(layoutUser);
		layout.addView(layoutPass);
		layout.addView(layoutClass);

		helpBuilder.setView(layout);

		helpBuilder.setPositiveButton(R.string._login,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						/** Do nothing but close the dialog */
						String inputUserTxt = inputName.getText().toString();
						String inputPasswordTxt = inputPass.getText()
								.toString();
						String inputClassTxt = inputClass.getText().toString();

						RequestParams params = new RequestParams();
						params.put("teacher[email]", inputUserTxt);
						params.put("teacher[password]", inputPasswordTxt);
						params.put("teacher[classid]", inputClassTxt);

						SharedPreferences settings = getSharedPreferences(
								PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("teacher[email]", inputUserTxt);
						editor.putString("teacher[password]", inputPasswordTxt);
						editor.putString("teacher[classid]", inputClassTxt);

						editor.commit();

						try {
							login(params);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		helpBuilder.setNegativeButton(R.string._cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Do nothing
						finish();
					}
				});

		AlertDialog helpDialog = helpBuilder.create();

		helpDialog.setCanceledOnTouchOutside(false);

		helpDialog.show();

		helpDialog.getWindow().setLayout(450, 430);
		/**
		 * Controlling width and height
		 */
	}

	public void login(RequestParams params) throws JSONException {

		RestClient.post("", params, new JsonHttpResponseHandler() {
			private ProgressDialog dialog;
			private String _id;
			private String _classid;
			private String _email;
			private String _allowed = null;

			@Override
			public void onStart() {
				dialog = ProgressDialog.show(MainActivity.this, "Loading",
						"Data Loading", true, true, new OnCancelListener() {
							public void onCancel(DialogInterface dialog) {
								dialog.dismiss();
							}
						});
			}

			@Override
			public void onSuccess(JSONObject response) {
				if (this.dialog.isShowing())
					this.dialog.dismiss();

				try {
					_id = response.getString("id");
					_classid = response.getString("classid");
					_email = response.getString("email");
					_allowed = response.getString("allowed");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(_id);
				System.out.println(_classid);
				System.out.println(_email);
				if (_allowed.equals("No")) {
					onFailure();
				} else {
					System.out.println("ok");
				}

			}

			public void onFailure() {
				Toast toast = Toast.makeText(getBaseContext(), R.string._error,
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();

				showAccessPopUpDialog();

			}
		});

	}

}