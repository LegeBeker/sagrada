package main.java.view;

import java.util.Map;

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

        private Map<String, String> stats;

        private final Button buttonBack = new Button("Terug");
        private static final int TEXTTITLEINSET = 10;
        private static final int SPACING = 20;
        private static final Color TEXTCOLOR = Color.WHITE;

        public StatView(final ViewController view, final String username) {
                this.setBackground(view.getBackground());
                this.setAlignment(Pos.CENTER);

                Text text = new Text("Statistieken van " + view.getUsername());
                text.setStyle("-fx-font-size: 40px");
                text.setStroke(Color.web("#000000"));
                text.setFill(Color.web("#ffffff"));

                this.textTitle = new StackPane(text);
                this.textTitle.setPadding(new Insets(TEXTTITLEINSET, 0, SPACING, 0));

                this.stats = view.getStats(username);

                Text winText = new Text("Aantal gewonnen potjes: " + this.stats.get("wonGames"));
                Text loseText = new Text("Aantal verloren potjes: " + this.stats.get("lostGames"));
                Text highestScore = new Text("Hoogste score: "
                                + ((this.stats.get("highestScore") != null ? this.stats.get("highestScore")
                                                : "Geen scores gevonden")));
                Text mostPlacedColor = new Text("Meest geplaatste kleur: "
                                + ((this.stats.get("mostPlacedColor") != null ? this.stats.get("mostPlacedColor")
                                                : "Geen dobbelsteen geplaatst")));
                Text mostPlacedValue = new Text("Meest geplaatste waarde: "
                                + ((this.stats.get("mostPlacedValue") != null ? this.stats.get("mostPlacedValue")
                                                : "Geen dobbelsteen geplaatst")));
                Text amountUniqueOpponents = new Text(
                                "Aantal verschillende tegenstanders: " + this.stats.get("amountUniqueOpponents"));

                winText.setFill(TEXTCOLOR);
                loseText.setFill(TEXTCOLOR);
                highestScore.setFill(TEXTCOLOR);
                mostPlacedColor.setFill(TEXTCOLOR);
                mostPlacedValue.setFill(TEXTCOLOR);
                amountUniqueOpponents.setFill(TEXTCOLOR);

                this.boxButtons = new HBox();
                this.boxButtons.getChildren().addAll(this.buttonBack);

                this.boxButtons.setAlignment(Pos.CENTER);
                this.boxButtons.setSpacing(SPACING);
                this.boxButtons.setPadding(new Insets(SPACING, 0, SPACING, 0));

                this.buttonBack.setOnAction(e -> {
                        view.openStatsView();
                });

                this.getChildren().addAll(this.textTitle, winText, loseText, highestScore,
                                mostPlacedColor,
                                mostPlacedValue,
                                amountUniqueOpponents, this.boxButtons);
                this.setBackground(view.getBackground());
        }
}
