package main.java.view;

import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.java.controller.ViewController;

public class DieDropTarget extends StackPane {

    private final ViewController view;
    private static int amountPlacedDie = 0;
    private static int amountToolcardDie = 0;

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
            DieView dieView = (DieView) event.getGestureSource();
            if (view.getSelectedToolcardName() == null || !view.getSelectedToolcardName().equals("runningPliers")) {
                System.out.println("Dies " + amountPlacedDie + " ToolcardDies" + amountToolcardDie);

                if ((DieDropTarget.amountPlacedDie == 0 && view.getSelectedToolcardName() == null)
                        || (amountToolcardDie < 2 && view.getSelectedToolcardName() != null)) {
                    System.out.println("Place die triggert");
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
                    } else if (amountToolcardDie >= 2) {
                        System.out.println("Amount toolcard die: " + amountToolcardDie);
                        this.view.displayError(
                                "Je hebt al 2 dobbelstenen geplaatst met de gereedschapskaart. Eindig de beurt.");
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
                                "Je hebt al 2 dobbelstenen geplaatst d.m.v. de gereedschapskaart. Eindig de beurt.");
                        return;
                    } else {
                        // -- @TimBogersGitHub, watch out: you also need to skip another round
                        Boolean placeDie = this.view.doMove(view.getPatternCardId(), dieView.getEyes(),
                                dieView.getColor(), dieView.getNumber(),
                                GridPane.getColumnIndex(this), GridPane.getRowIndex(this));

                        if (!placeDie) {
                            this.view.displayError("Deze zet is niet geldig.");
                            return;
                        }
                    }
                }
            }

            if (view.getSelectedToolcardName() != null) {
                if (!view.getSelectedToolcardName().equals("eglomiseBrush")
                        && !view.getSelectedToolcardName().equals("copperFoilBurnisher")
                        && !view.getSelectedToolcardName().equals("lathekin")) {
                    System.out.println(!view.getSelectedToolcardName().equals("lathekin"));
                    System.out.println("Die placed in if not: Amount placed die: " + DieDropTarget.amountPlacedDie);
                    DieDropTarget.amountPlacedDie++;
                } else if (view.getSelectedToolcardName().equals("lathekin")) {
                    System.out.println("else from if not triggered");
                    if (DieDropTarget.amountToolcardDie < 2) {
                        DieDropTarget.amountToolcardDie++;
                        System.out.println(amountToolcardDie);
                    }
                }
            } else {
                System.out.println("Die placed in else: Amount placed die: " + DieDropTarget.amountPlacedDie);
                DieDropTarget.amountPlacedDie++;
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
