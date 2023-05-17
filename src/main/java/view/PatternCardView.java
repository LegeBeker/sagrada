package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.controller.ViewController;
import main.java.model.Die;
import main.java.model.Game;
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class PatternCardView extends BorderPane implements Observer {

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private static final int RECTANGLE = 50;
    private static final int PADDING = 10;

    private static final int EASY = 3;
    private static final int MEDIUM = 4;
    private static final int HARD = 5;
    private static final int VERYHARD = 6;

    private static final Double BRIGHTNESS = 0.2;

    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;

    private final GridPane grid = new GridPane();
    private final Text cardTopText = new Text();
    private final Text gameTokenText = new Text();
    private final BorderPane topDisplay = new BorderPane();

    private final ViewController view;

    private final int patternCardId;
    private final Integer playerId;

    public PatternCardView(final ViewController view, final int patternCardId, final Integer playerId) {
        this.view = view;
        this.setPrefSize(WIDTH, HEIGHT);
        this.getStyleClass().add("patterncard");

        this.patternCardId = patternCardId;
        this.playerId = playerId;

        this.update();
        grid.setPadding(new Insets(0, PADDING, PADDING, 0));
        this.setCenter(grid);

        TextFlow cardTopTextFlow = new TextFlow(cardTopText);
        TextFlow gameTokenTextFlow = new TextFlow(gameTokenText);
        cardTopTextFlow.setPadding(new Insets(PADDING, PADDING, 0, PADDING));
        gameTokenTextFlow.setPadding(new Insets(PADDING, PADDING, 0, PADDING));
        cardTopText.setFill(Color.WHITE);
        gameTokenText.setFill(Color.WHITE);
        topDisplay.setLeft(cardTopTextFlow);
        topDisplay.setRight(gameTokenTextFlow);
        this.setTop(topDisplay);

        if (playerId == null) {
            cardTopText.setText(view.getPatternCardName(patternCardId));

            Text cardDifficulty = new Text("Moeilijkheid: ");
            cardDifficulty.setFill(Color.WHITE);

            String dots = "";
            for (int i = 0; i < view.getPatternCardDifficulty(patternCardId); i++) {
                dots += " • ";
            }
            Text cardDifficultyDots = new Text(dots);
            cardDifficultyDots.setStyle("-fx-font-weight: bold;");

            switch (view.getPatternCardDifficulty(patternCardId)) {
                case EASY:
                    cardDifficultyDots.setFill(Color.GREEN);
                    break;
                case MEDIUM:
                    cardDifficultyDots.setFill(Color.YELLOW);
                    break;
                case HARD:
                    cardDifficultyDots.setFill(Color.ORANGE);
                    break;
                case VERYHARD:
                    cardDifficultyDots.setFill(Color.RED);
                    break;
                default:
                    cardDifficultyDots.setFill(Color.WHITE);
                    break;
            }

            TextFlow cardDifficultyFlow = new TextFlow(cardDifficulty, cardDifficultyDots);
            cardDifficultyFlow.setPadding(new Insets(0, PADDING, PADDING, PADDING));

            this.setStyle("-fx-background-color: black;");

            this.setBottom(cardDifficultyFlow);
        } else {
            cardTopText.setText(view.getPlayerUsername(playerId));

            gameTokenText.setText(" Betaalstenen: " + Integer.toString(view.getPlayerGameTokens(playerId)));
            Observable.addObserver(Game.class, this);

            Color playerColor = view.getPlayerColor(playerId).deriveColor(0, 1, BRIGHTNESS, 1);
            this.setStyle("-fx-background-color: " + playerColor.toString().replace("0x", "#") + ";");
            if (view.isTurnPlayer()) {
                this.setStyle("-fx-border-color: #00FFBF; -fx-border-width: 1px;" + this.getStyle());
            }
        }

        grid.setHgap(PADDING);
        grid.setVgap(PADDING);
    }

    @Override
    public void update() {
        grid.getChildren().clear();
        drawPatternCard(view, patternCardId, playerId);
    }

    private void drawPatternCard(final ViewController view, final int patternCardId, final Integer playerId) {
        final boolean isCardOwner;
        if (playerId != null) {
            isCardOwner = view.isCurrentPlayer(playerId);
        } else {
            isCardOwner = false;
        }

        for (int col = 1; col <= COLUMNS; col++) {
            for (int row = 1; row <= ROWS; row++) {
                Integer value = view.getPatternCardFieldValue(patternCardId, col, row);
                Color color = view.getPatternCardFieldColor(patternCardId, col, row);

                if (value != null) {
                    createAndAddNode(isCardOwner, new DieView(value), color, col, row);
                } else {
                    createAndAddNode(isCardOwner, new Rectangle(RECTANGLE, RECTANGLE), color, col, row);
                }
            }
        }
    }

    private void createAndAddNode(final boolean isCardOwner, final Node node, final Color color, final int col,
            final int row) {
        if (node instanceof Rectangle) {
            ((Rectangle) node).setFill(color == null ? Color.WHITE : color);
        }

        StackPane stackPane;

        if (isCardOwner) {
            stackPane = new DieDropTarget(this.view);
        } else {
            stackPane = new StackPane();
        }
        stackPane.getChildren().add(node);
        node.setStyle("-fx-border-color: transparent;");

        if (this.playerId != null && view.getPlayerBoardField(this.playerId, row, col) != null) {
            Die die = view.getPlayerBoardField(this.playerId, row, col);
            DieView dieView = new DieView(this.view, die.getEyes(), die.getColor(), die.getNumber(), false);
            stackPane.getChildren().add(dieView);
        }

        grid.add(stackPane, col, row);
    }

    public GridPane getGrid() {
        return this.grid;
    }
}
