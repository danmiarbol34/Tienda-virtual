package modelo;

public class Producto {

    private final int id;
    private final String marca;
    private final String nombre;
    private final double precio;
    private final String descripcion;
    private final String presentacion;
    private final int stock;
    private final String imagen;

    public Producto(int id, String marca, String nombre, double precio,
            String descripcion, String presentacion, int stock, String imagen) {
        this.id = id;
        this.marca = marca;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
        this.stock = stock;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public int getStock() {
        return stock;
    }

    /** Ruta del recurso de la imagen dentro del classpath. */
    public String getImagen() {
        return imagen;
    }
}
