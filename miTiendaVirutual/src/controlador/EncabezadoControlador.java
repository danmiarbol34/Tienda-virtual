package controlador;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modelo.Usuario;

public class EncabezadoControlador {

    @FXML
    private Button btnMisCompras;
    @FXML
    private Button btnAdministracion;
    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lblUsuario;

    private Runnable alSalir = () -> { };

    /**
     * Ajusta el encabezado según la sesión.
     *
     * @param usuario usuario con sesión iniciada, o null en el login
     * @param alSalir acción al pulsar "Salir"
     */
    public void configurar(Usuario usuario, Runnable alSalir) {
        this.alSalir = alSalir;
        boolean haySesion = usuario != null;
        mostrar(btnIngresar, !haySesion);
        mostrar(btnMisCompras, haySesion);
        mostrar(btnAdministracion, haySesion && usuario.getRol() == Usuario.Rol.ADMINISTRADOR);
        mostrar(lblUsuario, haySesion);
        mostrar(btnSalir, haySesion);
        if (haySesion) {
            lblUsuario.setText(usuario.getNombre());
        }
    }

    @FXML
    private void salir() {
        alSalir.run();
    }

    private void mostrar(Node nodo, boolean visible) {
        nodo.setVisible(visible);
        nodo.setManaged(visible);
    }
}
