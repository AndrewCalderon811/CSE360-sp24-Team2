/*CSE 360 Group tu2
	Zachary Stewart
	Andrew Calderon
	Tyler Fujikawa
	Trevor Long
	Landon Oliver
*/
package spr24cse360;

import java.io.Serializable;
import java.util.Stack;


public class Account implements Serializable {
	
	// Serialization
	private static final long serialVersionUID = 1L;
	
	// User Information
	private final String username;
	private final String firstName;
	private final String lastName;
	private byte[] password;
	private final UserType role;
	private int monthOfBirth;
	private int dayOfBirth;
	private int yearOfBirth;
	private String email = "";
	private String contactNumber = "";
	private String address = "";
	
	public enum UserType{
		DOCTOR,
		NURSE,
		PATIENT
	}
	
	// Login 
	private boolean firstLogin = true;
	private boolean loggedIn = false;
	
	// Messaging
	private Stack<Message> messageLog;
	
	
	
	public Account(String usernameIn, byte[] passwordIn, String firstNameIn, String lastNameIn, int month, int day, int year, UserType role) {
		firstName = firstNameIn;
		lastName = lastNameIn;
		username = usernameIn;
		password = passwordIn;
		monthOfBirth = month;
		dayOfBirth = day;
		yearOfBirth = year;
		this.role = role;
		if(messageLog == null) {
			messageLog = new Stack<>();
		}
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPatientInformation() throws Exception {
		
		String ret;
	
		ret = "Name: " + getFullName() + "\nEmail: " + getEmail() + "\nPhone Number: " + getContactNumber() + "\nAddress: " + getAddress();
		
		return ret;
	}
	
	public String getUsername() {
		return username;
	}
	
	public byte[] getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setPassword(byte[] newPassword) {
		password = newPassword;
		System.out.println(password);
	}
	
	public void setEmail(String newEmail) {
		email = newEmail;
	}
	
	public void setContactNumber(String newContactNumber) {
		contactNumber = newContactNumber;
	}
	
	public void setAddress(String newAddress) {
		address = newAddress;
	}

	public int getMonthOfBirth() {
		return monthOfBirth;
	}

	public void setMonthOfBirth(int monthOfBirth) {
		this.monthOfBirth = monthOfBirth;
	}

	public int getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(int dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	
	public String getBirthdate() {
		return monthOfBirth + "/" + dayOfBirth + "/" + yearOfBirth;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.loggedIn = isLoggedIn;
	}

	public Stack<Message> getMessageLog() {
		Stack<Message> copy = messageLog;
		return copy;
	}
	
	public void addMessageToStack(Message message) {
		messageLog.push(message);
	}

	public UserType getRole() {
		return role;
	}
	
}
