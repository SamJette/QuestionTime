package com.ehb.questiontime;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class QuestionTab extends Activity implements OnItemClickListener {
	// XML node keys
	static final String KEY_ROW = "row"; // parent node
	static final String KEY_DATA = "data";
	static final String KEY_ID = "ID";
	static final String KEY_VRAGENTEXT = "VRAGENTEXT";
	static final String KEY_ANTWOORDTEXT = "ANTWOORDTEXT";

	public String questionText;

	private ListView m_listview;

	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int PUSH_ID = Menu.FIRST + 2;
	private static final int COPY_ID = Menu.FIRST + 3;

	public Question aQuestion;
	public ArrayList<Question> questions = new ArrayList<Question>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.question_tab);

		Question aQuestion1 = new Question();
		aQuestion1.questionText = "Is een vlinder een vogel ?";
		aQuestion1.answerText = "Nee";

		Question aQuestion2 = new Question();
		aQuestion2.questionText = "2 + 2 = ?";
		aQuestion2.answerText = "4";

		questions.add(aQuestion1);
		questions.add(aQuestion2);

		m_listview = (ListView) findViewById(R.id.listViewTabVragen);

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < questions.size(); i++) {
			Question temp = questions.get(i);

			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put(KEY_VRAGENTEXT, temp.questionText);
			map.put(KEY_ANTWOORDTEXT, temp.answerText);
			listItem.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(QuestionTab.this, listItem,
				R.layout.list_item_question, new String[] { KEY_VRAGENTEXT },
				new int[] { R.id.vragenTextTextView });

		m_listview.setAdapter(adapter);

		/** to create a Context Menu for the "long press" **/
		m_listview.setOnCreateContextMenuListener(this);
		m_listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		/** click on an item in the listView **/

		m_listview.setOnItemClickListener(this);

	}

	/** long press to delete, to copy, to push ? .... **/
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("CONTEXT MENU");
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
		menu.add(0, COPY_ID, 0, android.R.string.copy);
	}

	/** one of the Context Item is selected **/
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		// to find the item of the list view that was clicked
		// AdapterView.AdapterContextMenuInfo info =
		// (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		// int index=info.position;

		switch (item.getItemId()) {
		case DELETE_ID:
			Toast.makeText(getApplicationContext(), "Delete the question",
					Toast.LENGTH_LONG).show();
			break;
		case PUSH_ID:
			Toast.makeText(getApplicationContext(), "Push the question",
					Toast.LENGTH_LONG).show();
			break;
		case COPY_ID:
			Toast.makeText(getApplicationContext(), "Copy the question",
					Toast.LENGTH_LONG).show();
			break;

		}

		return super.onContextItemSelected(item);
	}

	/** onClick - button "Add Question" **/
	public void onClick(View v) {
		// Intent i = new Intent(VragenTab.this, VragenDetails.class);
		// startActivityForResult(i, ACTIVITY_DETAIL);
		Intent intent = new Intent(getParent(), QuestionDetails.class);
		TabGroupActivity parentactivity = (TabGroupActivity) getParent();
		parentactivity.startChildActivity("QuestionDetails", intent);

	}

	// Called with the result of the other activity
	// requestCode was the origin request code send to the activity
	// resultCode is the return code, 0 is everything is ok
	// intend can be used to get data
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
	}

	// click on a row
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		Intent intent = new Intent(getParent(), QuestionDetails.class);

		intent.putExtra(KEY_VRAGENTEXT, questions.get(position).questionText);
		intent.putExtra(KEY_ANTWOORDTEXT, questions.get(position).answerText);

		Log.d("demo", "question text=" + questions.get(position).questionText);
		TabGroupActivity parentactivity = (TabGroupActivity) getParent();
		parentactivity.startChildActivity("VragenDetails", intent);

	}

	/** the push button clicker **/

	public void onPushButtonClick(View v) {
		Toast.makeText(getApplicationContext(), "Push the question",
				Toast.LENGTH_LONG).show();

	}

}
