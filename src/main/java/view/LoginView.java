package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.controller.ViewController;

public class LoginView extends VBox {

    private ViewController view;

    private TextField fieldUsername;
    private PasswordField fieldPassword;

    private HBox boxButtons;

    private Button buttonSubmit;
    private Button buttonRegisterPage;

    private final int buttonHeight = 25;
    private final int buttonWidth = 200;

    private final int padding = 200;
    private final int spacing = 15;

    public LoginView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());

        this.setAlignment(Pos.CENTER);

        this.fieldUsername = new TextField();
        this.fieldUsername.setPromptText("Gebruikersnaam");

        this.fieldPassword = new PasswordField();
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

        this.fieldUsername.setOnAction(e -> this.submit());
        this.fieldPassword.setOnAction(e -> this.submit());
    }

    public void submit() {
        try {
            if (this.fieldUsername.getText().isEmpty() || this.fieldPassword.getText().isEmpty()) {
                throw new RuntimeException("Vul alle velden in");
            }

            if (!this.fieldUsername.getText().matches("^[a-zA-Z0-9]{3,25}$")) {
                throw new RuntimeException("Gebruikersnaam is ongeldig");
            } else if (!this.fieldPassword.getText().matches("^[a-zA-Z0-9]{3,25}$")) {
                throw new RuntimeException("Wachtwoord is ongeldig");
            }

            if (!view.getAccountController().loginAccount(this.fieldUsername.getText(), this.fieldPassword.getText())) {
                throw new RuntimeException("Gebruikersnaam of wachtwoord is onjuist");
            }

            view.openMenuView();
        } catch (RuntimeException e) {
            view.displayError(e.getMessage());
            return;
        }
    }

    public void openRegisterView() {
        view.openRegisterView();
    }
}
