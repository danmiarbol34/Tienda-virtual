package controlador;

import java.util.Locale;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import modelo.Producto;

public class ProductoTarjetaControlador {

    @FXML
    private ImageView imagen;
    @FXML
    private Label lblMarca;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblPresentacion;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblPrecio;

    private Producto producto;
    private Consumer<Producto> alAbrir;

    public void mostrar(Producto producto, Consumer<Producto> alAbrir) {
        this.producto = producto;
        this.alAbrir = alAbrir;
        imagen.setImage(new Image(getClass().getResourceAsStream(producto.getImagen())));
        Rectangle recorte = new Rectangle(imagen.getFitWidth(), imagen.getFitHeight());
        recorte.setArcWidth(20);
        recorte.setArcHeight(20);
        imagen.setClip(recorte);
        lblMarca.setText(producto.getMarca().toUpperCase(Locale.ROOT));
        lblNombre.setText(producto.getNombre());
        lblPresentacion.setText(producto.getPresentacion());
        lblDescripcion.setText(producto.getDescripcion());
        lblPrecio.setText(String.format(Locale.US, "$%.2f USD", producto.getPrecio()));
    }

    @FXML
    private void abrirVistaPrevia() {
        alAbrir.accept(producto);
    }
}
