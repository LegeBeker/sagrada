package main.java.view;

import javafx.scene.layout.VBox;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Account;
import main.java.model.Game;

public class AccountsView extends VBox {
    private ViewController view;

    private ArrayList<AccountView> tableContents = new ArrayList<AccountView>();

    private AnchorPane tableHeader;

    private ScrollPane scrollBox;

    private VBox contentBox;

    private final int scrollBoxHeight = 300;
    private final int scrollBoxWidth = 300;
    private final int widthCorrection = 100;

    private final int tableHeaderInsets = -10;

    private final int padding = 200;
    private final int spacing = 20;

    private final int yValueText = 20;

    public AccountsView(final ViewController view, final Game game) {
        generateGeneralAccountsView(view);
        generateAccountViews(game);
        setScrollBoxStyling();

        this.getChildren().addAll(this.tableHeader, this.scrollBox);
    }

    public AccountsView(final ViewController view) {
        generateGeneralAccountsView(view);
        generateAccountViews();
        setScrollBoxStyling();

        this.getChildren().addAll(this.tableHeader, this.scrollBox);
    }

    public void resize(final double width, final double height) {
        super.resize(width, height);
        // -- TODO: @Tim, check this out later, resizing is doing le funnies atm.
        for (AccountView av : tableContents) {
            av.resize(width, height);
        }
    }

    private void generateGeneralAccountsView(final ViewController view) {
        this.view = view;

        this.setAlignment(Pos.CENTER);
        this.setMinWidth(scrollBoxWidth);

        Text text2 = new Text("Gebruikersnaam:");
        text2.setStyle("-fx-font-size: 20px");
        text2.setFill(Color.web("#ffffff"));
        text2.setY(yValueText);

        this.tableHeader = new AnchorPane(text2);
        this.tableHeader.setPadding(new Insets(tableHeaderInsets, 0, this.spacing, 0));

        this.contentBox = new VBox();
        this.contentBox.setMinWidth(scrollBoxWidth - 2);

        this.setPadding(new Insets(0, this.padding, 0, this.padding));
    }

    private void setScrollBoxStyling() {
        this.scrollBox.setPrefSize(scrollBoxWidth, scrollBoxHeight);
        this.scrollBox.setMinWidth(scrollBoxWidth - widthCorrection);
        this.scrollBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.scrollBox.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), null, null)));
        this.scrollBox.setPadding(new Insets(0, 0, 0, 0));
    }

    private void generateAccountViews(final Game game) {
        for (Account acc : view.getAccountController().getAccounts()) {
            if (acc.getUsername().equals(view.getAccountController().getAccount().getUsername())) {
                continue;
            }
            AccountView accountView = new AccountView(view, acc, game);
            this.contentBox.getChildren().add(accountView);
            tableContents.add(accountView);
        }
        this.scrollBox = new ScrollPane(this.contentBox);
    }

    private void generateAccountViews() {
        for (Account acc : view.getAccountController().getAccounts()) {
            AccountView accountView = new AccountView(view, acc);
            this.contentBox.getChildren().add(accountView);
            tableContents.add(accountView);
        }
        this.scrollBox = new ScrollPane(this.contentBox);
    }

}
