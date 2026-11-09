package controlador;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import modelo.Producto;
import modelo.Tienda;
import modelo.Usuario;

public class TiendaControlador {

    @FXML
    private EncabezadoControlador encabezadoController;
    @FXML
    private VBox contenido;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private ComboBox<String> cmbOrden;
    @FXML
    private Label lblSinResultados;
    @FXML
    private FlowPane grilla;
    @FXML
    private StackPane capa;

    private Tienda tienda;

    @FXML
    private void initialize() {
        // la grilla ocupa el ancho disponible del contenido
        grilla.prefWrapLengthProperty().bind(contenido.widthProperty());
        cmbOrden.getSelectionModel().selectFirst();
        // la lista se actualiza mientras se escribe y al cambiar el orden
        txtBusqueda.textProperty().addListener((obs, antes, ahora) -> actualizar());
        cmbOrden.valueProperty().addListener((obs, antes, ahora) -> actualizar());
    }

    public void iniciar(Tienda tienda, Usuario usuario, Runnable alSalir) {
        this.tienda = tienda;
        encabezadoController.configurar(usuario, alSalir);
        actualizar();
    }

    private void actualizar() {
        List<Producto> lista = tienda.buscar(txtBusqueda.getText());
        lista.sort(comparadorSeleccionado());

        grilla.getChildren().clear();
        lblSinResultados.setManaged(lista.isEmpty());
        lblSinResultados.setVisible(lista.isEmpty());
        for (Producto p : lista) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ProductoTarjeta.fxml"));
            Node tarjeta = cargar(loader);
            loader.<ProductoTarjetaControlador>getController().mostrar(p, this::mostrarVistaPrevia);
            grilla.getChildren().add(tarjeta);
        }
    }

    private Comparator<Producto> comparadorSeleccionado() {
        String orden = cmbOrden.getValue();
        if ("Precio: menor a mayor".equals(orden)) {
            return Comparator.comparingDouble(Producto::getPrecio);
        }
        if ("Precio: mayor a menor".equals(orden)) {
            return Comparator.comparingDouble(Producto::getPrecio).reversed();
        }
        return Comparator.comparing(p -> p.getMarca() + " " + p.getNombre(), String.CASE_INSENSITIVE_ORDER);
    }

    private void mostrarVistaPrevia(Producto producto) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaPrevia.fxml"));
        Node vistaPrevia = cargar(loader);
        loader.<VistaPreviaControlador>getController().mostrar(producto, this::cerrarVistaPrevia);
        capa.getChildren().setAll(vistaPrevia);
        capa.setVisible(true);
        capa.requestFocus();
    }

    private void cerrarVistaPrevia() {
        capa.setVisible(false);
        capa.getChildren().clear();
    }

    @FXML
    private void clicCapa(MouseEvent e) {
        if (e.getTarget() == capa) {
            cerrarVistaPrevia();
        }
    }

    @FXML
    private void teclaCapa(KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE) {
            cerrarVistaPrevia();
        }
    }

    private Node cargar(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
