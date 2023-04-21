package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class RoundTrackView extends HBox {

    private final int padding = 10;
    private final int size = 65;
    private final int textPadding = 5;
    private final int rounds = 10;

    private final Background background = new Background(new BackgroundFill(Color.web("#ffffff"), null, null));
    private final GridPane grid = new GridPane();

    public RoundTrackView() {
        this.setBackground(background);
        this.setPadding(new Insets(padding));
        grid.setVgap(padding);
        grid.setHgap(padding);

        for (int i = 0; i < rounds; i++) {
            VBox vbox = new VBox();
            Rectangle rectangle = new Rectangle(size, size);
            rectangle.setFill(Color.BEIGE);
            Text text = new Text("Ronde " + (i + 1));
            text.setFont(text.getFont().font(12));
            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(textPadding));
            textFlow.setTextAlignment(TextAlignment.CENTER);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(rectangle, textFlow);
            grid.add(vbox, i, 1);
        }

        this.getChildren().add(grid);

    }
}
