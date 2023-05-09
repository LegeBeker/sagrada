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

    private static final int BUTTONHEIGHT = 25;
    private static final int BUTTONWIDTH = 200;

    private static final int TEXTTITLEINSET = 10;

    private static final int PADDING = 200;
    private static final int SPACING = 20;

    private static final int YVALUETEXT = 20;

    public StatsView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());
        this.setAlignment(Pos.CENTER);

        Text text = new Text("Alle Accounts");
        text.setStyle("-fx-font-size: 40px");
        text.setStroke(Color.web("#000000"));
        text.setFill(Color.web("#ffffff"));

        this.textTitle = new StackPane(text);
        this.textTitle.setPadding(new Insets(TEXTTITLEINSET, 0, SPACING, 0));

        Text text2 = new Text("Gebruikersnaam:");
        text2.setStyle("-fx-font-size: 20px");
        text2.setFill(Color.web("#ffffff"));
        text2.setY(YVALUETEXT);

        this.accountsView = new AccountsView(view);

        this.buttonBack = new Button("Terug");
        this.buttonBack.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        this.buttonBack.setOnAction(e -> this.view.openMenuView());

        this.boxButtons = new HBox();
        this.boxButtons.getChildren().addAll(this.buttonBack);

        this.boxButtons.setAlignment(Pos.CENTER);
        this.boxButtons.setSpacing(SPACING);
        this.boxButtons.setPadding(new Insets(SPACING, 0, SPACING, 0));

        this.setPadding(new Insets(0, PADDING, 0, PADDING));

        this.getChildren().addAll(this.textTitle, this.accountsView, this.boxButtons);
    }
}
