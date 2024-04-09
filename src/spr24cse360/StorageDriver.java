/*CSE 360 Group tu2
	Zachary Stewart
	Andrew Calderon
	Tyler Fujikawa
	Trevor Long
	Landon Oliver
*/
package spr24cse360;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;





public class StorageDriver {
	
	private String srcDir = System.getProperty("user.dir") + "/src/spr24cse360/data";
	//private String srcDir2 = System.getProperty("user.dir") + "/src/spr24cse360/data";
	
	
	protected String[] getPatientList(){
		
		File directory = new File(srcDir);
		
		if(!directory.exists()) {
			directory.mkdir();
		}
		
		File[] files = directory.listFiles((dir, name) -> !name.startsWith("."));
		String[] ret = new String[files.length];
		
		for(int i = 0; i < files.length; i++) {
			ret[i] = files[i].getName();
		}
		return ret;
	}

	// Writes data to a file given use case and patient identifier. 
	// useCase defines the type of data being read for the patient
	// Use 1 for patient history
	// Use 2 for patient visit records
	protected boolean write(int useCase, String username, byte[] content) {
		
		LocalDate date = LocalDate.now();
		
		String patientDir = "/" + username;
		String dirAdditive = "";
		String writeLocation = "/" + date.toString();
		
		switch(useCase) {
		case 1:
			dirAdditive = "/history";
			break;
		case 2:
			dirAdditive = "/records";
			break;
		case 3:
			dirAdditive = "/prescriptions";
			break;
		default:
			System.out.println("Read function is not being used properly, please read docs");
			System.out.println("Program will continue, path used to write is: " + srcDir + patientDir + writeLocation);
		}
		String filePat = srcDir + patientDir + dirAdditive;
		File director = new File(filePat);
			if (!director.exists())
				director.mkdir();
		
		String filePath = srcDir + patientDir + dirAdditive + writeLocation;
			
		File directory = new File(filePath);
			if (!directory.exists())
				directory.mkdir();
			
		
		int i = 1;
		String newFilePath = filePath;
		while(directory.exists() && i <= 100){
			newFilePath = filePath + "(" + i + ")";
			directory = new File(newFilePath);
			i++;
		}
		filePath = newFilePath;
		
		try {
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            
            bos.write(content); // Write the byte array to the output stream
            bos.flush(); // Flush the buffered output stream
            bos.close(); // Close the stream
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
		
//		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
//			bw.write(content);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}

		
	}
	
	// Receives all data entered about the patient depending on the useCase
	// useCase defines the type of data being read for the patient
	// Use 1 for patient history
	// Use 2 for patient visit records
	protected byte[] readAll(int useCase, String username) throws IOException {
		
		String srcDir = System.getProperty("user.dir") + "/src/spr24cse360/data";
		String patientDir = "/" + username;
		String dirAdditive = "";
		
		
		switch(useCase) {
		case 1:
			dirAdditive = "/history";
			break;
		case 2:
			dirAdditive = "/records";
			break;
		case 3:
			dirAdditive = "/prescriptions";
			break;
		default:
			System.out.println("Read function is not being used properly, please read docs");
			System.out.println("Program will continue, path used to read is: " + srcDir + patientDir);
		}
		
		String filePath = srcDir + patientDir + dirAdditive;
		File directory = new File(filePath);
		File[] files = directory.listFiles((dir, name) -> !name.startsWith("."));
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] fullContent = null;
		if (files.length != 0) {
			for(int i = 0; i < files.length; i++) {
				if(files[i].isFile()) {
					output.write(read(files[i].getPath()));					
				}
			}
			
			return output.toByteArray();
		}
		else {
			return fullContent;
		}
		
	}
	
	
	
	//1st degree reading implementation, do not use manually, call readAll(2)
	private byte[] read(String dir) {
		
		try {
            FileInputStream fis = new FileInputStream(dir);
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] data = baos.toByteArray();
            fis.close();
            baos.close();
            return data;
        
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return null;
		
		
//		try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
//			byte[] line;
//			byte[] completeFile = null;
//			while ((line = br.readLine()) != null) {
//				 completeFile += line;
//			}
//
//			return completeFile;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
		
	}
	
	protected boolean addPatientFile(String username) {
		File data = new File(srcDir);
		
		if(!data.exists()) {
			data.mkdir();
		}
		
		String patientDir = srcDir + "/" + username;
		File directory = new File(patientDir);
		File historyDir = new File(patientDir + "/history");
		File recordsDir = new File(patientDir + "/records");
		File prescriptionsDir = new File(patientDir + "/prescriptions");
		
		if(directory.exists()) {
			System.out.println("Patient already exists");
			return false;
		} else {
			directory.mkdir();
			historyDir.mkdir();
			recordsDir.mkdir();
			prescriptionsDir.mkdir();
			return true;
		}
		
		
	}
	
	protected String checkIfUsernameExists(String username) {
		String[] usernames = getPatientList();

		int newPatientExtension = 0;
		boolean addExtension = false;
		String newUserName = username;
		
		//Goes through all other usernames
		//If a name+year combo is the same, we look to see if there is already a number extension
		//save the highest number extension of similar names
		//add 1 to the highest number and add that to the new username.
		for(int i = 0; i < usernames.length; i++) {
			if(usernames[i]!= null && usernames[i].startsWith(username)) {
				String patientExtNumbers = usernames[i].substring(username.length(), usernames[i].length());
				addExtension = true;
				
				if (patientExtNumbers != "") {
					//subtract 1 char because the number extension starts with "_"
					patientExtNumbers = patientExtNumbers.substring(1, patientExtNumbers.length());
					int newNum = Integer.parseInt(patientExtNumbers);
					if (newNum > newPatientExtension) {
						newPatientExtension = newNum;
					}
				}
			}
		}
		
		if (addExtension == true) {
			newPatientExtension++;
			newUserName = String.format(username + "_%d", newPatientExtension);
		}
		
		System.out.println("New username: " + newUserName);
		return newUserName;
	}
	
	
	// -----------------------------------------------------------------

	
	
	
	
	
}