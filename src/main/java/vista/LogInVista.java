package vista;

import com.example.proyecto.Main;
import controlador.BL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class LogInVista {

    @FXML
    public TextField txtUsuario;
    @FXML
    public Button btnRegistrarse;
    @FXML
    public PasswordField txtConstrasenna;
    private BL blConexion = BL.getInstanciaBl();

    public void initialize() {

    }

    public void handleButtonLogIn(ActionEvent event) {
        Usuario usuario = blConexion.buscarUsuario(txtConstrasenna.getText(), txtUsuario.getText());

        if (usuario == null && !(txtUsuario.getText().isEmpty() && txtConstrasenna.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nombre de usuario o contraseña incorrecta");
            alert.setContentText("¿Desea intentar denuevo?");
            ButtonType tryAgainButton = new ButtonType("Intentar de nuevo");
            ButtonType registerButton = new ButtonType("Registrarse");
            alert.getButtonTypes().setAll(tryAgainButton, registerButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == registerButton) {
                Main.cambiaPantalla("registrarUsuario");
            }

        } else if (usuario != null) {
            Main.cambiaPantalla("paginaPrincipal");
        }
    }

    public void handleButtonExit(ActionEvent event) {
        System.exit(0);
    }

    public void handleButtonRegister()  {
        Main.cambiaPantalla("registrarUsuario");
    }
}
