package com.ehb.questiontime;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class QuestionDetailParser extends DefaultHandler {

	public ArrayList<Question> questions;
	private Question tempQuestion;
	private Answer tempAnswer;
	private StringBuilder builder;

	/** XML node keys **/
	static final String KEY_DATA = "data";
	static final String KEY_ANSWERS = "answers";

	static final String KEY_createdat = "createdat";
	static final String KEY_deletedat = "deletedat";
	static final String KEY_id = "id";
	static final String KEY_isopen = "isopen";
	static final String KEY_ispushed = "ispushed";
	static final String KEY_question = "question";
	static final String KEY_teacherid = "teacherid";
	static final String KEY_updatedat = "updatedat";

	static final String KEY_item = "item";
	static final String KEY_answer = "answer";
	static final String KEY_createdat_ANSWER = "createdat";
	static final String KEY_deletedat_ANSWER = "deletedat";
	static final String KEY_id_ANSWER = "id";
	static final String KEY_iscorrect_ANSWER = "iscorrect";
	static final String KEY_questionid_ANSWER = "questionid";
	static final String KEY_updatedat_ANSWER = "updatedat";

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String tempString = new String(ch, start, length);
		builder.append(tempString);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.toLowerCase().equals(KEY_DATA)) {
			this.questions.add(tempQuestion);
		}
		/** finished reading "question text" tag assign it to the temp question **/
		else if (localName.equalsIgnoreCase(KEY_ANSWERS)) {
			if (localName.equalsIgnoreCase(KEY_item)) {
				tempQuestion.answers.add(tempAnswer);
			} else if (localName.equalsIgnoreCase(KEY_answer)) {
				tempAnswer.answerText = builder.toString();
			} else if (localName.equalsIgnoreCase(KEY_createdat_ANSWER)) {
				tempAnswer.createdDate = builder.toString();
			} else if (localName.equalsIgnoreCase(KEY_deletedat_ANSWER)) {
				tempAnswer.deleteDate = builder.toString();
			} else if (localName.equalsIgnoreCase(KEY_id_ANSWER)) {
				tempAnswer.id = builder.toString();
			} else if (localName.equalsIgnoreCase(KEY_iscorrect_ANSWER)) {
				tempAnswer.isCorrect = builder.toString();
			} else if (localName.equalsIgnoreCase(KEY_questionid_ANSWER)) {
				tempAnswer.questionId = builder.toString();
			} else if (localName.equalsIgnoreCase(KEY_updatedat_ANSWER)) {
				tempAnswer.updateDate = builder.toString();
			}

		} else if (localName.equalsIgnoreCase(KEY_createdat)) {
			tempQuestion.createdDate = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_deletedat)) {
			tempQuestion.deleteDate = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_id)) {
			tempQuestion.ID = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_isopen)) {
			tempQuestion.isOpen = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_ispushed)) {
			tempQuestion.isGepusht = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_question)) {
			tempQuestion.questionText = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_teacherid)) {
			tempQuestion.teachersID = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_updatedat)) {
			tempQuestion.updateDate = builder.toString();
		}

	}

	@Override
	public void startDocument() throws SAXException {
		questions = new ArrayList<Question>();

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
	}

}