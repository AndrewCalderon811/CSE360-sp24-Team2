package spr24cse360;

import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	final private String message;
	final private Account sender;
	final private Account reciever;
	final private LocalDate timeSent;
	private boolean viewed = false;
	
	public Message(Account sender, Account recipient, String message) {
		this.message = message;
		this.sender = sender;
		this.reciever = recipient;
		this.timeSent = LocalDate.now();
		
	}
	
	public String toString() {
		return timeSent.toString() + " " + sender.getFullName() + ": " + message + "\n\n";
	}
	
	public void setViewed() {
		viewed = true;
	}
	
	public boolean isViewed() {
		return viewed;
	}
	
	public Account getReciever() {
		return reciever;
	}
	
	public Account getSender() {
		return sender;
	}
	

}
