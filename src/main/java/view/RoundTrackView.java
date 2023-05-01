package main.java.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import main.java.model.Die;
import main.java.model.Game;
import main.java.pattern.Observer;

public class RoundTrackView extends GridPane implements Observer {

    private final int padding = 10;
    private final int fontSize = 12;
    private final int size = 65;
    private final int textPadding = 5;
    private final int rounds = 10;
    private final double width = 500;
    private final double height = 100;

    private Game game;

    private final Background background = new Background(new BackgroundFill(Color.web("#ffffff"), null, null));

    private final ArrayList<Group> roundGroups;

    public RoundTrackView(final Game game) {
        this.game = game;
        this.roundGroups = new ArrayList<>();
        this.setPrefSize(width, height);
        this.setMaxWidth(width);
        this.setMaxHeight(height);
        this.setBackground(background);
        this.setPadding(new Insets(padding));
        this.setVgap(padding);
        this.setHgap(padding);

        for (int i = 0; i < rounds; i++) {
            VBox vbox = new VBox();
            Group group = new Group();
            Rectangle rectangle = new Rectangle(size, size);
            group.getChildren().add(rectangle);
            roundGroups.add(group);
            rectangle.setFill(Color.BEIGE);
            Text text = new Text("Ronde " + (i + 1));
            text.setFont(text.getFont().font(fontSize));
            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(textPadding));
            textFlow.setTextAlignment(TextAlignment.CENTER);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(group, textFlow);
            this.add(vbox, i, 1);
        }

        update();
    }

    @Override
    public void update() {
        for (Die die : game.getRoundTrack()) {
            roundGroups.get(die.getRoundTrack() - 1).getChildren().add(new DieView(die.getEyes(), die.getColor(), false));
        }
    }

}
