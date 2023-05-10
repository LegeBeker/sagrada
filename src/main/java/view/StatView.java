package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.AccountController;
import main.java.controller.ViewController;
import main.java.model.Account;

public class StatView extends VBox {

    private ViewController view;
    private StackPane textTitle;
    private HBox boxButtons;
    private AccountController accountController;

    private final Button buttonBack = new Button("Terug");
    private final int textTitleInset = 10;
    private final int padding = 200;
    private final int spacing = 20;
    private final Color textColor = Color.WHITE;

    public StatView(final ViewController view, final Account account) {
        this.view = view;
        this.setBackground(view.getBackground());
        this.setAlignment(Pos.CENTER);
        this.accountController = new AccountController();

        Text text = new Text("Statistieken van " + account.getUsername());
        text.setStyle("-fx-font-size: 40px");
        text.setStroke(Color.web("#000000"));
        text.setFill(Color.web("#ffffff"));

        this.textTitle = new StackPane(text);
        this.textTitle.setPadding(new Insets(textTitleInset, 0, this.spacing, 0));

        Text winText = new Text(
                "Aantal gewonnen potjes: " + accountController.getAmountWonGames(account.getUsername()));
        Text loseText = new Text(
                "Aantal verloren potjes: " + accountController.getAmountLostGames(account.getUsername()));
        Text highestScore = new Text("Hoogste score: " + accountController.getHighestScore(account.getUsername()));
        Text mostPlacedColor = new Text(
                "Meest geplaatste kleur: " + accountController.getMostPlacedColor(account.getUsername()));
        Text mostPlacedValue = new Text(
                "Meest geplaatste waarde: " + accountController.getMostPlacedValue(account.getUsername()));
        Text amountUniqueOpponents = new Text(
                "Aantal verschillende tegenstanders: " + accountController.getAmountOpponents(account.getUsername()));

        winText.setFill(textColor);
        loseText.setFill(textColor);
        highestScore.setFill(textColor);
        mostPlacedColor.setFill(textColor);
        mostPlacedValue.setFill(textColor);
        amountUniqueOpponents.setFill(textColor);

        this.boxButtons = new HBox();
        this.boxButtons.getChildren().addAll(this.buttonBack);

        this.boxButtons.setAlignment(Pos.CENTER);
        this.boxButtons.setSpacing(this.spacing);
        this.boxButtons.setPadding(new Insets(this.spacing, 0, this.spacing, 0));

        this.buttonBack.setOnAction(e -> {
            view.openStatsView();
        });

        this.getChildren().addAll(this.textTitle, winText, loseText, highestScore, mostPlacedColor, mostPlacedValue,
                amountUniqueOpponents, this.boxButtons);
        this.setBackground(view.getBackground());
    }
}
