package com.ehb.questiontime;

import java.util.ArrayList;

public class Question {

	public String ID;
	public String questionText;
	public String teachersID;
	public String isGepusht;
	public String createdDate;
	public String deleteDate;
	public String updateDate;

	public String answerID;
	public ArrayList<Answer> answers;
	public String answerText;

	public String isCorrect;
	public String isOpen;

	public String openQuestionsID;
	public String studentAnswer;
	public String resultID;
	public Answer anAnswer;

	public Question() {
		super();
	}

}
