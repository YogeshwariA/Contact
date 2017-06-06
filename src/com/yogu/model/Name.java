package com.yogu.model;

public class Name {

	private String firstName;
	private String lastName;
	private String nickName;
	private String phoneticName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneticName() {
		return phoneticName;
	}

	public void setPhoneticName(String phoneticName) {
		this.phoneticName = phoneticName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		String name="";
		if(firstName!=null)
		{
			name+="First name: "+firstName;
		}
		if(lastName!=null)
		{
			name+=" Last name: " + this.lastName;
		}
		return name;
	}
}
