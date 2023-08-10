package dataaccess;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase maneja la conexion a la base de datos
 */
public class ConnectionManager {
    private Connection connection;
    private static ConnectionManager instancia;

    private static Logger logger = Logger.getLogger(ConnectionManager.class.getName());


    private ConnectionManager(){

    }


    public static ConnectionManager obtenerInstancia(){

        if(instancia == null){
            instancia = new ConnectionManager();
        }

        return instancia;
    }

    public Connection abrirConexion() {
        cerrarConexion();
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:33097/homeflix","root","JaAch060896");
        } catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo abrir la conexión");
            logger.log(Level.SEVERE, e.toString());
        }
        return this.connection;
    }


    public void cerrarConexion() {
        try {
            if(this.connection != null && !this.connection.isClosed()){
                this.connection.close();
            }
        } catch (SQLException e){
            logger.log(Level.SEVERE,"No se pudo cerrar la conexión");
            logger.log(Level.SEVERE, e.toString());
        }
    }


}
