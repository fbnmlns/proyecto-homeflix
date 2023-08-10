package vista;

import com.example.proyecto.Main;
import controlador.BL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.ListaReproduccion;
import modelo.Video;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class modificarPlayList {
    @FXML
    public TextField txtNombre;

    @FXML
    public TextField txtEnlaceImagen;

    @FXML
    public Button btnUpdate;

    @FXML
    public Button btnSubirArchivo;

    @FXML
    public Label userNameLabel;

    @FXML
    public Circle circleUserPhoto;

    @FXML
    public AnchorPane mainPane;

    @FXML
    public ImageView imgBack;

    public Border border = Main.obtenerBordeError();

    private BL blConexion = BL.getInstanciaBl();
    Stage stage;

    {
        if (mainPane != null) {
            stage = (Stage) mainPane.getScene().getWindow();
        }
    }

    public void initialize() {
        Main.userInformation(userNameLabel, circleUserPhoto);

        ListaReproduccion listaReproduccion = blConexion.getPlayListActual();
        txtNombre.setText(listaReproduccion.getNombre());
        txtEnlaceImagen.setText(listaReproduccion.getArchivoImagen());


        imgBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.cambiaPantalla("playList");
            }
        });

        circleUserPhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.cambiaPantalla("modificarUsuario");
            }
        });


    }


    public void handleButonUpdate()  {
        if (modificarVerificacion() == true) {
            ListaReproduccion listaReproduccion = blConexion.getPlayListActual();
            String nombre = txtNombre.getText();
            String enlaceImagen = txtEnlaceImagen.getText();

            listaReproduccion.setNombre(nombre);
            listaReproduccion.setArchivoImagen(enlaceImagen);

            listaReproduccion.setIdUsuario(blConexion.getUsuarioActual().getId());
            blConexion.modificarPlayList(listaReproduccion);

            Main.showAlertOneOption("Exito", "Modificación exitosa", "OK", "playList", Alert.AlertType.INFORMATION);
        }
    }

    public boolean modificarVerificacion() {
        String nombre = txtNombre.getText();
        String enlaceImage = txtEnlaceImagen.getText();

        boolean esValido = true;

        if (nombre == null || txtNombre.getText().isEmpty()) {
            esValido = false;
            txtNombre.setBorder(border);
        }

        if (enlaceImage == null || txtEnlaceImagen.getText().isEmpty()) {
            esValido = false;
            txtEnlaceImagen.setBorder(border);
        }

        return esValido;
    }

    public void handleButtonSubirArchivo(ActionEvent event) {
        this.txtEnlaceImagen.setEditable(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir recurso del video");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            this.txtEnlaceImagen.setText(file.getAbsolutePath());
        }
    }

}
