package spr24cse360;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Admin extends UserInterface {

	public Admin(Backend backend, Account account) {
		super(backend, account);
		// TODO Auto-generated constructor stub
	}
	
	public Scene getScene() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 900, 500);
		
		
		VBox page = new VBox();
		root.getChildren().add(page);
		Button startPatient = new Button("Start a static Patient Instance");
		page.getChildren().add(startPatient);
		
		return scene;
		
	}

}
