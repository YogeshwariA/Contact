package com.yogu.model;

import java.util.List;

public class ContactDetails {

	private List<PhoneDetail> phoneNumbers;

	private String internetCall;

	public List<PhoneDetail> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneDetail> phoneNumber) {
		this.phoneNumbers = phoneNumber;
	}

	public String getInternetCall() {
		return internetCall;
	}

	public void setInternetCall(String internetCall) {
		this.internetCall = internetCall;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String phoneNumber="";
		if(phoneNumbers!=null)
		{
			phoneNumber+=" Phone numbers:"+phoneNumbers;
		}
		
		
		return phoneNumber;
	}
}
