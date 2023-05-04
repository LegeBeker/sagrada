package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.PatternCard;
import main.java.model.Player;

public class PatternCardSelectionView extends BorderPane {

    private VBox vbox;
    private FlowPane flowPane;
    private StackPane textTitle;
    private StackPane buttonPane;

    private final int padding = 50;
    private final int spacing = 10;

    private final ViewController view;
    private final Player player;

    private final Background background = new Background(new BackgroundFill(Color.web("#334564"), null, null));

    public PatternCardSelectionView(final ViewController view, final Player player) {
        super();
        this.view = view;
        this.flowPane = new FlowPane();
        this.player = player;

        this.setBackground(background);

        Text text = new Text("Patroonkaart kiezen");
        text.setStyle("-fx-font-size: 40px");
        text.setFill(Color.web("#ffffff"));

        this.textTitle = new StackPane(text);
        textTitle.setPadding(new Insets(padding, 0, 0, 0));
        this.setTop(textTitle);

        player.getPatternCardOptions().forEach(patternCard -> {
            flowPane.getChildren().add(patternCardSelect(patternCard));
        });

        this.flowPane.setHgap(10);
        this.flowPane.setVgap(10);
        this.flowPane.setAlignment(Pos.CENTER);

        this.setCenter(this.flowPane);

        Button buttonBack = new Button("Terug");
        buttonBack.setOnAction(e -> view.openGamesView());
        this.buttonPane = new StackPane(buttonBack);
        this.buttonPane.setPadding(new Insets(0, 0, padding, 0));
        this.setBottom(this.buttonPane);
    }

    private VBox patternCardSelect(final PatternCard patternCard) {
        this.vbox = new VBox();
        this.vbox.getChildren().add(new PatternCardView(this.view, patternCard, null));
        Button button = new Button("Kiezen");

        button.setOnAction(event -> {
            this.view.getGameController().setGame(player.getGame());
            this.view.getGameController().choosePatternCard(patternCard);
            this.view.openGameView(player.getGame());
        });

        this.vbox.getChildren().add(button);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(spacing);
        return this.vbox;
    }

}