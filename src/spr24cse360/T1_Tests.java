package spr24cse360;

import static org.junit.jupiter.api.Assertions.*;

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
	Account account = new Account("usernamein", randomBytes, "firstname", "lastname", 1, 1, 2024, UserType.DOCTOR);
	

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
	
}
