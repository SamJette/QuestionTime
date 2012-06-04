package com.ehb.questiontime;

import java.util.ArrayList;

public class Question {

	public int ID;
	public String questionText;
	public int teachersID;
	public boolean isGepusht;

	public int answerID;
	public ArrayList<Answer> answers;
	public String answerText;

	public boolean isCorrect;
	public boolean isOpen;

	public int openQuestionsID;
	public String studentAnswer;
	public int resultID;

	public Question() {
		super();
	}

}
