package com.yogu.application;

import java.util.List;
import java.util.Scanner;

import com.yogu.manage.ContactManager;
import com.yogu.model.Contact;

public class ContactManagement {
	
	public static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		ContactManager contactManager = new ContactManager();
		
		int choice;
		String firstName;
		long mobileNumber;
		Contact contact;
		do
		{
			choice=contactManager.displayMainMenu();
			switch(choice)
			{
			
			case 1:
				contact=contactManager.getContactFromUser();
				contactManager.addContact(contact);
				System.out.println("Contact Added Successfully");
				break;
			case 2:
				System.out.println("Enter First Name: ");
				firstName=scanner.nextLine();
				System.out.println("Enter Mobile Number: ");
				mobileNumber=Long.parseLong(scanner.nextLine());
				if(contactManager.updateContact(firstName, mobileNumber))
				{
					System.out.println("Contact updated successfully");
				}
				else
				{
					System.out.println("Contact not found");
				}
				break;
			case 3:
				System.out.println("Enter First Name: ");
				firstName=scanner.nextLine();
				System.out.println("Enter Mobile Number: ");
				mobileNumber=Long.parseLong(scanner.nextLine());
				boolean isDeleted=contactManager.deleteContact(firstName, mobileNumber);
				if(isDeleted)
				{
					System.out.println("Contact deleted successfully...");
				}
				else
				{
					System.out.println("Contact not deleted successfully...");
				}
				break;
				
			case 4:
				System.out.println("Enter First Name: ");
				firstName=scanner.nextLine();
				System.out.println("Enter Mobile Number: ");
				mobileNumber=Long.parseLong(scanner.nextLine());
				contact=contactManager.searchContact(firstName, mobileNumber);
				if(contact!=null)
				{
					System.out.println(contact);
				}
				else
				{
					System.out.println("Contact not found...");
				}
				break;
			case 5:
				System.out.println("Enter First Name: ");
				firstName=scanner.nextLine();
				List<Contact> contacts=contactManager.readContacts(firstName);
				System.out.println(contacts);
				break;
			
			}
		}
		
		while(choice>0 && choice<6);
		scanner.close();
		System.out.println("Bye...Have a good day..");
		System.exit(1);
		
	}

	
	
	

}
