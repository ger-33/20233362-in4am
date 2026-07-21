package com.geraldcalderon.model;

public class Usuario {
    private int id;
    private String username;
    private String password;
    private String nombreCompleto = ""; // Le asignamos valor por defecto para evitar el error

    public Usuario() {
    }

    public Usuario(int id, String username, String password, String nombreCompleto) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsername() {
    return username;
}
}