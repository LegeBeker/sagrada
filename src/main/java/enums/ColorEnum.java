package main.java.enums;

public enum ColorEnum {
    RED("#F44336"),
    BLUE("#2196F3"),
    GREEN("#4CAF50"),
    YELLOW("#FFEB3B"),
    PURPLE("#9C27B0"),
    DEFAULT("#F8F8F8");

    private final String hexCode;

    ColorEnum(final String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }

    public static ColorEnum fromString(final String color) {
        if (color == null) {
            return DEFAULT;
        }
        switch (color.toLowerCase()) {
            case "red":
                return RED;
            case "blue":
                return BLUE;
            case "green":
                return GREEN;
            case "yellow":
                return YELLOW;
            case "purple":
                return PURPLE;
            default:
                return DEFAULT;
        }
    }

}
