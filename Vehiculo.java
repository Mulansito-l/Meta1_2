
enum TIPO_VEHICULO{
    AUTOMOVIL,
    CAMION,
    MARITIMO,
    MOTOCICLETA,
    BICICLETA
}

/**
 * Vehiculo
 */
public class Vehiculo {
    protected TIPO_VEHICULO tipo;
    protected String controlDireccion; 

    TIPO_VEHICULO getTipo(){
        return tipo;
    }

    public String getControlDireccion() {
        return controlDireccion;
    }
}
