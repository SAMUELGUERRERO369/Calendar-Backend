package com.example.demo.models.citas;

public enum CitaStatus {
    CONFIRMADA("Confirmada", "green"),
    PENDIENTE("Pendiente", "yellow"),
    CANCELADA("Cancelada", "gray");

    private final String displayName;
    private final String color;

    CitaStatus(String displayName, String color) {
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
