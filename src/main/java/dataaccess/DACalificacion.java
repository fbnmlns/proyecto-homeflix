package dataaccess;

import modelo.Calificacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DACalificacion {
    private static Logger logger = Logger.getLogger(DACalificacion.class.getName());

    public int guardarCalificacion (Calificacion calificacion)  {
        ConnectionManager connectionManager = ConnectionManager.obtenerInstancia();
        String insert = "Insert into Calificacion(idVideo, idUsuario, estado) values (?, ?, ?)";

        try (Connection connection = connectionManager.abrirConexion()) {
            try (PreparedStatement statement = connection.prepareStatement(insert)) {
                statement.setInt(1, calificacion.getIdVideo());
                statement.setInt(2, calificacion.getIdUsuario());
                statement.setBoolean(3, calificacion.isEstado());
                return statement.executeUpdate();
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo guardar la calificación");
            logger.log(Level.SEVERE, e.toString());
        }
        return 0;
    }

    public void borrarCalificacion (int idVideo, int idUsuario)  {
        ConnectionManager connectionManager = ConnectionManager.obtenerInstancia();
        String delete = "Delete from Calificacion Where idVideo = ? and idUsuario = ?";

        try (Connection connection = connectionManager.abrirConexion()) {
            try (PreparedStatement statement = connection.prepareStatement(delete)) {
                statement.setInt(1, idVideo);
                statement.setInt(2, idUsuario);

                statement.executeUpdate();
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo borrar la calificación");
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public Calificacion obtenerCalificacionActual (int idVideo, int idUsuario) {
        ConnectionManager connectionManager = ConnectionManager.obtenerInstancia();
        String select = "Select idCalificacion, estado From Calificacion Where idVideo = ? and idUsuario = ?";

        try (Connection connection = connectionManager.abrirConexion()) {
            try (PreparedStatement statement = connection.prepareStatement(select)) {
                statement.setInt(1, idVideo);
                statement.setInt(2, idUsuario);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int idCalificacion = resultSet.getInt("idCalificacion");
                    boolean estado = resultSet.getBoolean("estado");

                    Calificacion calificacion = new Calificacion();
                    calificacion.setIdCalificacion(idCalificacion);
                    calificacion.setEstado(estado);

                    return calificacion;
                }
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo obtener la calificación");
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    public int borrarVideo(int idVideo){
        ConnectionManager connectionManager = ConnectionManager.obtenerInstancia();
        String delete = "Delete from Calificacion Where idVideo = ?";

        try (Connection connection = connectionManager.abrirConexion()) {
            try ( PreparedStatement statement = connection.prepareStatement(delete)) {
                statement.setInt(1, idVideo);

                return statement.executeUpdate();
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo borrar el id del video en la tabla Calificación");
            logger.log(Level.SEVERE, e.toString());
        }
        return 0;
    }

    public void borrarUsuario(int idUsuario) {
        ConnectionManager connectionManager = ConnectionManager.obtenerInstancia();
        String delete = "Delete from Calificacion Where idUsuario = ?";

        try (Connection connection = connectionManager.abrirConexion()) {
            try (PreparedStatement statement = connection.prepareStatement(delete)) {
                statement.setInt(1, idUsuario);

                statement.executeUpdate();
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo borrar el usuario de la tabla Calificación");
            logger.log(Level.SEVERE, e.toString());
        }
    }
}
