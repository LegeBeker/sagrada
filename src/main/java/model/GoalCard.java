package main.java.model;

public class GoalCard {
    private int idGoalCard;
    private String name;
    private String description;

    public GoalCard() {
    }

    public int getIdGoalCard() {
        return idGoalCard;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static GoalCard getGoalCard(final String name) {
        GoalCard goalCard = new GoalCard();

        goalCard.idGoalCard = idGoalCard;
        goalCard.name = "Name";
        goalCard.description = "Description";

        return goalCard;
    }
}
