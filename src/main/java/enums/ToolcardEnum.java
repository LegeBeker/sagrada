package main.java.enums;

public enum ToolcardEnum {
    GROZINGPLIERS("Grozing Pliers"),
    COPPERFOILBURNISHER("Copper Foil Burnisher"),
    LATHEKIN("Lathekin"),
    LENSCUTTER("Lens Cutter"),
    FLUXBRUSH("Flux Brush"),
    GLAZINGHAMMER("Glazing Hammer"),
    RUNNINGPLIERS("Running Pliers"),
    CORKBACKEDSTRAIGHTEDGE("Cork-backed Straightedge"),
    GRINDINGSTONE("Grinding Stone"),
    FLUXREMOVER("Flux Remover"),
    TAPWHEEL("Tap Wheel"),
    EGLOMISEBRUSH("Eglomise Brush");

    private final String name;

    ToolcardEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ToolcardEnum fromString(final String name) {
        if (name == null) {
            return null;
        }
        switch (name.toLowerCase()) {
            case "grozing pliers":
                return GROZINGPLIERS;
            case "copper foil burnisher":
                return COPPERFOILBURNISHER;
            case "lathekin":
                return LATHEKIN;
            case "lens cutter":
                return LENSCUTTER;
            case "flux brush":
                return FLUXBRUSH;
            case "glazing hammer":
                return GLAZINGHAMMER;
            case "running pliers":
                return RUNNINGPLIERS;
            case "cork-backed straightedge":
                return CORKBACKEDSTRAIGHTEDGE;
            case "grinding stone":
                return GRINDINGSTONE;
            case "flux remover":
                return FLUXREMOVER;
            case "tap wheel":
                return TAPWHEEL;
            case "eglomise brush":
                return EGLOMISEBRUSH;
            default:
                return null;
        }
    }

    public String getDutchName() {
        switch (this) {
            case GROZINGPLIERS:
                return "driepuntstang";
            case COPPERFOILBURNISHER:
                return "folie-aandrukker";
            case LATHEKIN:
                return "loodopenhalen";
            case LENSCUTTER:
                return "rondsnijder";
            case FLUXBRUSH:
                return "fluxborstel";
            case GLAZINGHAMMER:
                return "loodhamer";
            case RUNNINGPLIERS:
                return "glasbreektang";
            case CORKBACKEDSTRAIGHTEDGE:
                return "snijliniaal";
            case GRINDINGSTONE:
                return "shuurblok";
            case FLUXREMOVER:
                return "fluxverwijderaar";
            case TAPWHEEL:
                return "olieglassnijder";
            case EGLOMISEBRUSH:
                return "eglomise borstel";
            default:
                return null;
        }
    }

    public String getMethodName() {
        switch (this) {
            case GROZINGPLIERS:
                return "grozingPliers";
            case COPPERFOILBURNISHER:
                return "copperFoilBurnisher";
            case LATHEKIN:
                return "lathekin";
            case LENSCUTTER:
                return "lensCutter";
            case FLUXBRUSH:
                return "fluxBrush";
            case GLAZINGHAMMER:
                return "glazingHammer";
            case RUNNINGPLIERS:
                return "runningPliers";
            case CORKBACKEDSTRAIGHTEDGE:
                return "corkBackedStraightedge";
            case GRINDINGSTONE:
                return "grindingStone";
            case FLUXREMOVER:
                return "fluxRemover";
            case TAPWHEEL:
                return "tapWheel";
            case EGLOMISEBRUSH:
                return "eglomiseBrush";
            default:
                return null;
        }
    }

    public String getImageName() {
        return name.toLowerCase().replace(" ", "-");
    }
}
