package vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import modelo.Usuario;

/** Barra de anuncio y barra de navegación compartidas por todas las pantallas. */
public class EncabezadoVista extends VBox {

    private final Button btnIngresar = new Button("Ingresar");
    private final Button btnSalir = new Button("Salir");

    /** @param usuario usuario con sesión iniciada, o null si es la pantalla de login */
    public EncabezadoVista(Usuario usuario) {
        Label anuncio = new Label("ENVÍO GRATIS EN COMPRAS DESDE $50 USD");
        anuncio.getStyleClass().add("anuncio-texto");
        StackPane barraAnuncio = new StackPane(anuncio);
        barraAnuncio.getStyleClass().add("anuncio");

        Label logo = new Label("Rouge.Atelier");
        logo.getStyleClass().addAll("logo", "serif");

        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);

        HBox navbar = new HBox(18);
        navbar.getStyleClass().add("navbar");
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(14, 120, 14, 120));
        navbar.getChildren().addAll(logo, espacio, enlace("Catálogo"));

        if (usuario != null) {
            navbar.getChildren().add(enlace("Mis compras"));
            if (usuario.getRol() == Usuario.Rol.ADMINISTRADOR) {
                navbar.getChildren().add(enlace("Administración"));
            }
        }
        navbar.getChildren().addAll(enlace("♡"), enlace("🛍"));

        if (usuario == null) {
            btnIngresar.getStyleClass().add("nav-pildora");
            navbar.getChildren().add(btnIngresar);
        } else {
            Label nombre = new Label(usuario.getNombre());
            nombre.getStyleClass().add("nav-usuario");
            btnSalir.getStyleClass().add("nav-pildora-borde");
            navbar.getChildren().addAll(nombre, btnSalir);
        }

        getChildren().addAll(barraAnuncio, navbar);
    }

    private Button enlace(String texto) {
        Button b = new Button(texto);
        b.getStyleClass().add("nav-link");
        return b;
    }

    public Button getBtnIngresar() {
        return btnIngresar;
    }

    public Button getBtnSalir() {
        return btnSalir;
    }
}
