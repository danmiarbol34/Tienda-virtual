package controlador;

import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import modelo.Producto;

public class VistaPreviaControlador {

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
    @FXML
    private Label lblStock;

    private Runnable alCerrar;

    public void mostrar(Producto producto, Runnable alCerrar) {
        this.alCerrar = alCerrar;
        imagen.setImage(new Image(getClass().getResourceAsStream(producto.getImagen())));
        Rectangle recorte = new Rectangle(imagen.getFitWidth(), imagen.getFitHeight());
        recorte.setArcWidth(24);
        recorte.setArcHeight(24);
        imagen.setClip(recorte);
        lblMarca.setText(producto.getMarca().toUpperCase(Locale.ROOT));
        lblNombre.setText(producto.getNombre());
        lblPresentacion.setText("Presentación: " + producto.getPresentacion());
        lblDescripcion.setText(producto.getDescripcion());
        lblPrecio.setText(String.format(Locale.US, "$%.2f USD", producto.getPrecio()));
        lblStock.setText(producto.getStock() + " unidades disponibles");
    }

    @FXML
    private void cerrar() {
        alCerrar.run();
    }
}
