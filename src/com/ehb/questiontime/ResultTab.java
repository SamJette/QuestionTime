<<<<<<< HEAD
package com.ehb.questiontime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;

public class ResultTab extends Activity {
	
	private ArrayList<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
	private ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<ArrayList<Map<String, String>>>();


	static final String KEY_FIRSTNAME = "FIRSTNAME";
	static final String KEY_NAME = "NAME";
	static final String KEY_ISJUIST = "ISJUIST";

	static final String KEY_QUESTIONTEXT = "QUESTIONTEXT";
	static final String KEY_DATE = "CREATEDDATE";
	static final String KEY_ANSWER = "ANSWERTEXT";

	Student aStudent;
	Question aQuestion;
	Result aResult;
	Answer anAnswer;

	public static final ArrayList<Student> students = new ArrayList<Student>();
	public static final ArrayList<Question> questions = new ArrayList<Question>();
	public static final ArrayList<Result> results = new ArrayList<Result>();
	public static final ArrayList<Answer> answers = new ArrayList<Answer>();

	/** private ArrayList<Result> resultArray */

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results_tab);

		getResults();

	}

	public void getResults() {
		
		RestClient.get("results.json", null, new JsonHttpResponseHandler() {
			private ProgressDialog dialog;
			// private JSONArray name;
			private JSONArray _data = null;

			public void onStart() {
				dialog = ProgressDialog.show(ResultTab.this, "Loading",
						"Data loading", true, true, new OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						dialog.dismiss();
					}
				});
			}

			@Override
			public void onSuccess(JSONObject response)  {
				if (this.dialog.isShowing())
					this.dialog.dismiss();

				try {
					_data = response.getJSONArray("DATA");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(_data);
				for (int i = 0; i < _data.length(); i++) {
					try {
						JSONArray tmp = _data.getJSONArray(i);
						HashMap<String, String> map = new HashMap<String,String>();

						System.out.println(tmp.getString(1));
						System.out.println(tmp.getString(2));

						map.put(KEY_FIRSTNAME, tmp.getString(1));
						map.put(KEY_ISJUIST, tmp.getString(2));

						groupData.add(map);
						Log.d("groupList",groupData.toString());

						RestClient.get("results/"+tmp.getString(0)+".json", null, new JsonHttpResponseHandler() {
							private JSONArray _data = null;

							public void onStart() {}

							@Override
							public void onSuccess(JSONObject response)  {
								try {
									_data = response.getJSONArray("DATA");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println(_data);
								ArrayList<Map<String, String>> children = new ArrayList<Map<String, String>>();

								for (int i = 0; i < _data.length(); i++) {
									try {
										JSONArray tmp = _data.getJSONArray(i);
										HashMap<String, String> map = new HashMap<String,String>();

										System.out.println(tmp.getString(1));
										System.out.println(tmp.getString(2));
										System.out.println(tmp.getString(4));
										
										map.put(KEY_QUESTIONTEXT, tmp.getString(1));
										map.put(KEY_ANSWER, tmp.getString(2));
										map.put(KEY_DATE, tmp.getString(4));
										
										children.add(map);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								childData.add(children);
							}

						});
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				fillAdapter(groupData, childData);
			}

			
		});
Log.d("groupList",groupData.toString());
Log.d("childList",childData.toString());

		
	}
	private void fillAdapter(ArrayList<Map<String, String>> groupData, ArrayList<ArrayList<Map<String, String>>> childData) {
		SimpleExpandableListAdapter listAdapter = new SimpleExpandableListAdapter(
				this, groupData, R.layout.list_item_results_students,
				new String[] { KEY_FIRSTNAME, KEY_NAME, KEY_ISJUIST },
				new int[] { R.id.firstnameResults, R.id.nameResults,
						R.id.resultsTextView }, childData,
						R.layout.list_item_results_results, new String[] {
						KEY_QUESTIONTEXT, KEY_DATE, KEY_ANSWER }, new int[] {
						R.id.questionTextView, R.id.dateAnswerTextView,
						R.id.answerTextTextView });
		ExpandableListView myListView = (ExpandableListView) findViewById(R.id.listViewTabResultaten);
		myListView.setAdapter(listAdapter);
		
	}
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		Log.d("demo", "onContentChanged");
	}

}
=======
package com.ehb.questiontime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class ResultTab extends Activity {

	static final String KEY_FIRSTNAME = "FIRSTNAME";
	static final String KEY_NAME = "NAME";
	static final String KEY_ISJUIST = "ISJUIST";

	static final String KEY_QUESTIONTEXT = "QUESTIONTEXT";
	static final String KEY_DATE = "CREATEDDATE";
	static final String KEY_ANSWER = "ANSWERTEXT";

	Student aStudent;
	Question aQuestion;
	Result aResult;
	Answer anAnswer;

	public static final ArrayList<Student> students = new ArrayList<Student>();
	public static final ArrayList<Question> questions = new ArrayList<Question>();
	public static final ArrayList<Result> results = new ArrayList<Result>();
	public static final ArrayList<Answer> answers = new ArrayList<Answer>();

	/** private ArrayList<Result> resultArray */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results_tab);

		ArrayList<Map<String, String>> groupList = new ArrayList<Map<String, String>>();
		Map<String, String> groupData1 = new HashMap<String, String>();
		groupData1.put(KEY_FIRSTNAME, "Jean");
		groupData1.put(KEY_NAME, " Dupond ");
		groupData1.put(KEY_ISJUIST, "1/2");
		groupList.add(groupData1);

		Map<String, String> groupData2 = new HashMap<String, String>();
		groupData2.put(KEY_FIRSTNAME, "Capucine");
		groupData2.put(KEY_NAME, " Durand ");
		groupData2.put(KEY_ISJUIST, "2/2");
		groupList.add(groupData2);

		ArrayList<ArrayList<Map<String, String>>> childList = new ArrayList<ArrayList<Map<String, String>>>();

		ArrayList<Map<String, String>> childList1 = new ArrayList<Map<String, String>>();
		Map<String, String> childMap1 = new HashMap<String, String>();
		childMap1.put(KEY_QUESTIONTEXT, "Is een vlinder een vogel ?");
		//childMap1.put(KEY_DATE, "01/06/2012");
		childMap1.put(KEY_ANSWER, "Ja");
		childList1.add(childMap1);

		Map<String, String> childMap2 = new HashMap<String, String>();
		childMap2.put(KEY_QUESTIONTEXT, "2+2= ?");
		//childMap2.put(KEY_DATE, "01/06/2012");
		childMap2.put(KEY_ANSWER, "4");
		childList1.add(childMap2);

		childList.add(childList1);

		ArrayList<Map<String, String>> childList2 = new ArrayList<Map<String, String>>();
		Map<String, String> childMap21 = new HashMap<String, String>();
		childMap21.put(KEY_QUESTIONTEXT, "Is een vlinder een vogel ?");
		//childMap21.put(KEY_DATE, "01/06/2012");
		childMap21.put(KEY_ANSWER, "Nee");
		childList2.add(childMap21);

		Map<String, String> childMap22 = new HashMap<String, String>();
		childMap22.put(KEY_QUESTIONTEXT, "2+2= ?");
		//childMap22.put(KEY_DATE, "01/06/2012");
		childMap22.put(KEY_ANSWER, "4");
		childList2.add(childMap22);

		childList.add(childList2);

		SimpleExpandableListAdapter listAdapter = new SimpleExpandableListAdapter(
				this, groupList, R.layout.list_item_results_students,
				new String[] { KEY_FIRSTNAME, KEY_NAME, KEY_ISJUIST },
				new int[] { R.id.firstnameResults, R.id.nameResults,
						R.id.resultsTextView }, childList,
				R.layout.list_item_results_results, new String[] {
						KEY_QUESTIONTEXT, KEY_ANSWER }, new int[] {
						R.id.questionTextView,
						R.id.answerTextTextView });
		ExpandableListView myListView = (ExpandableListView) findViewById(R.id.listViewTabResultaten);
		myListView.setAdapter(listAdapter);

	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		Log.d("demo", "onContentChanged");
	}

}
>>>>>>> origin/Anderson2
