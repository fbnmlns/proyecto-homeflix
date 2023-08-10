package vista;

import com.example.proyecto.Main;
import controlador.BL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Usuario;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class RegistroUsuarioVista {
    @FXML
    public Button btnRegistrarUsuario;
    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtApellido;
    @FXML
    public TextField txtNombreUsuario;
    @FXML
    public PasswordField txtContrasenna;
    @FXML
    public TextField txtArchivoImagen;
    @FXML
    public Button btnCancelarRegistro;
    @FXML
    public Label labelUserExists;
    @FXML
    public Label labelWeakPassword;
    @FXML
    public AnchorPane mainPane;
    @FXML
    public Border border = Main.obtenerBordeError();
    private BL blConexion = BL.getInstanciaBl();

    Stage stage;

    {
        if (mainPane != null) {
            stage = (Stage) mainPane.getScene().getWindow();
        }
    }

    public void initialize() {
    }

    @FXML
    public void handleButtonRegistrarUsuario(ActionEvent event)  {
        if (registrarUsuarioVerificacion()) {
            Usuario usuario = new Usuario();

            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String nombreUsuario = txtNombreUsuario.getText();
            String contrasenna = txtContrasenna.getText();
            String archivoImagen = txtArchivoImagen.getText();

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasenna(contrasenna);
            usuario.setArchivoImagen(archivoImagen);

            blConexion.annadirUsuario(usuario);

            Main.showAlertOneOption("Registro exitoso", "Se ha registrado existosamente", "OK", "login", Alert.AlertType.INFORMATION);
        }
    }

    public boolean registrarUsuarioVerificacion()  {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String nombreUsuario = txtNombreUsuario.getText();
        String contrasenna = txtContrasenna.getText();

        boolean esValido = true;

        if (nombre == null || nombre.isEmpty()) {
            esValido = false;
            txtNombre.setBorder(border);
        } else {
            txtNombre.setBorder(null);
        }

        if (apellido == null || apellido.isEmpty()) {
            esValido = false;
            txtApellido.setBorder(border);
        } else {
            txtApellido.setBorder(null);
        }

        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            esValido = false;
            txtNombreUsuario.setBorder(border);

        } else if (nombreUsuario != null) {
            boolean userExists = blConexion.UsuarioExiste(txtNombreUsuario.getText());
            if (userExists) {
                labelUserExists.setText("este nombre de usuario no está disponible");
                esValido = false;
                txtNombreUsuario.setBorder(border);

            } else {
                labelUserExists.setText("");
                txtNombreUsuario.setBorder(null);
            }
        }


        if (contrasenna == null || txtContrasenna.getText().isEmpty()) {
            txtContrasenna.setBorder(border);
            esValido = false;
        } else if (!BL.validarContrasenna(contrasenna)) {
            labelWeakPassword.setWrapText(true);
            labelWeakPassword.setMaxWidth(200);
            labelWeakPassword.setText("contraseña ingresada muy débil! asegúrese de que sea MAYOR a 6 dígitos " +
                    "O menor a 8 dígitos incluyendo un número, una mayúscula, una mínuscula y un carácter especial");
            txtContrasenna.setBorder(border);
            esValido = false;
        } else if (BL.validarContrasenna(contrasenna) && !txtContrasenna.getText().isEmpty()) {
            labelWeakPassword.setText("");
            txtContrasenna.setBorder(null);
        }
        return esValido;
    }

    @FXML
    public void handleButtonSubirArchivoImagen(ActionEvent event) {
        this.txtArchivoImagen.setEditable(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir recurso del video");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            this.txtArchivoImagen.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void handleButtonCancelarRegsitro(ActionEvent event) {
        Main.cambiaPantalla("login");
    }
}

