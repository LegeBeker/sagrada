package view;

import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegisterView extends VBox {

    private ViewController view;

    private TextField fieldUsername;
	private TextField fieldPassword;

	private HBox boxButtons;

	private Button buttonSubmit;
	private Button buttonLoginPage;

	private int buttonHeight = 25;
	private int buttonWidth = 200;

	private int padding = 200;
	private int spacing = 15;

    public RegisterView(ViewController view) {
        this.view = view;
		this.setBackground(view.getBackground());

		this.setAlignment(Pos.CENTER);

		this.fieldUsername = new TextField();
		this.fieldUsername.setPromptText("Gebruikersnaam");

		this.fieldPassword = new TextField();
		this.fieldPassword.setPromptText("Wachtwoord");

		this.buttonSubmit = new Button("Registeren");
		this.buttonSubmit.setPrefSize(this.buttonWidth, this.buttonHeight);
		this.buttonSubmit.setOnAction(e -> this.submit());

		this.buttonLoginPage = new Button("Login pagina");
		this.buttonLoginPage.setPrefSize(this.buttonWidth, this.buttonHeight);
		this.buttonLoginPage.setOnAction(e -> this.openLoginView());

		this.boxButtons = new HBox();
		this.boxButtons.getChildren().addAll(this.buttonSubmit, this.buttonLoginPage);

		this.boxButtons.setAlignment(Pos.CENTER);
		this.boxButtons.setSpacing(this.spacing);

		this.setSpacing(this.spacing);
		this.setPadding(new Insets(0, this.padding, 0, this.padding));

		this.getChildren().addAll(view.getLogo(), this.fieldUsername, this.fieldPassword, this.boxButtons);
    }


    public void submit() {
        if (!view.getAccountController().createAccount(this.fieldUsername.getText(), this.fieldPassword.getText())) {
            throw new RuntimeException("Register failed");
        }

        view.openMenuView();
    }

    public void openLoginView(){
        view.openLoginView();
    }
}
