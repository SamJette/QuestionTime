package com.ehb.questiontime;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class SettingsStudentEdit extends Activity {

	/** dialog onItemClick **/
	static final private int STUDENT_DIALOG = 1;

	/** other variables **/
	public Student aStudent;
	private ListView myListView;
	public ArrayList<Student> students;

	/** keys **/
	static final String KEY_ROW = "row"; // parent node
	static final String KEY_DATA = "data";
	static final String KEY_ID = "ID";
	static final String KEY_FIRSTNAME = "FIRSTNAME";
	static final String KEY_NAME = "NAME";
	static final String KEY_EMAIL = "EMAIL";
	static final String KEY_NUMBER = "NUMBER";
	static final String KEY_ISONLINE = "ISONLINE";
	static final String KEY_PASSWORD = "PASSWORD";

	Button okButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_student_edit);

		okButton = (Button) findViewById(R.id.buttonDone);

		studentListing();

	}

	public void studentListing() {
		RestClient.get("students?format=xml", null,
				new AsyncHttpResponseHandler() {
					private ProgressDialog dialog;

					@Override
					public void onStart() {
						dialog = ProgressDialog.show(getParent(), "Loading",
								"Data loading", true, true,
								new OnCancelListener() {
									public void onCancel(DialogInterface dialog) {
										dialog.dismiss();
									}
								});
					}

					@Override
					public void onSuccess(String response) {
						if (this.dialog.isShowing())
							this.dialog.dismiss();

						ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

						// create an instance of our parser
						StudentParser parser = new StudentParser();

						SAXParserFactory factory = SAXParserFactory
								.newInstance();
						SAXParser sp = null;
						try {
							sp = factory.newSAXParser();
						} catch (ParserConfigurationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SAXException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						XMLReader reader = null;
						try {
							reader = sp.getXMLReader();
						} catch (SAXException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						// add our own parser to the xml reader and start
						// parsing
						// the
						// file
						reader.setContentHandler(parser);
						try {
							Xml.parse(response, parser);
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						students = parser.students;
						// Log.d("demo", "Students in the getStudents()= " +
						// students);

						for (int i = 0; i < students.size(); i++) {
							Student temp = students.get(i);

							HashMap<String, Object> map = new HashMap<String, Object>();

							map.put(KEY_FIRSTNAME, temp.firstName);
							map.put(KEY_NAME, temp.name);
							map.put(KEY_EMAIL, temp.email);

							listItem.add(map);
						}

						myListView = (ListView) findViewById(R.id.listViewSettingsLeerlingen);

						SimpleAdapter adapter = new SimpleAdapter(
								SettingsStudentEdit.this, listItem,
								R.layout.list_item_student_settings,
								new String[] { KEY_FIRSTNAME, KEY_NAME },
								new int[] { R.id.firstNameText,
										R.id.lastNameText });
						myListView.setAdapter(adapter);

						myListView
								.setOnItemClickListener(new OnItemClickListener() {

									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// Student temp = students.get(arg2);

										aStudent = students.get(arg2);
										showDialog(STUDENT_DIALOG);

									}
								});

						// System.out.println(response);
					}
				});
	}

	/** dialog onItemClick **/

	@Override
	public Dialog onCreateDialog(int id) {
		switch (id) {
		case (STUDENT_DIALOG):
			LayoutInflater li = LayoutInflater.from(this);
			View quakeDetailsView = li.inflate(
					R.layout.student_dialog_settings, null);

			AlertDialog.Builder quakeDialog = new AlertDialog.Builder(
					getParent());
			quakeDialog.setTitle("Student Time");
			quakeDialog.setView(quakeDetailsView);

			quakeDialog.setCancelable(false);

			return quakeDialog.create();
		}
		return null;
	}

	@Override
	public void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case (STUDENT_DIALOG):

			final AlertDialog studentDialog = (AlertDialog) dialog;
			studentDialog.setTitle(aStudent.firstName + "  " + aStudent.name);

			TextView mailTv = (TextView) studentDialog
					.findViewById(R.id.studentEmail);
			mailTv.setText("Email: " + aStudent.email);

			EditText editPass = (EditText) studentDialog
					.findViewById(R.id.studentPassword);
			editPass.setHint("" + aStudent.password);

			Button buttonCancel = (Button) studentDialog
					.findViewById(R.id.buttonCancel);

			buttonCancel.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					studentDialog.dismiss();

				}

			});

			Button buttonBewaren = (Button) dialog

			.findViewById(R.id.buttonBewaren);

			buttonBewaren.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					studentDialog.dismiss();

				}

			});

			break;
		}
	}

	public void doneEditStudentList(View v) {
		/** return to SettingsTab view **/
		Intent intent = new Intent(getParent(), SettingsTab.class);
		TabGroupActivity parentactivity = (TabGroupActivity) getParent();
		parentactivity.startChildActivity("SettingsTab", intent);

	}

}
