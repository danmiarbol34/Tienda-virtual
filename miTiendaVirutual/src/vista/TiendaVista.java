package vista;

import java.util.List;
import java.util.Locale;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import modelo.Producto;
import modelo.Usuario;

public class TiendaVista extends BorderPane {

    private final EncabezadoVista encabezado;
    private final FlowPane grilla = new FlowPane(24, 24);
    private final StackPane capa = new StackPane();
    private final TextField txtBusqueda = new TextField();
    private final ComboBox<String> cmbOrden = new ComboBox<>();
    private final Label sinResultados = new Label("No encontramos productos con esa búsqueda.");

    public TiendaVista(Usuario usuario) {
        encabezado = new EncabezadoVista(usuario);
        setTop(encabezado);

        Label titulo = new Label("Catálogo");
        titulo.getStyleClass().addAll("titulo", "serif");
        Label subtitulo = new Label("Descubre nuestros productos de maquillaje y cuidado personal");
        subtitulo.getStyleClass().add("subtitulo");

        txtBusqueda.setPromptText("Buscar por nombre, marca o descripción...");
        txtBusqueda.getStyleClass().add("campo");
        HBox.setHgrow(txtBusqueda, Priority.ALWAYS);
        cmbOrden.getItems().addAll("Nombre (A-Z)", "Precio: menor a mayor", "Precio: mayor a menor");
        cmbOrden.getSelectionModel().selectFirst();
        cmbOrden.setStyle("-fx-background-color: white; -fx-border-color: #E8C9C9; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 6 8 6 8;");
        HBox barra = new HBox(12, txtBusqueda, cmbOrden);
        barra.setAlignment(Pos.CENTER_LEFT);

        sinResultados.getStyleClass().add("subtitulo");
        sinResultados.setManaged(false);
        sinResultados.setVisible(false);

        grilla.setAlignment(Pos.TOP_LEFT);
        VBox contenido = new VBox(6, titulo, subtitulo, barra, sinResultados, grilla);
        VBox.setMargin(barra, new Insets(20, 0, 0, 0));
        VBox.setMargin(grilla, new Insets(20, 0, 0, 0));
        contenido.setPadding(new Insets(40, 0, 50, 0));
        contenido.setMaxWidth(1100);

        StackPane centrado = new StackPane(contenido);
        centrado.setPadding(new Insets(0, 24, 0, 24));
        ScrollPane scroll = new ScrollPane(new VBox(centrado, construirPie()));
        scroll.setFitToWidth(true);
        capa.setStyle("-fx-background-color: rgba(42,7,13,0.55);");
        capa.setVisible(false);
        capa.setOnMouseClicked(e -> {
            if (e.getTarget() == capa) {
                cerrarVistaPrevia();
            }
        });
        capa.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                cerrarVistaPrevia();
            }
        });
        setCenter(new StackPane(scroll, capa));

        // la grilla ocupa el ancho disponible del contenido
        grilla.prefWrapLengthProperty().bind(contenido.widthProperty());
    }

    public void mostrarProductos(List<Producto> productos) {
        grilla.getChildren().clear();
        sinResultados.setManaged(productos.isEmpty());
        sinResultados.setVisible(productos.isEmpty());
        for (Producto p : productos) {
            grilla.getChildren().add(crearTarjeta(p));
        }
    }

    /** Ventana flotante con el detalle del producto, sobre el catálogo. */
    private void mostrarVistaPrevia(Producto p) {
        ImageView imagen = new ImageView(new Image(getClass().getResourceAsStream(p.getImagen())));
        imagen.setFitWidth(340);
        imagen.setFitHeight(340);
        javafx.scene.shape.Rectangle recorte = new javafx.scene.shape.Rectangle(340, 340);
        recorte.setArcWidth(24);
        recorte.setArcHeight(24);
        imagen.setClip(recorte);

        Label marca = new Label(p.getMarca().toUpperCase(Locale.ROOT));
        marca.getStyleClass().add("producto-marca");
        Label nombre = new Label(p.getNombre());
        nombre.getStyleClass().addAll("titulo", "serif");
        nombre.setStyle("-fx-font-size: 28px;");
        nombre.setWrapText(true);
        Label presentacion = new Label("Presentación: " + p.getPresentacion());
        presentacion.getStyleClass().add("producto-presentacion");
        Label descripcion = new Label(p.getDescripcion());
        descripcion.getStyleClass().add("producto-descripcion");
        descripcion.setStyle("-fx-font-size: 14px;");
        descripcion.setWrapText(true);
        Label precio = new Label(String.format(Locale.US, "$%.2f USD", p.getPrecio()));
        precio.getStyleClass().add("producto-precio");
        precio.setStyle("-fx-font-size: 22px;");
        Label stock = new Label(p.getStock() + " unidades disponibles");
        stock.getStyleClass().add("producto-presentacion");

        Button agregar = new Button("Agregar al carrito");
        agregar.getStyleClass().add("boton-agregar");
        Button cerrar = new Button("Cerrar");
        cerrar.getStyleClass().add("boton-corazon");
        cerrar.setOnAction(e -> cerrarVistaPrevia());
        HBox acciones = new HBox(10, agregar, cerrar);

        Region relleno = new Region();
        VBox.setVgrow(relleno, Priority.ALWAYS);
        VBox detalle = new VBox(8, marca, nombre, presentacion, descripcion, relleno, precio, stock, acciones);
        detalle.setPrefWidth(340);

        HBox tarjeta = new HBox(28, imagen, detalle);
        tarjeta.getStyleClass().add("producto-tarjeta");
        tarjeta.setPadding(new Insets(24));
        tarjeta.setMaxSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);
        tarjeta.setCursor(Cursor.DEFAULT);

        capa.getChildren().setAll(tarjeta);
        capa.setVisible(true);
        capa.requestFocus();
    }

    private void cerrarVistaPrevia() {
        capa.setVisible(false);
        capa.getChildren().clear();
    }

    private VBox crearTarjeta(Producto p) {
        Image img = new Image(getClass().getResourceAsStream(p.getImagen()));
        ImageView vistaImagen = new ImageView(img);
        vistaImagen.setFitWidth(222);
        vistaImagen.setFitHeight(200);
        vistaImagen.setPreserveRatio(false);
        StackPane marco = new StackPane(vistaImagen);
        marco.setStyle("-fx-background-radius: 10; -fx-background-color: #FBF1EE;");
        javafx.scene.shape.Rectangle recorte = new javafx.scene.shape.Rectangle(222, 200);
        recorte.setArcWidth(20);
        recorte.setArcHeight(20);
        vistaImagen.setClip(recorte);

        Label marca = new Label(p.getMarca().toUpperCase(Locale.ROOT));
        marca.getStyleClass().add("producto-marca");
        Label nombre = new Label(p.getNombre());
        nombre.getStyleClass().addAll("producto-nombre", "serif");
        nombre.setWrapText(true);
        nombre.setMinHeight(46);
        Label presentacion = new Label(p.getPresentacion());
        presentacion.getStyleClass().add("producto-presentacion");
        Label descripcion = new Label(p.getDescripcion());
        descripcion.getStyleClass().add("producto-descripcion");
        descripcion.setWrapText(true);
        descripcion.setMinHeight(72);
        descripcion.setMaxHeight(72);
        Label precio = new Label(String.format(Locale.US, "$%.2f USD", p.getPrecio()));
        precio.getStyleClass().add("producto-precio");

        Button agregar = new Button("Agregar al carrito");
        agregar.getStyleClass().add("boton-agregar");
        Button deseo = new Button("♡");
        deseo.getStyleClass().add("boton-corazon");
        HBox acciones = new HBox(8, agregar, deseo);
        acciones.setAlignment(Pos.CENTER_LEFT);

        VBox tarjeta = new VBox(6, marco, marca, nombre, presentacion, descripcion, precio, acciones);
        tarjeta.getStyleClass().add("producto-tarjeta");
        tarjeta.setCursor(Cursor.HAND);
        tarjeta.setOnMouseClicked(e -> mostrarVistaPrevia(p));
        tarjeta.setPadding(new Insets(14));
        tarjeta.setPrefWidth(250);
        tarjeta.setMinWidth(250);
        tarjeta.setMaxWidth(250);
        return tarjeta;
    }

    private VBox construirPie() {
        Label logo = new Label("Rouge.Atelier");
        logo.getStyleClass().addAll("pie-logo", "serif");
        Label texto = new Label("Tienda virtual de maquillaje");
        texto.getStyleClass().add("pie-texto");
        VBox pie = new VBox(4, logo, texto);
        pie.getStyleClass().add("pie");
        pie.setAlignment(Pos.CENTER);
        return pie;
    }

    public TextField getTxtBusqueda() {
        return txtBusqueda;
    }

    public ComboBox<String> getCmbOrden() {
        return cmbOrden;
    }

    public Button getBtnSalir() {
        return encabezado.getBtnSalir();
    }
}
