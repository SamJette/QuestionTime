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
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class StudentTab extends Activity {

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
	static final String KEY_IMAGE_ISONLINE = "IMAGE_ISONLINE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.ehb.questiontime.R.layout.student_tab);

		studentListing();

	}

	/** method to refresh the student list via the menu **/
	public void refreshStudentList() {

		studentListing();

	}

	/** method to refresh the student list via the button **/

	public void refreshStudentList(View v) {

		studentListing();

	}

	public void studentListing() {
		RestClient.get("students.xml", null, new AsyncHttpResponseHandler() {
			private ProgressDialog dialog;

			@Override
			public void onStart() {
				dialog = ProgressDialog.show(StudentTab.this, "Loading",
						"Data loading", true, true, new OnCancelListener() {
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

				SAXParserFactory factory = SAXParserFactory.newInstance();
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

				// add our own parser to the xml reader and start parsing
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
					map.put(KEY_ISONLINE, temp.isOnLine);

					// change image if student is online or not
					Log.d("demo", "is on line= " + temp.isOnLine);
					if (temp.isOnLine.equalsIgnoreCase("1")) {
						map.put(KEY_IMAGE_ISONLINE,
								com.ehb.questiontime.R.drawable.online);
					} else {
						map.put(KEY_IMAGE_ISONLINE,
								com.ehb.questiontime.R.drawable.offline);
					}

					listItem.add(map);
				}

				myListView = (ListView) findViewById(com.ehb.questiontime.R.id.listViewTabLeerlingen);

				SimpleAdapter adapter = new SimpleAdapter(StudentTab.this,
						listItem,
						com.ehb.questiontime.R.layout.list_item_student,
						new String[] { KEY_FIRSTNAME, KEY_NAME,
								KEY_IMAGE_ISONLINE }, new int[] {
								com.ehb.questiontime.R.id.firstNameTextView,
								com.ehb.questiontime.R.id.lastNameTextView,
								com.ehb.questiontime.R.id.logo });
				myListView.setAdapter(adapter);

				myListView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// Student temp = students.get(arg2);

						aStudent = students.get(arg2);
						showDialog(STUDENT_DIALOG);

					}
				});

				System.out.println(response);
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
					com.ehb.questiontime.R.layout.student_detail, null);

			AlertDialog.Builder quakeDialog = new AlertDialog.Builder(this);
			quakeDialog.setTitle("Student Time");
			quakeDialog.setView(quakeDetailsView);
			return quakeDialog.create();
		}
		return null;
	}

	@Override
	public void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case (STUDENT_DIALOG):
			String studentText = "Email: " + aStudent.email + "\n"
					+ "Password: " + aStudent.password + "\n" + "IsOnLine: "
					+ aStudent.isOnLine;

			AlertDialog studentDialog = (AlertDialog) dialog;
			studentDialog.setTitle(aStudent.name);
			TextView tv = (TextView) studentDialog
					.findViewById(com.ehb.questiontime.R.id.studentDetailsTextViewInDialog);
			tv.setText(studentText);

			break;
		}
	}

}
