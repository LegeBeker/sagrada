package main.java.enums;

public enum ToolcardsNameEnum {
    GROZINGPLIERS,
    EGLOMISEBRUSH,
    COPPERFOILBURNISHER,
    LATHEKIN,
    LENSCUTTER,
    FLUXBRUSH,
    GLAZINGHAMMER,
    RUNNINGPLIERS,
    CORKBACKEDSTRAIGHTEDGE,
    GRINDINGSTONE,
    FLUXREMOVER,
    TAPWHEEL;
    
    public String toString() {
        return this.name().toLowerCase();
    }
}
