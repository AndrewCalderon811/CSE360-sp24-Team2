package spr24cse360;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NewAccountPopup {
	protected static void makeNewAccountScene(String newUsername, String newPass, Scene parentScene) {
		// Set up root pane
		BorderPane root = new BorderPane();
		Scene newScene = new Scene(root, 400, 400);
		Stage newStage = new Stage();
		root.setPadding(new Insets(5, 5, 5, 5));

		// Title
		Label homeTitle = new Label("New account sucessfully created!");
		homeTitle.setFont(new Font("Arial", 14));
		homeTitle.setPadding(new Insets(5, 0, 25, 0));

		Label nameLabel = new Label(String.format("New account username: %s", newUsername));

		Label passLabel = new Label(String.format("New account password: %s", newPass));
		
		// Organizing
		root.setTop(homeTitle);
		root.setAlignment(homeTitle, Pos.CENTER);
		nameLabel.setAlignment(Pos.CENTER);
		root.setCenter(nameLabel);
		nameLabel.setAlignment(Pos.CENTER);
		root.setBottom(passLabel);
		root.setAlignment(passLabel, Pos.CENTER);
		
		newStage.initOwner(parentScene.getWindow());
		newStage.setTitle("New account created");
		newStage.setScene(newScene);
		newStage.show();
	}
}
