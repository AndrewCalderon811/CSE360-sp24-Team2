package spr24cse360;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import spr24cse360.Account.UserType;

class T1_Tests {
	
	// Tests for all classes with only one degree of implementation (No communication between classes)

	// Encryption Tests
	Encryption encryption = new Encryption();
	
	@Test
	void Encrypt1() throws Exception {
		assertEquals("Hello World", encryption.decrypt(encryption.encrypt("Hello World")));
	}
	
	@Test
	void Encrypt2() throws Exception {
		assertEquals("This is a long String that could be too long\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", encryption.decrypt(encryption.encrypt("This is a long String that could be too long\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")));
	}
	
	@Test
	void Encrypt3() throws Exception {
		assertEquals("`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?", encryption.decrypt(encryption.encrypt("`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?")));
	}
	
	@Test
	void Encrypt4() throws Exception {
		assertEquals("", encryption.decrypt(encryption.encrypt("")));
	}
	
	// Account Test
	
	byte[] randomBytes = {-65, 53, 24, -91, 77, -12, 101, -33, -75, 112};
	Account account = new Account("lastname_firstname2024", randomBytes, "firstname", "lastname", 1, 1, 2024, UserType.DOCTOR);
	

	@Test
	void Account1() {
		assertEquals("firstname lastname", account.getFullName());				
	}
	
	@Test
	void Account2() {
		assertEquals("firstname", account.getFirstName());
	}
	
	@Test
	void Account3() {
		assertEquals("lastname", account.getLastName());
	}
	
	@Test
	void Account4() throws Exception {
		assertEquals("Name: firstname lastname\nEmail: \nPhone Number: \nAddress: ", account.getPatientInformation());
	}
	
	@Test
	void Account5() throws Exception {
		assertEquals("lastname_firstname2024", account.getUsername());
	}
	
	@Test
	void Account6() throws Exception {
		assertEquals(randomBytes, account.getPassword());
	}

	@Test
	void Account7() throws Exception {
		assertEquals("", account.getEmail());
	}
	
	@Test
	void Account8() throws Exception {
		assertEquals("", account.getContactNumber());
	}
	
	@Test
	void Account9() throws Exception {
		assertEquals("", account.getAddress());
	}
	
	@Test
	void Account10() throws Exception {
		assertEquals(1, account.getMonthOfBirth());
	}
	
	@Test
	void Account11() throws Exception {
		assertEquals(1, account.getDayOfBirth());
	}
	
	@Test
	void Account12() throws Exception {
		assertEquals(2024, account.getYearOfBirth());
	}
	
	@Test
	void Account13() throws Exception {
		assertEquals("1/1/2024", account.getBirthdate());
	}
	
	@Test
	void Account14() throws Exception {
		assertEquals(true, account.isFirstLogin());
	}
	
	@Test
	void Account15() throws Exception {
		assertEquals(false, account.isLoggedIn());
	}
	
	@Test
	void Account16() throws Exception {
		assertEquals(UserType.DOCTOR, account.getRole());
	}
	
	// Messages Test
	
	String txt = "This is a message";
	Account account2 = new Account("lastname2_firstname2024", randomBytes, "firstname2", "lastname2", 1, 1, 2024, UserType.PATIENT);
	Message message = new Message(account2, account, txt);
	LocalDate timeSent = LocalDate.now();
	
	@Test
	void Message1() throws Exception {
		assertEquals(timeSent.toString() + " firstname2 lastname2: This is a message\n\n" , message.toString());
	}
	
	@Test
	void Message2() throws Exception {
		assertEquals(false, message.isViewed());
	}
	
	@Test
	void Message3() throws Exception {
		assertEquals(account, message.getReciever());
	}
	
	@Test
	void Message4() throws Exception {
		assertEquals(account2, message.getSender());
	}
	
	// Back-end Tests
	
	Backend backend = new Backend();
	
		// File Management Tests
		// using "account" as the get information account
		
	@Test
	void History1() throws Exception {
		backend.writeHistory(account.getUsername(), account, "This is generated Content");
		assertEquals(true, backend.getHistory(account.getUsername(), account).contains("This is generated Content"));
	}
	
	@Test
	void History2() throws Exception {
		backend.writeHistory(account.getUsername(), account, "abcdefghijklmnopqrstuvwxyz");
		assertEquals(true, backend.getHistory(account.getUsername(), account).contains("abcdefghijklmnopqrstuvwxyz"));
		
	}
	
	@Test
	void Records1() throws Exception {
		backend.writeRecords(account.getUsername(), account, "This is generated Content");
		assertEquals(true, backend.getRecords(account.getUsername(), account).contains("This is generated Content"));
	}
	
	@Test
	void Records2() throws Exception {
		backend.writeRecords(account.getUsername(), account, "abcdefghijklmnopqrstuvwxyz");
		assertEquals(true, backend.getRecords(account.getUsername(), account).contains("abcdefghijklmnopqrstuvwxyz"));
	}
	
	@Test
	void Prescription1() throws Exception {
		backend.writePrescription(account.getUsername(), account, "This is generated Content");
		assertEquals(true, backend.getPrescription(account.getUsername(), account).contains("This is generated Content"));
	}
	
	@Test
	void Prescription2() throws Exception {
		backend.writePrescription(account.getUsername(), account, "abcdefghijklmnopqrstuvwxyz");
		assertEquals(true, backend.getPrescription(account.getUsername(), account).contains("abcdefghijklmnopqrstuvwxyz"));
	}
	
	@Test
	void SearchPatient1() throws Exception {
		backend.startup();
		boolean ret = false;
		
		if(backend.searchPatients("a", UserType.DOCTOR).length > 1) {
			ret = true;
		}
		assertEquals(true, ret);
		backend.close();
	}
	
	@Test
	void SearchPatient2() {
		backend.startup();
		assertEquals(null, backend.searchPatients("qwertyuiopasdfghjkl", UserType.DOCTOR)[0]);
		backend.close();
	}
	
	@Test
	void GetAllPatients() {
		backend.startup();
		boolean ret = false;
		if(backend.getAllPatients().length > 1) {
			ret = true;
		}
		assertEquals(true, ret);
		backend.close();
	}
	
	//Verifying new account input
	
	//Names
	@Test
	void input_verifyName1() {
		assertEquals(true, backend.verifyName("John", 2, 100)); //Normal
	}

	@Test
	void input_verifyName2() {
		assertEquals(false, backend.verifyName("J", 2, 100)); //Too short
	}

	@Test
	void input_verifyName3() {
		assertEquals(false, backend.verifyName("", 2, 100)); //Blank
	}

	@Test
	void input_verifyName4() {
		assertEquals(false, backend.verifyName("TooManyCharacters", 2, 16)); //Too long
	}

	@Test
	void input_verifyName5() {
		assertEquals(true, backend.verifyName("JustEnoughCharacters", 2, 20)); //Perfect fit
	}

	@Test
	void input_verifyName6() {
		assertEquals(false, backend.verifyName("Bob jr", 2, 16)); //Space in middle
	}

	@Test
	void input_verifyName7() {
		assertEquals(false, backend.verifyName("Bob ", 2, 16)); //Space after
	}

	@Test
	void input_verifyName8() {
		assertEquals(false, backend.verifyName(" Bob", 2, 16)); //Space before
	}

	@Test
	void input_verifyName9() {
		assertEquals(false, backend.verifyName(" Bob ", 2, 16)); //Space before and after
	}
	
	//Addresses
	@Test
	void input_verifyAddress1() {
		assertEquals(true, backend.verifyAddress("123 place street", 2, 100)); //Normal
	}

	@Test
	void input_verifyAddress2() {
		assertEquals(false, backend.verifyAddress("place", 10, 100)); //Too short
	}

	@Test
	void input_verifyAddress3() {
		assertEquals(false, backend.verifyAddress("", 2, 100)); //Blank
	}

	@Test
	void input_verifyAddress4() {
		assertEquals(false, backend.verifyAddress("1234 West Street", 2, 10)); //Too long
	}

	@Test
	void input_verifyAddress5() {
		assertEquals(true, backend.verifyAddress("1234 West Street", 2, 16)); //Perfect fit
	}

	@Test
	void input_verifyAddress7() {
		assertEquals(false, backend.verifyAddress("1234 West Street ", 2, 100)); //Space after
	}

	@Test
	void input_verifyAddress8() {
		assertEquals(false, backend.verifyAddress(" 1234 West Street", 2, 100)); //Space before
	}

	@Test
	void input_verifyAddress9() {
		assertEquals(false, backend.verifyAddress(" 1234 West Street ", 2, 100)); //Space before and after
	}
	
	//Emails
	@Test
	void input_verifyEmail1() {
		assertEquals(true, backend.verifyEmail("easy@email.com")); //regular
	}

	@Test
	void input_verifyEmail2() {
		assertEquals(false, backend.verifyEmail("easyemail.com")); //Missing @
	}

	@Test
	void input_verifyEmail3() {
		assertEquals(false, backend.verifyEmail("easy@email.c")); //Domain too short
	}

	@Test
	void input_verifyEmail4() {
		assertEquals(false, backend.verifyEmail("@email.com")); //Missing name
	}
	
	//Numbers
	@Test
	void input_verifyPhoneNumber1() {
		assertEquals(true, backend.verifyPhoneNumber("1234567890")); //regular
	}
	
	@Test
	void input_verifyPhoneNumber2() {
		assertEquals(false, backend.verifyPhoneNumber("123456789")); //too short
	}
	
	@Test
	void input_verifyPhoneNumber3() {
		assertEquals(false, backend.verifyPhoneNumber("12345678901")); //too long
	}
	
	//Verifying full table of new patient info
	@Test
	void input_verifyNewPatientInfo1() { //regular
		Integer day = 5;
		Integer month = 5;
		Integer year = 2005;
		assertEquals("true", backend.verifyNewPatientInfo("Trevor", "Long", "email@gmail.com", "1234567890", "123 W Street", day, month, year));
	}
	
	@Test
	void input_verifyNewPatientInfo2() { //birthday is missing
		Integer day = null;
		Integer month = 5;
		Integer year = 2005;
		assertEquals("Birthdate is not filled out correctly.", backend.verifyNewPatientInfo("Trevor", "Long", "email@gmail.com", "1234567890", "123 W Street", day, month, year));
	}
	
	@Test
	void input_verifyNewPatientInfo3() { //birthday is missing
		Integer day = null;
		Integer month = null;
		Integer year = 2005;
		assertEquals("Birthdate is not filled out correctly.", backend.verifyNewPatientInfo("Trevor", "Long", "email@gmail.com", "1234567890", "123 W Street", day, month, year));
	}
	
	//End new account input
	
	//contact info
	@Test
	void UpdateContactInfo1() {
		backend.startup();
		backend.updateContactInformation(account, "newemail@gmail.com", "1234567890", "123 west street");
		try {
			String contactInfo = backend.getPatientInformation(account);
			String match = "Name: " + account.getFullName() + "\nEmail: " + "newemail@gmail.com" + "\nPhone Number: " + "1234567890" + "\nAddress: " + "123 west street";
			assertEquals(match, contactInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backend.close();
	}
	
	@Test
	void getReport1() {
		String[] report = backend.getReport(account);
		boolean check = false;
		if (report == null) {
			check = true;
		}
		assertEquals(true, check);
	}
	
	@Test
	void getReport2() {
		backend.addReport(account, new String[] {"filler report", "aaaaaaa"});
		String[] report = backend.getReport(account);
		boolean check = false;
		if (report != null) {
			check = true;
		}
		assertEquals(true, check);
	}
	
	@Test
	void addActivePatient1() {
		backend.startup();
		ArrayList<Account> activePatients = backend.getActivePatients();
		boolean check = false;
		if (activePatients != null && activePatients.size() == 0) {
			check = true;
		}
		assertEquals(true, check);

		check = false;
		backend.addActivePatient(account);
		activePatients = backend.getActivePatients();
		if (activePatients != null && activePatients.size() >= 1) {
			check = true;
		}
		assertEquals(true, check);
		
		backend.removeActivePatient(account);
		activePatients = backend.getActivePatients();
		check = false;
		if (activePatients != null && activePatients.size() == 0) {
			check = true;
		}
		assertEquals(true, check);
		
		backend.close();
	}
	
	@Test
	void resetPass1() {
		//current pass -> encryptionInterface.decrypt(randomBytes)
		backend.startup();
		boolean passed = false;
		try {
			String oldPass = "oldpassword";
			account.setPassword(encryption.encrypt(oldPass));
			backend.resetPassword(account, oldPass, "123Password321");
			passed = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(true, passed);
		passed = false;
		try {
			assertEquals(encryption.decrypt(account.getPassword()), "123Password321");
			passed = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, passed);
	}
}
