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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ResultTab extends Activity {

	static final String KEY_FIRSTNAME = "FIRSTNAME";
	static final String KEY_NAME = "NAME";
	static final String KEY_ISJUIST = "ISJUIST";
	static final String KEY_SCORE = "SCORE";

	static final String KEY_QUESTIONTEXT = "QUESTIONTEXT";
	static final String KEY_POINTS = "POINTS";
	static final String KEY_POINTS2 = "POINTS2";

	static final String KEY_ANSWER = "ANSWERTEXT";
	static final String KEY_SCORE2 = "SCORE2";

	Student aStudent;
	Question aQuestion;
	Result aResult;
	Answer anAnswer;
	private AbsListView myListView;

	public static final ArrayList<Student> students = new ArrayList<Student>();
	public static final ArrayList<Question> questions = new ArrayList<Question>();
	public static final ArrayList<Result> results = new ArrayList<Result>();
	public static final ArrayList<Answer> answers = new ArrayList<Answer>();

	private TextView questionTopBar;

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
			private JSONArray _data = null;

			@Override
			public void onStart() {
				dialog = ProgressDialog.show(getParent(), "Loading",
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

				ArrayList<Map<String, Object>> groupData = new ArrayList<Map<String, Object>>();
				final ArrayList<ArrayList<Map<String, Object>>> childData = new ArrayList<ArrayList<Map<String, Object>>>();

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

						// System.out.println(tmp.getString(1));
						// System.out.println(tmp.getString(2));

						map.put(KEY_FIRSTNAME, tmp.getString(1));
<<<<<<< HEAD
=======
						//map.put(KEY_NAME, tmp.getString(?));
>>>>>>> 59bfd206e77bb86832657544bb310337e9e1fd2a
						map.put(KEY_ISJUIST, tmp.getString(2));
						map.put(KEY_SCORE, tmp.getString(2));
						map.put(KEY_SCORE2, tmp.getString(2));

						String[] tmpArray = tmp.getString(2).split("/");
						double num = Double.parseDouble(tmpArray[0]);
						double denom = Double.parseDouble(tmpArray[1]);
						double quot = 0.0;
						if (denom != 0.0)
							quot = num / denom;
<<<<<<< HEAD
						if (quot == 1.0) {
=======
						if (quot == 0.0) {
>>>>>>> 59bfd206e77bb86832657544bb310337e9e1fd2a
							map.put(KEY_SCORE,
									getResources().getDrawable(
											R.color.greenColor));
							map.put(KEY_SCORE2,
									getResources().getDrawable(
											R.color.greenColor));

						} else if (quot >= 0.5 && quot <= 0.7) {
							map.put(KEY_SCORE,
									getResources().getDrawable(
											R.color.orangeColor));
						} else {
							map.put(KEY_SCORE,
									getResources()
											.getDrawable(R.color.redColor));
							map.put(KEY_SCORE2,
									getResources()
											.getDrawable(R.color.redColor));

						}
						Log.d("score", "" + quot);

						groupData.add(map);
						// Log.d("groupData", groupData.toString());

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
										// System.out.println(_data);
										ArrayList<Map<String, Object>> children = new ArrayList<Map<String, Object>>();

										for (int i = 0; i < _data.length(); i++) {
											try {
												JSONArray tmp = _data
														.getJSONArray(i);
												HashMap<String, Object> map = new HashMap<String, Object>();

												// System.out.println(tmp.getString(1));
												// System.out.println(tmp.getString(2));
												// System.out.println(tmp.getString(3));

												// change image if student is
												// online or not

												if (tmp.getString(3)
														.equalsIgnoreCase("0")) {
													map.put(KEY_POINTS,
															getResources()
																	.getDrawable(
																			R.color.redColor));
													map.put(KEY_POINTS2,
															getResources()
																	.getDrawable(
																			R.color.redColor));

												} else {
													map.put(KEY_POINTS,
															getResources()
																	.getDrawable(
																			R.color.greenColor));
													map.put(KEY_POINTS2,
															getResources()
																	.getDrawable(
																			R.color.greenColor));

												}

												// map.put(KEY_QUESTIONTEXT,tmp.getString(1));
												map.put(KEY_ANSWER,
														tmp.getString(2));

												children.add(map);

												// Temp Question Title Anderson
												questionTopBar = (TextView) findViewById(R.id.questionResults);
												questionTopBar.setText(tmp
														.getString(1)
														+ ' '
														+ ':'
														+ ' '
														+ tmp.getString(4));

											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										}
										childData.add(children);

										// Log.d("childData",
										// childData.toString());
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

	}

	private void fillAdapter(ArrayList<Map<String, Object>> groupData,
			ArrayList<ArrayList<Map<String, Object>>> childData) {
		final LayoutInflater layoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		SimpleExpandableListAdapter listAdapter = new SimpleExpandableListAdapter(

		this, groupData, R.layout.list_item_results_students, new String[] {
				KEY_SCORE, KEY_FIRSTNAME, KEY_NAME, KEY_ISJUIST, KEY_SCORE2 },
				new int[] {}, childData, R.layout.list_item_results_results,
				new String[] { KEY_POINTS, KEY_ANSWER, KEY_POINTS2 },
				new int[] {}) {

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				final View gv = super.getGroupView(groupPosition, isExpanded,
						convertView, parent);

				((ImageView) gv.findViewById(R.id.sscore1))
						.setBackgroundDrawable((Drawable) ((Map<String, Object>) getGroup(groupPosition))
								.get(KEY_SCORE));

				((TextView) gv.findViewById(R.id.firstnameResults))
						.setText((String) ((Map<String, Object>) getGroup(groupPosition))
								.get(KEY_FIRSTNAME));
				((TextView) gv.findViewById(R.id.nameResults))
						.setText((String) ((Map<String, Object>) getGroup(groupPosition))
								.get(KEY_NAME));
				((TextView) gv.findViewById(R.id.resultsTextView))
						.setText((String) ((Map<String, Object>) getGroup(groupPosition))
								.get(KEY_ISJUIST));

				((ImageView) gv.findViewById(R.id.sscore2))
						.setBackgroundDrawable((Drawable) ((Map<String, Object>) getGroup(groupPosition))
								.get(KEY_SCORE2));

				return gv;
			}

			@Override
			public View newGroupView(boolean isExpanded, ViewGroup parent) {
				return layoutInflater.inflate(
						R.layout.list_item_results_students, null, false);
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				final View v = super.getChildView(groupPosition, childPosition,
						isLastChild, convertView, parent);

				// Populate the custom view here
				((ImageView) v.findViewById(R.id.score1))
						.setBackgroundDrawable((Drawable) ((Map<String, Object>) getChild(
								groupPosition, childPosition)).get(KEY_POINTS));
				((TextView) v.findViewById(R.id.answerTextTextView))
						.setText((String) ((Map<String, Object>) getChild(
								groupPosition, childPosition)).get(KEY_ANSWER));
				((ImageView) v.findViewById(R.id.score2))
						.setBackgroundDrawable((Drawable) ((Map<String, Object>) getChild(
								groupPosition, childPosition)).get(KEY_POINTS2));

				return v;
			}

			@Override
			public View newChildView(boolean isLastChild, ViewGroup parent) {
				return layoutInflater.inflate(
						R.layout.list_item_results_results, null, false);
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
