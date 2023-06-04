package main.java.view;

import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.java.controller.ViewController;

public class DieDropTarget extends StackPane {

    private final ViewController view;
    private static int amountPlacedDie = 0;
    private static int amountToolcardDie = 0;
    private int maxAmountToolcardDie = 1;

    public DieDropTarget(final ViewController view) {
        this.view = view;

        this.setOnDragOver(event -> {
            if (event.getGestureSource() instanceof DieView) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        this.setOnDragEntered(event -> {
            if (event.getGestureSource() instanceof DieView) {
                this.setStyle("-fx-border-color: #00FFBF;");
            }
        });

        this.setOnDragExited(event -> {
            this.setStyle("-fx-border-color: transparent;");
        });

        this.setOnDragDropped(event -> {
            if (view.getSelectedToolcardName() != null && (view.getSelectedToolcardName().equals("lathekin")
                    || view.getSelectedToolcardName().equals("tapWheel"))) {
                maxAmountToolcardDie = 2;
            }

            DieView dieView = (DieView) event.getGestureSource();
            if(view.getAmountPlacedDieInRound() > 1){
                view.displayError("Je hebt in je vorige beurt al 2 stenen geplaatst. Eindig de beurt.");
                return;
            }

            if (view.getSelectedToolcardName() == null || !view.getSelectedToolcardName().equals("runningPliers")) {

                if ((DieDropTarget.amountPlacedDie == 0 && view.getSelectedToolcardName() == null)
                        || (amountToolcardDie < maxAmountToolcardDie && view.getSelectedToolcardName() != null)) {
                    Boolean placeDie = this.view.doMove(view.getPatternCardId(), dieView.getEyes(),
                            dieView.getColor(), dieView.getNumber(),
                            GridPane.getColumnIndex(this), GridPane.getRowIndex(this));

                    if (!placeDie) {
                        this.view.displayError("Deze zet is niet geldig.");
                        return;
                    }
                } else {
                    if (amountPlacedDie > 0) {
                        this.view.displayError(
                                "Je hebt al een dobbelsteen geplaatst deze ronde, eindig de ronde om nog eens te plaatsen.");
                        return;
                    } else if (amountToolcardDie >= maxAmountToolcardDie) {
                        this.view.displayError(
                                "Je hebt al " + maxAmountToolcardDie
                                        + " dobbelstenen geplaatst d.m.v. de gereedschapskaart. Eindig de beurt.");
                        return;
                    }
                }

            } else {
                // -- First check if this is the first round, otherwise user can place 3 die
                if (!view.getGameClockwise()) {
                    view.displayError("Je kan deze gereedschapskaart alleen activeren in je eerste beurt");
                } else {
                    if (amountPlacedDie > 1) {
                        this.view.displayError(
                                "Je hebt al " + maxAmountToolcardDie
                                        + " dobbelstenen geplaatst d.m.v. de gereedschapskaart. Eindig de beurt.");
                        return;
                    }

                    Boolean placeDie = this.view.doMove(view.getPatternCardId(), dieView.getEyes(),
                            dieView.getColor(), dieView.getNumber(),
                            GridPane.getColumnIndex(this), GridPane.getRowIndex(this));

                    if (!placeDie) {
                        this.view.displayError("Deze zet is niet geldig.");
                        return;
                    }
                }
            }

            if (view.getSelectedToolcardName() != null && DieDropTarget.amountToolcardDie < maxAmountToolcardDie) {
                DieDropTarget.amountToolcardDie++;
            }

            DieDropTarget.amountPlacedDie++;
            event.setDropCompleted(true);
            event.consume();
        });

    }

    public static void resetAmountPlacedDie() {
        DieDropTarget.amountPlacedDie = 0;
        DieDropTarget.amountToolcardDie = 0;
    }
}
