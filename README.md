Meta 1.2 - Diego Castaneda Covarrubias

Concepto
Código y descripción de la relación

Clase
El código se tienen las siguientes clases: CRUD, Persona, Vehiculo, Automovil, Camion, Maritimo, Motocicleta, Bicicleta.

Objeto
En código se utilizaron X objetos, por ejemplo: Se crearon múltiples objetos persona para almacenar los datos de un registro antes de subirlo a la base de datos.

Abstracción
Para la abstracción cada Vehiculo se encarga de asignar los datos correctamente según corresponda el tipo, de esta forma no es necesario realizar nada extra además de utilizar un constructor.

Encapsulamiento
Los atributos de los Vehículos como el tipo o el control de dirección no son directamente modificables solo se modifican en el constructor o se pueden obtener mediante un getter.

Herencia
Los distintos tipos de Vehículos heredan los atributos y comportamientos de su clase padre Vehiculo, lo cual les otorga un tipo y un control de dirección.

Polimorfismo
La clase Vehiculo tiene un getter para el control de dirección, cada clase hija de Vehículo responde diferente al momento de obtener este control de dirección.

