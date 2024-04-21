/*CSE 360 Group tu2
	Zachary Stewart
	Andrew Calderon
	Tyler Fujikawa
	Trevor Long
	Landon Oliver
*/
package spr24cse360;

import java.time.LocalDate;
import java.util.ArrayList;
// import java.awt.Insets;
import java.util.Stack;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import spr24cse360.Account.UserType;

public class UserInterface {
	
	private final Account account;
	private final Backend backendptr;
	protected final int WINDOW_WIDTH = 500;
	protected final int WINDOW_HEIGHT = 500;
	
	protected ListProperty<String> messagesProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
	protected ListProperty<HBox> activePatientHBox = new SimpleListProperty<>(FXCollections.observableArrayList());
	protected Account selectedActivePatient = null;

	
	private String firstPassword;
	
	private Font TITLE_FONT = new Font("Inter", 30);
	private Font SUBTITLE_FONT = new Font("Montserrat", 15);
	private Font GENERAL_FONT = new Font("Manrope", 10);
	
	
	
	public UserInterface(Backend backend, Account account) {
		backendptr = backend;
		this.account = account;
	}
	

	public Scene getResetPasswordScene(String oldPassword) { 
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,500,150);
		Stage newPasswordStage = new Stage(); 

		Label setPassword = new Label("Set New Password: ");
		Label setPasswordTwice = new Label("Retype New Password: ");

		TextField setPasswordField = new TextField();
		TextField setPasswordTwiceField = new TextField();
		Button save = new Button("Save");
		save.setOnAction(new EventHandler<>() {

			public void handle(ActionEvent event) {
				if (setPasswordField.getText().equals(setPasswordTwiceField.getText())) {
					firstPassword = setPasswordField.getText();
				if (firstPassword.equals("") || firstPassword.contains(" ") || firstPassword.length() <= 10) {
					firstPasswordPopups(1); // passwords do not meet conditions (contains space, null, < 10 chars)
					}
					else {
						firstPasswordPopups(3); // passwords match and meet conditions, saved
                        backendptr.resetPassword(account, oldPassword, firstPassword);
						newPasswordStage.close();
						account.setFirstLogin(false);
					}
				}
				else {
					firstPasswordPopups(2); // passwords dont match
				}
			}
			
		});
		
		
		setPasswordField.setEditable(true); 
		setPasswordField.setPrefHeight(5);
		setPasswordField.setPrefWidth(250);	

		setPasswordTwiceField.setEditable(true); 
		setPasswordTwiceField.setPrefHeight(5);
		setPasswordTwiceField.setPrefWidth(230);	

		HBox one = new HBox(10);
		HBox two = new HBox(10);

		one.getChildren().addAll(setPassword, setPasswordField);
		one.setAlignment(Pos.CENTER_LEFT);
		two.getChildren().addAll(setPasswordTwice, setPasswordTwiceField);
		two.setAlignment(Pos.CENTER_LEFT);

		VBox allgin = new VBox(10);
		allgin.setPadding(new Insets(20, 20, 20, 20));
		allgin.getChildren().addAll(one, two, save);

		root.setCenter(allgin);

	//	newPasswordStage.setTitle("Set Password");
	//	newPasswordStage.setScene(scene);
	//	newPasswordStage.show();
		
        return scene;
	}
	
	public void firstPasswordPopups(int typeOfError) {
		switch(typeOfError) {
		case 1:
			BorderPane root1 = new BorderPane();
			Scene errorScene1 = new Scene(root1,200,150);
			Stage errorStage1 = new Stage(); 
			Label errorLabel1 = new Label("Error: Invalid Password");
			Button returnButton1 = new Button("Return");

			returnButton1.setOnAction(new EventHandler<>() {
				public void handle(ActionEvent event) {
					errorStage1.close();  
				}
			});
			VBox errorVBox1 = new VBox(10);
			errorVBox1.getChildren().addAll(errorLabel1, returnButton1);
			errorVBox1.setPadding(new Insets(20, 20, 20, 20));
			errorVBox1.setAlignment(Pos.CENTER);
			root1.setCenter(errorVBox1);
			errorStage1.setTitle("Error"); 
			errorStage1.setScene(errorScene1);
			errorStage1.show();
			break;
		case 2:
			BorderPane root2 = new BorderPane();
			Scene errorScene2 = new Scene(root2,300,150);
			Stage errorStage2 = new Stage(); 
			Label errorLabel2 = new Label("Error: Passwords do not match");
			Button returnButton2 = new Button("Return");

			returnButton2.setOnAction(new EventHandler<>() {
				public void handle(ActionEvent event) {
					errorStage2.close();  
				}
			});
			VBox errorVBox2 = new VBox(10);
			errorVBox2.getChildren().addAll(errorLabel2, returnButton2);
			errorVBox2.setPadding(new Insets(20, 20, 20, 20));
			errorVBox2.setAlignment(Pos.CENTER);
			root2.setCenter(errorVBox2);
			errorStage2.setTitle("Error"); 
			errorStage2.setScene(errorScene2);
			errorStage2.show();
			break;
		case 3:
			BorderPane root3 = new BorderPane();
			Scene errorScene3 = new Scene(root3,300,150);
			Stage errorStage3 = new Stage(); 
			Label errorLabel3 = new Label("Password set successfully!");
			Button returnButton3 = new Button("Return");
			System.out.println(firstPassword);
			returnButton3.setOnAction(new EventHandler<>() {
				public void handle(ActionEvent event) {
					errorStage3.close();  
				}
			});
			VBox errorVBox3 = new VBox(10);
			errorVBox3.getChildren().addAll(errorLabel3, returnButton3);
			errorVBox3.setPadding(new Insets(20, 20, 20, 20));
			errorVBox3.setAlignment(Pos.CENTER);
			root3.setCenter(errorVBox3);
			errorStage3.setTitle("Password Set"); 
			errorStage3.setScene(errorScene3);
			errorStage3.show();
		default:
			break;
				
		}
	}
	
	
	protected Backend getBackend() {
		return backendptr;
	}
	
	public Scene getScene() {
		return null;
	}

	public Account getAccount() {
		return account;
	}
	
	public void receiveMessage(Message message) {
    	messagesProperty.add(message.toString());
    }
	
	public void messageUpdate(Account otherAccount) {
		Stack<Message> messageLog = account.getMessageLog();
  		messagesProperty.clear();
		for(Message message : messageLog){
			if(message.getReciever() != null && (message.getReciever().equals(otherAccount) || message.getSender().equals(otherAccount))) {
				receiveMessage(message);
			}
		}
	}
	
	public void activePatientUpdate() {
		ArrayList<Account> activePatients = backendptr.getActivePatients();
		activePatientHBox.clear();
		for(Account active : activePatients) {
			if(active != null) {
				activePatientHBox.add(hBoxFactoryActivePatient(active));
			}
		}
	}
	
	public HBox hBoxFactoryActivePatient(Account patient) {
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		hbox.setPrefHeight(30);
		Label patientID = new Label(patient.getFullName() + " : " + patient.getBirthdate());
		Button startExamButton = new Button(); //Used to be "Start"
		Button removeButton = new Button(); //Used to be "-"
		
		Image startExamImage = new Image(getClass().getResourceAsStream("/spr24cse360/icons/Pen.png"), 15, 15, true, true);
		ImageView startExamView = new ImageView(startExamImage);
		startExamButton.setGraphic(startExamView);
		
		Image removeImage = new Image(getClass().getResourceAsStream("/spr24cse360/icons/Ban.png"), 15, 15, true, true);
		ImageView removeView = new ImageView(removeImage);
		removeButton.setGraphic(removeView);
		
		hbox.getChildren().addAll(patientID, startExamButton, removeButton);
		
		
		startExamButton.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent event) {
				
				startExamButton.setVisible(false);
				Stage examinationStage = new Stage();
				Scene examinationScene = getExam(patient); //ERROR HERE?
				examinationStage.setTitle("Examination of " + patient.getFullName());
				examinationStage.setScene(examinationScene);
				examinationStage.show();
				examinationStage.setOnCloseRequest(new EventHandler<>() {

					@Override
					public void handle(WindowEvent arg0) {
						// TODO Auto-generated method stub
						startExamButton.setVisible(true);
					}
					
				});
			}
			
		});
		
		
		removeButton.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent event) {
				getBackend().removeActivePatient(patient);
				activePatientUpdate();
			}
			
		});
		
		
		return hbox;
	}
	
	public HBox hBoxFactory(Account patient) {
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		hbox.setPrefHeight(30);
		hbox.setPadding(new Insets(10));
		
		Image messageImage = new Image(getClass().getResourceAsStream("/spr24cse360/icons/message.png"), 15, 15, true, true);
		Image formImage = new Image(getClass().getResourceAsStream("/spr24cse360/icons/form.png"), 15, 15, true, true);
		
		ImageView messageView = new ImageView(messageImage);
		ImageView formView = new ImageView(formImage);
		
		Label patientID = new Label(patient.getFullName() + " : " + patient.getBirthdate());
		hbox.getChildren().add(patientID);
		Button message = new Button();
			message.setGraphic(messageView);
		Button info = new Button();
			info.setGraphic(formView);
		Button add2Q = new Button(); //Used to be "+"
		
		Image add2QImage = new Image(getClass().getResourceAsStream("/spr24cse360/icons/add.png"), 15, 15, true, true);
		ImageView add2QView = new ImageView(add2QImage);
		add2Q.setGraphic(add2QView);
		
		if(account.getRole().equals(UserType.NURSE)) {
			hbox.getChildren().add(add2Q);
		}
		hbox.getChildren().addAll(message, info);
		
		
		message.setOnAction(new EventHandler<ActionEvent>(){
	    	 
	         @Override
	     	public void handle(ActionEvent event1) {
	        	 
	// ---- Message Pop-up ---- //
	     		TilePane root = new TilePane();
	     		Scene popupScene = new Scene(root, 700, 600);
	     		Stage popupStage = new Stage();
	     			root.setAlignment(Pos.CENTER);
	     			messageUpdate(patient);
	     		
	// ---- Message VBox  ---- //
	     		VBox messageVBox = new VBox();
	     			messageVBox.setAlignment(Pos.CENTER);
	     			messageVBox.setSpacing(10);
	     			messageVBox.setPadding(new Insets (5, 5, 15, 5));

	// ---- Message List	
	     		ListView<String> messageListView = new ListView<>();
	     			messageListView.itemsProperty().bind(messagesProperty); 
	     			messageUpdate(patient);
	     		HBox messageFieldAndSendButton = new HBox();
	     		VBox buttons = new VBox();
	     		buttons.setSpacing(10);
	     		buttons.setPadding(new Insets (5, 15, 5, 15));
	     		TextArea messageField = new TextArea();
	     		
	
	
	// Creating an icon image
	     		Image iconImage = new Image(getClass().getResourceAsStream("/spr24cse360/icons/send.png"), 25, 25, true, true);
	     		Image iconImage2 = new Image(getClass().getResourceAsStream("/spr24cse360/icons/cancel.png"), 25, 25, true, true);

   // Creating an ImageView with the icon image
	     		ImageView iconView = new ImageView(iconImage);
	     		ImageView iconView2 = new ImageView(iconImage2);

   // Creating a button with the icon
	     		Button iconButton = new Button();
	     			iconButton.setGraphic(iconView);
	     		Button cancelButton = new Button();		     			
	     			cancelButton.setGraphic(iconView2);
	     		buttons.getChildren().addAll(iconButton, cancelButton);
	     		
//---- Cancel Button Event ---- // 
					cancelButton.setOnAction(new EventHandler<>() {
						@Override
						public void handle(ActionEvent event6) {
							// need to close window
							popupStage.close();
						}
					});		     	
					
//---- Event for send ---- //
	     		iconButton.setOnAction(new EventHandler<>() {
	     			@SuppressWarnings("null")
					@Override
	     			public void handle(ActionEvent arg0) {
	     				String message = messageField.getText();
	     				if(message != null || !message.isBlank()) {
	     					// send message implementation
	     					if (getAccount() != patient) {
	     						getBackend().sendMessage(getAccount(), patient, message);
	     					}
	     					System.out.print("Successfully sent " + patient.getFullName() + " a message.");
	     					messageField.clear();
	     					messageListView.scrollTo(messagesProperty.size()-1);
	     				}
	     			}
   	
	     		});
  
   
	     		messageFieldAndSendButton.getChildren().addAll(messageField, buttons);
			
   // ---- Message Box Title
	     	Label messageInboxTitle = new Label("Message Inbox\nWith " + patient.getFullName());
	     		messageInboxTitle.setStyle("-fx-font-weight: bold");
	     		messageInboxTitle.setFont(new Font("Arial", 15));
	     		messageInboxTitle.setPadding(new Insets(5,0, 25, 0));
	     		messageInboxTitle.setUnderline(true);
	     		messageVBox.getChildren().addAll(messageInboxTitle, messageListView, messageFieldAndSendButton);
	     
	     			root.getChildren().add(messageVBox);
	     			popupStage.setScene(popupScene);
	     			popupStage.show();
	  }});
		
		
		
		info.setOnAction(new EventHandler<ActionEvent>(){
	    	 
	         @Override
	     	public void handle(ActionEvent event1) {
	        	 
	// ---- Info Pop-up ---- //
	     		TilePane root = new TilePane();
	     		Scene popupScene = new Scene(root, 600, 500);
	     		Stage popupStage = new Stage();
	     			root.setAlignment(Pos.CENTER);
	  
	// ---- Info Label ---- //
	     		Label infoLabel = new Label(patient.getFullName() + " Information");
	     			infoLabel.setStyle("-fx-font-weight: bold");
	     			infoLabel.setFont(new Font("Arial", 13));
	     			infoLabel.setPadding(new Insets(5,0, 15, 0));
	     			infoLabel.setUnderline(true);
	     		
	// ---- Info HBox  ---- //
	     		GridPane infoGPane = new GridPane();
	     			infoGPane.setAlignment(Pos.CENTER);
	     			infoGPane.setPadding(new Insets (5, 5, 5, 5));
	     			//infoGPane.setGridLinesVisible(true);
	     			
	// ---- Contact Info VBox ---- //
	     		VBox contactVBox = new VBox();
	     			contactVBox.setAlignment(Pos.CENTER);
	     			contactVBox.setSpacing(10);
	     			contactVBox.setPadding(new Insets (5, 5, 5, 5));	
	     			
	// ---- Contact Info Label ---- //
	     		Label contactInfoLabel = new Label("Contact Information");
	     		
	// ---- Contact Info Text Area ---- //
	     		TextArea contactInfo = new TextArea();
	     			contactInfo.setMaxHeight(150);  
	     			contactInfo.setMaxWidth(250);
	     			contactInfo.setMinHeight(150);  
	     			contactInfo.setMinWidth(250);
	     			contactInfo.setEditable(false);
	     			
	     			
	     			try {
	     				contactInfo.setText(getBackend().getPatientInformation(patient));
	     			} catch (Exception e) {
	     				e.printStackTrace();
	     			}
	     			
	     			if(contactInfo.getText() == null || contactInfo.getText().isEmpty()) {
	     				contactInfo.setText("Error collecting patient information");
	     				contactInfo.setStyle("-fx-text-fill: red;");
	     			}
	     			
	     			contactVBox.getChildren().addAll(contactInfoLabel, contactInfo);
	     			
	 // ---- Previous Visit VBox ---- //
	     		VBox visitsVBox = new VBox();
	     			visitsVBox.setAlignment(Pos.CENTER);
	     			visitsVBox.setSpacing(10);
	     			visitsVBox.setPadding(new Insets (5, 5, 5, 5));	
	     			
	// ---- Previous Visit Label ---- //
	     		Label visitsLabel = new Label("Previous Visits");
	    			
	// ---- Previous Visit Info Text Area ---- //
	     		TextArea previousVisits = new TextArea();
	     			previousVisits.setMaxHeight(150);  
	     			previousVisits.setMaxWidth(250);
	     			previousVisits.setMinHeight(150);  
	     			previousVisits.setMinWidth(250);
	     			previousVisits.setEditable(false);
	     			
	     			String pv = "";
	     			try {
	     				pv = getBackend().getRecords(patient.getUsername(), getAccount());
	     			} catch (Exception e) {
	     				e.printStackTrace();
	     			}
	     			pv = pv.replace("\n\n\n\n\n\n\n\n\n\n", "\n");
	     			previousVisits.setText(pv);
	     			
	     			if(previousVisits.getText() == null || previousVisits.getText().isEmpty()) {
	     				previousVisits.setText("No previous Visits recorded.");
	     			}
	     			
	     			visitsVBox.getChildren().addAll(visitsLabel, previousVisits);
	 
	 // ---- Prescription History VBox ---- //
	     		VBox prescriptionVBox = new VBox();
	     			prescriptionVBox.setAlignment(Pos.CENTER);
	     			prescriptionVBox.setSpacing(10);
	     			prescriptionVBox.setPadding(new Insets (5, 5, 5, 5));	
	     			
	// ---- Prescription History Label ---- //
	     		Label prescriptionLabel = new Label("Prescription History");
	        			
	// ---- Prescription History Info Text Area ---- //    
	     		TextArea prescriptionHistory = new TextArea();
	     			prescriptionHistory.setMaxHeight(150);  
	     			prescriptionHistory.setMaxWidth(250);
	     			prescriptionHistory.setMinHeight(150);  
	     			prescriptionHistory.setMinWidth(250);
	     			prescriptionHistory.setEditable(false);
	     			//prescriptionHistory.setMouseTransparent(true);
	     			//prescriptionHistory.setFocusTraversable(false);
	     			
	     		String prescription = "";
	     		
	     			try {
	     				prescription = getBackend().getPrescription(patient.getUsername(), getAccount());
	     			} catch (Exception e) {
	     				e.printStackTrace();
	     			}
	     			
	     			String p = prescription.replace("\n\n\n\n\n\n\n\n\n\n", "\n");
	     			prescriptionHistory.setText(p);
			
			
			if(prescriptionHistory.getText() == null || prescriptionHistory.getText().isEmpty()) {
				prescriptionHistory.setText("None");
			}
	     			prescriptionVBox.getChildren().addAll(prescriptionLabel, prescriptionHistory);
	
	 // ---- Medical History VBox ---- //
	     		VBox medicalVBox = new VBox();
	     			medicalVBox.setAlignment(Pos.CENTER);
	     			medicalVBox.setSpacing(10);
	     			medicalVBox.setPadding(new Insets (5, 5, 5, 5));	
	     			
	// ---- Medical History Label ---- //
	     		Label medicalLabel = new Label("Medical History");
	     			medicalLabel.setAlignment(Pos.CENTER);
	        			
	// ---- Medical History Info Text Area ---- //
	     		TextArea medicalHistory = new TextArea();
	     			medicalHistory.setMaxHeight(150);  
	     			medicalHistory.setMaxWidth(250);
	     			medicalHistory.setMinHeight(150);  
	     			medicalHistory.setMinWidth(250);
	     			medicalHistory.setEditable(false);
	     			
	     			String mh = "";
	     			try {
	     				mh = getBackend().getHistory(patient.getUsername(), getAccount());
	     			} catch (Exception e) {
	     				e.printStackTrace();
	     			}
	     			
	     			mh = mh.replace("\n\n\n\n\n\n\n\n\n\n", "\n");
	     			medicalHistory.setText(mh);
	     			
	     			if(medicalHistory.getText() == null || medicalHistory.getText().isEmpty()) {
	     				medicalHistory.setText("None");
	     			}
	     			
	     			medicalVBox.getChildren().addAll(medicalLabel, medicalHistory);
	     			
	     			infoGPane.add(infoLabel, 0, 0);
	     			infoGPane.add(contactVBox, 0, 1);
	     			infoGPane.add(visitsVBox, 1, 1);
	     			infoGPane.add(medicalVBox, 0, 2);
	     			infoGPane.add(prescriptionVBox, 1, 2);
	     			
	     			root.getChildren().add(infoGPane);
	     			popupStage.setScene(popupScene);
	     			popupStage.show();
	  }});
		
		
		add2Q.setOnAction(new EventHandler<>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println("Username: " + patient.getUsername());
					getBackend().addActivePatient(patient);
					activePatientUpdate();
				}
				
		});
		
		
		
		
		return hbox;
	}
	
	
	
	
	
	
protected Scene getExam(Account patient) {
		
		final int EXAMHEIGHT = 600;
		final int EXAMWIDTH = 1200;
		
		String[] prevEnteredData = backendptr.getReport(patient);
		
		
		GridPane root = new GridPane();
		Scene scene = new Scene(root, EXAMWIDTH, EXAMHEIGHT);
		
//		8 Vitals: 
//		Body Temperature.
//		Pulse Rate.
//		Respiration Rate.
//		Blood Pressure.
//		Blood Oxygen.
//		Height.
//		Weight.
//		Blood Glucose Level.
		
		GridPane patientInfoRoot = new GridPane();
		patientInfoRoot.setPrefSize(EXAMWIDTH/3, EXAMHEIGHT);
		root.add(patientInfoRoot, 1, 1, 1, 1);
		
		Label patientInfoTitle = new Label(patient.getFullName() + "\nRecords:");
		patientInfoTitle.setFont(TITLE_FONT);
		patientInfoRoot.add(patientInfoTitle, 1, 1, 2, 2);
		
		
		
		VBox mHistory = new VBox();
		patientInfoRoot.add(mHistory, 1, 3);
		Label mHistoryLabel = new Label("Medical History:");
		mHistoryLabel.setFont(SUBTITLE_FONT);
		TextArea mHistoryTextArea = new TextArea();
		mHistoryTextArea.setFont(GENERAL_FONT);
		try {
			System.out.println(backendptr.getHistory(patient.getUsername(), account));
			mHistoryTextArea.setText(backendptr.getHistory(patient.getUsername(), account));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mHistoryTextArea.setEditable(false);
		mHistory.getChildren().addAll(mHistoryLabel, mHistoryTextArea);
		
		
		VBox contact = new VBox();
		patientInfoRoot.add(contact, 2, 3);
		Label contactLabel = new Label("Contact Information:");
		contactLabel.setFont(SUBTITLE_FONT);
		TextArea contactTextArea = new TextArea();
		contactTextArea.setFont(GENERAL_FONT);
		try {
			System.out.println(patient.getPatientInformation());
			contactTextArea.setText(patient.getPatientInformation());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contactTextArea.setEditable(false);
		contact.getChildren().addAll(contactLabel, contactTextArea);
		
		
		VBox vRecord = new VBox();
		patientInfoRoot.add(vRecord, 1, 4);
		Label vRecordLabel = new Label("Visit Records:");
		vRecordLabel.setFont(SUBTITLE_FONT);
		TextArea vRecordTextArea = new TextArea();
		vRecordTextArea.setFont(GENERAL_FONT);
		try {
			System.out.println(backendptr.getRecords(patient.getUsername(), account));
			vRecordTextArea.setText(backendptr.getRecords(patient.getUsername(), account));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vRecordTextArea.setEditable(false);
		vRecord.getChildren().addAll(vRecordLabel, vRecordTextArea);
		
		
		
		VBox pRecords = new VBox();
		patientInfoRoot.add(pRecords, 2, 4);
		Label pRecordsLabel = new Label("Active Prescriptions:");
		pRecordsLabel.setFont(SUBTITLE_FONT);
		TextArea pRecordsTextArea = new TextArea();
		pRecordsTextArea.setFont(GENERAL_FONT);
		try {
			System.out.println(backendptr.getPrescription(patient.getUsername(), account));
			pRecordsTextArea.setText(backendptr.getPrescription(patient.getUsername(), account));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pRecordsTextArea.setEditable(false);
		pRecords.getChildren().addAll(pRecordsLabel, pRecordsTextArea);
		
		
		
		
		
		
		
		
		
		GridPane examinationRoot = new GridPane();
		examinationRoot.setPrefSize(((EXAMWIDTH/3)*2), EXAMHEIGHT);
		examinationRoot.setPadding(new Insets(10));
		examinationRoot.setHgap(10);
		examinationRoot.setVgap(10);
		ColumnConstraints c0 = new ColumnConstraints(0);
		ColumnConstraints c1 = new ColumnConstraints(120);
		ColumnConstraints c2 = new ColumnConstraints(60);
		ColumnConstraints c3 = new ColumnConstraints(120);
		ColumnConstraints c4 = new ColumnConstraints(60);
		ColumnConstraints c5 = new ColumnConstraints(120);
		ColumnConstraints c6 = new ColumnConstraints(60);
		ColumnConstraints c7 = new ColumnConstraints(120);
		ColumnConstraints c8 = new ColumnConstraints(60);
		
		examinationRoot.getColumnConstraints().addAll(c0,c1,c2,c3,c4,c5,c6,c7,c8);
		root.add(examinationRoot, 2, 1, 2, 1);
		
		
		
		Label examinationTitle = new Label("Examination of " + patient.getFullName());
		examinationRoot.add(examinationTitle, 1, 1, 8, 2);
		
		
		HBox vitalTempHBox = new HBox();
		vitalTempHBox.setAlignment(Pos.CENTER_RIGHT);
		Label vitalTempLabel = new Label("Temperature:");
		vitalTempHBox.getChildren().add(vitalTempLabel);
		examinationRoot.add(vitalTempHBox, 1, 3);
		HBox vitalPulseHBox = new HBox();
		vitalPulseHBox.setAlignment(Pos.CENTER_RIGHT);
		Label vitalPulseLabel = new Label("Pulse Rate:");
		vitalPulseHBox.getChildren().add(vitalPulseLabel);
		examinationRoot.add(vitalPulseHBox, 3, 3);
		HBox vitalRespirationHBox = new HBox();
		vitalRespirationHBox.setAlignment(Pos.CENTER_RIGHT);
		Label vitalRespirationLabel = new Label("Respiration Rate:");
		vitalRespirationHBox.getChildren().add(vitalRespirationLabel);
		examinationRoot.add(vitalRespirationHBox, 5, 3);
		HBox vitalBPressureHBox = new HBox();
		vitalBPressureHBox.setAlignment(Pos.CENTER_RIGHT);
		Label vitalBPressureLabel = new Label("Blood Pressure:");
		vitalBPressureHBox.getChildren().add(vitalBPressureLabel);
		examinationRoot.add(vitalBPressureHBox, 7, 3);
		HBox vitalBOxygenHBox = new HBox();
		vitalBOxygenHBox.setAlignment(Pos.CENTER_RIGHT);
		Label vitalBOxygenLabel = new Label("Blood Oxygen:");
		vitalBOxygenHBox.getChildren().add(vitalBOxygenLabel);
		examinationRoot.add(vitalBOxygenHBox, 1, 4);
		HBox vitalHeightHBox = new HBox();
		vitalHeightHBox.setAlignment(Pos.CENTER_RIGHT);
		Label vitalHeightLabel = new Label("Height:");
		vitalHeightHBox.getChildren().add(vitalHeightLabel);
		examinationRoot.add(vitalHeightHBox, 3, 4);
		HBox vitalWeightHBox = new HBox();
		vitalWeightHBox.setAlignment(Pos.CENTER_RIGHT);
		Label vitalWeightLabel = new Label("Weight:");
		vitalWeightHBox.getChildren().add(vitalWeightLabel);
		examinationRoot.add(vitalWeightHBox, 5, 4);
		HBox vitalBGLevelHBox = new HBox();
		vitalBGLevelHBox.setAlignment(Pos.CENTER_RIGHT);
		Label vitalBGLevelLabel = new Label("Blood Glucose Level:");
		vitalBGLevelHBox.getChildren().add(vitalBGLevelLabel);
		examinationRoot.add(vitalBGLevelHBox, 7, 4);
		
		
		TextField[] data = new TextField[8];
		
		
		TextField vitalTemp = new TextField();
		data[0] = vitalTemp;
		vitalTemp.setMaxWidth(60);
		examinationRoot.add(vitalTemp, 2, 3);
		
		TextField vitalPulse = new TextField();
		data[1] = vitalPulse;
		vitalPulse.setMaxWidth(60);
		examinationRoot.add(vitalPulse, 4, 3);
		
		TextField vitalRespiration = new TextField();
		data[2] = vitalRespiration;
		vitalRespiration.setMaxWidth(60);
		examinationRoot.add(vitalRespiration, 6, 3);
		
		TextField vitalBPressure = new TextField();
		data[3] = vitalBPressure;
		vitalBPressure.setMaxWidth(60);
		examinationRoot.add(vitalBPressure, 8, 3);
		
		TextField vitalBOxygen = new TextField();
		data[4] = vitalBOxygen;
		vitalBOxygen.setMaxWidth(60);
		examinationRoot.add(vitalBOxygen, 2, 4);
		
		TextField vitalHeight = new TextField();
		data[5] = vitalHeight;
		vitalHeight.setMaxWidth(60);
		examinationRoot.add(vitalHeight, 4, 4);
		
		TextField vitalWeight = new TextField();
		data[6] = vitalWeight;
		vitalWeight.setMaxWidth(60);
		examinationRoot.add(vitalWeight, 6, 4);
		
		TextField vitalBGLevel = new TextField();
		data[7] = vitalBGLevel;
		vitalBGLevel.setMaxWidth(60);
		examinationRoot.add(vitalBGLevel, 8, 4);
		
		TextArea[] bigData = new TextArea[3];
		
		Label aAndPConcernsLabel = new Label("Allergies and Patient Concerns:");
		examinationRoot.add(aAndPConcernsLabel, 1, 5, 2, 1);
		
		TextArea aAndPConcerns = new TextArea();
		bigData[0] = aAndPConcerns;
		examinationRoot.add(aAndPConcerns, 1, 6, 6, 2);
		
		
		
		Label nurseNotesLabel = new Label("Nurse's Notes:");
		examinationRoot.add(nurseNotesLabel, 1, 9, 2, 1);
		
		TextArea nurseNotes = new TextArea();
		bigData[1] = nurseNotes;
		examinationRoot.add(nurseNotes, 1, 10, 6, 2);
		if(!account.getRole().equals(UserType.NURSE)) {
			nurseNotes.setEditable(false);
		}
		
		
		Label drNotesLabel = new Label("Doctor's Notes:");
		examinationRoot.add(drNotesLabel, 1, 13, 2, 1);
		
		TextArea drNotes = new TextArea();
		bigData[2] = drNotes;
		examinationRoot.add(drNotes, 1, 14, 6, 2);
		if(!account.getRole().equals(UserType.DOCTOR)) {
			drNotes.setEditable(false);
		}
		
		
		
		Button save = new Button("Save");
		examinationRoot.add(save, 4, 17, 2, 1);
		
		Button confirm = new Button("Confirm");
		examinationRoot.add(confirm, 7, 17, 2, 1);
		confirm.setVisible(false);
		if(account.getRole().equals(UserType.DOCTOR)) {
			confirm.setVisible(true);
		}
		
		
		save.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				String[] savedData = new String[11];
				for(int i = 0; i < data.length + bigData.length; i++) {
					
					if(i < data.length) {
						savedData[i] = data[i].getText();
					} else {
						savedData[i] = bigData[i-data.length].getText();
					}
				}
				
				
			
				
				backendptr.addReport(patient, savedData);
			}
			
			
			
		});
		
		
		confirm.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				
//				8 Vitals: 
//				Body Temperature.
//				Pulse Rate.
//				Respiration Rate.
//				Blood Pressure.
//				Blood Oxygen.
//				Height.
//				Weight.
//				Blood Glucose Level.
				
				String bTemp = vitalTemp.getText();
				String pulse = vitalPulse.getText();
				String respiration = vitalRespiration.getText();
				String bp = vitalBPressure.getText();
				String bo = vitalBOxygen.getText();
				String height = vitalHeight.getText();
				String weight = vitalWeight.getText();
				String bgL = vitalBGLevel.getText();
				String nNote = nurseNotes.getText();
				String dNote = drNotes.getText();
				String allergies = aAndPConcerns.getText();
				
				String ret = backendptr.generateReport(	LocalDate.now(), bTemp, pulse, respiration, bp, bo, height, weight, bgL, allergies, nNote, dNote);
				try {
					backendptr.writeRecords(patient.getUsername(), account, ret);
				} catch (Exception e) {
					// Put popup here with error
					e.printStackTrace();
				}
			}
			
		});
		
		
		
		if(prevEnteredData != null) {
			for(int i = 0; i < data.length + bigData.length; i++) {
				if(prevEnteredData[i] != null) {
					if(i < data.length) {
						data[i].setText(prevEnteredData[i]);
					} else {
						bigData[i-data.length].setText(prevEnteredData[i]);
					}
				}
			}
		}
		
		
		
		
		
		
		return scene;
	}
	
	//Start JavaFX Menus
	//------------------------

	// ---- Logout Menu ---- //
	protected Menu getLogoutMenu(Scene parentScene) {
		Menu logoutMenu = new Menu("Logout");
		//Menus will not recognize clicks without a menuitem,
		//so I add a dummy that is hidden
		MenuItem dummyMenuItem = new MenuItem();
		logoutMenu.getItems().add(dummyMenuItem);
		
		logoutMenu.setOnShowing(new EventHandler<>(){
	     	@Override
	     	public void handle(Event event1) {
	     		getBackend().logoutProcedure(getAccount());
	     		Stage stage = (Stage) parentScene.getWindow();
	     	    stage.close();
	     	}	
	    });
	    //End logout menu
	    return logoutMenu;
	}
     
	protected Menu getResetPasswordMenu(Scene parentScene) {
		Menu resetPasswordMenu = new Menu("Reset Password");
		
		//Menus will not recognize clicks without a menuitem, so I add a dummy that is hidden
		MenuItem dummyMenuItem = new MenuItem();
		resetPasswordMenu.getItems().add(dummyMenuItem);
		
		resetPasswordMenu.setOnShowing(new EventHandler<>() {
			@Override
			public void handle(Event event2) {
				dummyMenuItem.setVisible(false);
				// ---- Reset Password Pop-up ---- //
				TilePane root = new TilePane();
				Scene popupScene = new Scene(root, 400, 400);
				Stage popupStage = new Stage();
				root.setAlignment(Pos.CENTER);

				// ---- New password Vbox ---- //
				VBox newPasswordVBox = new VBox();
				newPasswordVBox.setAlignment(Pos.CENTER);
				newPasswordVBox.setSpacing(5);

				// ---- Old password label & textField ---- //
				Label oldPasswordLabel = new Label("Enter your old password: ");
				TextField oldPasswordText = new TextField();
				newPasswordVBox.getChildren().add(oldPasswordLabel);
				newPasswordVBox.getChildren().add(oldPasswordText);

				// ---- New password label & textField ---- //
				Label newPasswordLabel = new Label("Enter your new password: ");
				TextField newPasswordText = new TextField();
				newPasswordVBox.getChildren().add(newPasswordLabel);
				newPasswordVBox.getChildren().add(newPasswordText);

				// ---- New password HBox ---- //
				HBox horizontalButtons = new HBox();
				horizontalButtons.setAlignment(Pos.CENTER);
				horizontalButtons.setSpacing(5);

				// ---- Cancel and Confirm buttons ---- //
				Button cancel = new Button("Cancel");
				Button confirm = new Button("Confirm");

				horizontalButtons.getChildren().add(cancel);
				horizontalButtons.getChildren().add(confirm);
				newPasswordVBox.getChildren().add(horizontalButtons);

				// ---- Cancel Button Event ---- //
				cancel.setOnAction(new EventHandler<>() {
					@Override
					public void handle(ActionEvent event3) {
						// need to close window
						popupStage.close();
					}
				});

				// ---- Error Label ---- //
				Label error = new Label("Error changing your password,\nensure all fields are filled correctly");
				error.setVisible(false);
				error.setStyle("-fx-text-fill: red;");
				newPasswordVBox.getChildren().add(error);

				// ---- Confirm Button Event ---- //
				confirm.setOnAction(new EventHandler<>() {
					@Override
					public void handle(ActionEvent event4) {
						boolean isFilled = false;
						String oldPassword = oldPasswordText.getText();
						String newPassword = newPasswordText.getText();

						if (oldPasswordText.getText() != null && !oldPasswordText.getText().isEmpty()) {
							isFilled = true;
						}
						if (isFilled) {
							error.setVisible(false);
							getBackend().resetPassword(getAccount(), oldPassword, newPassword);
							System.out.println("Here is your new password: " + newPassword);
							popupStage.close();
						} else {
							error.setVisible(true);
							System.out.println("Something went wrong, try again.");
						}
					}
				});

				root.getChildren().add(newPasswordVBox);
				
				popupStage.setTitle(resetPasswordMenu.getText());
				popupStage.initOwner(parentScene.getWindow());
				popupStage.setScene(popupScene);
				popupStage.show();
	            popupStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            	public void handle(WindowEvent event) {
						dummyMenuItem.setVisible(true);
						resetPasswordMenu.hide();
	            	}
	            });

			}
		});
		return resetPasswordMenu;
	}
	// End resetpassword menu
	
	protected Menu getAddAccountMenu(UserType newAccountType, Scene parentScene) {
		//Add New Account Button
		Menu addAccountMenu = new Menu();
		if (newAccountType == UserType.PATIENT) {
			addAccountMenu.setText("Add Patient Account");
		} else if (newAccountType == UserType.NURSE){
			addAccountMenu.setText("Add Nurse Account");
		} else if (newAccountType == UserType.DOCTOR){
			addAccountMenu.setText("Add Doctor Account");
		}
		
		//Menus will not recognize clicks without a menuitem, so I add a dummy that is hidden
		MenuItem dummyMenuItem = new MenuItem();
		addAccountMenu.getItems().add(dummyMenuItem);
		
		addAccountMenu.setOnShowing(new EventHandler<>() {
			@Override
			public void handle(Event event) {
				dummyMenuItem.setVisible(false);
				//Add Account Popup
				TilePane root = new TilePane();
				Scene popupScene = new Scene(root, 400, 400);
				Stage popupStage = new Stage();
				
				root.setAlignment(Pos.CENTER);
				
				VBox informationForm = new VBox();
				
				informationForm.setAlignment(Pos.CENTER);
				informationForm.setSpacing(5);
				
				Label firstNameLabel = new Label("First Name: ");
				TextField firstNameText = new TextField();
				Label lastNameLabel = new Label("Last Name: ");
				TextField lastNameText = new TextField();
				Label emailLabel = new Label("Email: ");
				TextField emailText = new TextField();
				Label phoneLabel = new Label("Primary Phone: ");
				TextField phoneText = new TextField();
				Label addressLabel = new Label("Street Address: ");
				TextField addressText = new TextField();
				
				VBox birthdayBox = new VBox();
				HBox birthdayLabels = new HBox();
				HBox dateOfBirthBox = new HBox();
				
				birthdayLabels.setSpacing(20);
				dateOfBirthBox.setSpacing(20);
				
				Label dayOfBirthLabel = new Label("Day:");
				dayOfBirthLabel.setPrefWidth(70);
				ComboBox<Integer> dayOfBirth = new ComboBox<Integer>();
				for(int i = 1; i <= 31; i++) {
					dayOfBirth.getItems().add(i);
				}
				
				Label monthOfBirthLabel = new Label("Month:");
				monthOfBirthLabel.setPrefWidth(70);
				ComboBox<Integer> monthOfBirth = new ComboBox<Integer>();
				for(int i = 1; i <= 12; i++) {
					monthOfBirth.getItems().add(i);
				}
				
				Label yearOfBirthLabel = new Label("Year:");
				yearOfBirthLabel.setPrefWidth(70);
				ComboBox<Integer> yearOfBirth = new ComboBox<Integer>();
				for(int i = 2024; i >= 1900; i--) {
					yearOfBirth.getItems().add(i);
				}
				
				birthdayLabels.getChildren().addAll(dayOfBirthLabel, monthOfBirthLabel, yearOfBirthLabel);
				dateOfBirthBox.getChildren().addAll(dayOfBirth, monthOfBirth, yearOfBirth);
				birthdayBox.getChildren().addAll(birthdayLabels, dateOfBirthBox);
				informationForm.getChildren().addAll(firstNameLabel, firstNameText, lastNameLabel, 
													 lastNameText, birthdayBox, emailLabel, emailText, 
													 phoneLabel, phoneText, addressLabel, addressText);
				
				//Cancel Button - Might not need this, x button auto added
				Button cancel = new Button("Cancel");
				informationForm.getChildren().add(cancel);
	
				cancel.setOnAction(new EventHandler<>() {
					@Override
					public void handle(ActionEvent event) {
						// need to close window
						popupStage.close();
					}
				});
				
				//Confirm Button
				Button confirm = new Button("Confirm");
				informationForm.getChildren().add(confirm);
				Label error = new Label("Error loading your information,\n ensure all fields are filled correctly");
				error.setVisible(false);
				error.setStyle("-fx-text-fill: red;");
				informationForm.getChildren().add(error);
				
				confirm.setOnAction(new EventHandler<>() {
					@Override
					public void handle(ActionEvent event) {
						String first = firstNameText.getText();
						String last = lastNameText.getText();
						String email = emailText.getText();
						String phone = phoneText.getText();
						String address = addressText.getText();
						Integer dayBirth = dayOfBirth.getValue();
						Integer monthBirth = monthOfBirth.getValue();
						Integer yearBirth = yearOfBirth.getValue();
						
						String verifiedInfo = backendptr.verifyNewPatientInfo(first, last, email, phone, address, dayBirth, monthBirth, yearBirth);	
						
						if(verifiedInfo == "true") {
							String[] newUserAndPass = backendptr.addNewAccount(first, last, email, phone, address, dayOfBirth.getValue(), monthOfBirth.getValue(), yearOfBirth.getValue(), newAccountType);
	
							error.setVisible(false);
							if(newUserAndPass != null) {
								System.out.println("Here is the generated password: " + newUserAndPass[1]);
								popupStage.close();
								
								//popup to show user the name accounts credentials
								NewAccountPopup.makeNewAccountScene(newUserAndPass[0], newUserAndPass[1], parentScene);
							}
							
						} else {
							error.setText(verifiedInfo);
							error.setVisible(true);
						}
					}
				});
				
				root.getChildren().add(informationForm);
				
				popupStage.setTitle(addAccountMenu.getText());
				popupStage.initOwner(parentScene.getWindow());
				popupStage.setScene(popupScene);
	            popupStage.show();
	            popupStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            	public void handle(WindowEvent event) {
						dummyMenuItem.setVisible(true);
						addAccountMenu.hide();
	            	}
	            });
				
			}	
		});
		return addAccountMenu;
	}
	//End JavaFX Menus
}



	