package dataaccess;

import modelo.Usuario;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAUsuario {

    private static Logger logger = Logger.getLogger(DAUsuario.class.getName());

    public int annadirUsuario(Usuario usuario)  {
        ConnectionManager connectionManager = ConnectionManager.obtenerInstancia();
        String insert = "Insert into Usuario(nombre, apellido, nombreUsuario, contrasenna, archivoImagen) values(?, ?, ?, ?, ?)";

        try (Connection connection = connectionManager.abrirConexion()) {
            try (PreparedStatement statement = connection.prepareStatement(insert)) {
                statement.setString(1, usuario.getNombre());
                statement.setString(2, usuario.getApellido());
                statement.setString(3, usuario.getNombreUsuario());
                statement.setString(4, usuario.getContrasenna());
                statement.setString(5, usuario.getArchivoImagen());

                return statement.executeUpdate();
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo registrar el usuario");
            logger.log(Level.SEVERE, e.toString());
        }
        return 0;
    }

    public Usuario buscarUsuario(String contrasenna, String nombreUsuario)  {
        ConnectionManager connectionManager = ConnectionManager.obtenerInstancia();
        String select = "Select nombre, apellido, archivoImagen, id FROM Usuario WHERE nombreUsuario = ? and contrasenna = ?";

        try (Connection connection = connectionManager.abrirConexion()) {
            try (PreparedStatement statement = connection.prepareStatement(select)) {
                statement.setString(1, nombreUsuario);
                statement.setString(2, contrasenna);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String apellido = resultSet.getString("apellido");
                    String archivoImagen = resultSet.getString("archivoImagen");
                    int id = resultSet.getInt("id");
                    Usuario usuario = new Usuario();

                    usuario.setNombreUsuario(nombreUsuario);
                    usuario.setNombre(nombre);
                    usuario.setApellido(apellido);
                    usuario.setArchivoImagen(archivoImagen);
                    usuario.setContrasenna(contrasenna);
                    usuario.setId(id);

                    return usuario;
                }
                return null;
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo buscar el usuario");
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    public boolean buscarNombreUsuario(String nombreUsuario)  {
        ConnectionManager connectionManager = ConnectionManager.obtenerInstancia();
        String select = "Select id FROM Usuario WHERE nombreUsuario = ? ";

        try (Connection connection = connectionManager.abrirConexion()) {
            try (PreparedStatement statement = connection.prepareStatement(select)) {
                statement.setString(1, nombreUsuario);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return true;
                }
                return false;
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo encontrar el nombre del usuario");
            logger.log(Level.SEVERE, e.toString());
        }
        return false;
    }

    public void modificarUsuario(Usuario usuarioActual)  {
        ConnectionManager connectionManager = ConnectionManager.obtenerInstancia();
        String update = "UPDATE Usuario Set nombre = ?, apellido = ?, nombreUsuario = ?, contrasenna = ?, archivoImagen = ? Where id = ?";

        try (Connection connection = connectionManager.abrirConexion()) {
            try (PreparedStatement statement = connection.prepareStatement(update)) {
                statement.setString(1, usuarioActual.getNombre());
                statement.setString(2, usuarioActual.getApellido());
                statement.setString(3,usuarioActual.getNombreUsuario());
                statement.setString(4, usuarioActual.getContrasenna());
                statement.setString(5, usuarioActual.getArchivoImagen());
                statement.setInt(6, usuarioActual.getId());

                statement.executeUpdate();
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo modificar el usuario");
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public int borrarCuenta(int idUsuario) {
        ConnectionManager connectionManager = ConnectionManager.obtenerInstancia();
        String delete = "Delete from Usuario Where id = ?";

        try (Connection connection = connectionManager.abrirConexion()) {
            try (PreparedStatement statement = connection.prepareStatement(delete)) {
                statement.setInt(1, idUsuario);

                return statement.executeUpdate();
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo borrar la cuenta del usuario");
            logger.log(Level.SEVERE, e.toString());
        }
        return idUsuario;
    }
}