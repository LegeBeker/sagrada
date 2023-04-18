package main.java.view;

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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Account;
import main.java.model.Game;
import main.java.model.Invite;

public class InvitesList extends VBox {

    private ViewController view;

    private StackPane textTitle;

    private StackPane scrollBox;

    private final int scrollBoxHeight = 300;
    private final int scrollBoxWidth = 200;

    private TableView<Invite> table;

    private HBox boxButtons;

    private Button inviteTypeButton;

    private final int buttonHeight = 25;
    private final int buttonWidth = 200;

    private final int padding = 50;
    private final int spacing = 15;

    public InvitesList(final ViewController view) {
        Account player = view.getAccountController().getAccount();

        this.view = view;

        this.setAlignment(Pos.CENTER);

        Text text = new Text("Uitnodigingen");
        text.setStyle("-fx-font-size: 40px");
        text.setStroke(Color.web("#000000"));
        text.setFill(Color.web("#ffffff"));

        this.textTitle = new StackPane(text);
        this.textTitle.setPadding(new Insets(0, 0, this.spacing, 0));

        this.table = new TableView<Invite>();

        TableColumn<Invite, String> name = new TableColumn<>("Naam");
        name.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Invite, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("PlayStatus"));

        Collections.addAll(this.table.getColumns(), name, status);

        for (Invite invite : view.getInviteController().getInvites(player.getUsername())) {
            this.table.getItems().add(invite);
        }

        this.scrollBox = new StackPane();

        this.scrollBox.getChildren().add(table);

        this.scrollBox.setPrefSize(scrollBoxWidth, scrollBoxHeight);
        this.scrollBox.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), null, null)));

        this.scrollBox.setPadding(new Insets(0, 0, 0, 0));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.prefWidthProperty().bind(this.scrollBox.widthProperty());

        this.inviteTypeButton = new Button("Verstuurde uitnodigingen");
        this.inviteTypeButton.setPrefSize(this.buttonWidth, this.buttonHeight);
        // this.inviteTypeButton.setOnAction(e -> this.back());

        this.boxButtons = new HBox();
        this.boxButtons.getChildren().addAll(this.inviteTypeButton);

        this.boxButtons.setAlignment(Pos.CENTER);
        this.boxButtons.setSpacing(this.spacing);
        this.boxButtons.setPadding(new Insets(this.spacing, 0, this.spacing, 0));

        this.setPadding(new Insets(0, this.padding, 0, this.padding));

        this.getChildren().addAll(this.textTitle, this.scrollBox, this.boxButtons);
    }
}
