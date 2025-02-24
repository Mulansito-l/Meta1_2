import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JComboBox;
/**
 * CRUD
 */
public class CRUD extends Application{

    static int nextID = 0;
    static Connection conexion;
    Button registrar;
    Button eliminar;
    Button consultar_todos;
    Button consultar_id;
    Button modificar;

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/crud";

        try {
            conexion = DriverManager.getConnection(url, "root","Contrasena123!");
            System.out.println("Conexión exitosa a la base de datos.");
            ResultSet result = conexion.createStatement().executeQuery("SELECT * FROM Personas WHERE ID=(SELECT max(ID) FROM Personas)");
            if(result.next())
                nextID = result.getInt("ID") + 1;

            launch(args);
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    } 

    @Override
    public void start(Stage principal) throws Exception {
        principal.setTitle("CRUD");
        registrar = new Button("Registrar");
        registrar.setMinWidth(200);
        eliminar = new Button("Eliminar");
        eliminar.setMinWidth(200);
        consultar_todos = new Button("Consultar todos");
        consultar_todos.setMinWidth(200);
        consultar_id = new Button("Consultar por ID");
        consultar_id.setMinWidth(200);
        modificar = new Button("Modificar");
        modificar.setMinWidth(200);


        registrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                registrar(principal);
            }
        });

        consultar_todos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mostrarTodo(principal);
            }
        });

        consultar_id.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                consultarID(principal); 
            }
        });

        eliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eliminar(principal); 
            }
        });

        modificar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                modificar(principal); 
            }
        });

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setVgap(10);
        root.add(registrar,0,0);
        root.add(eliminar,0,1);
        root.add(consultar_todos,0,2);
        root.add(consultar_id,0,3);
        root.add(modificar,0,4);
        principal.setScene(new Scene(root, 300, 250));
        principal.show();
    }

    private static void registrar(Stage principal){

        Label lNombre = new Label("Nombre");
        TextField nombre = new TextField();
        Label lDireccion = new Label("Direccion");
        TextField direccion = new TextField();
        Label lTelefonos = new Label("Telefonos");
        ListView<String> telefonos = new ListView<String>();
        TextField nuevoTelefono = new TextField();
        Button agregar = new Button("Agregar telefono");
        Label lVehiculos = new Label("Vehiculos");
        ListView<String> vehiculos = new ListView<String>();
        ComboBox<String> tipoVehiculos = new ComboBox<String>(FXCollections.observableArrayList("Auto","Camion","Maritimo","Motocicleta","Bicicleta"));
        Button agregarVehiculo = new Button("Agregar vehiculo");

        agregar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if(!nuevoTelefono.getText().equals("")) 
                    telefonos.getItems().add(nuevoTelefono.getText());
            }
        });

        agregarVehiculo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                switch (tipoVehiculos.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        vehiculos.getItems().add("Auto"); 
                        break;
                    case 1:
                        vehiculos.getItems().add("Camion"); 
                        break;
                    case 2:
                        vehiculos.getItems().add("Maritimo"); 
                        break;
                    case 3:
                        vehiculos.getItems().add("Motocicleta"); 
                        break;
                    case 4:
                        vehiculos.getItems().add("Bicicleta"); 
                        break;
                    default:
                        break;
                }
            }
        });

        telefonos.setOnMouseClicked( e -> {
        
            if(telefonos.getSelectionModel().getSelectedIndex() != -1)
                telefonos.getItems().remove(telefonos.getSelectionModel().getSelectedIndex());

        });

        vehiculos.setOnMouseClicked( e -> {
            if(vehiculos.getSelectionModel().getSelectedIndex() != -1)
                vehiculos.getItems().remove(vehiculos.getSelectionModel().getSelectedIndex());

        });

        Button registrar = new Button("Registrar");

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(principal);
        dialog.setTitle("Registrar nuevo");

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.add(lNombre,0,0,2,1);
        root.add(nombre,0,1,2,1);
        root.add(lDireccion,0,2,2,1);
        root.add(direccion,0,3,2,1);
        root.add(lTelefonos,0,4,2,1);
        root.add(telefonos,0,5,2,1);
        root.add(nuevoTelefono,0,6,1,1);
        root.add(agregar,1,6,1,1);
        root.add(registrar,0,10,2,1);
        root.add(lVehiculos,0,7,2,1);
        root.add(vehiculos,0,8,2,1);
        root.add(tipoVehiculos,0,9,1,1);
        root.add(agregarVehiculo,1,9,1,1);
        root.setPadding(new Insets(10));
        dialog.setScene(new Scene(root, 400, 450));

        registrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if(nombre.getText().isBlank() || direccion.getText().isBlank() || telefonos.getItems().size() == 0)        
                    return;
                String sql = String.format("INSERT INTO Personas VALUES('%d','%s','%s')",nextID, nombre.getText(), direccion.getText()); 
                try{
                    conexion.prepareStatement(sql).execute();
                    for(String numero : telefonos.getItems()){
                        sql = String.format("INSERT INTO Telefonos VALUES('%s',%d)",numero, nextID);   
                        conexion.prepareStatement(sql).execute(); 
                    }
                    for (String vehiculo: vehiculos.getItems()) {
                        sql = String.format("INSERT INTO Vehiculos VALUES(%d, '%s')",nextID, vehiculo);   
                        conexion.prepareStatement(sql).execute();
                    }
                    nextID++;
                    dialog.close();
                }catch(SQLException e){
                    System.out.println(e);
                }


            }
        });

        dialog.show(); 
    }

    private static void mostrarTodo(Stage principal){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(principal);
        dialog.setTitle("Todos los registrados");

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10));
        dialog.setScene(new Scene(root, 600, 450));

        TableView<Persona> datos = new TableView<>();

        javafx.scene.control.TableColumn<Persona, Integer> columnaID = new javafx.scene.control.TableColumn<Persona, Integer>("ID");
        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));

        javafx.scene.control.TableColumn<Persona, String> columnaNombre = new javafx.scene.control.TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        javafx.scene.control.TableColumn<Persona, String> columnaDireccion = new javafx.scene.control.TableColumn<>("Direccion");
        columnaDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        javafx.scene.control.TableColumn<Persona, String> columnaTelefonos = new javafx.scene.control.TableColumn<>("Telefonos");
        columnaTelefonos.setCellValueFactory(new PropertyValueFactory<>("telefonos"));

        javafx.scene.control.TableColumn<Persona, String> columnaVehiculos = new javafx.scene.control.TableColumn<>("Vehiculos");
        columnaVehiculos.setCellValueFactory(new PropertyValueFactory<>("vehiculos"));

        datos.getColumns().addAll(columnaID, columnaNombre, columnaDireccion, columnaTelefonos, columnaVehiculos);

        ObservableList<Persona> personas = FXCollections.observableArrayList();

        try{
            ResultSet rs = conexion.prepareStatement("SELECT * FROM Personas").executeQuery();
            while(rs.next()){
                int id = rs.getInt("ID");
                String nombre = rs.getString("Nombre");
                String direccion = rs.getString("Direccion");
                ArrayList<String> telefonos = new ArrayList<String>();
                ResultSet telefonosID = conexion.prepareStatement(String.format("SELECT * FROM Telefonos WHERE Persona=%d",id)).executeQuery();
                while(telefonosID.next()){
                    telefonos.add(telefonosID.getString("Numero"));
                }

                ArrayList<String> vehiculos = new ArrayList<String>();
                ResultSet vehiculosID = conexion.prepareStatement(String.format("SELECT * FROM Vehiculos WHERE Persona=%d",id)).executeQuery();
                while(vehiculosID.next()){
                    vehiculos.add(vehiculosID.getString("Tipo"));
                }
                personas.add(new Persona(id, nombre, direccion, telefonos, vehiculos));
            }

            datos.setItems(personas);
        } catch(SQLException ex){
            System.out.println(ex);
        }

        datos.setMinWidth(500);
        root.minWidth(600);
        root.add(datos,0,0);

        dialog.show();
    }

    private static void eliminar(Stage principal){
        TextInputDialog idDialog = new TextInputDialog(); 
        idDialog.setTitle("Ingrese el ID a borrar");
        idDialog.showAndWait().ifPresent(string -> {
            Integer id = Integer.parseInt(string);
            try{
                conexion.prepareStatement(String.format("DELETE FROM Personas WHERE ID=%d", id)).execute();
                conexion.prepareStatement(String.format("DELETE FROM Telefonos WHERE Persona=%d",id)).execute();
                conexion.prepareStatement(String.format("DELETE FROM Vehiculos WHERE Persona=%d",id)).execute();
            } catch(SQLException e){
                System.out.println(e);
            }
        });
    }

    private static void modificar(Stage principal){
        TextInputDialog idDialog = new TextInputDialog(); 
        idDialog.setTitle("Ingrese el ID a modificar");
        idDialog.showAndWait().ifPresent(string -> {
            Integer id = Integer.parseInt(string);
            try{
                ResultSet result = conexion.prepareStatement(String.format("SELECT * FROM Personas WHERE ID=%d", id)).executeQuery();
                ResultSet resultTelefonos = conexion.prepareStatement(String.format("SELECT * FROM Telefonos WHERE Persona=%d",id)).executeQuery();
                ResultSet resultVehiculos= conexion.prepareStatement(String.format("SELECT * FROM Vehiculos WHERE Persona=%d",id)).executeQuery();
                
                Label lNombre = new Label("Nombre");
                TextField nombre = new TextField();
                Label lDireccion = new Label("Direccion");
                TextField direccion = new TextField();
                Label lTelefonos = new Label("Telefonos");
                ListView<String> telefonos = new ListView<String>();
                TextField nuevoTelefono = new TextField();
                Button agregar = new Button("Agregar telefono");
                Label lVehiculos = new Label("Vehiculos");
                ListView<String> vehiculos = new ListView<String>();
                ComboBox<String> tipoVehiculos = new ComboBox<String>(FXCollections.observableArrayList("Auto","Camion","Maritimo","Motocicleta","Bicicleta"));
                Button agregarVehiculo = new Button("Agregar vehiculo");


                if(result.next()){
                    nombre.setText(result.getString("Nombre"));
                    direccion.setText(result.getString("Direccion"));
                    while(resultTelefonos.next()){
                        telefonos.getItems().add(resultTelefonos.getString("Numero"));
                    }
                    while (resultVehiculos.next()) {
                        vehiculos.getItems().add(resultVehiculos.getString("Tipo"));
                    }
                }else {
                    return;
                }
                agregar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        if(!nuevoTelefono.getText().equals("")) 
                            telefonos.getItems().add(nuevoTelefono.getText());
                    }
                });

                telefonos.setOnMouseClicked( e -> {
                    if(telefonos.getSelectionModel().getSelectedIndex() != -1)
                        telefonos.getItems().remove(telefonos.getSelectionModel().getSelectedIndex());

                });

                agregarVehiculo.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        switch (tipoVehiculos.getSelectionModel().getSelectedIndex()) {
                            case 0:
                                vehiculos.getItems().add("Auto"); 
                                break;
                            case 1:
                                vehiculos.getItems().add("Camion"); 
                                break;
                            case 2:
                                vehiculos.getItems().add("Maritimo"); 
                                break;
                            case 3:
                                vehiculos.getItems().add("Motocicleta"); 
                                break;
                            case 4:
                                vehiculos.getItems().add("Bicicleta"); 
                                break;
                            default:
                                break;
                        }
                    }
                });

                vehiculos.setOnMouseClicked( e -> {
                    if(vehiculos.getSelectionModel().getSelectedIndex() != -1)
                        vehiculos.getItems().remove(vehiculos.getSelectionModel().getSelectedIndex());

                });

                Button modificar = new Button("Modificar");

                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(principal);
                dialog.setTitle("Modificar");

                GridPane root = new GridPane();
                root.setAlignment(Pos.CENTER);
                root.setHgap(10);
                root.setVgap(10);
                root.add(lNombre,0,0,2,1);
                root.add(nombre,0,1,2,1);
                root.add(lDireccion,0,2,2,1);
                root.add(direccion,0,3,2,1);
                root.add(lTelefonos,0,4,2,1);
                root.add(telefonos,0,5,2,1);
                root.add(nuevoTelefono,0,6,1,1);
                root.add(agregar,1,6,1,1);
                root.add(lVehiculos,0,7,2,1);
                root.add(vehiculos,0,8,2,1);
                root.add(tipoVehiculos,0,9,1,1);
                root.add(agregarVehiculo,1,9,1,1);
                root.add(modificar,0,10,2,1);
                root.setPadding(new Insets(10));
                dialog.setScene(new Scene(root, 400, 450));

                modificar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        if(nombre.getText().isBlank() || direccion.getText().isBlank() || telefonos.getItems().size() == 0)        
                            return;
                        String sql = String.format("UPDATE Personas SET Nombre='%s', Direccion='%s' WHERE ID=%d", nombre.getText(), direccion.getText(),id); 
                        try{
                            conexion.prepareStatement(sql).execute();
                            conexion.prepareStatement(String.format("DELETE FROM Telefonos WHERE Persona=%d",id)).execute();
                            conexion.prepareStatement(String.format("DELETE FROM Vehiculos WHERE Persona=%d",id)).execute();
                            for(String numero : telefonos.getItems()){
                                sql = String.format("INSERT INTO Telefonos VALUES('%s',%d)",numero, id);   
                                conexion.prepareStatement(sql).execute(); 
                            }

                            for(String vehiculo : vehiculos.getItems()){
                                sql = String.format("INSERT INTO Vehiculos VALUES(%d,'%s')", id, vehiculo);   
                                conexion.prepareStatement(sql).execute(); 
                            }
                            dialog.close();
                        }catch(SQLException e){
                            System.out.println(e);
                        }
                    }
                });

                dialog.show();
            } catch(SQLException e){
                System.out.println(e);
            }
        });
    }

    private static void consultarID(Stage principal){
        TextInputDialog idDialog = new TextInputDialog(); 
        idDialog.setTitle("Ingrese el ID a consultar");
        idDialog.showAndWait().ifPresent(string -> {
            Integer id = Integer.parseInt(string);
            try{
                ResultSet result = conexion.prepareStatement(String.format("SELECT * FROM Personas WHERE ID=%d", id)).executeQuery();
                ResultSet resultTelefonos = conexion.prepareStatement(String.format("SELECT * FROM Telefonos WHERE Persona=%d",id)).executeQuery();
                ResultSet resultVehiculos = conexion.prepareStatement(String.format("SELECT * FROM Vehiculos WHERE Persona=%d",id)).executeQuery();

                Label lNombre = new Label("Nombre");
                TextField nombre = new TextField();
                Label lDireccion = new Label("Direccion");
                TextField direccion = new TextField();
                Label lTelefonos = new Label("Telefonos");
                ListView<String> telefonos = new ListView<String>();
                Label lVehiculos = new Label("Vehiculos");
                ListView<String> vehiculos = new ListView<String>();

                if(result.next()){
                    nombre.setText(result.getString("Nombre"));
                    direccion.setText(result.getString("Direccion"));
                    while(resultTelefonos.next()){
                        telefonos.getItems().add(resultTelefonos.getString("Numero"));
                    }
                    while (resultVehiculos.next()) {
                        vehiculos.getItems().add(resultVehiculos.getString("Tipo"));
                    }

                    nombre.setDisable(true);
                    direccion.setDisable(true);
                    telefonos.setDisable(true);
                    vehiculos.setDisable(true);
                }else {
                    return;
                }

                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(principal);
                dialog.setTitle("Modificar");

                GridPane root = new GridPane();
                root.setAlignment(Pos.CENTER);
                root.setHgap(10);
                root.setVgap(10);
                root.add(lNombre,0,0,2,1);
                root.add(nombre,0,1,2,1);
                root.add(lDireccion,0,2,2,1);
                root.add(direccion,0,3,2,1);
                root.add(lTelefonos,0,4,2,1);
                root.add(telefonos,0,5,2,1);
                root.add(lVehiculos,0,6,2,1);
                root.add(vehiculos,0,7,2,1);
                root.setPadding(new Insets(10));
                dialog.setScene(new Scene(root, 400, 450));
                dialog.show();
            } catch(SQLException e){
                System.out.println(e);
            }
        });
    }
}
