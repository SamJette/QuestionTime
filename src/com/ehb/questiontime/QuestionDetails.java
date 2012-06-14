package com.ehb.questiontime;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class QuestionDetails extends Activity implements
		OnCheckedChangeListener {

	EditText editQuestionText;

	RadioGroup radioGroup;

	RadioButton radioJaNeeVraag;
	RadioButton radioMeerKeuzeVraag;
	RadioButton radioInvulVraag;

	boolean isJaNeeVraag;
	boolean isMeerKeuzeVraag;
	boolean isInvulVraag;

	EditText editJa1InvulVraag;
	EditText editNee2;
	EditText edit3;
	EditText edit4;
	EditText edit5;

	CheckBox checkJa1;
	CheckBox checkNee2;
	CheckBox check3;
	CheckBox check4;
	CheckBox check5;

	Button buttonJa1;
	Button buttonNee2;
	Button button3;
	Button button4;
	Button button5;

	public Question aQuestion;
	public Answer anAnswer;
	public ArrayList<Answer> answers = new ArrayList<Answer>();

	public static final String PREFS_NAME = "LoginPrefs";

	static final String KEY_ID = "ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_details);

		radioJaNeeVraag = (RadioButton) findViewById(R.id.radioJaNee);
		radioMeerKeuzeVraag = (RadioButton) findViewById(R.id.radioMeerKeuzeVraag);
		radioInvulVraag = (RadioButton) findViewById(R.id.radioInvulTextVraag);

		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(this);

		editJa1InvulVraag = (EditText) findViewById(R.id.textViewJa);
		editNee2 = (EditText) findViewById(R.id.textViewNee);
		edit3 = (EditText) findViewById(R.id.textView3);
		edit4 = (EditText) findViewById(R.id.textView4);
		edit5 = (EditText) findViewById(R.id.textView5);

		checkJa1 = (CheckBox) findViewById(R.id.checkBoxJa);
		checkNee2 = (CheckBox) findViewById(R.id.checkBoxNee);
		check3 = (CheckBox) findViewById(R.id.checkBox3);
		check4 = (CheckBox) findViewById(R.id.checkBox4);
		check5 = (CheckBox) findViewById(R.id.checkBox5);

		buttonJa1 = (Button) findViewById(R.id.buttonJa);
		buttonNee2 = (Button) findViewById(R.id.buttonNee);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);

		editQuestionText = (EditText) findViewById(R.id.editTextVraag);

		/** to retrieve the value of the clicked row in QuestionTab **/

		Bundle extras = getIntent().getExtras();

		// Log.d("demo", "extras in bundle = " + extras.toString());
		if (extras != null) {

			String vragenText = extras.getString(QuestionTab.KEY_QUESTION);
			Log.d("demo", "Bundel: vragen Text in detail view: " + vragenText);
			if (vragenText != null) {
				editQuestionText.setText(vragenText);
				editQuestionText.setFocusable(false);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editQuestionText.getWindowToken(),
						0);

			} else
				editQuestionText.setFocusable(true);
			editQuestionText.requestFocus();

			retrieveDetailsQuestion();

		}

		editJa1InvulVraag.invalidate();
		editNee2.invalidate();
		edit3.invalidate();
		edit4.invalidate();
		edit5.invalidate();

	}

	public void retrieveDetailsQuestion() {

		Bundle extras = getIntent().getExtras();
		String questionIDString = null;
		if (extras != null) {
			questionIDString = extras.getString(KEY_ID);

			Log.d("demo", "Question's ID = " + questionIDString);
		}
		// final int questionID = Integer.parseInt(questionIDString);

		RestClient.get("questions/" + questionIDString + "/?format=xml", null,
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

						/** create an instance of our parser */

						QuestionDetailParser parser = new QuestionDetailParser();

						SAXParserFactory factory = SAXParserFactory
								.newInstance();
						SAXParser sp = null;
						try {
							sp = factory.newSAXParser();
						} catch (ParserConfigurationException e1) {
							e1.printStackTrace();
						} catch (SAXException e1) {
							e1.printStackTrace();
						}

						XMLReader reader = null;
						try {
							reader = sp.getXMLReader();
						} catch (SAXException e1) {
							e1.printStackTrace();
						}

						// add our own parser to the xml reader and start
						// parsing
						// the
						// file
						reader.setContentHandler(parser);
						try {
							Xml.parse(response, parser);

							Log.d("demo", "Response = " + response);

						} catch (SAXException e) {
							e.printStackTrace();
						}
						aQuestion = parser.tempQuestion;
						editQuestionText.setText(aQuestion.questionText);

						// aQuestion.answers = parser.answers;
						anAnswer = parser.tempAnswer;

						editJa1InvulVraag.setText(anAnswer.answerText);

						if (aQuestion.isOpen.equalsIgnoreCase("1")) {

							radioInvulVraag.setChecked(true);
							radioJaNeeVraag.setChecked(false);
							radioMeerKeuzeVraag.setChecked(false);
						} else {
							if (anAnswer.answerText.equalsIgnoreCase("Ja")
									|| anAnswer.answerText
											.equalsIgnoreCase("Nee")) {

								radioInvulVraag.setChecked(false);
								radioJaNeeVraag.setChecked(true);
								radioMeerKeuzeVraag.setChecked(false);

							} else {
								radioInvulVraag.setChecked(false);
								radioJaNeeVraag.setChecked(false);
								radioMeerKeuzeVraag.setChecked(true);

								answers = parser.answers;

								ArrayList<String> answersTextItem = new ArrayList<String>();
								ArrayList<String> answerIsCorrect = new ArrayList<String>();

								for (int i = 0; i < answers.size(); i++) {
									Answer temp = answers.get(i);
									answersTextItem.add(temp.answerText);
									answerIsCorrect.add(temp.isCorrect);
								}

								for (String s : answersTextItem) {
									if (!s.isEmpty()) {

										try {
											// "row" 1

											editJa1InvulVraag
													.setText(answersTextItem
															.get(0));
											if (answerIsCorrect.get(0)
													.equalsIgnoreCase("1"))
												checkJa1.setChecked(true);
											else
												checkJa1.setChecked(false);
											// "row" 2

											editNee2.setText(answersTextItem
													.get(1));
											if (answerIsCorrect.get(1)
													.equalsIgnoreCase("1"))
												checkNee2.setChecked(true);
											else
												checkNee2.setChecked(false);
											// "row" 3

											edit3.setText(answersTextItem
													.get(2));
											if (answerIsCorrect.get(2)
													.equalsIgnoreCase("1"))
												check3.setChecked(true);
											else
												check3.setChecked(false);
											// "row" 4

											edit4.setText(answersTextItem
													.get(3));
											if (answerIsCorrect.get(3)
													.equalsIgnoreCase("1"))
												check4.setChecked(true);
											else
												check4.setChecked(false);
											// "row" 5

											edit5.setText(answersTextItem
													.get(4));
											if (answerIsCorrect.get(4)
													.equalsIgnoreCase("1"))
												check5.setChecked(true);
											else
												check5.setChecked(false);

										} catch (IndexOutOfBoundsException err) {
											Log.d("demo",
													"Index  is out of bounds.");
										}

									}
								}

							}

						}

					}

				});

	}

	/** method to save the question and return to the QuestionTab **/
	public void saveTheQuestionOnCLick(View v) {

		// save the question text
		String answerText = editQuestionText.getText().toString();
		if (answerText.equals("")) {
			Toast.makeText(getApplicationContext(),
					"Ben je zeker ? Ik vind geen text voor de vraag ....",
					Toast.LENGTH_LONG).show();

			answerText = "Vraag zonder text";

		} else {
			Toast.makeText(getApplicationContext(), "JE VRAAG= " + answerText,
					Toast.LENGTH_LONG).show();

		}
		/** save the object **/

		/*
		 * aQuestion = new Question(); aQuestion.answerText = answerText;
		 * questions.add(aQuestion);
		 * 
		 * 
		 * if (isJaNeeVraag) { Log.d("demo", "isJaNeeVraag");
		 * 
		 * if (checkJa1.isChecked()) { aQuestion.answerText = "Ja"; } else if
		 * (checkNee2.isChecked()) { aQuestion.answerText = "Nee"; }
		 * 
		 * } else if (isMeerKeuzeVraag) { Log.d("demo", "isMeerKeuzeVraag");
		 * 
		 * } else if (isInvulVraag) { Log.d("demo", "isInvulVraag");
		 * 
		 * }
		 */

		/****** VRAGEN OPSLAAN *****/

		/*
		 * SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		 * 
		 * //eerst pushen we de vraag naar de server dan de antwoorden
		 * RequestParams qParams = new RequestParams();
		 * qParams.put("question[question]", questiontext);//question tekst
		 * qParams.put("question[teacherid]", settings.getString("teacherid",
		 * null)); //get teacherid from the shared preferences.
		 * qParams.put("question[isopen]", ((isInvulVraag) ? "1" : "0") );
		 * 
		 * 
		 * RestClient.post("questions", qParams, new AsyncHttpResponseHandler()
		 * { private ProgressDialog dialog;
		 * 
		 * @Override public void onStart() { dialog =
		 * ProgressDialog.show(QuestionDetails.this, "Loading", "Data Loading",
		 * true, true, new OnCancelListener() { public void
		 * onCancel(DialogInterface dialog) { dialog.dismiss(); } }); }
		 * 
		 * @Override public void onSuccess(String response) { if
		 * (this.dialog.isShowing()) this.dialog.dismiss(); }
		 * 
		 * public void onFailure() { Toast toast =
		 * Toast.makeText(getBaseContext(), R.string._error, Toast.LENGTH_LONG);
		 * toast.setGravity(Gravity.CENTER, 0, 0); toast.show();
		 * 
		 * } });
		 * 
		 * 
		 * //loop over de antwoorden voorbeeld // Stel je vragen zitten in een
		 * array
		 * 
		 * String [] answers ={"antwoord1", "antwoord2", "antwoord3",
		 * "antwoord4"};//bv bij meerkeuze boolean []
		 * isCorrect={false,true,false,false} ;//bv antwoord 2 is correct
		 * 
		 * int size = answers.length;
		 * 
		 * for (int i=0; i<size; i++) //loopen over alle antwoorden en voegen ze
		 * een voor één toe. { RequestParams aParams = new RequestParams();
		 * 
		 * aParams.put("answer[questionid]",questionid );//we moeten de question
		 * id hebben aParams.put("answer[answer]",answers[i]); //get teacherid
		 * from the shared preferences. aParams.put("answer[iscorrect]",
		 * ((isCorrect[i]) ? "1" : "0") );//isCorrectAnswer
		 * 
		 * RestClient.post("answers", aParams, new AsyncHttpResponseHandler() {
		 * private ProgressDialog dialog;
		 * 
		 * @Override public void onStart() { dialog =
		 * ProgressDialog.show(QuestionDetails.this, "Loading", "Data Loading",
		 * true, true, new OnCancelListener() { public void
		 * onCancel(DialogInterface dialog) { dialog.dismiss(); } }); }
		 * 
		 * @Override public void onSuccess(String response) { if
		 * (this.dialog.isShowing()) this.dialog.dismiss(); }
		 * 
		 * public void onFailure() { Toast toast =
		 * Toast.makeText(getBaseContext(), R.string._error, Toast.LENGTH_LONG);
		 * toast.setGravity(Gravity.CENTER, 0, 0); toast.show();
		 * 
		 * } }); }
		 */

		// hetzelfde moeten we doen voor de update maar met een
		// RestClient.put("questions/1 ipv RestClient.post("questions"...
		// en met url "question/"+questionid

		/** return to QuestionTab view **/
		Intent intent = new Intent(getParent(), QuestionTab.class);
		TabGroupActivity parentactivity = (TabGroupActivity) getParent();
		parentactivity.startChildActivity("VragenTab", intent);

	}

	/** method to cancel and return to the Question tab **/
	public void cancelTheQuestionOnCLick(View v) {

		/** return to QuestionTab view **/
		Intent intent = new Intent(getParent(), QuestionTab.class);
		TabGroupActivity parentactivity = (TabGroupActivity) getParent();
		parentactivity.startChildActivity("VragenTab", intent);

	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.radioJaNee:
			isJaNeeVraag = true;
			isInvulVraag = false;
			isMeerKeuzeVraag = false;
			break;

		case R.id.radioMeerKeuzeVraag:
			isJaNeeVraag = false;
			isInvulVraag = false;
			isMeerKeuzeVraag = true;

			break;

		case R.id.radioInvulTextVraag:
			isJaNeeVraag = false;
			isInvulVraag = true;
			isMeerKeuzeVraag = false;

			break;

		default:
			break;
		}
		setVisibilityFromRadio();
	}

	void setVisibilityFromRadio() {
		if (isJaNeeVraag) {
			editJa1InvulVraag.setVisibility(View.VISIBLE);
			editJa1InvulVraag.setText("JA");

			editNee2.setVisibility(View.VISIBLE);
			editNee2.setText("NEE");
			checkJa1.setVisibility(View.VISIBLE);
			checkNee2.setVisibility(View.VISIBLE);
			buttonJa1.setVisibility(View.VISIBLE);
			buttonNee2.setVisibility(View.VISIBLE);

			edit3.setVisibility(View.INVISIBLE);
			edit4.setVisibility(View.INVISIBLE);
			edit5.setVisibility(View.INVISIBLE);
			check3.setVisibility(View.INVISIBLE);
			check4.setVisibility(View.INVISIBLE);
			check5.setVisibility(View.INVISIBLE);
			button3.setVisibility(View.INVISIBLE);
			button4.setVisibility(View.INVISIBLE);
			button5.setVisibility(View.INVISIBLE);

			// editInvulVraag.setVisibility(View.INVISIBLE);

		} else if (isMeerKeuzeVraag) {
			editJa1InvulVraag.setVisibility(View.VISIBLE);
			editJa1InvulVraag.setText("A");
			editNee2.setVisibility(View.VISIBLE);
			editNee2.setText("B");
			checkJa1.setVisibility(View.VISIBLE);
			checkNee2.setVisibility(View.VISIBLE);
			buttonJa1.setVisibility(View.VISIBLE);
			buttonNee2.setVisibility(View.VISIBLE);

			edit3.setVisibility(View.VISIBLE);

			edit4.setVisibility(View.VISIBLE);
			edit5.setVisibility(View.VISIBLE);
			check3.setVisibility(View.VISIBLE);
			check4.setVisibility(View.VISIBLE);
			check5.setVisibility(View.VISIBLE);
			button3.setVisibility(View.VISIBLE);
			button4.setVisibility(View.VISIBLE);
			button5.setVisibility(View.VISIBLE);

			// editInvulVraag.setVisibility(View.INVISIBLE);

		} else if (isInvulVraag) {
			editJa1InvulVraag.setVisibility(View.VISIBLE);
			// editJa1InvulVraag.setHint("Enter the answer here");
			// editJa1InvulVraag.setHintTextColor(R.color.whiteColor);
			// editJa1InvulVraag.setText("");
			editNee2.setVisibility(View.INVISIBLE);
			checkJa1.setVisibility(View.INVISIBLE);
			checkNee2.setVisibility(View.INVISIBLE);
			buttonJa1.setVisibility(View.INVISIBLE);
			buttonNee2.setVisibility(View.INVISIBLE);

			edit3.setVisibility(View.INVISIBLE);
			edit4.setVisibility(View.INVISIBLE);
			edit5.setVisibility(View.INVISIBLE);
			check3.setVisibility(View.INVISIBLE);
			check4.setVisibility(View.INVISIBLE);
			check5.setVisibility(View.INVISIBLE);
			button3.setVisibility(View.INVISIBLE);
			button4.setVisibility(View.INVISIBLE);
			button5.setVisibility(View.INVISIBLE);

			// editInvulVraag.setVisibility(View.VISIBLE);

		}
	}

	/** method for the checkboxes **/

	public void myCheckListener(View v) {
		if (isJaNeeVraag) {
			if (checkJa1.isChecked()) {
				checkNee2.setChecked(false);

			} else if (!checkJa1.isChecked()) {
				checkNee2.setChecked(true);

			}

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBackPressed();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		/** return to QuestionTab view **/
		Intent intent = new Intent(getParent(), QuestionTab.class);
		TabGroupActivity parentactivity = (TabGroupActivity) getParent();
		parentactivity.startChildActivity("VragenTab", intent);

	}

}
