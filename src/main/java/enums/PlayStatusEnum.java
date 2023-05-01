package main.java.enums;

public enum PlayStatusEnum {
    ACCEPTED,
    CHALLENGEE,
    CHALLANGER,
    FINISHED,
    REFUSED;

    public String toString() {
        return this.name().toLowerCase();
    }
}
