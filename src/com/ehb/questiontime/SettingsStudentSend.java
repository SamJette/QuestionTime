package com.ehb.questiontime;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SettingsStudentSend extends Activity {
	Button okButton;
	private ListView m_listview;
	public ArrayList<Student> students = new ArrayList<Student>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_student_send);

		okButton = (Button) findViewById(R.id.buttonSettingsSendDone);

		Student aStudent1 = new Student();
		aStudent1.firstName = "Janssens";
		aStudent1.name = "Jan";

		Student aStudent2 = new Student();
		aStudent2.firstName = "Peeters";
		aStudent2.name = "Bart";

		students.add(aStudent1);
		students.add(aStudent2);

		m_listview = (ListView) findViewById(R.id.listViewSettingsSendDataStudenten);
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < students.size(); i++) {
			Student temp = students.get(i);

			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("firstname", temp.firstName);
			map.put("name", temp.name);
			listItem.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(SettingsStudentSend.this,
				listItem, R.layout.list_item_settings_student_send,
				new String[] { "firstname", "name" }, new int[] {
						R.id.textViewFirstNameSend, R.id.textViewNameSend });

		m_listview.setAdapter(adapter);

	}

	public void doneSendStudentList(View v) {
		/** return to SettingsTab view **/
		Intent intent = new Intent(getParent(), SettingsTab.class);
		TabGroupActivity parentactivity = (TabGroupActivity) getParent();
		parentactivity.startChildActivity("SettingsTab", intent);

	}

	public void sendStudentList(View v) {

		Toast.makeText(this, "send data student", Toast.LENGTH_LONG);

	}

}
