package com.yogu.model;

public class PhoneDetail {

	private long mobileNumber;
	private Category category;

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Mobile number: " + this.mobileNumber + " Category: "+this.category+"";
	}
}
