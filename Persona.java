import java.util.ArrayList;

/**
 * Persona
 */
public class Persona {
    int id;
    String nombre;
    String direccion;
    ArrayList<String> telefonos;
    ArrayList<String> vehiculos;

    Persona(int id, String nombre, String direccion, ArrayList<String> telefonos, ArrayList<String> vehiculos){
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefonos = telefonos;
        this.vehiculos = vehiculos;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public ArrayList<String> getTelefonos() {
        return telefonos;
    }

    public ArrayList<String> getVehiculos() {
        return vehiculos;
    }
}
