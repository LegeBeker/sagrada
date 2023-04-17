package main.java.view;

import javafx.scene.layout.VBox;
import java.util.Collections;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Account;

public class AccountsView extends VBox {
    private ViewController view;

    private StackPane textTitle;

    private StackPane scrollBox;

    private final int scrollBoxHeight = 300;
    private final int scrollBoxWidth = 400;

    private TableView<Account> table;

    private HBox boxButtons;

    private Button buttonBack;

    private final int buttonHeight = 25;
    private final int buttonWidth = 200;

    private final int padding = 200;
    private final int spacing = 15;

    public AccountsView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());

        this.setAlignment(Pos.CENTER);

        Text text = new Text("Alle Accounts");
        text.setStyle("-fx-font-size: 40px");
        text.setStroke(Color.web("#000000"));
        text.setFill(Color.web("#ffffff"));

        this.textTitle = new StackPane(text);
        this.textTitle.setPadding(new Insets(0, 0, this.spacing, 0));

        this.table = new TableView<Account>();

        TableColumn<Account, Integer> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        Collections.addAll(this.table.getColumns(), usernameCol);

        for (Account acc : view.getAccountController().getAccounts()) {
            this.table.getItems().add(acc);
        }

        this.table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Account acc = this.table.getSelectionModel().getSelectedItem();
                // this.view.openGameView(a);
                // -- @Tim, check/discuss out what we want to do at on-click event.
            }
        });

        this.scrollBox = new StackPane();

        this.scrollBox.getChildren().add(table);

        this.scrollBox.setPrefSize(scrollBoxWidth, scrollBoxHeight);
        this.scrollBox.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), null, null)));

        this.scrollBox.setPadding(new Insets(0, 0, 0, 0));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.prefWidthProperty().bind(this.scrollBox.widthProperty());

        this.buttonBack = new Button("Terug");
        this.buttonBack.setPrefSize(this.buttonWidth, this.buttonHeight);
        this.buttonBack.setOnAction(e -> this.back());

        this.boxButtons = new HBox();
        this.boxButtons.getChildren().addAll(this.buttonBack);

        this.boxButtons.setAlignment(Pos.CENTER);
        this.boxButtons.setSpacing(this.spacing);
        this.boxButtons.setPadding(new Insets(this.spacing, 0, this.spacing, 0));

        this.setPadding(new Insets(0, this.padding, 0, this.padding));

        this.getChildren().addAll(this.textTitle, this.scrollBox, this.boxButtons);
    }

    private void back() {
        this.view.openMenuView();
    }
}
