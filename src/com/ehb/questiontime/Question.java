package com.ehb.questiontime;

import java.util.ArrayList;

public class Question {

	public String ID;
	public String questionText;
	public String teachersID;
	public String isGepusht;

	public int answerID;
	public ArrayList<Answer> answers;
	public String answerText;

	public String isCorrect;
	public String isOpen;

	public int openQuestionsID;
	public String studentAnswer;
	public int resultID;

	public Question() {
		super();
	}

}
