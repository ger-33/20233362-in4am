package com.geraldcalderon.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // METODO PARA OBTENER TODOS LOS USUARIOS (READ)
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre_completo"),
                    rs.getString("usuario"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getString("tipo_usuario")
                );
                lista.add(u);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return lista;
    }

    // METODO PARA REGISTRAR UN USUARIO (CREATE)
    public boolean registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre_completo, usuario, correo, contrasena, tipo_usuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getContrasena());
            ps.setString(5, u.getTipoUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    // METODO PARA ACTUALIZAR UN USUARIO (UPDATE)
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre_completo=?, usuario=?, correo=?, contrasena=?, tipo_usuario=? WHERE id=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getContrasena());
            ps.setString(5, u.getTipoUsuario());
            ps.setInt(6, u.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // METODO PARA ELIMINAR UN USUARIO (DELETE)
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    // METODO PARA VALIDAR LOGIN
    public Usuario login(String usuario, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
        Usuario u = null;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre_completo"),
                        rs.getString("usuario"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("tipo_usuario")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        }
        return u;
    }
}