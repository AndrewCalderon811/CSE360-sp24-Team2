/*CSE 360 Group tu2
	Zachary Stewart
	Andrew Calderon
	Tyler Fujikawa
	Trevor Long
	Landon Oliver
*/
package spr24cse360;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LandingPage extends Application {
	
	private static Backend backendptr;
	
	public LandingPage() {}
	
	public void launchLandingPage(Backend backend) {
		backendptr = backend;
		launch(LandingPage.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		//JAVAFX - Login Page
		TilePane stack = new TilePane();
		stack.setAlignment(Pos.CENTER);
		
		VBox layer = new VBox();
		layer.setAlignment(Pos.CENTER);
		layer.setSpacing(30);
		
		Image titleImage = new Image(getClass().getResourceAsStream("/spr24cse360/icons/landingpage.png"), 200, 200, true, true);
		ImageView iconView = new ImageView(titleImage);
		
		
		
		HBox loginOptions = new HBox();
		loginOptions.setSpacing(10);
		
		HBox usernameSpace = new HBox();
		usernameSpace.setAlignment(Pos.CENTER);
		usernameSpace.setSpacing(15);
		TextField usernameTextField = new TextField();
		usernameTextField.setPromptText("Username");
		usernameSpace.getChildren().add(usernameTextField);
		
		HBox passwordSpace = new HBox();
		passwordSpace.setAlignment(Pos.CENTER);
		passwordSpace.setSpacing(15);
		PasswordField passwordTextField = new PasswordField();
		passwordTextField.setPromptText("Password");
		passwordSpace.getChildren().add(passwordTextField);
		
		
		
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(15);
		buttonBox.setAlignment(Pos.CENTER);
		
		//Login Button
		Button loginButton = new Button("Login");
		loginButton.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				UserInterface newLogin = backendptr.loginProcedure(usernameTextField.getText(), passwordTextField.getText());
				if(newLogin != null) {
					String enteredPassword = passwordTextField.getText();
					usernameTextField.clear();
					passwordTextField.clear();
					Scene popupScene = newLogin.getScene();
					Stage popupStage = new Stage();
					popupStage.setTitle("User: " + newLogin.getAccount().getFullName());
					popupStage.initOwner(primaryStage);
					popupStage.setScene(popupScene);
		            popupStage.show();
		            
		            if(newLogin.getAccount().isFirstLogin()) {
		            	Scene newPasswordScene = newLogin.getResetPasswordScene(enteredPassword);
		            	Stage newPasswordStage = new Stage();
		            	newPasswordStage.setTitle("Password Reset");
		            	newPasswordStage.initOwner(popupStage);
		            	newPasswordStage.setScene(newPasswordScene);
		            	newPasswordStage.show();
		            }
		            
		            popupStage.setOnCloseRequest(new EventHandler<>() {
						@Override
						public void handle(WindowEvent event) {
							backendptr.logoutProcedure(newLogin.getAccount());
						}
		            });
//		            backendptr.resetPassword(newLogin.getAccount(), "ev85glm89eld41higmvg9552ir", "password");
				}
			}	
		});
		
		//JAVAFX - Add Elements to Page
		buttonBox.getChildren().addAll(loginButton);
	
		layer.getChildren().add(iconView);
		layer.getChildren().add(loginOptions);
		layer.getChildren().add(usernameSpace);
		layer.getChildren().add(passwordSpace);
		layer.getChildren().add(buttonBox);
		
		stack.getChildren().add(layer);
		primaryStage.setTitle("Red Cross Patient Portal");
		primaryStage.setScene(new Scene(stack, 500, 500));
		primaryStage.show();
		
	}
}
