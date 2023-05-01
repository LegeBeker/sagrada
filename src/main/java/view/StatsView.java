package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;

public class StatsView extends VBox {

    private ViewController view;

    private StackPane textTitle;
    private AccountsView accountsView;

    private HBox boxButtons;

    private Button buttonBack;

    private final int buttonHeight = 25;
    private final int buttonWidth = 200;

    private final int textTitleInset = 10;

    private final int padding = 200;
    private final int spacing = 20;

    private final int yValueText = 20;

    public StatsView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());
        this.setAlignment(Pos.CENTER);

        Text text = new Text("Alle Accounts");
        text.setStyle("-fx-font-size: 40px");
        text.setStroke(Color.web("#000000"));
        text.setFill(Color.web("#ffffff"));

        this.textTitle = new StackPane(text);
        this.textTitle.setPadding(new Insets(textTitleInset, 0, this.spacing, 0));

        Text text2 = new Text("Gebruikersnaam:");
        text2.setStyle("-fx-font-size: 20px");
        text2.setFill(Color.web("#ffffff"));
        text2.setY(yValueText);

        this.accountsView = new AccountsView(view);

        this.buttonBack = new Button("Terug");
        this.buttonBack.setPrefSize(this.buttonWidth, this.buttonHeight);
        this.buttonBack.setOnAction(e -> this.view.openMenuView());

        this.boxButtons = new HBox();
        this.boxButtons.getChildren().addAll(this.buttonBack);

        this.boxButtons.setAlignment(Pos.CENTER);
        this.boxButtons.setSpacing(this.spacing);
        this.boxButtons.setPadding(new Insets(this.spacing, 0, this.spacing, 0));

        this.setPadding(new Insets(0, this.padding, 0, this.padding));

        this.getChildren().addAll(this.textTitle, this.accountsView, this.boxButtons);
    }
}
