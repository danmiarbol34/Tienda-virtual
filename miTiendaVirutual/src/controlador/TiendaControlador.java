package controlador;

import java.util.Comparator;
import java.util.List;
import modelo.Producto;
import modelo.Tienda;
import vista.TiendaVista;

public class TiendaControlador {

    private final TiendaVista vista;
    private final Tienda tienda;

    public TiendaControlador(TiendaVista vista, Tienda tienda, Runnable alSalir) {
        this.vista = vista;
        this.tienda = tienda;
        actualizar();
        vista.getBtnSalir().setOnAction(e -> alSalir.run());
        // la lista se actualiza mientras se escribe y al cambiar el orden
        vista.getTxtBusqueda().textProperty().addListener((obs, antes, ahora) -> actualizar());
        vista.getCmbOrden().valueProperty().addListener((obs, antes, ahora) -> actualizar());
    }

    private void actualizar() {
        List<Producto> lista = tienda.buscar(vista.getTxtBusqueda().getText());
        lista.sort(comparadorSeleccionado());
        vista.mostrarProductos(lista);
    }

    private Comparator<Producto> comparadorSeleccionado() {
        String orden = vista.getCmbOrden().getValue();
        if ("Precio: menor a mayor".equals(orden)) {
            return Comparator.comparingDouble(Producto::getPrecio);
        }
        if ("Precio: mayor a menor".equals(orden)) {
            return Comparator.comparingDouble(Producto::getPrecio).reversed();
        }
        return Comparator.comparing(p -> p.getMarca() + " " + p.getNombre(), String.CASE_INSENSITIVE_ORDER);
    }
}
