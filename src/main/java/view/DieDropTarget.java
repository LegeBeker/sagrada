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
                    || view.getSelectedToolcardName().equals("tapWheel")
                    || view.getSelectedToolcardName().equals("runningPliers"))) {
                maxAmountToolcardDie = 2;
            }

            DieView dieView = (DieView) event.getGestureSource();
            System.out.println(DieDropTarget.amountPlacedDie > 0);
            if (DieDropTarget.amountPlacedDie > 0 && (view.getSelectedToolcardName() != null
                    && (view.getSelectedToolcardName().equals("runningPliers")
                            || view.getSelectedToolcardName().equals("corkBackedStraightedge")))) {
                view.displayError("Je hebt al een dobbelsteen geplaatst deze ronde. Eindig de beurt");
                return;
            }
            if (!view.getGameClockwise() && view.getAmountPlacedDieInPrevRound() > 1 && view.boughtRunningPliers()) {
                view.displayError("Je hebt in je vorige beurt al 2 stenen geplaatst. Eindig de beurt.");
                return;
            }

            if (!view.getGameClockwise() && view.getSelectedToolcardName() != null
                    && view.getSelectedToolcardName().equals("runningPliers")) {
                view.displayError("Je kan deze gereedschapskaart alleen activeren in je eerste beurt");
                return;
            }

            if ((DieDropTarget.amountPlacedDie == 0 && view.getSelectedToolcardName() == null)
                    || (amountToolcardDie < maxAmountToolcardDie && view.getSelectedToolcardName() != null)) {
                Boolean placeDie = this.view.doMove(view.getPatternCardId(), dieView.getEyes(),
                        dieView.getColor(), dieView.getNumber(),
                        GridPane.getColumnIndex(this), GridPane.getRowIndex(this));

                if (!placeDie) {
                    this.view.displayError("Deze zet is niet geldig.");
                    return;
                }

                if (view.getSelectedToolcardName() == null
                        || view.getSelectedToolcardName().equals("corkBackedStraightedge")
                        || view.getSelectedToolcardName().equals("runningPliers")) {
                    DieDropTarget.amountPlacedDie++;
                }

                if (view.getSelectedToolcardName() != null && DieDropTarget.amountToolcardDie < maxAmountToolcardDie) {
                    DieDropTarget.amountToolcardDie++;
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

            event.setDropCompleted(true);
            event.consume();
        });

    }

    public static void resetAmountPlacedDie() {
        DieDropTarget.amountPlacedDie = 0;
        DieDropTarget.amountToolcardDie = 0;
    }
}
