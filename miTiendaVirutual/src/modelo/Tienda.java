package modelo;

import java.util.ArrayList;
import java.util.List;

/** Datos de la tienda en memoria, usando listas simples. */
public class Tienda {

    private static final String IMG = "/vista/imagenes/";

    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Producto> productos = new ArrayList<>();

    public Tienda() {
        usuarios.add(new Usuario("admin@rouge.com", "admin123", "Administrador", Usuario.Rol.ADMINISTRADOR));
        usuarios.add(new Usuario("cliente@rouge.com", "cliente123", "Cliente", Usuario.Rol.CLIENTE));

        productos.add(new Producto(1, "Dior", "Backstage Rosy Glow", 42.00,
                "Rubor en polvo reactivo al pH que se adapta a tu piel para un tono rosado único, fresco y natural.",
                "4.4 g", 20, IMG + "dior-rosy-glow.jpg"));
        productos.add(new Producto(2, "e.l.f.", "Instant Lift Waterproof Eyeliner Pen", 6.00,
                "Delineador líquido de ojos en formato plumón con punta fina de fieltro para trazos precisos, intensos y de larga duración.",
                "0.8 g", 40, IMG + "elf-eyeliner-pen.jpg"));
        productos.add(new Producto(3, "KIKO Milano", "Precision Eyeliner", 10.00,
                "Delineador líquido fluido con aplicador de fieltro de alta precisión para trazos definidos, pigmentación negra intensa y secado rápido.",
                "2.5 ml", 30, IMG + "kiko-precision-eyeliner.jpg"));
        productos.add(new Producto(4, "Benefit", "Benetint Rose-Tinted Lip & Cheek Stain", 24.00,
                "Tinta líquida multiusos para labios y mejillas con acabado natural, fórmula de larga duración y resistente a transferencias.",
                "6.0 ml", 25, IMG + "benefit-benetint.jpg"));
        productos.add(new Producto(5, "Dior", "Addict Lip Glow Oil", 40.00,
                "Aceite labial nutritivo con aceite de cereza que hidrata intensamente, realza el color natural de los labios y brinda un brillo espejo no pegajoso.",
                "6.0 ml", 18, IMG + "dior-lip-glow-oil.jpg"));
        productos.add(new Producto(6, "KIKO Milano", "3D Hydra Lipgloss", 14.00,
                "Brillo de labios hidratante con efecto 3D y acabado brillante con destellos, de textura suave, confortable y no pegajosa.",
                "6.5 ml", 35, IMG + "kiko-3d-hydra-lipgloss.jpg"));
    }

    /** Devuelve el usuario que coincide con las credenciales, o null si no existe. */
    public Usuario autenticar(String correo, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.coincide(correo.trim(), contrasena)) {
                return u;
            }
        }
        return null;
    }

    /** Productos cuyo nombre, marca o descripción contienen el texto (sin distinguir mayúsculas ni tildes). */
    public List<Producto> buscar(String texto) {
        String buscado = normalizar(texto);
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            String contenido = normalizar(p.getMarca() + " " + p.getNombre() + " " + p.getDescripcion());
            if (contenido.contains(buscado)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    private String normalizar(String s) {
        return java.text.Normalizer.normalize(s.trim(), java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase();
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }
}
