package com.depalma.whoswhere.shared.messages;

import com.depalma.whoswhere.shared.Gender;
import com.depalma.whoswhere.shared.Message;

public class JoinMessage extends Message {

	private String name;
	private Gender gender;
	private int age;
	
	public JoinMessage(String name, Gender gender, int age) {
		this.name = name;
		this.gender = gender;
		this.age = age;
	}
}
