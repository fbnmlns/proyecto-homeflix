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

/**
 * Esta clase maneja el registro de los videos
 */
public class ModificarVideoVista {

    @FXML
    public TextField txtNombreVideo;
    @FXML
    public TextField txtCategoria;
    @FXML
    public TextArea txtDescripcion;
    @FXML
    public Button btnModificar;
    @FXML
    public Button btnCancelar;
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

        Video video = blConexion.getVideoActual();
        txtNombreVideo.setText(video.getNombre());
        txtCategoria.setText(video.getCategoria());
        txtDescripcion.setText(video.getDescripcion());
        txtSubirThumbnail.setText(video.getThumbnailVideo());

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

    /**
     * @param event Este evento registra el video
     */
    @FXML
    public void handleButtonModificar(ActionEvent event)  {

        if (modificarVerificacion() == true) {
            Video video = blConexion.getVideoActual();
            video.setFecha(LocalDate.now());
            String nombre = txtNombreVideo.getText();
            String categoria = txtCategoria.getText();
            String descripcion = txtDescripcion.getText();
            String subirArchivoImagen = txtSubirThumbnail.getText();
            video.setCalificacion(0);
            video.setNombre(nombre);
            video.setCategoria(categoria);
            video.setDescripcion(descripcion);
            video.setThumbnailVideo(subirArchivoImagen);
            video.setIdUsuario(blConexion.getUsuarioActual().getId());
            blConexion.getUsuarioActual().getVideosUsuario().add(video);
            blConexion.modificar(video);

            Main.showAlertOneOption("Exito", "Modificación exitosa", "OK", "reproducirVideo", Alert.AlertType.INFORMATION);
        }
    }


    /**
     * Esta funcion permite la verfificacion de los espacios en blanco del registro
     */
    public boolean modificarVerificacion() {
        String nombre = txtNombreVideo.getText();
        String categoria = txtCategoria.getText();
        String descripcion = txtDescripcion.getText();

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
        return esValido;
    }

    /**
     * @param event Este evento cancela el registro y envia al usuario a la pagina principal
     */
    @FXML
    public void handleButtonCancelar(ActionEvent event) {
        Main.cambiaPantalla("paginaPrincipal");
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