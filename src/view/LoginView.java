package view;

import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {

	private ViewController view;

	private TextField fieldUsername;
	private TextField fieldPassword;

	private HBox boxButtons;

	private Button buttonSubmit;
	private Button buttonRegisterPage;

	private int buttonHeight = 25;
	private int buttonWidth = 200;

	private int padding = 200;
	private int spacing = 15;

	public LoginView(ViewController view) {
		this.view = view;
		this.setBackground(view.getBackground());

		this.setAlignment(Pos.CENTER);

		this.fieldUsername = new TextField();
		this.fieldUsername.setPromptText("Gebruikersnaam");

		this.fieldPassword = new TextField();
		this.fieldPassword.setPromptText("Wachtwoord");

		this.buttonSubmit = new Button("Inloggen");
		this.buttonSubmit.setPrefSize(this.buttonWidth, this.buttonHeight);
		this.buttonSubmit.setOnAction(e -> this.submit());

		this.buttonRegisterPage = new Button("Registreer pagina");
		this.buttonRegisterPage.setPrefSize(this.buttonWidth, this.buttonHeight);
		this.buttonRegisterPage.setOnAction(e -> this.openRegisterView());

		this.boxButtons = new HBox();
		this.boxButtons.getChildren().addAll(this.buttonSubmit, this.buttonRegisterPage);

		this.boxButtons.setAlignment(Pos.CENTER);
		this.boxButtons.setSpacing(this.spacing);

		this.setSpacing(this.spacing);
		this.setPadding(new Insets(0, this.padding, 0, this.padding));

		this.getChildren().addAll(view.getLogo(), this.fieldUsername, this.fieldPassword, this.boxButtons);
	}

	public void submit() {
		if (!view.getAccountController().loginAccount(this.fieldUsername.getText(), this.fieldPassword.getText())) {
			throw new RuntimeException("Login failed");
		}
		view.openMenuView();
	}

	public void openRegisterView() {
		view.openRegisterView();
	}
}
