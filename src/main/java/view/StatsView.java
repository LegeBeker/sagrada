package main.java.view;

import javafx.scene.layout.VBox;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Account;
import main.java.model.Game;

public class StatsView extends VBox {

    private ViewController view;

    private ArrayList<AccountView> tableContents = new ArrayList<AccountView>();

    private StackPane textTitle;
    private AnchorPane tableHeader;

    private ScrollPane scrollBox;

    private VBox contentBox;

    private final int scrollBoxHeight = 300;
    private final int scrollBoxWidth = 400;

    private HBox boxButtons;

    private Button buttonBack;

    private final int buttonHeight = 25;
    private final int buttonWidth = 200;

    private final int tableHeaderInsets = -10;
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

        this.tableHeader = new AnchorPane(text2);
        this.tableHeader.setPadding(new Insets(tableHeaderInsets, 0, this.spacing, 0));

        this.contentBox = new VBox();
        this.contentBox.setMinWidth(scrollBoxWidth - 2);

        for (Account acc : view.getAccountController().getAccounts()) {
            AccountView accountView = new AccountView(view, acc);
            this.contentBox.getChildren().add(accountView);
            tableContents.add(accountView);
        }
        this.scrollBox = new ScrollPane(this.contentBox);

        this.scrollBox.setPrefSize(scrollBoxWidth, scrollBoxHeight);
        this.scrollBox.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), null, null)));

        this.scrollBox.setPadding(new Insets(0, 0, 0, 0));

        this.buttonBack = new Button("Terug");
        this.buttonBack.setPrefSize(this.buttonWidth, this.buttonHeight);
        this.buttonBack.setOnAction(e -> this.view.openMenuView());

        this.boxButtons = new HBox();
        this.boxButtons.getChildren().addAll(this.buttonBack);

        this.boxButtons.setAlignment(Pos.CENTER);
        this.boxButtons.setSpacing(this.spacing);
        this.boxButtons.setPadding(new Insets(this.spacing, 0, this.spacing, 0));

        this.setPadding(new Insets(0, this.padding, 0, this.padding));

        this.getChildren().addAll(this.textTitle, this.tableHeader, this.scrollBox, this.boxButtons);
    }

    public void resize(final double width, final double height) {
        super.resize(width, height);
        // -- TODO: @Tim, check this out later, resizing is doing le funnies atm.
        for (AccountView av : tableContents) {
            av.resize(width, height);
        }
    }
}
