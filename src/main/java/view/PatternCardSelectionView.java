package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;

public class PatternCardSelectionView extends BorderPane {

    private VBox vbox;
    private FlowPane flowPane;
    private StackPane textTitle;
    private StackPane buttonPane;

    private static final int PADDING = 50;
    private static final int SPACING = 10;

    private final ViewController view;

    public PatternCardSelectionView(final ViewController view) {
        this.view = view;
        this.flowPane = new FlowPane();

        Text text = new Text("Patroonkaart kiezen");
        text.setStyle("-fx-font-size: 40px");
        text.setFill(Color.web("#ffffff"));

        this.textTitle = new StackPane(text);
        textTitle.setPadding(new Insets(PADDING, 0, 0, 0));
        this.setTop(textTitle);

        view.getPatternCardOptions().get(view.getPlayerId()).forEach(patternCard -> {
            flowPane.getChildren().add(patternCardSelect(patternCard));
        });

        this.flowPane.setHgap(10);
        this.flowPane.setVgap(10);
        this.flowPane.setAlignment(Pos.CENTER);

        this.setCenter(this.flowPane);

        Button buttonBack = new Button("Terug");
        buttonBack.setOnAction(e -> view.openGamesView());
        this.buttonPane = new StackPane(buttonBack);
        this.buttonPane.setPadding(new Insets(0, 0, PADDING, 0));
        this.setBottom(this.buttonPane);

        this.setBackground(view.getBackground());
    }

    private VBox patternCardSelect(final int idPatternCard) {
        this.vbox = new VBox();
        this.vbox.getChildren().add(new PatternCardView(this.view, idPatternCard, null));
        Button button = new Button("Kiezen");

        button.setOnAction(event -> {
            this.view.choosePatternCard(idPatternCard);
        });

        this.vbox.getChildren().add(button);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(SPACING);
        return this.vbox;
    }

}
