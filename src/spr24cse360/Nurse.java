/*CSE 360 Group tu2
	Zachary Stewart
	Andrew Calderon
	Tyler Fujikawa
	Trevor Long
	Landon Oliver
*/
package spr24cse360;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import spr24cse360.Account.UserType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Nurse extends UserInterface {
	int sidePaneWidth = 300;
	int centerPaneWidth = 400;
	
	String patientUsername;
	Account patientAccount;

	public Nurse(Backend backend, Account account) {
		super(backend, account);
	}
	
	@Override
	public Scene getScene() {
		BorderPane root = new BorderPane();
		//TODO scene sizes should be static across all users so we need the size to be a variable somewhere.
		Scene scene = new Scene(root, 900, 500);

		//JAVAFX - Menu Bar
		MenuBar menuBar = new MenuBar();
		root.setTop(menuBar);
		Menu logoutMenu = getLogoutMenu(scene);
		Menu resetPasswordMenu = getResetPasswordMenu(scene);
		Menu addPatientAccountMenu = getAddAccountMenu(UserType.PATIENT, scene);
		menuBar.getMenus().addAll(logoutMenu, resetPasswordMenu, addPatientAccountMenu);
		
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
		
		//JAVAFX - Active Patients Pane
		Label activePatientsTitle = new Label("Active Patients");
		activePatientsTitle.setStyle("-fx-font-weight: bold");
        activePatientsTitle.setFont(new Font("Arial", 15));
        activePatientsTitle.setPadding(new Insets(5,0, 25, 0));
        activePatientsTitle.setUnderline(true);
        
        VBox activePatientsList = new VBox(); //might need to turn this into a scrollable box
        activePatientsList.setAlignment(Pos.CENTER);
        
        ListView<HBox> activePatientListView = new ListView<>();
        activePatientListView.itemsProperty().bind(activePatientHBox);
        
        activePatientsList.getChildren().add(activePatientListView);
        
        FlowPane activePatientButtons = new FlowPane(Orientation.HORIZONTAL);
        activePatientButtons.setAlignment(Pos.CENTER);
        activePatientButtons.setPrefWidth(250);
        activePatientButtons.setHgap(20);
        
        //Start Exam and Remove Active Patient Button
        

        //JAVAFX - Center Pane
        VBox centerPaneTitles = new VBox();
        centerPaneTitles.setAlignment(Pos.CENTER);
        centerPaneTitles.setPrefWidth(centerPaneWidth);
        
        Label nurseTitle = new Label("Faculty (Nurse) Page");
        nurseTitle.setStyle("-fx-font-weight: bold");
        nurseTitle.setFont(new Font("Arial", 15));
        nurseTitle.setPadding(new Insets(5,0, 25, 0));
        
        Label nameLabel = new Label("Welcome Nurse Doe");
        nameLabel.setFont(new Font("Arial", 15));
        nameLabel.setPadding(new Insets(5,0, 25, 0));
        
        Image titleImage = new Image(getClass().getResourceAsStream("/spr24cse360/icons/landingpage.png"), 200, 200, true, true);
		ImageView iconView = new ImageView(titleImage);
        
        centerPaneTitles.getChildren().addAll(nurseTitle, nameLabel, iconView);

        //JAVAFX - Patient Search Box
        VBox patientSearchVBox = new VBox();
        patientSearchVBox.setAlignment(Pos.CENTER);
		patientSearchVBox.setBackground(sidesBackground);
        patientSearchVBox.setMaxWidth(300);
       // patientSearchVBox.setMaxHeight(300);
        patientSearchVBox.setSpacing(10);
        VBox.setMargin(patientSearchVBox, new Insets(10, 0, 10, 0));

   
        
        ListView<HBox> patientSearchBox = new ListView<HBox>();
        patientSearchBox.setPrefHeight(200);
        
        
        
         Account[] allAccounts = getBackend().getAllPatients();
		     	patientSearchBox.getItems().clear();
		     	for(int i = 0; i < allAccounts.length; i++) {
		     		if(allAccounts[i] != null) {		     							
		     			patientSearchBox.getItems().add(hBoxFactory(allAccounts[i]));
		     			
		     		}
		     	}
        
        Label patientNameLabel = new Label("Name of the Patient: ");
		TextField patientNameText = new TextField();
		     			
		patientNameLabel.setMaxWidth(220);
		patientNameText.setMaxWidth(200);
     			
		patientSearchVBox.getChildren().add(patientNameLabel);
		patientSearchVBox.getChildren().add(patientNameText);
		patientSearchVBox.getChildren().add(patientSearchBox);
		patientNameText.setOnKeyTyped(new EventHandler<>() {  				
		     @Override
		     public void handle(KeyEvent event) {

		     	if (patientNameText.getText().isBlank() ) {
		     		patientSearchBox.getItems().clear();
		     		Account[] allAccounts = getBackend().getAllPatients();
		     		for(int i = 0; i < allAccounts.length; i++) {
		     			if(allAccounts[i] != null) {		     							
			     			patientSearchBox.getItems().add(hBoxFactory(allAccounts[i]));
			     			
			     		}
		     		}
		     		
		     	}
		     	Account[] possibleAccounts = getBackend().searchPatients(patientNameText.getText(), getAccount().getRole());
		     	patientSearchBox.getItems().clear();
		     	for(int i = 0; i < possibleAccounts.length; i++) {
		     		if(possibleAccounts[i] != null) {		     							
		     			patientSearchBox.getItems().add(hBoxFactory(possibleAccounts[i]));
		     			
		     		}
		     	}
		     					
		     	patientSearchBox.setVisible(true);
		     					
		     	patientSearchBox.setOnMouseClicked(new EventHandler<>() {
		     		@Override
		     		public void handle(MouseEvent event) {
		     			int j = patientSearchBox.getSelectionModel().getSelectedIndex();
		     			patientUsername = possibleAccounts[j].getUsername();
		     			patientAccount = possibleAccounts[j];
		     			patientNameText.setText(patientUsername);
		     			patientSearchBox.getItems().clear();
		     			patientSearchBox.setVisible(false);
		     		}
		     	}); 	
		     }
		});
     
	

        //JAVAFX - Search Pane
        Label searchPaneTitle = new Label("Patient Search");
        searchPaneTitle.setStyle("-fx-font-weight: bold");
        searchPaneTitle.setFont(new Font("Arial", 15));
        searchPaneTitle.setPadding(new Insets(5,0, 25, 0));
        searchPaneTitle.setUnderline(true);
        
        //JAVAFX - Pane Settings
        leftPane.setBackground(sidesBackground);
        leftPane.setBorder(new Border(stroke));
        centerPane.setBackground(centerBackground);
        rightPane.setBackground(sidesBackground);
        rightPane.setBorder(new Border(stroke));
        
        leftPane.setTop(activePatientsTitle);
        leftPane.setAlignment(activePatientsTitle, Pos.TOP_CENTER);
        leftPane.setBottom(activePatientButtons);
        leftPane.setAlignment(activePatientButtons,  Pos.BOTTOM_CENTER);
        leftPane.setCenter(activePatientsList);
        
        centerPane.setTop(centerPaneTitles);
        centerPane.setAlignment(centerPaneTitles, Pos.TOP_CENTER);
        
        rightPane.setTop(searchPaneTitle);
        rightPane.setAlignment(searchPaneTitle, Pos.TOP_CENTER);
        rightPane.setCenter(patientSearchVBox);
        rightPane.setAlignment(patientSearchVBox, Pos.TOP_CENTER);
        
        root.setLeft(leftPane);
        root.setCenter(centerPane);
        root.setRight(rightPane);
		
		return scene;
	}
}