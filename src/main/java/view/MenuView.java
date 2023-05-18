package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.java.controller.ViewController;

public class MenuView extends VBox {

    private ViewController view;

    private Text textGreet;

    private Button buttonGames;
    private Button buttonStats;
    private Button buttonLogout;

    private static final int BUTTONHEIGHT = 25;
    private static final int BUTTONWIDTH = 200;

    private static final int PADDING = 200;
    private static final int SPACING = 15;

    public MenuView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());

        this.setAlignment(Pos.CENTER);

        this.textGreet = new Text("Welkom " + view.getUsername() + "!");
        this.textGreet.setStyle("-fx-font-size: 20px;");
        this.textGreet.setWrappingWidth(BUTTONWIDTH);
        this.textGreet.setTextAlignment(TextAlignment.CENTER);
        this.textGreet.setFill(Color.WHITE);

        this.buttonGames = new Button("Spellen");
        this.buttonGames.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        this.buttonGames.setOnAction(e -> view.openGamesView());

        this.buttonStats = new Button("Statistieken");
        this.buttonStats.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        this.buttonStats.setOnAction(e -> view.openStatsView());

        this.buttonLogout = new Button("Uitloggen");
        this.buttonLogout.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        this.buttonLogout.setOnAction(e -> this.logout());

        this.setSpacing(SPACING);
        this.setPadding(new Insets(0, PADDING, 0, PADDING));

        this.getChildren().addAll(view.getLogo(), this.textGreet, this.buttonGames,
                this.buttonStats,
                this.buttonLogout);
    }

    private void logout() {
        view.logoutAccount();
        view.openLoginView();
    }
}
