package com.ehb.questiontime;

import java.util.ArrayList;

public class Student {

	public int ID;
	public String firstName;
	public String name;
	public String password;
	public String email;
	public String number;
	public String isOnLine;

	public String classesStudentsID;
	public String schoolYearsID;
	public String startDate;
	public String endDate;

	public String classesID;
	public String classText;

	public String teachersID;

	public int correctResults;
	public int totalQuestion;

	public String resultText;

	public ArrayList<Answer> answers;
	public ArrayList<Question> questions;

	public Student() {
		super();
	}

}
