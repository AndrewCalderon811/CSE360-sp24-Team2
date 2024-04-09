package spr24cse360;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import spr24cse360.Account.UserType;

public class Doctor extends UserInterface{
	
	int sidePaneWidth = 300;
	int centerPaneWidth = 400;
	Account patientAccount;
	String patientUsername;
	Account activePatient;
	Backend backendptr = new Backend();

	public Doctor(Backend backend, Account account) {
		super(backend, account);
	}
	
	@Override
	public Scene getScene() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 900, 500);
		
		//JAVAFX - Menu Bar
		MenuBar menuBar = new MenuBar();
		root.setTop(menuBar);
		Menu logoutMenu = getLogoutMenu(scene);
		Menu resetPasswordMenu = getResetPasswordMenu(scene);
		Menu addPatientAccountMenu = getAddAccountMenu(UserType.PATIENT, scene);
		Menu addNurseAccountMenu = getAddAccountMenu(UserType.NURSE, scene);
		Menu addDoctorAccountMenu = getAddAccountMenu(UserType.DOCTOR, scene);
		menuBar.getMenus().addAll(logoutMenu, resetPasswordMenu, addPatientAccountMenu, addNurseAccountMenu, addDoctorAccountMenu);
		
		//JAVAFX - Panes
		BorderPane leftPane = new BorderPane();
		leftPane.setPrefWidth(sidePaneWidth);
		BorderPane centerPane = new BorderPane();
		centerPane.setPrefWidth(centerPaneWidth);
		BorderPane rightPane = new BorderPane();
		rightPane.setPrefWidth(sidePaneWidth);
		
		BorderStroke stroke = new BorderStroke(Color.valueOf("#000000"),
        		BorderStrokeStyle.SOLID,
        		CornerRadii.EMPTY,
        		BorderWidths.DEFAULT);
		
		Background sidesBackground = new Background(new BackgroundFill(Color.web("#" + "ffffed"), CornerRadii.EMPTY, Insets.EMPTY));
		Background centerBackground = new Background(new BackgroundFill(Color.web("#" + "e1f6ff"), CornerRadii.EMPTY, Insets.EMPTY));
		
//JAVAFX - Left Side Pane - "Active Patients"
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		
		// ---- VBox for left side
			VBox leftPaneVBox = new VBox();
				leftPaneVBox.setAlignment(Pos.CENTER);
				leftPaneVBox.setPrefWidth(centerPaneWidth);
				leftPaneVBox.setBackground(sidesBackground);
				//leftPaneVBox.setPadding(new Insets(20, 20, 20, 20));

		// ---- Active Patients Title		
			Label activePatientsTitle = new Label("Active Patients");
				activePatientsTitle.setStyle("-fx-font-weight: bold");
				activePatientsTitle.setFont(new Font("Arial", 15));
				activePatientsTitle.setPadding(new Insets(5,0, 25, 0));
				activePatientsTitle.setUnderline(true);
				
		// ---- Active Patient Button HBox
			HBox activeButtonHbox = new HBox();
				activeButtonHbox.setAlignment(Pos.BOTTOM_CENTER);
				activeButtonHbox.setSpacing(5);
			Button startExamButton = new Button("Start Exam");
		
				activePatientUpdate();
				
		// ---- Active Patient Box
			ListView<HBox> activePatientListView = new ListView<>();
				activePatientListView.itemsProperty().bind(activePatientHBox);
				activePatientUpdate();
				//leftPaneVBox.getChildren().add(activePatientListView);	
				leftPaneVBox.getChildren().addAll(activePatientsTitle, activePatientListView, startExamButton);
					
		
				
				
//JAVAFX - Center - "Prescription Portal"
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		//TODO
		// Pull patient information and push changes
		// Menu bar
				
		// ---- VBox for Welcoming phrases
		VBox centerPaneTitles = new VBox();
		        centerPaneTitles.setAlignment(Pos.CENTER);
		        centerPaneTitles.setPrefWidth(centerPaneWidth);
		
		// ---- Patient Home Page Title
		Label doctorTitle = new Label("Faculty View");
		        doctorTitle.setStyle("-fx-font-weight: bold");
		        doctorTitle.setFont(new Font("Arial", 15));
		        doctorTitle.setPadding(new Insets(5,0, 25, 0));
		        
		        
		// ---- Welcome label
		// ---- Get Doctor Name
		String doctorName = null;
				try {

					doctorName = getAccount().getLastName();

				//	patientName = getBackend().getLastName(getAccount().getUsername(), getAccount());

				} catch (Exception e) {
					e.printStackTrace();
				}
		
		Label nameLabel = new Label("Welcome, Doctor " + doctorName + ".");
		        nameLabel.setFont(new Font("Arial", 15));
		        nameLabel.setPadding(new Insets(5,0, 25, 0));
		        
		        centerPaneTitles.getChildren().addAll(doctorTitle, nameLabel);
		        
		// ---- GridPlane for prescription labels and text areas
		GridPane prescriptionGPane = new GridPane();
		        
		        prescriptionGPane.setAlignment(Pos.CENTER);
		        prescriptionGPane.setBackground(sidesBackground);
		        prescriptionGPane.setBorder(new Border(stroke));		        
		        prescriptionGPane.setPrefWidth(sidePaneWidth);
		        prescriptionGPane.setMaxWidth(sidePaneWidth);
		        prescriptionGPane.setMaxHeight(200);
		        prescriptionGPane.setMinHeight(200);
		        prescriptionGPane.setMaxWidth(250);
		        prescriptionGPane.setMinWidth(250);
		        prescriptionGPane.setHgap(10);
		        prescriptionGPane.setVgap(10);
		        prescriptionGPane.setPadding(new Insets(20, 20, 20, 20));
		       		        
		// ---- Prescription Title
		Label prescriptionTitle = new Label("Prescription Portal");
		        prescriptionTitle.setStyle("-fx-font-weight: bold");
		        prescriptionTitle.setFont(new Font("Arial", 15));
		        prescriptionTitle.setPadding(new Insets(5,0, 5, 0));
		        prescriptionTitle.setUnderline(true);
		        //prescriptionGPane.add(prescriptionTitle, 0, 0);
		        GridPane.setConstraints(prescriptionTitle, 0, 0, 2, 1);
				prescriptionGPane.getChildren().add(prescriptionTitle);
				
				
		// Prescription Portal Button
		Button prescriptionPortal = new Button("To Prescription Portal");
				prescriptionPortal.setMinSize(100, 60);
				prescriptionPortal.setStyle("-fx-background-color: #3a9fbf; ");
			
				GridPane.setConstraints(prescriptionPortal, 0, 1, 2, 1);
				prescriptionGPane.getChildren().add(prescriptionPortal);
				centerPaneTitles.getChildren().add(prescriptionGPane);

		        
//JAVAFX - Right Pane - "Patient Search"
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ---- Patient Search VBox
		VBox patientSearchVBox = new VBox();
		        patientSearchVBox.setAlignment(Pos.TOP_CENTER);
				patientSearchVBox.setBackground(sidesBackground);
		        patientSearchVBox.setBorder(new Border(stroke));
		        patientSearchVBox.setMaxWidth(sidePaneWidth);
		        patientSearchVBox.setMaxWidth(450);
		        patientSearchVBox.setSpacing(10);
		        VBox.setMargin(patientSearchVBox, new Insets(10, 0, 10, 0));

		// ---- Patient Search Label       
		Label patientSearchLabel = new Label("Patient Search");
			patientSearchLabel.setStyle("-fx-font-weight: bold");
				patientSearchLabel.setFont(new Font("Arial", 15));
				patientSearchLabel.setPadding(new Insets(5,0, 25, 0));
				patientSearchLabel.setUnderline(true);
				patientSearchLabel.setAlignment(Pos.TOP_CENTER);
				
				patientSearchVBox.getChildren().add(patientSearchLabel);
				
		// ---- Name of Patient label & textField & HBox ---- //
		Label patientNameLabel = new Label("Name of the Patient: ");
		TextField patientNameText = new TextField();
		     			
		     patientNameLabel.setMaxWidth(220);
		     patientNameText.setMaxWidth(200);
		     			
		     patientSearchVBox.getChildren().add(patientNameLabel);
		     patientSearchVBox.getChildren().add(patientNameText);
		     
		// ---- Patient Search Box
		ListView<HBox> patientSearchBox = new ListView<HBox>();
		        patientSearchBox.setMaxHeight(300);
		      
		       
		        patientSearchVBox.getChildren().add(patientSearchBox);
		        
		        Account[] allAccounts = getBackend().getAllPatients();
		     	patientSearchBox.getItems().clear();
		     	for(int i = 0; i < allAccounts.length; i++) {
		     		if(allAccounts[i] != null) {		     							
		     			patientSearchBox.getItems().add(hBoxFactory(allAccounts[i]));
		     			
		     		}
		     	}
		     
				
		/*
		// ---- HBox for Message Button &  Info Button
		HBox rightPaneButtons = new HBox(sidePaneWidth/4);
		        rightPaneButtons.setAlignment(Pos.BOTTOM_CENTER);
		        rightPaneButtons.setPrefWidth(sidePaneWidth);
		        rightPaneButtons.setPadding(new Insets(5, 5, 5, 5));

		Button messageButton = new Button("Message");
		        messageButton.setPadding(new Insets(5, 5, 5, 5));
		        //messageButton.setPrefWidth(sidePaneWidth/6);
		        
		Button viewInfoButton = new Button("View Information");
		        viewInfoButton.setPadding(new Insets(5, 5, 5, 5));
		        rightPaneButtons.getChildren().addAll(messageButton, viewInfoButton);
		        patientSearchVBox.getChildren().add(rightPaneButtons);
		 */       
		        
//JAVAFX - Pane Settings
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		// ---- Left Side Pane
		        leftPane.setTop(leftPaneVBox);
		        leftPane.setAlignment(leftPaneVBox, Pos.TOP_CENTER);
		        leftPane.setBorder(new Border(stroke));
		        leftPane.setBackground(sidesBackground);

        // ---- Center Pane
		        centerPane.setTop(centerPaneTitles);
		        centerPane.setAlignment(centerPaneTitles, Pos.TOP_CENTER);
		        
		        //centerPane.setTop(prescriptionGPane);
		        //centerPane.setAlignment(prescriptionGPane, Pos.CENTER);
		        
		        centerPane.setBackground(centerBackground);
		        
		// ---- Right Side Pane
		        rightPane.setCenter(patientSearchVBox);
		        rightPane.setAlignment(patientSearchVBox, Pos.TOP_CENTER);
		        
		        //rightPane.setCenter(rightPaneButtons);
		        //rightPane.setAlignment(rightPaneButtons, Pos.BOTTOM_CENTER);
		        
		        rightPane.setBackground(sidesBackground);
		        
		// ---- Setting Root
		        root.setLeft(leftPane);
		        root.setCenter(centerPane);
		        root.setRight(rightPane);

        
        /*TODO
         * active patient list
         * messaging system
         */
//JAVAFX - EVENT HANDLERS
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		     
// ---- Patient Name Search ---- //		        
		 startExamButton.setOnAction(new EventHandler<>() {
		    @Override
			public void handle(ActionEvent event) {
				if(selectedActivePatient == null) return;
				
				startExamButton.setVisible(false);
				Stage examinationStage = new Stage();
				Scene examinationScene = getExam(selectedActivePatient); //ERROR HERE?
				examinationStage.setTitle("Examination of " + selectedActivePatient.getFullName());
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
		        
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		     
// ---- Patient Name Search ---- //
		 patientNameText.setOnKeyTyped(new EventHandler<>() {  				
		     @Override
		     public void handle(KeyEvent event) {

		     	if (patientNameText.getText().isBlank() ) {
		     		patientSearchBox.getItems().clear();

		     		return;
		     	} // TODO
		     	Account[] possibleAccounts = getBackend().searchPatients(patientNameText.getText(), getAccount().getRole());
		     	patientSearchBox.getItems().clear();
		     	for(int i = 0; i < possibleAccounts.length; i++) {
		     		if(possibleAccounts[i] != null) {	
		     			System.out.println("Here");
		     			patientSearchBox.getItems().addAll(hBoxFactory(possibleAccounts[i]));
		     			
		     		}
		     	}
		     					
		     	patientSearchBox.setVisible(true);
		     	/*				
		     	patientSearchBox.setOnMouseClicked(new EventHandler<>() {
		     		@Override
		     		public void handle(MouseEvent event) {
		     			int j = patientSearchBox.getSelectionModel().getSelectedIndex();
		     			patientNameText.setText(patientSearchBox.getSelectionModel().getSelectedItem());
		     			patientUsername = possibleAccounts[j].getUsername();
		     			patientAccount = possibleAccounts[j];
		     			patientSearchBox.getItems().clear();
		     			patientSearchBox.setVisible(false);
		     		}
		     	});
		     	*/ 	
		     }
		});
		 
		 
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
/*		 
// ---- Patient Search - Message Button ---- //
		     messageButton.setOnAction(new EventHandler<ActionEvent>(){
		    	 
		         @Override
		     	public void handle(ActionEvent event1) {
		        	 if (patientAccount == null) {
		    		 return;
		        	 }
		// ---- Message Pop-up ---- //
		     		TilePane root = new TilePane();
		     		Scene popupScene = new Scene(root, 700, 600);
		     		Stage popupStage = new Stage();
		     			root.setAlignment(Pos.CENTER);
		  
		     		
		// ---- Message VBox  ---- //
		     		VBox messageVBox = new VBox();
		     			messageVBox.setAlignment(Pos.CENTER);
		     			messageVBox.setSpacing(10);
		     			messageVBox.setPadding(new Insets (5, 5, 15, 5));

		// ---- Message List	
		     		ListView<String> messageListView = new ListView<>();
		     			messageListView.itemsProperty().bind(messagesProperty);
		
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
		     		Button sendButton = new Button();
		     			sendButton.setGraphic(iconView);
		     		Button cancelButton = new Button();		     			
		     			cancelButton.setGraphic(iconView2);
		     		buttons.getChildren().addAll(sendButton, cancelButton);
		     		
// ---- Cancel Button Event ---- // 
						cancelButton.setOnAction(new EventHandler<>() {
							@Override
							public void handle(ActionEvent event6) {
								// need to close window
								popupStage.close();
							}
						});		     	
						
// ---- Event for send ---- //
		     		sendButton.setOnAction(new EventHandler<>() {
		     			@SuppressWarnings("null")
						@Override
		     			public void handle(ActionEvent arg0) {
		     				String message = messageField.getText();
		     				if(message != null || !message.isBlank()) {
		     					// send message implementation
		     					if (getAccount() != patientAccount) {
		     						getBackend().sendMessage(getAccount(), patientAccount, message);
		     					}
		     					System.out.print("Successfully sent " + patientAccount.getFullName() + " a message.");
		     					messageField.clear();
		     				}
		     			}
        	
		     		});
       
        
		     		messageFieldAndSendButton.getChildren().addAll(messageField, buttons);
				
        // ---- Message Box Title
		     	Label messageInboxTitle = new Label("Message Inbox\nWith " + patientAccount.getFullName());
		     		messageInboxTitle.setStyle("-fx-font-weight: bold");
		     		messageInboxTitle.setFont(new Font("Arial", 15));
		     		messageInboxTitle.setPadding(new Insets(5,0, 25, 0));
		     		messageInboxTitle.setUnderline(true);
		     		messageVBox.getChildren().addAll(messageInboxTitle, messageListView, messageFieldAndSendButton);
		     
		     			root.getChildren().add(messageVBox);
		     			popupStage.setScene(popupScene);
		     			popupStage.show();
		  }});
*/
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
/* 
// ---- Patient Search - Info Button ---- //
		     viewInfoButton.setOnAction(new EventHandler<ActionEvent>(){
		    	 
		         @Override
		     	public void handle(ActionEvent event1) {
		        	 if (patientAccount == null) {
		    		 return;
		        	 }
		        	 
		// ---- Info Pop-up ---- //
		     		TilePane root = new TilePane();
		     		Scene popupScene = new Scene(root, 600, 400);
		     		Stage popupStage = new Stage();
		     			root.setAlignment(Pos.CENTER);
		  
		// ---- Info Label ---- //
		     		Label infoLabel = new Label(patientAccount.getFullName() + " Information");
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
		     			contactInfo.setMaxHeight(100);  
		     			contactInfo.setMaxWidth(200);
		     			contactInfo.setMinHeight(100);  
		     			contactInfo.setMinWidth(200);
		     			contactInfo.setEditable(false);
		     			
		     			
		     			try {
		     				contactInfo.setText(getBackend().getPatientInformation(patientAccount.getUsername(), getAccount()));
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
		     			previousVisits.setMaxHeight(100);  
		     			previousVisits.setMaxWidth(200);
		     			previousVisits.setMinHeight(100);  
		     			previousVisits.setMinWidth(200);
		     			previousVisits.setEditable(false);
		     			
		     			
		     			try {
		     				previousVisits.setText(getBackend().getRecords(patientAccount.getUsername(), getAccount()));
		     			} catch (Exception e) {
		     				e.printStackTrace();
		     			}
		     			
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
		     			prescriptionHistory.setMaxHeight(100);  
		     			prescriptionHistory.setMaxWidth(200);
		     			prescriptionHistory.setMinHeight(100);  
		     			prescriptionHistory.setMinWidth(200);
		     			prescriptionHistory.setEditable(false);
		     			//prescriptionHistory.setMouseTransparent(true);
		     			//prescriptionHistory.setFocusTraversable(false);
		     			
		     		String prescription = "";
		     		
		     			try {
		     				prescription = getBackend().getPrescription(patientAccount.getUsername(), getAccount());
		     			} catch (Exception e) {
		     				e.printStackTrace();
		     			}
		     			
				
		     			prescriptionHistory.setText(prescription);
				
				
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
		     			medicalHistory.setMaxHeight(100);  
		     			medicalHistory.setMaxWidth(200);
		     			medicalHistory.setMinHeight(100);  
		     			medicalHistory.setMinWidth(200);
		     			medicalHistory.setEditable(false);
		     			
		     			
		     			try {
		     				medicalHistory.setText(getBackend().getHistory(patientAccount.getUsername(), getAccount()));
		     			} catch (Exception e) {
		     				e.printStackTrace();
		     			}
		     			
		     			if(medicalHistory.getText() == null || medicalHistory.getText().isEmpty()) {
		     				medicalHistory.setText("None");
		     			}
		     			
		     			medicalVBox.getChildren().addAll(medicalLabel, medicalHistory);
		     			
		     			infoGPane.add(infoLabel, 1, 0);
		     			infoGPane.add(contactVBox, 0, 1);
		     			infoGPane.add(visitsVBox, 2, 1);
		     			infoGPane.add(medicalVBox, 0, 2);
		     			infoGPane.add(prescriptionVBox, 2, 2);
		     			
		     			root.getChildren().add(infoGPane);
		     			popupStage.setScene(popupScene);
		     			popupStage.show();
		  }});
*/
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		     
// ---- Prescription Portal Button ---- //
		        // TODO
		     prescriptionPortal.setOnAction(new EventHandler<ActionEvent>(){
		         @Override
		     	public void handle(ActionEvent event1) {
		// ---- Portal Pop-up ---- //
		     		TilePane root = new TilePane();
		     		Scene popupScene = new Scene(root, 650, 450);
		     		Stage popupStage = new Stage();
		     			root.setAlignment(Pos.CENTER);
		     			
		     		Insets i = new Insets(5, 0, 5, 0);
		     		
		// ---- Portal VBox  ---- //
		     		VBox portalVBox = new VBox();
		     			portalVBox.setAlignment(Pos.CENTER);
		     			portalVBox.setSpacing(0);
		     			
		// ---- Prescription Portal Label
		     		Label portalLabel = new Label("Prescription Portal");
		     		 	portalLabel.setStyle("-fx-font-weight: bold");
		     		 	portalLabel.setFont(new Font("Arial", 15));
		     			portalVBox.getChildren().add(portalLabel);
		     		
		// ---- Date Issued label & textField & HBox ---- //
		     		Label dateIssuedLabel = new Label("Date of Issuance: ");
		     		TextField dateIssuedText = new TextField();
		     		LocalDate date = LocalDate.now();
		     		HBox dateIssuedHBox = new HBox();
		     			dateIssuedHBox.setAlignment(Pos.CENTER_LEFT);
		     			dateIssuedHBox.setSpacing(5);
		     			dateIssuedHBox.setPadding(i);
		     			
		     			dateIssuedLabel.setMinWidth(220);
		     			dateIssuedText.setMinWidth(200);
		     			
		     			dateIssuedText.setText(date.toString());
		     			dateIssuedHBox.getChildren().add(dateIssuedLabel);
		     			dateIssuedHBox.getChildren().add(dateIssuedText);
		     			portalVBox.getChildren().add(dateIssuedHBox);
		     					
		// ---- Name of Patient label & textField & HBox ---- //
		     		Label patientNameLabel = new Label("Name of the Patient: ");
		     		TextField patientNameText = new TextField();
		     		HBox patientNameHBox = new HBox();
		     			patientNameHBox.setAlignment(Pos.CENTER_LEFT);
		     			patientNameHBox.setSpacing(5);
		     			patientNameHBox.setPadding(i);
		     			
		     			patientNameLabel.setMinWidth(220);
		     			patientNameText.setMinWidth(200);
		     			
		     			patientNameHBox.getChildren().add(patientNameLabel);
		     			patientNameHBox.getChildren().add(patientNameText);
		     			portalVBox.getChildren().add(patientNameHBox);
		     			
		     			ListView<String> patientSearchBox = new ListView<String>();
		     			patientSearchBox.setPrefHeight(5);
		     			patientSearchBox.setVisible(false);
		     			portalVBox.getChildren().add(patientSearchBox);
		     	     			
		     			patientNameText.setOnKeyTyped(new EventHandler<>() {
		     				
		     				@Override
		     				public void handle(KeyEvent event) {
		     					patientSearchBox.setPrefHeight(200);
		     					if (patientNameText.getText().isBlank() ) {
		     						patientSearchBox.getItems().clear();
		     						patientSearchBox.setPrefHeight(25);
		     						return;
		     					}
		     					Account[] possibleAccounts = getBackend().searchPatients(patientNameText.getText(), getAccount().getRole());
		     					patientSearchBox.getItems().clear();
		     					for(int i = 0; i < possibleAccounts.length; i++) {
		     						if(possibleAccounts[i] != null) {		     							
		     							patientSearchBox.getItems().add(possibleAccounts[i].getFullName() + " " + possibleAccounts[i].getBirthdate());
		     						}
		     					}
		     					
		     					patientSearchBox.setVisible(true);
		     					
		     					patientSearchBox.setOnMouseClicked(new EventHandler<>() {
		     						@Override
		     						public void handle(MouseEvent event) {
		     							int j = patientSearchBox.getSelectionModel().getSelectedIndex();
		     							patientNameText.setText(patientSearchBox.getSelectionModel().getSelectedItem());
		     							patientUsername = possibleAccounts[j].getUsername();
		     							patientAccount = possibleAccounts[j];
		     							patientSearchBox.getItems().clear();
		     							patientSearchBox.setVisible(false);
		     							portalVBox.getChildren().remove(patientSearchBox);
		     						}
		     					}); 	
		     				}
		     			});
		     			
		     			
		     			
		     			
		// ---- Address of Patient label & textField & HBox ---- //
		     		Label patientAddressLabel = new Label("Address of the Patient: ");
		     		TextField patientAddressText = new TextField();
		     		HBox patientAddressHBox = new HBox();
		     			patientAddressHBox.setAlignment(Pos.CENTER_LEFT);
		     			patientAddressHBox.setSpacing(5);
		     			patientAddressHBox.setPadding(i);
		     			
		     			patientAddressLabel.setMinWidth(220);
		     			patientAddressText.setMinWidth(200);
		     			
		     			patientAddressHBox.getChildren().add(patientAddressLabel);
		     			patientAddressHBox.getChildren().add(patientAddressText);
		     			portalVBox.getChildren().add(patientAddressHBox);
		     			
		// ---- Drug Information label & textField & HBox ---- //
		     		Label drugInformationLabel = new Label("Drug name, strength, and dosage form: ");
		     		TextField drugInformationText = new TextField();
		     		HBox drugInformationHBox = new HBox();
		     			drugInformationHBox.setAlignment(Pos.CENTER_LEFT);
		     			drugInformationHBox.setSpacing(5);
		     			drugInformationHBox.setPadding(i);
		     			
		     			drugInformationLabel.setMinWidth(220);
		     			drugInformationText.setMinWidth(200);
		     			
		     			drugInformationHBox.getChildren().add(drugInformationLabel);
		     			drugInformationHBox.getChildren().add(drugInformationText);
		     			portalVBox.getChildren().add(drugInformationHBox);
		     			
		// ---- Directions for use label & textField & HBox ---- //
		     		Label directionsLabel = new Label("Directions for use: ");
		     		TextField directionsText = new TextField();
		     		HBox directionsHBox = new HBox();
		     			directionsHBox.setAlignment(Pos.CENTER_LEFT);
		     			directionsHBox.setSpacing(5);
		     			directionsHBox.setPadding(i);

		     			
		     			directionsLabel.setMinWidth(220);
		     			directionsText.setMinWidth(200);
		     			
		     			directionsHBox.getChildren().add(directionsLabel);
		     			directionsHBox.getChildren().add(directionsText);
		     			portalVBox.getChildren().add(directionsHBox);     			
		     			
		// ---- Quantity label & textField & HBox ---- //
		     		Label QuantityLabel = new Label("Quantity prescribed: ");
		     		TextField QuantityText = new TextField();
		     		HBox QuantityHBox = new HBox();
		     			QuantityHBox.setAlignment(Pos.CENTER_LEFT);
		     			QuantityHBox.setSpacing(5);
		     			QuantityHBox.setPadding(i);
		     			
		     			QuantityLabel.setMinWidth(220);
		     			QuantityText.setMinWidth(200);
		     			
		     			QuantityHBox.getChildren().add(QuantityLabel);
		     			QuantityHBox.getChildren().add(QuantityText);
		     			portalVBox.getChildren().add(QuantityHBox); 
		     			
		// ---- Address of Doctor label & textField & HBox ---- //
		     		Label doctorAddressLabel = new Label("Address of the Doctor: ");
		     		TextField doctorAddressText = new TextField();
		     		HBox doctorAddressHBox = new HBox();
		     			doctorAddressHBox.setAlignment(Pos.CENTER_LEFT);
		     			doctorAddressHBox.setSpacing(5);
		     			doctorAddressHBox.setPadding(i);
		     			
		     			doctorAddressLabel.setMinWidth(220);
		     			doctorAddressText.setMinWidth(200);
		     			
		     			doctorAddressHBox.getChildren().add(doctorAddressLabel);
		     			doctorAddressHBox.getChildren().add(doctorAddressText);
		     			portalVBox.getChildren().add(doctorAddressHBox);
		
		// ---- E-signature label & textField & HBox ---- //
		     		Label signatureLabel = new Label("Eletronic signature of the Doctor: ");
		     		TextField signatureText = new TextField();
		     		HBox signatureHBox = new HBox();
		     			signatureHBox.setAlignment(Pos.CENTER_LEFT);
		     			signatureHBox.setSpacing(5);
		     			signatureHBox.setPadding(i);
		     		
		     			signatureLabel.setMinWidth(220);
		     			signatureText.setMinWidth(200);
		     			
		     			signatureHBox.getChildren().add(signatureLabel);
		     			signatureHBox.getChildren().add(signatureText);
		     			portalVBox.getChildren().add(signatureHBox);       			
		     			
		// ---- Button HBox ---- // 						
		     		HBox horizontalButtons = new HBox();
		     			horizontalButtons.setAlignment(Pos.CENTER);
		     			horizontalButtons.setSpacing(25);
		     			horizontalButtons.setPadding(new Insets(10,0, 0, 0));
		     						
		// ---- Cancel and Confirm buttons ---- //
		     			Button cancel = new Button("Cancel");
		     			Button confirm = new Button("Confirm");		
		     				horizontalButtons.getChildren().add(cancel);
		     				horizontalButtons.getChildren().add(confirm);
		     				portalVBox.getChildren().add(horizontalButtons);
		     		
		// ---- Cancel Button Event ---- // 
		     		cancel.setOnAction(new EventHandler<>() {
		     			@Override
		     			public void handle(ActionEvent event3) {
		     				popupStage.close();
		     			}
		     		});
		     		
		// ---- Error Label ---- //	
		     			Label error = new Label("Error sending prescription,\nensure all fields are filled correctly");
		     				error.setVisible(false);
		     				error.setStyle("-fx-text-fill: red;");
		     				portalVBox.getChildren().add(error);
		     				
		// ---- Confirm Button Event ---- //
		     	confirm.setOnAction(new EventHandler<>() {
		     		@Override
		     		public void handle(ActionEvent event4) {
		     			boolean isFilled = false;
		     			String date = dateIssuedText.getText();
		     			String name = patientNameText.getText();
		     			String pAddress = patientAddressText.getText();
		     			String drugInfo = drugInformationText.getText();
		     			String useDesc = directionsText.getText();
		     			String quantity = QuantityText.getText();
		     			String dAddress = doctorAddressText.getText();
		     			String eSig = signatureText.getText();
		     			String prescriptionInfo = "";
		     			
		     			if((dateIssuedText.getText() != null && !dateIssuedText.getText().isEmpty())
		     			&& (patientNameText.getText() != null && !patientNameText.getText().isEmpty())
		     			&& (patientAddressText.getText() != null && !patientAddressText.getText().isEmpty())
		     			&& (drugInformationText.getText() != null && !drugInformationText.getText().isEmpty())
		     			&& (directionsText.getText() != null && !directionsText.getText().isEmpty())	
		     			&& (QuantityText.getText() != null && !QuantityText.getText().isEmpty())	
		     			&& (signatureText.getText() != null && !signatureText.getText().isEmpty())){
		     					isFilled = true;
		     			}
		     			if (isFilled) {
		     				if (patientAccount.getRole().equals(UserType.PATIENT)) {
		     					error.setVisible(false);
		     					prescriptionInfo = "Date Issued: " + date +"\nMedication: " + drugInfo + "\nDirections: " + useDesc + "\nQuantity: " + quantity + "\n";
		     							
		     					try {		     					
		     						getBackend().writePrescription(patientUsername, getAccount(), prescriptionInfo);
		     					} catch (Exception e) {
		     						e.printStackTrace();
		     					}
		     				
		     					popupStage.close();
		     				}
		     				else {
		     					error.setVisible(true);
		     					System.out.println("You can only write prescriptions for patients.");
		     				}
		     			}	
		     			else {
		     				error.setVisible(true);
		     				System.out.println("Something went wrong, try again.");
		     			}
		     		}
		     	});
		     						
		     	root.getChildren().add(portalVBox);
		     	popupStage.setScene(popupScene);
		     	popupStage.show();
		  }});
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		return scene;
	}

	
}
