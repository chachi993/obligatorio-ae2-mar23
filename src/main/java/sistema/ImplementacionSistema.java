package sistema;

import dominio.ABB;
import dominio.Conexion;
import dominio.EstacionDeTren;
import dominio.Pasajero;
import interfaz.EstadoCamino;
import interfaz.Retorno;
import interfaz.Sistema;

import java.util.ArrayList;
import java.util.regex.*;

public class ImplementacionSistema implements Sistema {
    private ABB pasajeros;
    private ArrayList<EstacionDeTren> estaciones;
    private int maxEstaciones;
     private Conexion[][] matrizConexion;


    @Override
    public Retorno inicializarSistema(int maxEstaciones) {
        pasajeros = new ABB();
        if (maxEstaciones <= 5) {
            return Retorno.error1("La cantidad de estaciones debe ser 5 o mayor");
        } else {
            this.maxEstaciones = maxEstaciones;
            matrizConexion = new Conexion[maxEstaciones][maxEstaciones];
            inicializarMatrizConexion();
            return Retorno.ok();
        }
    }
    public void inicializarMatrizConexion(){
        for (int i = 0; i < matrizConexion.length ; i++) {
            for (int j = 0; j < matrizConexion[0].length; j++) {
                Conexion c = new Conexion();
                matrizConexion[i][j] = c;
            }
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
*/
private boolean validarCodigoEstacion(String codigo) {
    return (Pattern.matches(codigo, "(([A-Z]{3}))+([0-9]{3}))"));//true si codigo es correcto
}
    @Override
    public Retorno registrarEstacionDeTren(String codigo, String nombre) {
        if(estaciones.size() == maxEstaciones) {
            return Retorno.error1("Si en el sistema ya hay registrados maxEstaciones.");
        } else if(codigo == "" || nombre == "" || codigo == null || nombre == null){
            return Retorno.error2("Algún dato es vacío o nulo.");
        } else if(!validarCodigoEstacion(codigo)){
            return Retorno.error3("El código es inválido");
        } else if (existeEstacion(codigo)) {
                    return Retorno.error4("Ya existe una estación con ese código.");
        } else {
            EstacionDeTren e = new EstacionDeTren(codigo,nombre);
            estaciones.add(e);
            return Retorno.ok();
        }
    }

        public boolean existeEstacion (String codigo){
            boolean existe = false;
            for (int i = 0; i < estaciones.size(); i++) {
                if(estaciones.get(i).getCodigo().equals(codigo)){
                    return true;
                }
            }
            return existe;
        }
    private int buscarPosicionEstacion(String codigo){
        for (int i = 0; i < estaciones.size() ; i++) {
            if(estaciones.get(i).getCodigo().equals(codigo)){
                return i;
            }
        }
        return -1;
    }
    @Override
    public Retorno registrarConexion(String codigoEstacionOrigen, String codigoEstacionDestino,
                                     int identificadorConexion, double costo, double tiempo, double kilometros,
                                     EstadoCamino estadoDeLaConexion) {
        if(costo <= 0 || tiempo <= 0 || kilometros <= 0){
            return Retorno.error1("Alguno de los parametros double es menor o igual a 0")
        } else if(codigoEstacionOrigen == "" || codigoEstacionOrigen == null || codigoEstacionDestino == "" || codigoEstacionDestino == null || estadoDeLaConexion == null ){
            return Retorno.error2("Alguno de los parámetros String o enum es vacío  o null");
        } else if(!validarCodigoEstacion(codigoEstacionOrigen) || !validarCodigoEstacion(codigoEstacionDestino)){
            return Retorno.error3("Alguno de los códigos de estación no es válido");
        }else if(!existeEstacion(codigoEstacionOrigen)){
            return Retorno.error4("No existe la estación de Origen");
        }else if(!existeEstacion(codigoEstacionDestino)){
            return Retorno.error5("No existe la estación de Destino");
        }else if(validarExistenciaCamino(codigoEstacionOrigen, codigoEstacionDestino)){
            return Retorno.error6("Ya existe un camino entre el origen y el destino");
        } else {
            agregarConexionAMatriz(codigoEstacionOrigen, codigoEstacionDestino, identificadorConexion, costo,  tiempo,  kilometros, estadoDeLaConexion);
            return Retorno.ok("El camino fue registrado exitosamente");
        }
    }

    private void agregarConexionAMatriz(String codigoEstacionOrigen, String codigoEstacionDestino,
            int identificadorConexion, double costo, double tiempo, double kilometros,
            EstadoCamino estadoDeLaConexion) {
        int posOrigen = buscarPosicionEstacion(codigoEstacionOrigen); //no son navegables
        int posDestino = buscarPosicionEstacion(codigoEstacionDestino);
        Conexion c = matrizConexion[posDestino][posOrigen];
        c.setCodigoEstacionOrigen(codigoEstacionOrigen);
        c.setCodigoEstacionDestino(codigoEstacionDestino);
        c.setIdentificadorConexion(identificadorConexion);
        c.setCosto(costo);
        c.setKilometros(kilometros);
        c.setTiempo(tiempo);
        c.setEstadoDeLaConexion(estadoDeLaConexion);
    }

    private boolean validarExistenciaCamino(String codigoEstacionOrigen, String codigoEstacionDestino) {
        int posOrigen = buscarPosicionEstacion(codigoEstacionOrigen);
        int posDestino = buscarPosicionEstacion(codigoEstacionDestino);
        Conexion c = matrizConexion[posDestino][posOrigen];
        if(c.getCosto() > 0 ){ //ya hay un camino definido
            return true;
        }
        return false; //no existe
    }



    @Override
    public Retorno actualizarCamino(String codigoEstacionOrigen, String codigoEstacionDestino,
            int identificadorConexion, double costo, double tiempo, double kilometros,
            EstadoCamino estadoDeLaConexion) {
        if(costo <= 0 || tiempo <= 0 || kilometros <= 0){
            return Retorno.error1("Alguno de los parametros double es menor o igual a 0")
        } else if(codigoEstacionOrigen == "" || codigoEstacionOrigen == null || codigoEstacionDestino == "" || codigoEstacionDestino == null || estadoDeLaConexion == null ){
            return Retorno.error2("Alguno de los parámetros String o enum es vacío  o null");
        } else if(!validarCodigoEstacion(codigoEstacionOrigen) || !validarCodigoEstacion(codigoEstacionDestino)){
            return Retorno.error3("Alguno de los códigos de estación no es válido");
        }else if(!existeEstacion(codigoEstacionOrigen)){
            return Retorno.error4("No existe la estación de Origen");
        }else if(!existeEstacion(codigoEstacionDestino)){
            return Retorno.error5("No existe la estación de Destino");
        }else if(!validarExistenciaCamino(codigoEstacionOrigen, codigoEstacionDestino)){
            return Retorno.error6("No existe la conexión con identificador entre origen y  destino");
        } else {
            agregarConexionAMatriz(codigoEstacionOrigen, codigoEstacionDestino, identificadorConexion, costo,  tiempo,  kilometros, estadoDeLaConexion);
            return Retorno.ok("El camino se actualizó correctamente");
        }
    }
/*
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
