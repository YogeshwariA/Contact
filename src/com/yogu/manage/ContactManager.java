package com.yogu.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.yogu.application.ContactManagement;
import com.yogu.model.Address;
import com.yogu.model.Category;
import com.yogu.model.Contact;
import com.yogu.model.ContactDetails;
import com.yogu.model.Name;
import com.yogu.model.PhoneDetail;

public class ContactManager {
	private Map<String, List<Contact>> contactsByFirstName = new TreeMap<>();

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

	public boolean deleteContact(String firstName, long mobileNumber) {

		Contact contact = searchContact(firstName, mobileNumber);
		if (contact != null) {
			return contactsByFirstName.get(firstName).remove(contact);
		}

		return false;

	}

	public Map<String, List<Contact>> readContacts() {

		return contactsByFirstName;

	}

	public boolean updateContact(String firstName, long mobileNumber) {

		Contact contactToBeUpdated = searchContact(firstName, mobileNumber);
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
					addressToBeUpdated.setZipCode(Integer.parseInt(scanner.nextLine()));
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

	public Contact getContactFromUser() {
		Contact contact = new Contact();

		System.out.println("Enter first name: ");
		String firstName = scanner.nextLine();

		System.out.println("Enter last name: ");
		String lastName = scanner.nextLine();

		ContactDetails contactDetails = new ContactDetails();
		List<PhoneDetail> phoneDetails = getPhoneDetailsFromUser();
		contactDetails.setPhoneNumbers(phoneDetails);

		System.out.println("Enter street name: ");
		String streetName = scanner.nextLine();

		System.out.println("Enter city: ");
		String city = scanner.nextLine();

		System.out.println("Enter state: ");
		String state = scanner.nextLine();

		System.out.println("Enter zipcode: ");
		int zipCode = Integer.parseInt(scanner.nextLine());

		System.out.println("Enter Birth day (dd/mm/yyyy): ");
		String birthDay = scanner.nextLine();

		try {
			Date birthDate = simpleDateFormat.parse(birthDay);
			contact.setBirthDay(birthDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Enter email-id: ");
		String emailId = scanner.nextLine();

		Name name = new Name();
		name.setFirstName(firstName);
		name.setLastName(lastName);

		Address address = new Address();
		address.setStreet(streetName);
		address.setCity(city);
		address.setState(state);
		address.setZipCode(zipCode);

		contact.setEmail(emailId);
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

			System.out.println("Enter Category(MOBILE/HOME/OFFICE/OTHER): ");
			String category = scanner.nextLine();

			phoneDetail.setCategory(Category.valueOf(category.toUpperCase()));

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
