package com.ehb.questiontime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ResultTab extends Activity {

	private final ArrayList<Map<String, Object>> groupData = new ArrayList<Map<String, Object>>();
	private final ArrayList<ArrayList<Map<String, Object>>> childData = new ArrayList<ArrayList<Map<String, Object>>>();

	static final String KEY_FIRSTNAME = "FIRSTNAME";
	static final String KEY_NAME = "NAME";
	static final String KEY_ISJUIST = "ISJUIST";

	static final String KEY_QUESTIONTEXT = "QUESTIONTEXT";
	static final String KEY_POINTS = "POINTS";
	static final String KEY_ANSWER = "ANSWERTEXT";
	
	final String NAME = "name";
	final String IMAGE = "image";


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

	public void onRefresh(View v) {
		getResults();
	}
	
	public void getResults() {

		RestClient.get("results.json", null, new JsonHttpResponseHandler() {
			private ProgressDialog dialog;
			// private JSONArray name;
			private JSONArray _data = null;

			@Override
			public void onStart() {
				dialog = ProgressDialog.show(ResultTab.this, "Loading",
						"Data loading", true, true, new OnCancelListener() {
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
					_data = response.getJSONArray("DATA");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(_data);
				for (int i = 0; i < _data.length(); i++) {
					try {
						JSONArray tmp = _data.getJSONArray(i);
						Map<String, Object> map = new HashMap<String, Object>();

						System.out.println(tmp.getString(1));
						System.out.println(tmp.getString(2));

						map.put(KEY_FIRSTNAME, tmp.getString(1));
						map.put(KEY_ISJUIST, tmp.getString(2));

						groupData.add(map);
						Log.d("groupList", groupData.toString());

						RestClient.get("results/" + tmp.getString(0) + ".json",
								null, new JsonHttpResponseHandler() {
									private JSONArray _data = null;

									@Override
									public void onStart() {
									}

									@Override
									public void onSuccess(JSONObject response) {
										try {
											_data = response
													.getJSONArray("DATA");
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										System.out.println(_data);
										ArrayList<Map<String, Object>> children = new ArrayList<Map<String, Object>>();

										for (int i = 0; i < _data.length(); i++) {
											try {
												JSONArray tmp = _data
														.getJSONArray(i);
												HashMap<String, Object> map = new HashMap<String, Object>();

												System.out.println(tmp
														.getString(1));
												System.out.println(tmp
														.getString(2));
												System.out.println(tmp
														.getString(3));
												
												// change image if student is online or not
												
												if (tmp.getString(3).equalsIgnoreCase("0")) {
													map.put(KEY_POINTS,getResources().getDrawable(R.drawable.offline));
															
												}else{
													map.put(KEY_POINTS,getResources().getDrawable(R.drawable.online));
												}

												
												map.put(KEY_QUESTIONTEXT,
														tmp.getString(1));
												map.put(KEY_ANSWER,
														tmp.getString(2));
		

												children.add(map);
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
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
		Log.d("groupList", groupData.toString());
		Log.d("childList", childData.toString());

	}
	

	private void fillAdapter(ArrayList<Map<String, Object>> groupData,
			ArrayList<ArrayList<Map<String, Object>>> childData) {
		final LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		SimpleExpandableListAdapter listAdapter = new SimpleExpandableListAdapter(
				this, groupData, R.layout.list_item_results_students,
				new String[] { KEY_FIRSTNAME, KEY_NAME, KEY_ISJUIST },
				new int[] { R.id.firstnameResults, R.id.nameResults,
						R.id.resultsTextView }, 
				childData,
				R.layout.list_item_results_results, 
				new String[] {KEY_QUESTIONTEXT, KEY_ANSWER, KEY_POINTS }, 
				new int[] {}){
					            @Override
					            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
					                final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);

					                // Populate your custom view here
					                ((TextView)v.findViewById(R.id.questionTextView)).setText( (String) ((Map<String,Object>)getChild(groupPosition, childPosition)).get(KEY_QUESTIONTEXT) );
					                ((TextView)v.findViewById(R.id.answerTextTextView)).setText( (String) ((Map<String,Object>)getChild(groupPosition, childPosition)).get(KEY_ANSWER) );
					                ((ImageView)v.findViewById(R.id.score)).setImageDrawable( (Drawable) ((Map<String,Object>)getChild(groupPosition, childPosition)).get(KEY_POINTS) );

					                return v;
					            }

					            @Override
					            public View newChildView(boolean isLastChild, ViewGroup parent) {
					                 return layoutInflater.inflate(R.layout.list_item_results_results, null, false);
					            }
					        };
		ExpandableListView myListView = (ExpandableListView) findViewById(R.id.listViewTabResultaten);
		myListView.setAdapter(listAdapter);

	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		Log.d("demo", "onContentChanged");
	}

}
