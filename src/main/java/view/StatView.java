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

public class StatView extends VBox {

        private StackPane textTitle;
        private HBox boxButtons;

        private final Button buttonBack = new Button("Terug");
        private static final int TEXTTITLEINSET = 10;
        private static final int SPACING = 20;
        private static final Color TEXTCOLOR = Color.WHITE;

        public StatView(final ViewController view) {
                this.setBackground(view.getBackground());
                this.setAlignment(Pos.CENTER);

                Text text = new Text("Statistieken van " + view.getUsername());
                text.setStyle("-fx-font-size: 40px");
                text.setStroke(Color.web("#000000"));
                text.setFill(Color.web("#ffffff"));

                this.textTitle = new StackPane(text);
                this.textTitle.setPadding(new Insets(TEXTTITLEINSET, 0, SPACING, 0));

                // Text winText = new Text("Aantal gewonnen potjes: "
                //                 + view.getAccountController().getAmountWonGames(account.getUsername()));
                // Text loseText = new Text("Aantal verloren potjes: "
                //                 + view.getAccountController().getAmountLostGames(account.getUsername()));
                // Text highestScore = new Text("Hoogste score: "
                //                 + view.getAccountController().getHighestScore(account.getUsername()));
                // Text mostPlacedColor = new Text("Meest geplaatste kleur: "
                //                 + view.getAccountController().getMostPlacedColor(account.getUsername()));
                // Text mostPlacedValue = new Text("Meest geplaatste waarde: "
                //                 + view.getAccountController().getMostPlacedValue(account.getUsername()));
                // Text amountUniqueOpponents = new Text("Aantal verschillende tegenstanders: "
                //                 + view.getAccountController().getAmountOpponents(account.getUsername()));

                // winText.setFill(TEXTCOLOR);
                // loseText.setFill(TEXTCOLOR);
                // highestScore.setFill(TEXTCOLOR);
                // mostPlacedColor.setFill(TEXTCOLOR);
                // mostPlacedValue.setFill(TEXTCOLOR);
                // amountUniqueOpponents.setFill(TEXTCOLOR);

                this.boxButtons = new HBox();
                this.boxButtons.getChildren().addAll(this.buttonBack);

                this.boxButtons.setAlignment(Pos.CENTER);
                this.boxButtons.setSpacing(SPACING);
                this.boxButtons.setPadding(new Insets(SPACING, 0, SPACING, 0));

                this.buttonBack.setOnAction(e -> {
                        view.openStatsView();
                });

                // this.getChildren().addAll(this.textTitle, winText, loseText, highestScore, mostPlacedColor,
                //                 mostPlacedValue,
                //                 amountUniqueOpponents, this.boxButtons);
                this.setBackground(view.getBackground());
        }
}
