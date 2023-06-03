package main.java.enums;

public enum ToolcardEnum {
    GROZINGPLIERS("Grozing Pliers", "Grozingtang", "grozingPliers"),
    COPPERFOILBURNISHER("Copper Foil Burnisher", "Koperfoliebrander", "copperFoilBurnisher"),
    LATHEKIN("Lathekin", "Lathekin", "lathekin"),
    LENSCUTTER("Lens Cutter", "Lenssnijder", "lensCutter"),
    FLUXBRUSH("Flux Brush", "Fluxkwast", "fluxBrush"),
    GLAZINGHAMMER("Glazing Hammer", "Glazuurhamer", "blazingHammer"),
    RUNNINGPLIERS("Running Pliers", "Snijtang", "runningPliers"),
    CORKBACKEDSTRAIGHTEDGE("Cork-backed Straightedge", "Kurkliniaal", "corkBackedStraightedge"),
    GRINDINGSTONE("Grinding Stone", "Slijpsteen", "grindingStone"),
    FLUXREMOVER("Flux Remover", "Fluxremover", "fluxRemover"),
    TAPWHEEL("Tap Wheel", "Tapwiel", "tapWheel"),
    EGLOMISEBRUSH("Eglomise Brush", "Eglomisekwast", "eglomiseBrush");

    private final String englishName;
    private final String dutchName;
    private final String methodName;

    ToolcardEnum(final String englishName, final String dutchName, final String methodName) {
        this.englishName = englishName;
        this.dutchName = dutchName;
        this.methodName = methodName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getDutchName() {
        return dutchName;
    }

    public String getMethodName() {
        return methodName;
    }
}
