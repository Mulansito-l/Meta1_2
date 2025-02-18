import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * PersonaDAO
 */
public class PersonaDAO {
    static Conector conector = new Conector();
    static Connection conexion = conector.getConexion();

    static int getNextID(){
        try {
            ResultSet result = conexion.createStatement().executeQuery("SELECT * FROM Personas WHERE ID=(SELECT max(ID) FROM Personas)");
            if(result.next()){
                int nextID = result.getInt("ID") + 1;
                return nextID;
            }       
            return 0;

        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
        
    }

    static void registrarPersona(String nombre, String direccion, List<String> telefonos, List<String> vehiculos){
        String sql = String.format("INSERT INTO Personas VALUES('%d','%s','%s')",getNextID() + 1, nombre, direccion); 
        try{
            conexion.prepareStatement(sql).execute();
            for(String numero : telefonos){
                sql = String.format("INSERT INTO Telefonos VALUES('%s',%d)",numero, getNextID() + 1);   
                conexion.prepareStatement(sql).execute(); 
            }
            for (String vehiculo: vehiculos) {
                sql = String.format("INSERT INTO Vehiculos VALUES(%d, '%s')",getNextID() + 1, vehiculo);   
                conexion.prepareStatement(sql).execute();
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    static void modificarPersona(int id, String nombre, String direccion, List<String> telefonos, List<String> vehiculos){
        String sql = String.format("UPDATE Personas SET Nombre='%s', Direccion='%s' WHERE ID=%d", nombre, direccion,id); 
        try{
            conexion.prepareStatement(sql).execute();
            conexion.prepareStatement(String.format("DELETE FROM Telefonos WHERE Persona=%d",id)).execute();
            conexion.prepareStatement(String.format("DELETE FROM Vehiculos WHERE Persona=%d",id)).execute();
            for(String numero : telefonos){
                sql = String.format("INSERT INTO Telefonos VALUES('%s',%d)",numero, id);   
                conexion.prepareStatement(sql).execute(); 
            }

            for(String vehiculo : vehiculos){
                sql = String.format("INSERT INTO Vehiculos VALUES(%d,'%s')", id, vehiculo);   
                conexion.prepareStatement(sql).execute(); 
            }

        }catch(SQLException e){
            System.out.println(e);
        }
    }

    static ObservableList<Persona> obtenerPersonas(){
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

            return personas;

        } catch(SQLException ex){
            System.out.println(ex);
            return personas;
        }
    }

    static void borrarPersona(int id){
        try{
            conexion.prepareStatement(String.format("DELETE FROM Personas WHERE ID=%d", id)).execute();
            conexion.prepareStatement(String.format("DELETE FROM Telefonos WHERE Persona=%d",id)).execute();
            conexion.prepareStatement(String.format("DELETE FROM Vehiculos WHERE Persona=%d",id)).execute();
        } catch(SQLException e){
            System.out.println(e);
        }

    }

    static ResultSet obtenerDatosPersona(int ID){
        try {
            return conexion.prepareStatement(String.format("SELECT * FROM Personas WHERE ID=%d", ID)).executeQuery();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static ResultSet obtenerTelefonosPersona(int ID){
        try {
            return conexion.prepareStatement(String.format("SELECT * FROM Telefonos WHERE ID=%d", ID)).executeQuery();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static ResultSet obtenerVehiculosPersona(int ID){
        try {
            return conexion.prepareStatement(String.format("SELECT * FROM Vehiculos WHERE ID=%d", ID)).executeQuery();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    } 
}
