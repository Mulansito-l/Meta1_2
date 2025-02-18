import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Conector
 */
public class Conector {

    Connection conexion;
   
    Conector(){
        String url = "jdbc:mysql://localhost:3306/crud";
        try {
            conexion = DriverManager.getConnection(url, "root","Contrasena123!");
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
   
    Connection getConexion(){
        return conexion;
    }

    void cerrarConexion(){
        if(conexion != null){
            try {
                conexion.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    
}
