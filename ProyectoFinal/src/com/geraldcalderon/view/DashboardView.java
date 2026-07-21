package com.geraldcalderon.view;

import com.geraldcalderon.model.Usuario;
import com.geraldcalderon.model.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {

    private Usuario usuarioLogueado;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Componentes del Formulario
    private TextField txtId = new TextField();
    private TextField txtNombre = new TextField();
    private TextField txtUsuario = new TextField();
    private TextField txtCorreo = new TextField();
    private PasswordField txtContrasena = new PasswordField();
    private ComboBox<String> cbTipoUsuario = new ComboBox<>();

    // Tabla y Lista de Datos
    private TableView<Usuario> tablaUsuarios = new TableView<>();
    private ObservableList<Usuario> listaUsuarios;

    // Botones
    private Button btnGuardar = new Button("Guardar");
    private Button btnActualizar = new Button("Actualizar");
    private Button btnEliminar = new Button("Eliminar");
    private Button btnLimpiar = new Button("Limpiar");

    public DashboardView(Usuario usuario) {
        this.usuarioLogueado = usuario;
        
        Stage stage = new Stage();
        stage.setTitle("Panel Principal - Gestión de Usuarios");

        // Layout Principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // --- ENCABEZADO ---
        Label lblHeader = new Label("Panel de Administración | Usuario activo: " + usuarioLogueado.getNombreCompleto());
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 0 0 15 0;");
        root.setTop(lblHeader);

        // --- IZQUIERDA: FORMULARIO DE REGISTRO ---
        VBox panelFormulario = crearPanelFormulario();
        root.setLeft(panelFormulario);

        // --- CENTRO: TABLA DE DATOS ---
        VBox panelTabla = crearPanelTabla();
        BorderPane.setMargin(panelTabla, new Insets(0, 0, 0, 15));
        root.setCenter(panelTabla);

        // Cargar registros iniciales en la tabla
        cargarDatosTabla();

        // Configurar Eventos
        configurarEventos();

        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearPanelFormulario() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 10;");

        Label lblTitulo = new Label("Datos del Usuario");
        lblTitulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        txtId.setDisable(true); // El ID es autogenerado por MySQL
        txtId.setPromptText("Auto-ID");

        cbTipoUsuario.getItems().addAll("Profesor", "Estudiante");
        cbTipoUsuario.getSelectionModel().selectFirst();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(txtId, 1, 0);

        grid.add(new Label("Nombre Completo:"), 0, 1);
        grid.add(txtNombre, 1, 1);

        grid.add(new Label("Usuario:"), 0, 2);
        grid.add(txtUsuario, 1, 2);

        grid.add(new Label("Correo:"), 0, 3);
        grid.add(txtCorreo, 1, 3);

        grid.add(new Label("Contraseña:"), 0, 4);
        grid.add(txtContrasena, 1, 4);

        grid.add(new Label("Tipo:"), 0, 5);
        grid.add(cbTipoUsuario, 1, 5);

        // Panel de Botones
        HBox panelBotones = new HBox(8);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(10, 0, 0, 0));
        
        btnGuardar.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
        btnActualizar.setStyle("-fx-background-color: #1565c0; -fx-text-fill: white;");
        btnEliminar.setStyle("-fx-background-color: #c62828; -fx-text-fill: white;");
        
        panelBotones.getChildren().addAll(btnGuardar, btnActualizar, btnEliminar, btnLimpiar);

        panel.getChildren().addAll(lblTitulo, grid, panelBotones);
        return panel;
    }

    @SuppressWarnings("unchecked")
    private VBox crearPanelTabla() {
        VBox panel = new VBox(10);

        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre Completo");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colNombre.setPrefWidth(160);

        TableColumn<Usuario, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colUsuario.setPrefWidth(100);

        TableColumn<Usuario, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colCorreo.setPrefWidth(140);

        TableColumn<Usuario, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));
        colTipo.setPrefWidth(90);

        tablaUsuarios.getColumns().addAll(colId, colNombre, colUsuario, colCorreo, colTipo);
        panel.getChildren().add(tablaUsuarios);

        return panel;
    }

    private void cargarDatosTabla() {
        listaUsuarios = FXCollections.observableArrayList(usuarioDAO.obtenerTodos());
        tablaUsuarios.setItems(listaUsuarios);
    }

    private void configurarEventos() {
        // Evento al seleccionar una fila de la tabla
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(String.valueOf(newSelection.getId()));
                txtNombre.setText(newSelection.getNombreCompleto());
                txtUsuario.setText(newSelection.getUsuario());
                txtCorreo.setText(newSelection.getCorreo());
                txtContrasena.setText(newSelection.getContrasena());
                cbTipoUsuario.setValue(newSelection.getTipoUsuario());
            }
        });

        // Evento Guardar
        btnGuardar.setOnAction(e -> {
            if (validarCampos()) {
                Usuario nuevo = new Usuario(
                    txtNombre.getText().trim(),
                    txtUsuario.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtContrasena.getText().trim(),
                    cbTipoUsuario.getValue()
                );
                if (usuarioDAO.registrar(nuevo)) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado correctamente.");
                    limpiarFormulario();
                    cargarDatosTabla();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el usuario.");
                }
            }
        });

        // Evento Actualizar
        btnActualizar.setOnAction(e -> {
            if (txtId.getText().isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un usuario de la tabla para actualizar.");
                return;
            }
            if (validarCampos()) {
                Usuario actual = new Usuario(
                    Integer.parseInt(txtId.getText()),
                    txtNombre.getText().trim(),
                    txtUsuario.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtContrasena.getText().trim(),
                    cbTipoUsuario.getValue()
                );
                if (usuarioDAO.actualizar(actual)) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario actualizado correctamente.");
                    limpiarFormulario();
                    cargarDatosTabla();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el usuario.");
                }
            }
        });

        // Evento Eliminar
        btnEliminar.setOnAction(e -> {
            if (txtId.getText().isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un usuario de la tabla para eliminar.");
                return;
            }
            int id = Integer.parseInt(txtId.getText());
            if (usuarioDAO.eliminar(id)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario eliminado correctamente.");
                limpiarFormulario();
                cargarDatosTabla();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el usuario.");
            }
        });

        // Evento Limpiar
        btnLimpiar.setOnAction(e -> limpiarFormulario());
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
            txtUsuario.getText().trim().isEmpty() ||
            txtCorreo.getText().trim().isEmpty() ||
            txtContrasena.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor llene todos los campos del formulario.");
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtId.clear();
        txtNombre.clear();
        txtUsuario.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        cbTipoUsuario.getSelectionModel().selectFirst();
        tablaUsuarios.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    void start(Stage stage) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}