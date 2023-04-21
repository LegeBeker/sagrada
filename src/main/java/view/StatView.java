package main.java.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Account;

public class StatView extends VBox {

    private ViewController view;
    private final Button buttonBack = new Button("Terug");

    public StatView(final ViewController view, final Account account) {
        this.view = view;

        this.buttonBack.setOnAction(e -> {
            view.openStatsView();
        });

        this.getChildren().addAll(new Text(account.getUsername()), buttonBack);
        this.setBackground(view.getBackground());
    }
}
