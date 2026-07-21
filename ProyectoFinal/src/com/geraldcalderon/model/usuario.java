package com.geraldcalderon.model;

public class Usuario {

    private int id;
    private String nombreCompleto;
    private String usuario;
    private String correo;
    private String contrasena;
    private String tipoUsuario;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor sin ID (Para REGISTRAR nuevos usuarios)
    public Usuario(String nombreCompleto, String usuario, String correo, String contrasena, String tipoUsuario) {
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }

    // Constructor completo con ID (Para ACTUALIZAR o cargar de la BD)
    public Usuario(int id, String nombreCompleto, String usuario, String correo, String contrasena, String tipoUsuario) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    // Compatibilidad por si en alguna parte llamaste getUsername()
    public String getUsername() { return usuario; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}