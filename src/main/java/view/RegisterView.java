package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.controller.ViewController;

public class RegisterView extends VBox {

    private ViewController view;

    private TextField fieldUsername;
    private PasswordField fieldPassword;
    private PasswordField fieldPasswordRepeat;

    private HBox boxButtons;

    private Button buttonSubmit;
    private Button buttonLoginPage;

    private static final int BUTTONHEIGHT = 25;
    private static final int BUTTONWIDTH = 200;

    private static final int PADDING = 200;
    private static final int SPACING = 15;

    public RegisterView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());

        this.setAlignment(Pos.CENTER);

        this.fieldUsername = new TextField();
        this.fieldUsername.setPromptText("Gebruikersnaam");

        this.fieldPassword = new PasswordField();
        this.fieldPassword.setPromptText("Wachtwoord");

        this.fieldPasswordRepeat = new PasswordField();
        this.fieldPasswordRepeat.setPromptText("Herhaal wachtwoord");

        this.buttonSubmit = new Button("Registeren");
        this.buttonSubmit.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        this.buttonSubmit.setOnAction(e -> this.submit());

        this.buttonLoginPage = new Button("Login pagina");
        this.buttonLoginPage.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        this.buttonLoginPage.setOnAction(e -> this.openLoginView());

        this.boxButtons = new HBox();
        this.boxButtons.getChildren().addAll(this.buttonSubmit, this.buttonLoginPage);

        this.boxButtons.setAlignment(Pos.CENTER);
        this.boxButtons.setSpacing(SPACING);

        this.setSpacing(SPACING);
        this.setPadding(new Insets(0, PADDING, 0, PADDING));

        this.getChildren().addAll(view.getLogo(), this.fieldUsername, this.fieldPassword,
                this.fieldPasswordRepeat, this.boxButtons);
    }

    public void submit() {
        try {
            if (this.fieldUsername.getText().isEmpty() || this.fieldPassword.getText().isEmpty()
                    || this.fieldPasswordRepeat.getText().isEmpty()) {
                throw new RuntimeException("Vul alle velden in");
            }

            if (!this.fieldPassword.getText().equals(this.fieldPasswordRepeat.getText())) {
                throw new RuntimeException("Wachtwoorden komen niet overeen");
            }

            if (!this.fieldUsername.getText().matches("^[a-zA-Z0-9]{3,25}$")) {
                throw new RuntimeException("Gebruikersnaam is ongeldig");
            } else if (!this.fieldPassword.getText().matches("^[a-zA-Z0-9]{3,25}$")) {
                throw new RuntimeException("Wachtwoord is ongeldig");
            }

            if (!view.getAccountController().createAccount(this.fieldUsername.getText(),
                    this.fieldPassword.getText())) {
                throw new RuntimeException("Aanmaken account is mislukt");
            }
        } catch (RuntimeException e) {
            view.displayError(e.getMessage());
        }

        view.openMenuView();
    }

    public void openLoginView() {
        view.openLoginView();
    }
}
