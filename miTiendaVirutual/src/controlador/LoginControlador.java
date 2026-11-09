package controlador;

import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.Tienda;
import modelo.Usuario;

public class LoginControlador {

    @FXML
    private EncabezadoControlador encabezadoController;
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private Label lblError;

    private Tienda tienda;
    private Consumer<Usuario> alIngresar;

    public void iniciar(Tienda tienda, Consumer<Usuario> alIngresar) {
        this.tienda = tienda;
        this.alIngresar = alIngresar;
        encabezadoController.configurar(null, null);
    }

    @FXML
    private void iniciarSesion() {
        String correo = txtCorreo.getText();
        String contrasena = txtContrasena.getText();
        if (correo.isBlank() || contrasena.isEmpty()) {
            mostrarError("Ingresa tu correo y contraseña.");
            return;
        }
        Usuario usuario = tienda.autenticar(correo, contrasena);
        if (usuario == null) {
            mostrarError("Correo o contraseña incorrectos.");
            return;
        }
        alIngresar.accept(usuario);
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setManaged(true);
        lblError.setVisible(true);
    }
}
