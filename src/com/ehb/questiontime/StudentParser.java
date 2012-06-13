package com.ehb.questiontime;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StudentParser extends DefaultHandler {
	public ArrayList<Student> students;
	private Student tempStudent;
	private StringBuilder builder;

	/** XML node keys **/
	static final String KEY_ROW = "row"; // parent node
	static final String KEY_DATA = "data";
	static final String KEY_ID = "ID";
	static final String KEY_FIRSTNAME = "FIRSTNAME";
	static final String KEY_NAME = "NAME";
	static final String KEY_EMAIL = "EMAIL";
	static final String KEY_NUMBER = "NUMBER";
	static final String KEY_ISONLINE = "ISONLINE";
	static final String KEY_PASSWORD = "PASSWORD";

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
			this.students.add(tempStudent);

		}
		// finished reading "firstname" tag assign it to the temp person
		else if (localName.equalsIgnoreCase(KEY_FIRSTNAME)) {
			tempStudent.firstName = builder.toString();
		}
		// finished reading "lastname" tag assign it to the temp person
		else if (localName.equalsIgnoreCase(KEY_NAME)) {
			tempStudent.name = builder.toString();
		}
		// finished reading "number" tag assign it to the temp person
		else if (localName.equalsIgnoreCase(KEY_NUMBER)) {
			tempStudent.number = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_EMAIL)) {
			tempStudent.email = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_PASSWORD)) {
			tempStudent.password = builder.toString();
		} else if (localName.equalsIgnoreCase(KEY_ID)) {
			tempStudent.ID = Integer.parseInt(builder.toString());
		} else if (localName.equalsIgnoreCase(KEY_ISONLINE)) {
			tempStudent.isOnLine = builder.toString();
		}

	}

	@Override
	public void startDocument() throws SAXException {
		students = new ArrayList<Student>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equalsIgnoreCase(KEY_DATA)) {

			builder = new StringBuilder();
		}

		if (localName.equalsIgnoreCase(KEY_ROW)) {
			tempStudent = new Student();
			builder = new StringBuilder();
		}

		else if (localName.toUpperCase().equals(KEY_FIRSTNAME)) {
			builder = new StringBuilder();
		}

		else if (localName.toUpperCase().equals(KEY_NAME)) {
			builder = new StringBuilder();
		}

		else if (localName.toUpperCase().equals(KEY_NUMBER)) {
			builder = new StringBuilder();
		} else if (localName.toUpperCase().equals(KEY_EMAIL)) {
			builder = new StringBuilder();
		} else if (localName.toUpperCase().equals(KEY_PASSWORD)) {
			builder = new StringBuilder();
		} else if (localName.toUpperCase().equals(KEY_ID)) {
			builder = new StringBuilder();
		} else if (localName.toUpperCase().equals(KEY_ISONLINE)) {
			builder = new StringBuilder();
		}

		else
			builder = new StringBuilder();

	}

}
