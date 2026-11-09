package modelo;

public class Usuario {

    public enum Rol { ADMINISTRADOR, CLIENTE }

    private final String correo;
    private final String contrasena;
    private final String nombre;
    private final Rol rol;

    public Usuario(String correo, String contrasena, String nombre, Rol rol) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.rol = rol;
    }

    public boolean coincide(String correo, String contrasena) {
        return this.correo.equalsIgnoreCase(correo) && this.contrasena.equals(contrasena);
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public Rol getRol() {
        return rol;
    }
}
