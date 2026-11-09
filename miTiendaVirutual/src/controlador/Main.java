package controlador;

import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import modelo.Tienda;
import modelo.Usuario;

/**
 * Punto de entrada. Cambia entre la vista de login y la de la tienda.
 *
 */
public class Main extends Application {

    private final Tienda tienda = new Tienda();
    private Scene escena;

    @Override
    public void start(Stage primaryStage) {
        escena = new Scene(new Region(), 1280, 800);
        escena.getStylesheets().add(getClass().getResource("/vista/estilos.css").toExternalForm());
        mostrarLogin();

        primaryStage.setTitle("Rouge Atelier");
        primaryStage.setScene(escena);
        primaryStage.show();
    }

    private void mostrarLogin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Login.fxml"));
        Parent raiz = cargar(loader);
        loader.<LoginControlador>getController().iniciar(tienda, this::mostrarTienda);
        escena.setRoot(raiz);
    }

    private void mostrarTienda(Usuario usuario) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Tienda.fxml"));
        Parent raiz = cargar(loader);
        loader.<TiendaControlador>getController().iniciar(tienda, usuario, this::mostrarLogin);
        escena.setRoot(raiz);
    }

    private Parent cargar(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
