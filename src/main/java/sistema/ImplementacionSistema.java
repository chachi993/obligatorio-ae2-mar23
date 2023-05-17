package sistema;

import dominio.ABB;
import dominio.Pasajero;
import interfaz.Retorno;
import interfaz.Sistema;
import java.util.regex.*;

public class ImplementacionSistema implements Sistema {
    private ABB pasajeros;
    private int maxEstaciones;

    @Override
    public Retorno inicializarSistema(int maxEstaciones) {
        pasajeros = new ABB();
        if (maxEstaciones <= 5) {
            return Retorno.error1("La cantidad de estaciones debe ser 5 o mayor");
        } else {
            this.maxEstaciones = maxEstaciones;
            return Retorno.ok();
        }
    }
    private boolean validarIdentificador(String identificadorPasajero) {
        return (Pattern.matches(identificadorPasajero, "((FR|DE|ES|OT|UK)+([1-9]{1}))+((([.]{1})+([0-9]{3})+([.]{1}))|(([0-9]{2})+([.]{1})))+(([0-9]{3})+([#]{1})+([0-9]{1}))"));//true si identificador es correcto
    }
    private String rectificarIdentificador(String idenfificadorPasajero){
        char[] aux = idenfificadorPasajero.toCharArray();
        String resultado = "";
        for(int i=0; i<aux.length-1; i++){
            if(Character.isDigit(aux[i])){
                resultado += aux[i];
            }
        }
        return resultado;
    }

    @Override
    public Retorno registrarPasajero(String identificadorPasajero, String nombre, int edad) {
        if(identificadorPasajero == null || identificadorPasajero == " " || nombre == null || nombre == " " || edad == 0 ) {
            if (validarIdentificador(identificadorPasajero)) {
                String identificadorSoloNum = rectificarIdentificador(identificadorPasajero);
                if (pasajeros.existePasajero(identificadorSoloNum)) {
                    return Retorno.error3("Ya existe un pasajero registrado con ese identificador.");
                } else {
                    Pasajero pasajeroNuevo = new Pasajero(identificadorSoloNum, nombre, edad);
                    pasajeros.insertar(pasajeroNuevo);
                    return Retorno.ok("Se ha registrado correctamente");
                }
            } else {
                return Retorno.error2("Si el identificador no tiene el formato válido.");
            }
        }
        return Retorno.error1("Alguno de los parámetros es vacío o null");
    }

    @Override
    public Retorno listarPasajerosAscendente() {

        String resultado = pasajeros.listarAscendente();

        return Retorno.ok(resultado);
    }

    @Override
    public Retorno listarPasajerosDescendente() {
        String resultado = pasajeros.listarDescendente();

        return Retorno.ok(resultado);
    }
/*

    @Override
    public Retorno filtrarPasajeros(Consulta consulta) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno buscarPasajero(String identificador) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarPasajerosPorNacionalidad(Nacionalidad nacionalidad) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarEstacionDeTren(String codigo, String nombre) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarConexion(String codigoEstacionOrigen, String codigoEstacionDestino,
                                     int identificadorConexion, double costo, double tiempo, double kilometros,
                                     EstadoCamino estadoDeLaConexion) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno actualizarCamino(String codigoEstacionOrigen, String codigoEstacionDestino,
                                    int identificadorConexion, double costo, double tiempo,
                                    double kilometros, EstadoCamino estadoDelCamino) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listadoEstacionesCantTrasbordos(String codigo, int cantidad) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoKilometros(String codigoEstacionOrigen, String codigoEstacionDestino) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoEuros(String codigoEstacionOrigen, String codigoEstacionDestino) {
        return null;
    }*/

}
