package main.java.enums;

public enum ColorEnum {
    RED("#F44336"),
    BLUE("#2196F3"),
    GREEN("#4CAF50"),
    YELLOW("#FFEB3B"),
    PURPLE("#9C27B0");

    private final String hexCode;

    ColorEnum(final String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public static ColorEnum fromString(final String color) {
        if (color == null) {
            return null;
        }
        switch (color.toLowerCase()) {
            case "red":
            case "#f44336":
            case "0xf44336ff":
                return RED;
            case "blue":
            case "#2196f3":
            case "0x2196f3ff":
                return BLUE;
            case "green":
            case "#4caf50":
            case "0x4caf50ff":
                return GREEN;
            case "yellow":
            case "#ffeb3b":
            case "0xffeb3bff":
                return YELLOW;
            case "purple":
            case "#9c27b0":
            case "0x9c27b0ff":
                return PURPLE;
            default:
                return null;
        }
    }
}
