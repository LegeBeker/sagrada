package view;

import controller.ViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegisterView extends VBox {

    private ViewController view;

    private TextField fieldUsername;
	private TextField fieldPassword;

	private Button buttonSubmit;
	private Button buttonRegister; 

    public RegisterView(ViewController view) {
        this.view = view;

		this.setAlignment(Pos.CENTER);

		this.fieldUsername = new TextField();

		this.fieldPassword = new TextField();

		this.buttonSubmit = new Button("Registeren");
		this.buttonSubmit.setOnAction(e -> this.submit());

		this.buttonRegister = new Button("Login pagina");
		this.buttonRegister.setOnAction(e -> this.login());

		this.getChildren().addAll(this.fieldUsername, this.fieldPassword, this.buttonSubmit, this.buttonRegister);
    }


    public void submit() {
        if (!view.getAccountController().createAccount(this.fieldUsername.getText(), this.fieldPassword.getText())) {
            throw new RuntimeException("Register failed");
        }

        view.OpenMenuView();
    }

    public void login(){
        view.OpenLoginView();
    }
}
