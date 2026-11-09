package vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginVista extends BorderPane {

    private final TextField txtCorreo = new TextField();
    private final PasswordField txtContrasena = new PasswordField();
    private final Button btnIngresar = new Button("Ingresar");
    private final Label lblError = new Label();

    public LoginVista() {
        setTop(new EncabezadoVista(null));
        setCenter(construirTarjeta());
    }

    private StackPane construirTarjeta() {
        // Panel izquierdo con el lema
        Label lema = new Label("Tu rutina, tus favoritos.");
        lema.getStyleClass().addAll("login-lema", "serif");
        lema.setWrapText(true);
        Label sub = new Label("Guarda tu lista de deseos, compra y revisa tu historial.");
        sub.getStyleClass().add("login-sub");
        sub.setWrapText(true);

        Region relleno = new Region();
        VBox.setVgrow(relleno, Priority.ALWAYS);
        VBox izquierdo = new VBox(12, relleno, lema, sub);
        izquierdo.getStyleClass().add("login-panel-izq");
        izquierdo.setPrefWidth(430);
        izquierdo.setMinWidth(430);

        // Formulario
        Label titulo = new Label("Iniciar sesión");
        titulo.getStyleClass().addAll("titulo", "serif");

        txtCorreo.setPromptText("Correo electrónico");
        txtCorreo.getStyleClass().add("campo");
        txtContrasena.setPromptText("Contraseña");
        txtContrasena.getStyleClass().add("campo");

        btnIngresar.getStyleClass().add("boton-primario");
        btnIngresar.setMaxWidth(Double.MAX_VALUE);
        btnIngresar.setDefaultButton(true);

        lblError.getStyleClass().add("error");
        lblError.setManaged(false);
        lblError.setVisible(false);

        Label registro = new Label("¿No tienes cuenta? Regístrate");
        registro.getStyleClass().add("enlace");
        VBox cajaRegistro = new VBox(registro);
        cajaRegistro.setAlignment(Pos.CENTER);

        Label tituloCuentas = new Label("Cuentas de prueba");
        tituloCuentas.getStyleClass().add("cuentas-titulo");
        Label admin = new Label("Administrador: admin@rouge.com / admin123");
        Label cliente = new Label("Usuario: cliente@rouge.com / cliente123");
        admin.getStyleClass().add("cuentas-texto");
        cliente.getStyleClass().add("cuentas-texto");
        VBox cuentas = new VBox(2, tituloCuentas, admin, cliente);
        cuentas.getStyleClass().add("cuentas-prueba");

        VBox formulario = new VBox(16, titulo, txtCorreo, txtContrasena, lblError, btnIngresar, cajaRegistro, cuentas);
        formulario.setPadding(new Insets(36, 40, 36, 40));
        formulario.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(formulario, Priority.ALWAYS);

        HBox tarjeta = new HBox(izquierdo, formulario);
        tarjeta.getStyleClass().add("login-tarjeta");
        tarjeta.setMaxWidth(1060);
        tarjeta.setMaxHeight(530);
        tarjeta.setPrefHeight(530);

        StackPane centro = new StackPane(tarjeta);
        centro.setPadding(new Insets(40));
        return centro;
    }

    public void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setManaged(true);
        lblError.setVisible(true);
    }

    public String getCorreo() {
        return txtCorreo.getText();
    }

    public String getContrasena() {
        return txtContrasena.getText();
    }

    public Button getBtnIngresar() {
        return btnIngresar;
    }
}
