/*CSE 360 Group tu2
	Zachary Stewart
	Andrew Calderon
	Tyler Fujikawa
	Trevor Long
	Landon Oliver
*/
package spr24cse360;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

import javafx.scene.layout.HBox;
import spr24cse360.Account.UserType;


public class Backend extends StorageDriver {

	// Implementation
	// needs to
	// - Authenticate the instance permission
	// - encrypt/parse data
	// - create instance
	// --- Likely host main page, and depending on the button pressed
	// --- open the corresponding instance (Patient, Nurse, Doctor)
	// - Need to figure out how to 
	
	
	// Main menu UI
	// needs to implement
	// bring up correct login page
	// a successful login calls the corresponding instance. 
	
	// Data Storage and encryption
	
	private static Map<String, Account> accounts = new HashMap<>();
	private Map<Account, UserInterface> sessions = new HashMap<>();
	private Map<Account, String[]> reports = new HashMap<>();
	private static ArrayList<Account> activePatients = new ArrayList<>();
	private static SecureRandom random = new SecureRandom();
	private Encryption encryptionInterface = new Encryption();
	
	
	private static String srcDir = System.getProperty("user.dir") + "/src/spr24cse360";
	private static final String FILENAME = srcDir + "/serObjects";
	
	// Getter Functions
	
	
	public String getHistory(String username, Account account) throws Exception {
		String ret;
		if (account.getRole().equals(UserType.NURSE) || account.getRole().equals(UserType.DOCTOR)) {
			ret = encryptionInterface.decrypt(readAll(1, username));
		} else {
			ret = encryptionInterface.decrypt(readAll(1, account.getUsername()));
		}
		
		if (ret.equals(null) || ret.isBlank()) {
			ret = "No previous history found.";
		}
		
		return ret;
	}
	
	
	public String getRecords(String username, Account account) throws Exception {
		
		String ret;
		
		if (account.getRole().equals(UserType.NURSE) || account.getRole().equals(UserType.DOCTOR)) {
			ret = encryptionInterface.decrypt(readAll(2, username));
		} else {
			ret = encryptionInterface.decrypt(readAll(2, account.getUsername()));
		}
		
		if (ret.equals(null) || ret.isBlank()) {
			ret = "No previous Visits recorded.";
		}
		
		return ret;
	}
	
	public String getPrescription(String username, Account account) throws Exception {
		
		String ret;
		
		if (account.getRole().equals(UserType.NURSE) || account.getRole().equals(UserType.DOCTOR)) {
			ret = encryptionInterface.decrypt(readAll(3, username));
		} else {
			ret = encryptionInterface.decrypt(readAll(3, account.getUsername()));
		}
		
		if (ret.equals(null) || ret.isBlank()) {
			ret = "No prescriptions found.";
		}
		
		return ret;
	}
	
	public String getPatientInformation(String username, Account account) throws Exception {
		
		String ret;
	
		ret = "Name: " + account.getFullName() + "\nEmail: " + account.getEmail() + "\nPhone Number: " + account.getContactNumber() + "\nAddress: " + account.getAddress();
		
		return ret;
	}

	public boolean writeHistory(String username, Account account, String content) throws Exception{
		if(!account.getRole().equals(UserType.PATIENT)) {
			byte[] encryptedContent = encryptionInterface.encrypt(content);
			return write(1, username, encryptedContent);
		} else {
			return false;
		}
	}
	
	public boolean writeRecords(String username, Account account, String content) throws Exception {
		if(!account.getRole().equals(UserType.PATIENT)) {
			byte[] encryptedContent = encryptionInterface.encrypt(content);
			
			return write(2, username, encryptedContent);
		} else {
			return false;
		}
	}
	
	// Write Patient Prescription.
	public boolean writePrescription(String username, Account account, String content) throws Exception {
		if(account.getRole().equals(UserType.DOCTOR)) {
			byte[] encryptedContent = encryptionInterface.encrypt(content);
			
			return write(3, username, encryptedContent);
		} else {
			return false;
		}
	}
	
	public Account[] searchPatients(String partialName, UserType type) {
        Account[] possibleAccounts = new Account[accounts.size()];
        int index = 0;
        for (String name : accounts.keySet()) {
        	if(!type.equals(UserType.PATIENT)) {
	            if (accounts.get(name).getFullName().toLowerCase().contains(partialName.toLowerCase()) && accounts.get(name).getRole().equals(UserType.PATIENT)) {
	                 possibleAccounts[index] = accounts.get(name);
	                 index++;
	            }
        	} else {
        		if (accounts.get(name).getFullName().toLowerCase().contains(partialName.toLowerCase()) && !accounts.get(name).getRole().equals(UserType.PATIENT)) {
	                 possibleAccounts[index] = accounts.get(name);
	                 index++;
	            }
        	}
        }
        return possibleAccounts;
    }
	
	public Account[] getAllPatients() {
        Account[] possibleAccounts = new Account[accounts.size()];
        int index = 0;
        for (String name : accounts.keySet()) {
        	if(accounts.get(name).getRole().equals(UserType.PATIENT)) {
        		possibleAccounts[index] = accounts.get(name);
        		index++;
        	}
        	
        }
        return possibleAccounts;
    }
	
	public void sendMessage(Account sender, Account recipient, String message) {
		Message newMessage = new Message(sender, recipient, message);
		UserInterface recipientUI = sessions.get(recipient);
		UserInterface senderUI = sessions.get(sender);
		if(recipientUI != null) {
			recipientUI.receiveMessage(newMessage);
		}
		senderUI.receiveMessage(newMessage);
		sender.addMessageToStack(newMessage);
		recipient.addMessageToStack(newMessage);
		
/*		In the UI classes add to setup messaging:
  
		This in your scene as the "TextArea"
		ListView<String> messageListView = new ListView<>();
        messageListView.itemsProperty().bind(messagesProperty);

*/		
		
	
	}
	
	public void addReport(Account account, String[] data) {
		reports.put(account, data);
	}
	
	public String[] getReport(Account account){
		return reports.get(account);
	}
	
	
	//Patient input verification
	public static boolean verifyName(String name, int minLength, int maxLength) {
		String nameRegex = "^[\\p{L} .'-]*[\\p{L}]+$";
		String lengthAdditive = String.format("{%d,%d}$", minLength, maxLength);
		nameRegex = nameRegex + lengthAdditive;
                  
		Pattern pat = Pattern.compile(nameRegex); 
		if (name == null) 
			return false; 
		return pat.matcher(name).matches(); 
	}
	
	public static boolean verifyAddress(String name, int minLength, int maxLength) {
		String nameRegex = "^[0-9\\p{L} .'-]*[0-9\\p{L}]+$";
		String lengthAdditive = String.format("{%d,%d}$", minLength, maxLength);
		nameRegex = nameRegex + lengthAdditive;
                  
		Pattern pat = Pattern.compile(nameRegex); 
		if (name == null) 
			return false;
		return pat.matcher(name).matches(); 
	}
	
	//from https://www.geeksforgeeks.org/check-email-address-valid-not-java/
	public static boolean verifyEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$"; 
                  
		Pattern pat = Pattern.compile(emailRegex); 
		if (email == null) 
			return false; 
		return pat.matcher(email).matches(); 
	}
	
	public static boolean verifyPhoneNumber(String phoneNum) {
		String phoneRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";
                  
		Pattern pat = Pattern.compile(phoneRegex); 
		if (phoneNum == null) 
			return false; 
		return pat.matcher(phoneNum).matches(); 
	}
	
	//Verifies information patient puts in is valid
	//Goes through the order of input, if any is invalid it returns a table with one string containing the appropriate error.
	
	//TODO - check if name + birthyear is already taken
	public String[] verifyNewPatientInfo(String firstName, String lastName, String email, String phoneNum, String address, Integer dayBirth, Integer monthBirth, Integer yearBirth) {
		int maxStringLength = 100;
		String verifiedTable[] = {"true"};
		
		if (verifyName(firstName, 2, maxStringLength) == false) {
			verifiedTable[0] = "First Name is not filled out correctly.";
		} else if (verifyName(lastName, 2, maxStringLength) == false) {
			verifiedTable[0] = "Last Name is not filled out correctly.";
		} else if (verifyEmail(email) == false) {
			verifiedTable[0] = "Email is not filled out correctly.";
		} else if (verifyPhoneNumber(phoneNum) == false) {
			verifiedTable[0] = "Phone number is not filled out correctly.";
		} else if (verifyAddress(address, 2, maxStringLength) == false) {
			verifiedTable[0] = "Address not filled out correctly";
		} else if (dayBirth == null || monthBirth == null || yearBirth == null) {
			verifiedTable[0] = "Birthdate is not filled out correctly.";
		}
		
		return verifiedTable;
	}
	//End patient input verification
	
	public void resetPassword(Account account, String oldPassword, String newPassword) {
		try {
			
			if(encryptionInterface.decrypt(account.getPassword()).equals(oldPassword) && !newPassword.equals(null)) {
				account.setPassword(encryptionInterface.encrypt(newPassword));
				System.out.println("Successfully reset password to: " + account.getPassword());
				account.setFirstLogin(false);
			} else {
				
				System.out.println("Wrong password try again");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateContactInformation(Account account, String newEmail, String newPhone, String newAddress) {
		try {
				account.setEmail(newEmail);
				account.setContactNumber(newPhone);
				account.setAddress(newAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ArrayList<Account> getActivePatients() {
		ArrayList<Account> copy = activePatients;
		return copy;
	}
	
	public void addActivePatient(Account patient) {
		activePatients.add(patient);
		updateActivePatient();
	}
	
	public void removeActivePatient(Account patient) {
		activePatients.remove(patient);
		updateActivePatient();
	}
	
	public void updateActivePatient() {
		for(UserInterface ui : sessions.values()) {
			ui.activePatientUpdate();
		}
	}
	
	// Plumbing for Getter Functions
	
	
	
	
	
	
	
	// Creation Functions
	
	
	
	public UserInterface getNewInstance(Account user) {
		UserType role = user.getRole();
		accounts.put(user.getUsername(), user);
		switch (role) {
		case UserType.NURSE :
			Nurse nurse = new Nurse(this, user);
			return nurse;
			
		case UserType.DOCTOR : 
			Doctor doctor = new Doctor(this, user);
			return doctor;
		
		case UserType.PATIENT : 
			Patient patient = new Patient(this, user);
			return patient;
		}
		return null;
	}
	
	
	public String generateReport(	LocalDate date, String bodyTemp, String pulse, 
									String respiration, String bPressure, String bOxygen, 
									String height, String weight, String bGLevel, 
									String allergies, String nurseNote, String drNote) {
		
//		8 Vitals: 
//		Body Temperature.
//		Pulse Rate.
//		Respiration Rate.
//		Blood Pressure.
//		Blood Oxygen.
//		Height.
//		Weight.
//		Blood Glucose Level.
		
		String ret = "";
		String delimiter = "\n--------------------------\n";
		
		ret = 	date.toString() + 
				"\nBody Temerature:\t" + bodyTemp + "\t\tPulse Rate:\t" + pulse + 
				"\nRespiration Rate:\t" + respiration + "\t\tBlood Pressure:\t" + bPressure +
				"\nBlood Oxygen Level:\t" + bOxygen + "\t\tBlood Glucose Level:\t" + bGLevel +
				"\nHeight:\t" + height + "\t\tWeight:\t" + weight +
				"\nAllergies and Patient Concerns:\n" + allergies + 
				"\nNurse's Notes:\n" + nurseNote + "\nDoctor's Notes:\n" + drNote +
				delimiter;
		
		return ret;
		
	}
	
	
	
	
	public String addNewAccount (String firstName, String lastName, 
								  String email,		String contactNumber, 
								  String address,	int dayOfBirth, 
								  int monthOfBirth, int yearOfBirth,
							 	  UserType role) {
		
		String parsedFirstNameLC = firstName.toLowerCase();
		String parsedLastNameLC = lastName.toLowerCase();
		
		String parsedFirstNameStandard = firstName.substring(0, 1).toUpperCase() + firstName.substring(1, firstName.length()).toLowerCase();
		String parsedLastNameStandard = lastName.substring(0, 1).toUpperCase() + lastName.substring(1, lastName.length()).toLowerCase();
		
		
		
		String username = generateUsername(parsedFirstNameLC, parsedLastNameLC, yearOfBirth);
		String tempPassword = new BigInteger(130, random).toString(32);
		byte[] cipherPassword = null;
		
		addPatientFile(username);
		
		
		try {
			cipherPassword = encryptionInterface.encrypt(tempPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(tempPassword);
		Account newPatient = new Account(username, cipherPassword, 
								   parsedFirstNameStandard, parsedLastNameStandard, 
								   monthOfBirth, dayOfBirth,
								   yearOfBirth, role);
		newPatient.setAddress(address);
		newPatient.setContactNumber(contactNumber);
		newPatient.setEmail(email);
		
		accounts.put(username, newPatient);
		
		return tempPassword;
		
	}
	
	public UserInterface loginProcedure(String username, String password) {
		
		Account newLogin = accounts.get(username);
		
		
		if(newLogin == null || newLogin.isLoggedIn() ) {
			System.out.println("Invalid Login Credentials");
			return null;
		}
		
		try {
			if (encryptionInterface.decrypt(newLogin.getPassword()).equals(password)){
				UserInterface ui = getNewInstance(newLogin);
				newLogin.setLoggedIn(true);
				sessions.put(newLogin, ui);
				if (newLogin.getRole().equals(UserType.DOCTOR) || newLogin.getRole().equals(UserType.NURSE)) {
					ui.activePatientUpdate();
				}
				return ui;
				
			} else {
				System.out.println("Invalid Login Credentials");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error (patientLoginProcedure):");
			
		}
		
		
		
		return null;
	
		
	}
	
	public void logoutProcedure(Account account) {
		account.setLoggedIn(false);
		sessions.remove(account);
	}
	
	
	
	private static void serializeData(Account data) {
		String path = FILENAME + "/" + data.getUsername() + ".ser";
		
		File directory = new File(FILENAME);
		
		if(!directory.exists()) {
			directory.mkdir();
		}
		
		try (FileOutputStream fileout = new FileOutputStream(path);
			 ObjectOutputStream out = new ObjectOutputStream(fileout)) {
			out.writeObject(data);
			System.out.println("Data saved in: " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Account deserializeData(String username) {
		String path = FILENAME + "/" + username + ".ser";
		
		File directory = new File(FILENAME);
		
		if(!directory.exists()) {
			directory.mkdir();
		}
		
		Account deserializedData = null;
		try (FileInputStream fileIn = new FileInputStream(path);
			 ObjectInputStream in = new ObjectInputStream(fileIn)){
			deserializedData = (Account) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Loading data for: " + username);
		return deserializedData;
	}
	
	

	
	private String generateUsername(String firstName, String lastName, int yearOfBirth) {
		String initialUsername = lastName + "_" + firstName + String.valueOf(yearOfBirth);
		String finalizedUsername = initialUsername;
		
		return checkIfUsernameExists(finalizedUsername);
	}
	
	
	public void startup() {
		String filePath = FILENAME;
		File folder = new File(filePath);
		File[] files = folder.listFiles((dir, name) -> !name.startsWith("."));
		
		for(File file : files) {
			String nameWithoutExtension = file.getName().substring(0, file.getName().lastIndexOf("."));
			accounts.put(nameWithoutExtension, deserializeData(nameWithoutExtension));
		}
		
		
	}
	
	public void close() {
		for(Map.Entry<String, Account> entry : accounts.entrySet()) {
			logoutProcedure(entry.getValue());
			serializeData(entry.getValue());
		}
		
		accounts.clear();
	}
}
