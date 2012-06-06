package com.ehb.questiontime;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class QuestionParser extends DefaultHandler {

	public ArrayList<Question> questions;
	private Question tempQuestion;
	private StringBuilder builder;

	/** XML node keys **/
	static final String KEY_ROW = "row"; // parent node
	static final String KEY_DATA = "data";
	static final String KEY_ID = "ID";
	static final String KEY_QUESTION = "QUESTION";
	static final String KEY_ISOPEN = "ISOPEN";
	static final String KEY_TEACHERID = "TEACHERID";
	static final String KEY_ISPUSHED = "ISPUSHED";

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String tempString = new String(ch, start, length);
		builder.append(tempString);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.toLowerCase().equals(KEY_ROW)) {
			this.questions.add(tempQuestion);

		}

		else if (localName.equalsIgnoreCase(KEY_ID)) {
			String temp = builder.toString();
			// Log.d("demo", "ID ??" + temp);

			tempQuestion.ID = temp;

		}

		/** finished reading "question text" tag assign it to the temp question **/
		else if (localName.equalsIgnoreCase(KEY_QUESTION)) {
			tempQuestion.questionText = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_ISOPEN)) {
			tempQuestion.isOpen = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_TEACHERID)) {
			tempQuestion.teachersID = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_ISPUSHED)) {
			tempQuestion.isGepusht = builder.toString();
		}
	}

	@Override
	public void startDocument() throws SAXException {
		questions = new ArrayList<Question>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equalsIgnoreCase(KEY_DATA)) {

			builder = new StringBuilder();
		}

		if (localName.equalsIgnoreCase(KEY_ROW)) {
			tempQuestion = new Question();
			builder = new StringBuilder();
		} else if (localName.toUpperCase().equals(KEY_ID)) {
			builder = new StringBuilder();
		}

		else if (localName.toUpperCase().equals(KEY_QUESTION)) {
			builder = new StringBuilder();
		}

		else if (localName.toUpperCase().equals(KEY_ISOPEN)) {
			builder = new StringBuilder();
		} else if (localName.toUpperCase().equals(KEY_TEACHERID)) {
			builder = new StringBuilder();
		} else if (localName.toUpperCase().equals(KEY_ISPUSHED)) {
			builder = new StringBuilder();
		} else
			builder = new StringBuilder();

	}

}
