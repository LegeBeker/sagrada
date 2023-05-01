package main.java.enums;

public enum PlayStatusEnum {
    ACCEPTED("accepted"),
    CHALLENGEE("challengee"),
    CHALLANGER("challenger"),
    FINISHED("finished"),
    REFUSED("refused");

    private final String playStatus;

    PlayStatusEnum(final String playStatus) {
        this.playStatus = playStatus;
    }

    public String getPlayStatus() {
        return playStatus;
    }

    public String toString() {
        return playStatus;
    }
}
