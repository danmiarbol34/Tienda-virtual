package controlador;

import java.util.function.Consumer;
import modelo.Tienda;
import modelo.Usuario;
import vista.LoginVista;

public class LoginControlador {

    private final LoginVista vista;
    private final Tienda tienda;
    private final Consumer<Usuario> alIngresar;

    public LoginControlador(LoginVista vista, Tienda tienda, Consumer<Usuario> alIngresar) {
        this.vista = vista;
        this.tienda = tienda;
        this.alIngresar = alIngresar;
        vista.getBtnIngresar().setOnAction(e -> iniciarSesion());
    }

    private void iniciarSesion() {
        String correo = vista.getCorreo();
        String contrasena = vista.getContrasena();
        if (correo.isBlank() || contrasena.isEmpty()) {
            vista.mostrarError("Ingresa tu correo y contraseña.");
            return;
        }
        Usuario usuario = tienda.autenticar(correo, contrasena);
        if (usuario == null) {
            vista.mostrarError("Correo o contraseña incorrectos.");
            return;
        }
        alIngresar.accept(usuario);
    }
}
