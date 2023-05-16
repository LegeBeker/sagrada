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
import main.java.model.PatternCard;
import main.java.model.PatternCardField;
import main.java.model.Player;
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

    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;

    private final GridPane grid = new GridPane();
    private final Text cardTopText = new Text();
    private final Text gameTokenText = new Text();
    private final BorderPane topDisplay = new BorderPane();

    private final ViewController view;
    private final PatternCard patternCard;
    private final Player player;

    public PatternCardView(final ViewController view, final PatternCard patternCard, final Player player) {
        this.view = view;
        this.patternCard = patternCard;
        this.player = player;

        this.setPrefSize(WIDTH, HEIGHT);
        this.getStyleClass().add("patterncard");

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

        if (player == null) {
            cardTopText.setText(patternCard.getName());

            Text cardDifficulty = new Text("Moeilijkheid: ");
            cardDifficulty.setFill(Color.WHITE);

            String dots = "";
            for (int i = 0; i < patternCard.getDifficulty(); i++) {
                dots += " • ";
            }
            Text cardDifficultyDots = new Text(dots);
            cardDifficultyDots.setStyle("-fx-font-weight: bold;");

            switch (patternCard.getDifficulty()) {
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
            cardTopText.setText(player.getUsername());
          
            gameTokenText.setText(" Betaalstenen: " + Integer.toString(player.getFavorTokensLeft()));
            Observable.addObserver(Game.class, this);
          
            Color playerColor = player.getColor().deriveColor(0, 1, 0.2, 1);
            this.setStyle("-fx-background-color: " + playerColor.toString().replace("0x", "#") + ";");
            if (player.getUsername().equals(player.getGame().getTurnPlayer().getUsername())) {
                this.setStyle("-fx-border-color: #00FFBF; -fx-border-width: 1px;" + this.getStyle());
            }
        }

        grid.setHgap(PADDING);
        grid.setVgap(PADDING);
    }

    @Override
    public void update() {
        grid.getChildren().clear();
        drawPatternCard(patternCard, view, player);
    }

    private void drawPatternCard(final PatternCard patternCard, final ViewController view, final Player player) {
        final boolean isCardOwner;
        if (player != null) {
            isCardOwner = view.getAccountController().getAccount().getUsername().equals(player.getUsername());
        } else {
            isCardOwner = false;
        }

        for (int col = 1; col <= COLUMNS; col++) {
            for (int row = 1; row <= ROWS; row++) {
                PatternCardField field = patternCard.getField(row, col);

                if (field.getValue() != null) {
                    createAndAddNode(isCardOwner, new DieView(field.getValue()), field.getColor(), col, row);
                } else {
                    createAndAddNode(isCardOwner, new Rectangle(RECTANGLE, RECTANGLE), field.getColor(), col, row);
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
            stackPane = new DieDropTarget(this.view, patternCard);
        } else {
            stackPane = new StackPane();
        }
        stackPane.getChildren().add(node);
        node.setStyle("-fx-border-color: transparent;");

        if (this.player != null && this.player.getBoard().getField(row, col) != null) {
            Die die = this.player.getBoard().getField(row, col);
            DieView dieView = new DieView(die.getEyes(), die.getColor(), die.getNumber(), false);
            stackPane.getChildren().add(dieView);
        }

        grid.add(stackPane, col, row);
    }

    public GridPane getGrid() {
        return this.grid;
    }
}
