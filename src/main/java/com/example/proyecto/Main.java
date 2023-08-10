package com.example.proyecto;

import controlador.BL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import modelo.Usuario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogManager;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class Main extends Application {

    private static Scene escenaPrincipal;

    public static final int HEIGHT = 170;
    public static final int WIDTH = 185;

    public static final int NCOLUMNS = 5;

    private static BL blConexion = BL.getInstanciaBl();

    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        InputStream loggingProperties = Main.class.getClassLoader().getResourceAsStream("logging.properties");
        LogManager.getLogManager().readConfiguration(loggingProperties);
        launch(args);

    }


    @Override
    public void start(Stage stage)  {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        logger.log(Level.INFO, "Iniciando");
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1100, 700);
            stage.setTitle("HomeFlix");
            stage.setScene(scene);
            stage.setResizable(false);
            escenaPrincipal = scene;
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "No se inició el programa");
            logger.log(Level.SEVERE, e.toString());
        }

    }



    public static void cambiaPantalla(String nombrePantlla) {
        String pantalla = nombrePantlla + ".fxml";
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Main.class.getResource(pantalla));
            escenaPrincipal.setRoot(parent);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "La pantalla no fue encontrada", e);
        }

    }

    public static void userInformation(Label userNameLabel, Circle circlePhoto){
        Usuario usuarioActual = blConexion.getUsuarioActual();
        userNameLabel.setText(usuarioActual.getNombreUsuario());

        if(usuarioActual.getArchivoImagen() != null && !usuarioActual.getArchivoImagen().equalsIgnoreCase("")){
            Image image = new Image("file:" + usuarioActual.getArchivoImagen());
            circlePhoto.setFill(new ImagePattern(image));
        }else {
            URL urlImage =  Main.class.getResource("img/defaultImage.png");
            Image imageDefault = new Image(urlImage.toString());
            circlePhoto.setFill(new ImagePattern(imageDefault));
        }
    }

    public static Border obtenerBordeError(){
        return new Border(new BorderStroke(Color.RED, Color.RED, Color.RED, Color.RED,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY));
    }

    public static Optional<ButtonType> showAlertOneOption(String titulo, String mensaje, String txtBoton, String pantalla, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        ButtonType OK = new ButtonType(txtBoton);
        alert.getButtonTypes().setAll(OK);
        Optional<ButtonType> result = alert.showAndWait();
        Main.cambiaPantalla(pantalla);
        return result;

    }

    public static Optional<ButtonType> showAlertTwoOptions(String titulo, String mensaje, String txtBoton, String txtBoton2, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        ButtonType OK = ButtonType.OK;
        ButtonType CANCEL = ButtonType.CANCEL;
        alert.getButtonTypes().setAll(OK, CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        return result;

    }

    public static Scene getEscenaPrincipal() {
        return escenaPrincipal;
    }
}