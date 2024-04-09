/*CSE 360 Group tu2
	Zachary Stewart
	Andrew Calderon
	Tyler Fujikawa
	Trevor Long
	Landon Oliver
*/
package spr24cse360;

public class Main {

	public static void main(String[] args) {
		Backend backend = new Backend();
		
		backend.startup();
		
//		String success1 = backend.addNewAccount("admin", "doctor", "(email)", "(phone)", "(streetAddress)", 0, 0, 0, UserType.DOCTOR);
//		String success2 = backend.addNewAccount("andrew", "nurse", "(email)", "(phone)", "(streetAddress)", 0, 0, 0, UserType.NURSE);
//		String success3 = backend.addNewAccount("admin", "patient", "(email)", "(phone)", "(streetAddress)", 0, 0, 0, UserType.PATIENT);

//		System.out.println(success);
		
		// test
		
		LandingPage LP = new LandingPage();
		LP.launchLandingPage(backend);
		
		
		
		
		
		backend.close();
	}
	
}
