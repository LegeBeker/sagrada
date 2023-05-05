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
import main.java.model.PatternCard;
import main.java.model.PatternCardField;
import main.java.model.Player;
import main.java.pattern.Observer;

public class PatternCardView extends BorderPane implements Observer {

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private static final int RECTANGLE = 50;
    private static final int PADDING = 10;

    private final int width = 300;
    private final int height = 300;

    private final GridPane grid = new GridPane();
    private final Text cardTopText = new Text();
    private final Text cardDifficulty = new Text();

    private final ViewController view;
    private final PatternCard patternCard;
    private final Player player;

    public PatternCardView(final ViewController view, final PatternCard patternCard, final Player player) {
        this.view = view;
        this.patternCard = patternCard;
        this.player = player;

        this.setPrefSize(width, height);
        this.getStyleClass().add("background");

        this.update();
        grid.setPadding(new Insets(0, PADDING, PADDING, 0));
        this.setCenter(grid);

        TextFlow cardTopTextFlow = new TextFlow(cardTopText);
        cardTopTextFlow.setPadding(new Insets(PADDING, PADDING, 0, PADDING));
        cardTopText.setFill(Color.WHITE);
        this.setTop(cardTopTextFlow);

        if (player == null) {
            cardTopText.setText(patternCard.getName());

            cardDifficulty.setText("Moeilijkheid: " + patternCard.getDifficulty());
            TextFlow cardDifficultyFlow = new TextFlow(cardDifficulty);
            cardDifficultyFlow.setPadding(new Insets(0, PADDING, PADDING, PADDING));
            cardDifficulty.setFill(Color.WHITE);

            this.setBottom(cardDifficultyFlow);
        } else {
            cardTopText.setText(player.getUsername());
            player.getGame().addObserver(this);
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
            ((Rectangle) node).setFill(color);
        }

        if (isCardOwner) {
            DieDropTarget dieDropTarget = new DieDropTarget(this.view, patternCard);
            dieDropTarget.getChildren().add(node);
            dieDropTarget.setStyle("-fx-border-color: black;");

            if (this.player.getBoard().getField(row, col) != null) {
                Die die = this.player.getBoard().getField(row, col);
                DieView dieView = new DieView(die.getEyes(), die.getColor(), die.getNumber(), false);
                dieDropTarget.getChildren().add(dieView);
            }

            grid.add(dieDropTarget, col, row);
        } else {
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(node);
            node.setStyle("-fx-border-color: black;");

            if (this.player.getBoard().getField(row, col) != null) {
                Die die = this.player.getBoard().getField(row, col);
                DieView dieView = new DieView(die.getEyes(), die.getColor(), die.getNumber(), false);
                stackPane.getChildren().add(dieView);
            }

            grid.add(stackPane, col, row);
        }
    }

    public GridPane getGrid() {
        return this.grid;
    }
}
