package com.ejemplo.clientesapi.model;

public enum TipoPlan {
    GRATUITO(5, 0.0),
    BASICO(100, 29.99),
    PREMIUM(500, 99.99),
    ILIMITADO(-1, 199.99);
    
    private final int busquedasIncluidas;
    private final double precio;
    
    TipoPlan(int busquedasIncluidas, double precio) {
        this.busquedasIncluidas = busquedasIncluidas;
        this.precio = precio;
    }
    
    public int getBusquedasIncluidas() { return busquedasIncluidas; }
    public double getPrecio() { return precio; }
}