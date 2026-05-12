package com.example.demo.models.citas;

public enum CitaType {
    REVISION_GENERAL("Revisión General", "teal"),
    ESPECIALIDAD("Especialidad", "purple"),
    SEGUIMIENTO("Seguimiento", "blue"),
    URGENCIA("Urgencia", "red");

    private final String displayName;
    private final String color;

    CitaType(String displayName, String color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColor() {
        return color;
    }
}
