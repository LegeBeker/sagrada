package main.java.enums;

public enum PlayStatusEnum {
    ACCEPTED,
    CHALLENGEE,
    CHALLENGER,
    FINISHED,
    REFUSED;

    public String toString() {
        return this.name().toLowerCase();
    }
}
