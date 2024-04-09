/*CSE 360 Group tu2
	Zachary Stewart
	Andrew Calderon
	Tyler Fujikawa
	Trevor Long
	Landon Oliver
*/
package spr24cse360;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class Patient extends UserInterface {
	int sidePaneWidth = 250;
	int centerPaneWidth = 400;
	String doctorUsername;
	Account doctorAccount;
	
	public Patient(Backend backend, Account account) {
		super(backend, account);
	}
	
	@Override
	public Scene getScene() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 900, 525);

		//JAVAFX - Menu Bar
		MenuBar menuBar = new MenuBar();
		root.setTop(menuBar);
		Menu logoutMenu = getLogoutMenu(scene);
		Menu resetPasswordMenu = getResetPasswordMenu(scene);
		menuBar.getMenus().addAll(logoutMenu, resetPasswordMenu);
		
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
		
//JAVAFX - Left Side Pane - TOP - "Prescription"
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
// ---- GridPlane for prescription labels and text areas
		VBox prescriptionVBox = new VBox();
		        
		        prescriptionVBox.setAlignment(Pos.CENTER_LEFT);
		        prescriptionVBox.setBackground(sidesBackground);
		        prescriptionVBox.setBorder(new Border(stroke));		        
		        prescriptionVBox.setPrefWidth(sidePaneWidth);
		        prescriptionVBox.setMaxWidth(sidePaneWidth);
		        prescriptionVBox.setMaxHeight(250);
		        prescriptionVBox.setMinHeight(250);
		        prescriptionVBox.setPadding(new Insets(5, 5, 5, 5));
		       		        
		// ---- Prescription Titles
		Label prescriptionTitle = new Label("Prescriptions");
		        prescriptionTitle.setStyle("-fx-font-weight: bold");
		        prescriptionTitle.setFont(new Font("Arial", 15));
		        //prescriptionTitle.setPadding(new Insets(5,0, 5, 0));
		        prescriptionTitle.setUnderline(true);
		        prescriptionTitle.setPadding(new Insets(0, 0, 20, 0));

		        //prescriptionGPane.add(prescriptionTitle, 0, 0);
		        //GridPane.setConstraints(prescriptionTitle, 0, 0, 2, 1);
				prescriptionVBox.getChildren().add(prescriptionTitle);

		// ---- Current Prescription (left)
		TextArea prescriptionDisplay = new TextArea("");
		        prescriptionDisplay.setFont(new Font("Arial", 10));
		        prescriptionDisplay.setMaxHeight(200);  
		        prescriptionDisplay.setMaxWidth(sidePaneWidth - 10);
		        prescriptionDisplay.setEditable(false);
		        
				//GridPane.setConstraints(prescriptionDisplay, 0, 2, 1, 1);
				prescriptionVBox.getChildren().add(prescriptionDisplay);	
				
		String prescription = "";
	     		
	     			try {
	     				prescription = getBackend().getPrescription(getAccount().getUsername(), getAccount());
	     			} catch (Exception e) {
	     				e.printStackTrace();
	     			}
	     			
	     			String p = prescription.replace("\n\n\n\n\n\n\n\n\n\n", "\n");
	     			prescriptionDisplay.setText(p);
			
				if(prescriptionDisplay.getText() == null || prescriptionDisplay.getText().isEmpty()) {
					prescriptionDisplay.setText("None");
				}
	

//JAVAFX - Left Side Pane - BOT - "Patient Information"
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		

		GridPane contactInformationGPane = new GridPane();
		        
		        contactInformationGPane.setAlignment(Pos.CENTER);
		        contactInformationGPane.setBackground(sidesBackground);
		        contactInformationGPane.setBorder(new Border(stroke));
		        
		        contactInformationGPane.setMaxWidth(300);
		        contactInformationGPane.setMaxHeight(250);
		        contactInformationGPane.setMinHeight(250);
		        
		        contactInformationGPane.setHgap(10);
		        contactInformationGPane.setVgap(10);
		        contactInformationGPane.setPadding(new Insets(20, 20, 20, 20));
		       
		// ---- Patient Contact Information Label
		Label patientContactLabel = new Label("Contact Information");
				patientContactLabel.setStyle("-fx-font-weight: bold");
				patientContactLabel.setFont(new Font("Arial", 12));
				patientContactLabel.setUnderline(true);
				
				contactInformationGPane.add(patientContactLabel, 0, 0);
		
		// ---- Text area for patient info
		TextArea contactInfoDisplay = new TextArea();
				contactInfoDisplay.setFont(new Font("Arial", 10));
				
				contactInfoDisplay.setMaxHeight(100);  
				contactInfoDisplay.setMaxWidth(250);
				contactInfoDisplay.setMinHeight(80);  
				contactInfoDisplay.setMinWidth(200);
				contactInfoDisplay.setEditable(false);
				
				
				//Example Text
				//contactInfoDisplay.setText("Patient Contact Information: \nName: \nAddress: \nEmail: \nDate of Birth:");
				GridPane.setConstraints(contactInfoDisplay, 0, 1, 3, 2);
				contactInformationGPane.getChildren().add(contactInfoDisplay);
		// Gathering Patient Information
				 
				try {
					contactInfoDisplay.setText(getBackend().getPatientInformation(getAccount()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
				if(contactInfoDisplay.getText() == null || contactInfoDisplay.getText().isEmpty()) {
					contactInfoDisplay.setText("Error collecting patient information");
					contactInfoDisplay.setStyle("-fx-text-fill: red;");
				}
				
				
				  
		// ---- Patient Information Change and Submit Button	
		Button changeButton = new Button("Edit");
		        contactInformationGPane.add(changeButton, 1, 0);

		// ---- HBox for Logout Button & Reset Password Button       
		VBox rightPaneVBoxBot = new VBox();
		        rightPaneVBoxBot.setAlignment(Pos.CENTER);
		        rightPaneVBoxBot.setPrefWidth(centerPaneWidth);
		        rightPaneVBoxBot.setBackground(sidesBackground);
		        rightPaneVBoxBot.setBorder(new Border(stroke));
		        rightPaneVBoxBot.setMinHeight(250); 
		        rightPaneVBoxBot.setMaxHeight(250); 
		        
//JAVAFX - Center Pane - "Previous Visits"
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		// ---- VBox for Welcoming phrases
		VBox centerPaneTitles = new VBox();
		        centerPaneTitles.setAlignment(Pos.CENTER);
		        centerPaneTitles.setPrefWidth(centerPaneWidth);
		
		// ---- Patient Home Page Title
		Label patientTitle = new Label("Patient Home Page");
		        patientTitle.setStyle("-fx-font-weight: bold");
		        patientTitle.setFont(new Font("Arial", 15));
		        patientTitle.setPadding(new Insets(5,0, 25, 0));
		        
		        
		// ---- Welcome label
		// ---- Get Patient Name
		String patientName = null;
				try {
					patientName = getAccount().getFirstName();
				} catch (Exception e) {
					e.printStackTrace();
				}
		
		Label nameLabel = new Label("Welcome, " + patientName + ".");
		        nameLabel.setFont(new Font("Arial", 15));
		        nameLabel.setPadding(new Insets(5,0, 25, 0));
		        
		        centerPaneTitles.getChildren().addAll(patientTitle, nameLabel);


		// ---- Previous Visit Title		
			Label previousVisitsTitle = new Label("Previous Visits");
				previousVisitsTitle.setStyle("-fx-font-weight: bold");
				previousVisitsTitle.setFont(new Font("Arial", 15));
				previousVisitsTitle.setPadding(new Insets(5,0, 25, 0));
				previousVisitsTitle.setUnderline(true);
				
		// ---- TextArea for previous visit
			TextArea previousVisitsText = new TextArea();
			previousVisitsText.setBorder(new Border(stroke));

		// ---- Get previous visit information
				
				// Check if null first. If null skip and set.
				try {
                    previousVisitsText.setText(getBackend().getRecords(getAccount().getUsername(), getAccount()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
              
				previousVisitsText.setEditable(false);
				
				
				centerPaneTitles.getChildren().addAll(previousVisitsTitle, previousVisitsText);
						        
		        
//JAVAFX - Right Side Panel - TOP - "Find my Doctor"
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	
		// ---- GridPlane for prescription labels and text areas
		VBox finderVBox = new VBox();
		        
		        finderVBox.setAlignment(Pos.CENTER);
		        finderVBox.setBackground(sidesBackground);
		        finderVBox.setBorder(new Border(stroke));		        
		        finderVBox.setPrefWidth(sidePaneWidth);
		        finderVBox.setMaxWidth(sidePaneWidth);
		        finderVBox.setMaxHeight(250);
		        finderVBox.setMinHeight(250);
		        finderVBox.setPadding(new Insets(5, 5, 5, 5));
		       		        
		// ---- Prescription Titles
		Label finderTitle = new Label("Find my Doctor");
		        finderTitle.setStyle("-fx-font-weight: bold");
				finderTitle.setFont(new Font("Arial", 15));
				finderTitle.setPadding(new Insets(5,0, 25, 0));
				finderTitle.setUnderline(true);

		        //prescriptionGPane.add(prescriptionTitle, 0, 0);
		        //GridPane.setConstraints(prescriptionTitle, 0, 0, 2, 1);
				finderVBox.getChildren().add(finderTitle);
				
		// ---- Name of Patient label & textField & HBox ---- //
		Label doctorNameLabel = new Label("Search: ");
		TextField doctorNameText = new TextField();
		     			
		     	finderVBox.getChildren().add(doctorNameLabel);
		     	finderVBox.getChildren().add(doctorNameText);
		     	
     	
			
		ListView<String> patientSearchBox = new ListView<String>();
		     	patientSearchBox.setPrefHeight(100);
		     	patientSearchBox.setVisible(false);
		     	finderVBox.getChildren().add(patientSearchBox);
		     	     			
//		     				
//		     		@Override
//		     		public void handle(KeyEvent event) {
//		     			
//		     			if (doctorNameText.getText().isBlank() ) {
//		     				patientSearchBox.getItems().clear();
//		     				return;
//		     			}
//		     			Account[] possibleAccounts = getBackend().searchPatients(doctorNameText.getText());
//		     			patientSearchBox.getItems().clear();
//		     			for(int i = 0; i < possibleAccounts.length; i++) {
//		     				if(possibleAccounts[i] != null) {		     							
//		     					patientSearchBox.getItems().add(possibleAccounts[i].getFullName());
//		     				}
//		     			}
//		     					
//		     			patientSearchBox.setVisible(true);
//		     					
//		     			patientSearchBox.setOnMouseClicked(new EventHandler<>() {
//		     				@Override
//		     				public void handle(MouseEvent event) {
//		     					int j = patientSearchBox.getSelectionModel().getSelectedIndex();
//		     					doctorNameText.setText(patientSearchBox.getSelectionModel().getSelectedItem());
//		     					doctorUsername = possibleAccounts[j].getUsername();
//		     					doctorAccount = possibleAccounts[j];
//		     					patientSearchBox.getItems().clear();
//		     					patientSearchBox.setVisible(false);
//		     					
//		     				}
//		     			}); 	
//		     		}
//		     	});
//JAVAFX - Right Side Panel - BOT - "Messages"
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		// TODO
		// Pull current and past prescriptions
		// Messaging UI and framework
		        
		        // 3 for prescriptions
		        
				// ---- Message Box 	
				
		ListView<String> messageListView = new ListView<>();
		patientSearchBox.setPrefHeight(100);
		messageListView.itemsProperty().bind(messagesProperty);
		
		HBox messageFieldAndSendButton = new HBox();
		TextField messageField = new TextField();
		
		
		// Creating an icon image
        Image iconImage = new Image(getClass().getResourceAsStream("/spr24cse360/icons/send.png"), 15, 15, true, true);

        // Creating an ImageView with the icon image
        ImageView iconView = new ImageView(iconImage);

        // Creating a button with the icon
        Button iconButton = new Button("Send");
        	iconButton.setGraphic(iconView);
        	iconButton.setVisible(false);
        
        
       
        
        messageFieldAndSendButton.getChildren().addAll(messageField, iconButton);
				
        // ---- Message Box Title
        Label messageInboxTitle = new Label("Message Inbox");
		        messageInboxTitle.setStyle("-fx-font-weight: bold");
		        messageInboxTitle.setFont(new Font("Arial", 15));
		        messageInboxTitle.setPadding(new Insets(5,0, 10, 0));
		        messageInboxTitle.setUnderline(true);
		        rightPaneVBoxBot.getChildren().addAll(messageInboxTitle, messageListView, messageFieldAndSendButton);
		        
		        
		        
		        
//JAVAFX - Pane Settings
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		// ---- Left Side Pane
		        leftPane.setTop(prescriptionVBox);
		        leftPane.setAlignment(prescriptionVBox, Pos.TOP_CENTER);
		        leftPane.setBackground(sidesBackground);
		        leftPane.setCenter(contactInformationGPane);
		        leftPane.setAlignment(contactInformationGPane, Pos.CENTER);

        // ---- Center Pane
		        centerPane.setTop(centerPaneTitles);
		        centerPane.setAlignment(centerPaneTitles, Pos.TOP_CENTER);
		        centerPane.setBorder(new Border(stroke));
		        centerPane.setBackground(centerBackground);
		        
		// ---- Right Side Pane
		        rightPane.setTop(finderVBox);
		        rightPane.setAlignment(finderVBox, Pos.TOP_CENTER);
		        rightPane.setCenter(rightPaneVBoxBot);
		        rightPane.setAlignment(rightPaneVBoxBot, Pos.CENTER);
		        rightPane.setBackground(sidesBackground);
		        
		// ---- Setting Root
		        root.setLeft(leftPane);
		        root.setCenter(centerPane);
		        root.setRight(rightPane);

//JAVAFX - EVENT HANDLERS
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ---- Find my Doctor ---- //
		doctorNameText.setOnKeyTyped(new EventHandler<>() {
     				
		     		@Override
		     		public void handle(KeyEvent event) {
		     			
		     			if (doctorNameText.getText().isBlank() ) {
		     				patientSearchBox.getItems().clear();
		     				return;
		     			}
		     			Account[] possibleAccounts = getBackend().searchPatients(doctorNameText.getText(), getAccount().getRole());
		     			patientSearchBox.getItems().clear();
		     			for(int i = 0; i < possibleAccounts.length; i++) {
		     				if(possibleAccounts[i] != null) {		     							
		     					patientSearchBox.getItems().add(possibleAccounts[i].getFullName());
		     				}
		     			}
		     					
		     			patientSearchBox.setVisible(true);
		     					
		     			patientSearchBox.setOnMouseClicked(new EventHandler<>() {
		     				@Override
		     				public void handle(MouseEvent event) {
		     					int j = patientSearchBox.getSelectionModel().getSelectedIndex();
		     					doctorNameText.setText(patientSearchBox.getSelectionModel().getSelectedItem());
		     					doctorUsername = possibleAccounts[j].getUsername();
		     					doctorAccount = possibleAccounts[j];
		     					patientSearchBox.getItems().clear();
		     					patientSearchBox.setVisible(false);
		     					iconButton.setVisible(true);
		     					messageUpdate(doctorAccount);
		     					
		     				}
		     			}); 	
		     		}
		     	});        
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ---- Send Message Button ---- //
		iconButton.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				String message = messageField.getText();
				if(message != null) {
					// send message implementation
					getBackend().sendMessage(getAccount(), doctorAccount, message);
					System.out.print("Successfully sent " + doctorAccount.getFullName() + " a message.\n");
					messageField.clear();
					messageListView.scrollTo(messagesProperty.size()-1);
				}
			}
        	
        });
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		        

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
// ---- Change Button ---- //
		        changeButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event5) {
						
		// ---- Change Information Pop-up ---- //
					TilePane root = new TilePane();
						Scene popupScene = new Scene(root, 400, 400);
						Stage popupStage = new Stage();
						root.setAlignment(Pos.CENTER);
		
		// ---- Change Information VBox  ---- //
						VBox changeVBox = new VBox();
							changeVBox.setAlignment(Pos.CENTER);
							changeVBox.setSpacing(5);
		
		// ---- New Email ---- //
						Label newEmailLabel = new Label("Enter your new Email: ");
						TextField newEmailText = new TextField();
							changeVBox.getChildren().add(newEmailLabel);
							changeVBox.getChildren().add(newEmailText);
					
		// ---- New password label & textField ---- //
						Label newPhoneLabel = new Label("Enter your new phone number: ");
						TextField newPhoneText = new TextField();
							changeVBox.getChildren().add(newPhoneLabel);
							changeVBox.getChildren().add(newPhoneText);
		
		// ---- Old password label & textField ---- //
						Label newAddressLabel = new Label("Enter your new address: ");
						TextField newAddressText = new TextField();
							changeVBox.getChildren().add(newAddressLabel);
							changeVBox.getChildren().add(newAddressText);
								
		// ---- New password HBox ---- // 						
						HBox horizontalButtons = new HBox();
							horizontalButtons.setAlignment(Pos.CENTER);
							horizontalButtons.setSpacing(5);
						
		// ---- Cancel and Confirm buttons ---- //
						Button cancel = new Button("Cancel");
						Button confirm = new Button("Confirm");
						
						
							horizontalButtons.getChildren().add(cancel);
							horizontalButtons.getChildren().add(confirm);
							changeVBox.getChildren().add(horizontalButtons);
		
// ---- Cancel Button Event ---- // 
						cancel.setOnAction(new EventHandler<>() {
							@Override
							public void handle(ActionEvent event6) {
								// need to close window
								popupStage.close();
							}
						});
						
		// ---- Error Label ---- //	
						Label error = new Label("Error changing your information,\nensure all fields are filled correctly");
						error.setVisible(false);
						error.setStyle("-fx-text-fill: red;");
						changeVBox.getChildren().add(error);
						
// ---- Confirm Button Event ---- //
						confirm.setOnAction(new EventHandler<>() {
							@Override
							public void handle(ActionEvent event7) {
								boolean isFilled = false;
								String newEmail = newEmailText.getText();
								String newPhoneNumber = newPhoneText.getText();
								String newAddress = newAddressText.getText();
								
								if((newEmailText.getText() != null && !newEmailText.getText().isEmpty()) && (newPhoneText.getText() != null
								    && !newPhoneText.getText().isEmpty()) && (newAddressText.getText() != null && !newAddressText.getText().isEmpty())) {
									isFilled = true;
								}
								if (isFilled) {
									error.setVisible(false);
									getBackend().updateContactInformation(getAccount(), newEmail, newPhoneNumber, newAddress);
			// ---- updates the information on screen.
									try {
										contactInfoDisplay.setText(getBackend().getPatientInformation(getAccount()));
									} catch (Exception e) {
										
										e.printStackTrace();
									
									}
									System.out.println("Information successfully updated");
									
									popupStage.close();
								}	
								else {
									error.setVisible(true);
									System.out.println("Something went wrong, try again.");
								}
							}
						});
						
						root.getChildren().add(changeVBox);
						popupStage.setScene(popupScene);
			            popupStage.show();
						
					}
		        }); 
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		        
// END OF CODE
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		      
		return scene;
	}
	
}
