package com.yogu.application;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.yogu.manage.ContactManager;
import com.yogu.model.Contact;

public class ContactManagement {

	public static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		ContactManager contactManager = new ContactManager();
		int choice;
		String firstName;
		Contact contact;
		do {
			choice = contactManager.displayMainMenu();
			switch (choice) {

			case 1:
				contact = contactManager.getContactFromUser();
				contactManager.addContact(contact);
				System.out.println("Contact Added Successfully");
				break;
			case 2:
				System.out.println("Enter First Name: ");
				firstName = scanner.nextLine();
				
				if (contactManager.updateContact(firstName)) {
					System.out.println("Contact updated successfully");
				} else {
					System.out.println("Contact not found");
				}
				break;
			case 3:
				System.out.println("Enter First Name: ");
				firstName = scanner.nextLine();

				boolean isDeleted = contactManager.deleteContact(firstName);
				if (isDeleted) {
					System.out.println("Contact deleted successfully...");
				} else {
					System.out.println("Contact not deleted successfully...");
				}
				break;

			case 4:
				System.out.println("Enter First Name: ");
				firstName = scanner.nextLine();
				List<Contact> contacts = contactManager.searchContact(firstName);
				for (Contact contactToSearch : contacts) {
					System.out.println(contactToSearch + "");
				}
				break;
			case 5:
				Map<String, List<Contact>> allContacts = contactManager.readContacts();
				Set<Map.Entry<String, List<Contact>>> contactsSet = allContacts.entrySet();
				for (Map.Entry<String, List<Contact>> contactSet : contactsSet) {
					List<Contact> ContactsToRead = contactSet.getValue();
					for (Contact contactToRead : ContactsToRead) {
						System.out.print(contactToRead + " ");
						System.out.println("");
					}
				}
				break;
			}
		} while (choice > 0 && choice < 6);
		scanner.close();
		System.out.println("Bye...Have a good day..");
		System.exit(1);

	}

}
