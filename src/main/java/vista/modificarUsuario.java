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
import modelo.Usuario;
import modelo.Video;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class modificarUsuario {
    @FXML
    public Button btnRegistrarUsuario;
    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtApellido;

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
    public TextField txtNombreUsuario;

    @FXML
    public Border border = Main.obtenerBordeError();

    @FXML
    public Circle userPhoto;

    @FXML
    public Label userName;

    @FXML
    public ImageView back;

    @FXML
    public Button btnDelete;

    private BL blConexion = BL.getInstanciaBl();

    Stage stage;

    {
        if (mainPane != null) {
            stage = (Stage) mainPane.getScene().getWindow();
        }
    }

    public void initialize() {
        Main.userInformation(userName, userPhoto);

        Usuario usuario = blConexion.getUsuarioActual();
        txtNombre.setText(usuario.getNombre());
        txtApellido.setText(usuario.getApellido());
        txtNombreUsuario.setText(usuario.getNombreUsuario());
        txtContrasenna.setText(usuario.getContrasenna());
        txtArchivoImagen.setText(usuario.getArchivoImagen());

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.cambiaPantalla("paginaPrincipal");
            }
        });
    }

    public void handleButtonSubirArchivoImagen(ActionEvent event) {
        this.txtArchivoImagen.setEditable(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir recurso del video");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            this.txtArchivoImagen.setText(file.getAbsolutePath());
        }
    }

    public void handleButtonModificarUsuario()  {
        if (verificacionModificar()) {
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
            usuario.setId(blConexion.getUsuarioActual().getId());

            blConexion.modificarUsuario(usuario);

            Main.showAlertOneOption("Modificación exitosa", "Modificación lista!", "OK", "login", Alert.AlertType.INFORMATION);
        }
    }

    public boolean verificacionModificar()  {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String nombreUsuario = txtNombreUsuario.getText();
        String contrasenna = txtContrasenna.getText();
        String archivoImagen = txtArchivoImagen.getText();

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

        } else if (nombreUsuario != null ) {
            boolean userExists = blConexion.UsuarioExiste(txtNombreUsuario.getText());
            if (userExists && !nombreUsuario.equals(blConexion.getUsuarioActual().getNombreUsuario())) {
                labelUserExists.setText("este nombre de usuario no está disponible");
                esValido = false;
                txtNombreUsuario.setBorder(border);

            } else {
                labelUserExists.setText("");
                txtNombreUsuario.setBorder(null);
            }
        }


        if (contrasenna == null || txtContrasenna.getText().isEmpty()) {
            txtContrasenna.setText(contrasenna);
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

    public void handleButtonCancelar()  {
        Main.cambiaPantalla("paginaPrincipal");
    }

    public void handleButtonDeleteAccount()  {
        Optional<ButtonType> opcion = Main.showAlertTwoOptions("Eliminar Cuenta", "Seguro desea eliminar esta cuenta", "Si", "Cancelar", Alert.AlertType.WARNING);
        if(opcion.get().equals(ButtonType.OK)){
            blConexion.eliminarCuenta(blConexion.getUsuarioActual().getId());
            Main.showAlertOneOption("Eliminación exitosa!", "************", "Ok", "login", Alert.AlertType.INFORMATION);
            Main.cambiaPantalla("login");
        }else {
            Main.cambiaPantalla("modificarUsuario");
        }
    }
}
