package view;

import controller.ViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {

	private ViewController view;

	private TextField fieldUsername;
	private TextField fieldPassword;

	private Button buttonSubmit;

	public LoginView(ViewController view) {
		this.view = view;

		this.setAlignment(Pos.CENTER);

		this.fieldUsername = new TextField();

		this.fieldPassword = new TextField();

		this.buttonSubmit = new Button("Inloggen");
		this.buttonSubmit.setOnAction(e -> this.submit());

		this.getChildren().addAll(this.fieldUsername, this.fieldPassword, this.buttonSubmit);
	}

	public void submit() {
		if (!view.getAccountController().loginAccount(this.fieldUsername.getText(), this.fieldPassword.getText())) {
			throw new RuntimeException("Login failed");
		}

		view.OpenMenuView();
	}
}
