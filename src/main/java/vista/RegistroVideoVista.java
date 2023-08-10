package vista;

import com.example.proyecto.Main;
import controlador.BL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Video;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Esta clase maneja el registro de los videos
 */
public class RegistroVideoVista {

    @FXML
    public TextField txtNombreVideo;
    @FXML
    public TextField txtCategoria;
    @FXML
    public TextArea txtDescripcion;
    @FXML
    public Button btnRegistrar;
    @FXML
    public Button btnCancelar;
    @FXML
    public TextField txtSubirArchivo;
    @FXML
    public TextField txtSubirThumbnail;
    @FXML
    public AnchorPane mainPane;
    @FXML
    public Label labelActualUser;
    @FXML
    public Circle actualUserPhoto;
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
        Main.userInformation(labelActualUser, actualUserPhoto);

        imgBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.cambiaPantalla("paginaPrincipal");
            }
        });

        actualUserPhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.cambiaPantalla("modificarUsuario");
            }
        });
    }


    @FXML
    public void handleButtonRegistrar(ActionEvent event) {
        if (registrarVerificacion()) {
            Video video = new Video();
            video.setFecha(LocalDate.now());
            String nombre = txtNombreVideo.getText();
            String categoria = txtCategoria.getText();
            String descripcion = txtDescripcion.getText();
            String subirArchivo = txtSubirArchivo.getText();
            String subirArchivoImagen = txtSubirThumbnail.getText();
            video.setCalificacion(0);
            video.setNombre(nombre);
            video.setCategoria(categoria);
            video.setDescripcion(descripcion);
            video.setArchivo(subirArchivo);
            video.setThumbnailVideo(subirArchivoImagen);
            video.setIdUsuario(blConexion.getUsuarioActual().getId());
            blConexion.getUsuarioActual().getVideosUsuario().add(video);
            int idVideo = blConexion.annadirVideo(video);
            video.setId(idVideo);

            Main.showAlertOneOption("Exito", "Registro Exitoso", "OK", "registrarVideo", Alert.AlertType.INFORMATION);
            Optional<ButtonType> result = Main.showAlertTwoOptions("Información", "Desea agregar el video a una lista de reproducción", "OK", "NO", Alert.AlertType.CONFIRMATION);
            if (result.get().equals(ButtonType.OK)) {
                blConexion.setVideoActual(video);
                Main.cambiaPantalla("listasDeReproduccion");
            } else {
                Main.cambiaPantalla("paginaPrincipal");
            }
        }
    }


    public boolean registrarVerificacion() {
        String nombre = txtNombreVideo.getText();
        String categoria = txtCategoria.getText();
        String descripcion = txtDescripcion.getText();
        String subirArchivo = txtSubirArchivo.getText();

        boolean esValido = true;

        if (nombre == null || txtNombreVideo.getText().isEmpty()) {
            esValido = false;
            txtNombreVideo.setBorder(border);
        }

        if (categoria == null || txtCategoria.getText().isEmpty()) {
            esValido = false;
            txtCategoria.setBorder(border);
        }

        if (descripcion == null || txtDescripcion.getText().isEmpty()) {
            esValido = false;
            txtDescripcion.setBorder(border);
        }

        if (subirArchivo == null || txtSubirArchivo.getText().isEmpty()) {
            esValido = false;
            txtSubirArchivo.setBorder(border);
        }
        return esValido;
    }

    @FXML
    public void handleButtonCancelar(ActionEvent event)  {
        Main.cambiaPantalla("paginaPrincipal");
    }


    @FXML
    public void handleButtonSubirArchivo(ActionEvent event) {
        this.txtSubirArchivo.setEditable(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir recurso del video");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            this.txtSubirArchivo.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void handleButtonSubirThumbnail(ActionEvent event) {
        this.txtSubirThumbnail.setEditable(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir recurso del video");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            this.txtSubirThumbnail.setText(file.getAbsolutePath());
        }
    }
}