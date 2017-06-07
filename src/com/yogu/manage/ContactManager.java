package com.yogu.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import com.yogu.application.ContactManagement;
import com.yogu.model.Address;
import com.yogu.model.Category;
import com.yogu.model.Contact;
import com.yogu.model.ContactDetails;
import com.yogu.model.Name;
import com.yogu.model.PhoneDetail;

public class ContactManager {
	private final Map<String, List<Contact>> contactsByFirstName = new TreeMap<>();

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");

	private static final Scanner scanner = ContactManagement.scanner;

	public void addContact(Contact contact) {

		if (contactsByFirstName.containsKey(contact.getName().getFirstName())) {
			contactsByFirstName.get(contact.getName().getFirstName()).add(contact);
		} else {
			List<Contact> contacts = new LinkedList<>();
			contacts.add(contact);
			contactsByFirstName.put(contact.getName().getFirstName(), contacts);
		}
	}

	public boolean deleteContact(String firstName) {

		Contact contactToBeDeleted = searchSingleContact(firstName);
		boolean isDeleted = false;
		for (Entry<String, List<Contact>> contactEntry : contactsByFirstName.entrySet()) {
			if (contactEntry.getKey().startsWith(firstName)) {
				for (Contact contact : contactEntry.getValue()) {
					if (contact.equals(contactToBeDeleted)) {
						isDeleted = contactEntry.getValue().remove(contact);
					}

				}
			}
		}
		return isDeleted;

	}

	public Map<String, List<Contact>> readContacts() {

		return contactsByFirstName;

	}

	public boolean updateContact(String firstName) {

		Contact contactToBeUpdated = searchSingleContact(firstName);
		if (contactToBeUpdated != null) {

			System.out.println("Do you want to update Last Name (Y/N): ");
			if (getUserChoice()) {
				contactToBeUpdated.getName().setLastName(scanner.nextLine());
			}

			System.out.println("Do you want to Add MobileNumber (Y/N): ");
			if (getUserChoice()) {

				contactToBeUpdated.getContactDetails().getPhoneNumbers().addAll(getPhoneDetailsFromUser());

			}
			System.out.println("Do you want to Delete Mobile Number (Y/N): ");

			if (getUserChoice()) {
				System.out.println("Enter Mobile Number to delete: ");
				deleteMobileNumber(Long.parseLong(scanner.nextLine()), contactToBeUpdated);
			}

			System.out.println("Do you want to update Mobile Number (Y/N): ");
			if (getUserChoice()) {
				System.out.println("Enter Old Mobile Number: ");
				long oldMobileNumber = Long.parseLong(scanner.nextLine());
				System.out.println("Enter New Mobile Number: ");
				long newMobileNumber = Long.parseLong(scanner.nextLine());

				updateMobileNumber(oldMobileNumber, newMobileNumber, contactToBeUpdated);
			}

			System.out.println("Do you want to update Email (Y/N): ");
			if (getUserChoice()) {
				System.out.println("Enter new email: ");
				contactToBeUpdated.setEmail(scanner.nextLine());
			}

			System.out.println("Do you want to update Address (Y/N): ");
			if (getUserChoice()) {
				Address addressToBeUpdated = contactToBeUpdated.getAddress();

				System.out.println("Do you want to update Street (Y/N): ");
				if (getUserChoice()) {
					System.out.println("Enter Street Name: ");
					String street = scanner.nextLine();
					addressToBeUpdated.setStreet(street);
				}

				System.out.println("Do you want to update City (Y/N): ");
				if (getUserChoice()) {
					System.out.println("Enter City: ");
					addressToBeUpdated.setCity(scanner.nextLine());
				}

				System.out.println("Do you want to update State (Y/N): ");
				if (getUserChoice()) {
					System.out.println("Enter State: ");
					addressToBeUpdated.setState(scanner.nextLine());
				}

				System.out.println("Do you want to update Zip Code (Y/N): ");
				if (getUserChoice()) {
					System.out.println("Enter Zip Code: ");
					int code=Integer.parseInt(scanner.nextLine());
					addressToBeUpdated.setZipCode(code);
				}

			}

			return true;
		}

		return false;
	}

	public Contact searchContact(String firstName, long mobileNumber) {

		if (contactsByFirstName.containsKey(firstName)) {
			List<Contact> contacts = contactsByFirstName.get(firstName);

			for (Contact contact : contacts) {

				List<PhoneDetail> phoneNumbers = contact.getContactDetails().getPhoneNumbers();

				for (PhoneDetail phoneDetail : phoneNumbers) {
					if (phoneDetail.getMobileNumber() == mobileNumber) {
						return contact;
					}
				}

			}
		}
		return null;

	}

	public List<Contact> searchContact(String firstName) {

		List<Contact> contacts = new ArrayList<>();
		Set<Entry<String, List<Contact>>> contactsEntrySet = contactsByFirstName.entrySet();
		for (Entry<String, List<Contact>> contactEntry : contactsEntrySet) {
			if (contactEntry.getKey().startsWith(firstName)) {
				contacts.addAll(contactEntry.getValue());
			}
		}
		return contacts;

	}

	private Contact searchSingleContact(String firstName) {
		List<Contact> contacts = searchContact(firstName);

		for (int i = 0; i < contacts.size(); i++) {
			System.out.println((i + 1) + ") " + contacts.get(i).getContactDetails().getPhoneNumbers());
		}

		System.out.println("Enter your choice: ");
		int value = Integer.parseInt(scanner.nextLine());
		return contacts.get(value - 1);

	}

	public Contact getContactFromUser() {
		Contact contact = new Contact();
		Name name = new Name();
		Address address = new Address();

		System.out.println("Enter first name: ");
		String firstName = scanner.nextLine();
		name.setFirstName(firstName);

		ContactDetails contactDetails = new ContactDetails();
		List<PhoneDetail> phoneDetails = getPhoneDetailsFromUser();
		contactDetails.setPhoneNumbers(phoneDetails);

		System.out.println("Do you want to add last name?.y or n");
		if (getUserChoice()) {
			System.out.println("Enter last name: ");
			String lastName = scanner.nextLine();
			name.setLastName(lastName);
		}

		System.out.println("Do you want to add street?.y or n");
		if (getUserChoice()) {
			System.out.println("Enter street name: ");
			String streetName = scanner.nextLine();
			address.setStreet(streetName);

		}
		System.out.println("Do you want to add city?.y or n");
		if (getUserChoice()) {
			System.out.println("Enter city: ");
			String city = scanner.nextLine();
			address.setCity(city);

		}
		System.out.println("Do you want to add state?.y or n");
		if (getUserChoice()) {
			System.out.println("Enter state: ");
			String state = scanner.nextLine();
			address.setState(state);

		}
		System.out.println("Do you want to add zipcode?.y or n");
		if (getUserChoice()) {
			System.out.println("Enter zipcode: ");
			int zipCode = Integer.parseInt(scanner.nextLine());
			address.setZipCode(zipCode);

		}

		System.out.println("Do you want to add birthday?.y or n");
		if (getUserChoice()) {
			try {
				System.out.println("Enter Birth day (dd/mm/yyyy): ");
				String birthDay = scanner.nextLine();
				Date birthDate = simpleDateFormat.parse(birthDay);
				contact.setBirthDay(birthDate);

			} catch (ParseException e) {
				System.out.println("Please enter Correct format!");
			}
		}
		System.out.println("Do you want to add email id?.y or n");
		if (getUserChoice()) {
			System.out.println("Enter email-id: ");
			String emailId = scanner.nextLine();
			contact.setEmail(emailId);

		}

		contact.setName(name);
		contact.setAddress(address);
		contact.setContactDetails(contactDetails);

		return contact;
	}

	private void deleteMobileNumber(long mobileNumber, Contact contact) {
		List<PhoneDetail> phoneDetails = contact.getContactDetails().getPhoneNumbers();

		for (PhoneDetail phoneDetail : phoneDetails) {
			if (phoneDetail.getMobileNumber() == mobileNumber) {
				phoneDetails.remove(phoneDetail);
			}
		}
	}

	private void updateMobileNumber(long oldMobileNumber, long newMobileNumber, Contact contact) {
		List<PhoneDetail> phoneDetails = contact.getContactDetails().getPhoneNumbers();

		for (PhoneDetail phoneDetail : phoneDetails) {
			if (phoneDetail.getMobileNumber() == oldMobileNumber) {
				phoneDetail.setMobileNumber(newMobileNumber);
			}
		}
	}

	private boolean getUserChoice() {
		return "Y".equalsIgnoreCase(scanner.nextLine());
	}

	private List<PhoneDetail> getPhoneDetailsFromUser() {
		List<PhoneDetail> phoneDetails = new LinkedList<>();
		boolean hasMoreNumber;
		do {
			System.out.println("Enter mobile Number: ");
			long mobileNumber = Long.parseLong(scanner.nextLine());

			PhoneDetail phoneDetail = new PhoneDetail();
			phoneDetail.setMobileNumber(mobileNumber);
			System.out.println("Do you want to add category?.y or n");
			if (getUserChoice()) {
				System.out.println("Enter Category(MOBILE/HOME/OFFICE/OTHER): ");
				String category = scanner.nextLine();

				phoneDetail.setCategory(Category.valueOf(category.toUpperCase()));
			}
			phoneDetails.add(phoneDetail);
			System.out.println("Do you want to add another number? Y or N ");
			hasMoreNumber = getUserChoice();

		} while (hasMoreNumber);
		return phoneDetails;
	}

	public int displayMainMenu() {
		System.out.println("Enter your choice: ");
		System.out.println("1) Add Contact");
		System.out.println("2) Update Contact");
		System.out.println("3) Delete Contact");
		System.out.println("4) Search Contact");
		System.out.println("5) Read Contacts");
		System.out.println("6) Exit");

		return Integer.parseInt(scanner.nextLine());
	}

}
