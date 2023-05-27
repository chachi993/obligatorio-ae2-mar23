package sistema;

import dominio.ABB;
import dominio.Conexion;
import dominio.EstacionDeTren;
import dominio.NodoABB;
import dominio.Pasajero;
import interfaz.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.*;

public class ImplementacionSistema implements Sistema {
    private int maxEstaciones;
    private ABB pasajeros;
    private Lista<EstacionDeTren> estaciones;
     private Lista<Conexion>[][] matrizConexion;
     private Lista<Pasajero> pasajeroFR ;
    private Lista<Pasajero> pasajeroDE ;
    private Lista<Pasajero> pasajeroUK ;
    private Lista<Pasajero> pasajeroES ;
    private Lista<Pasajero> pasajeroOT ;


    @Override
    public Retorno inicializarSistema(int maxEstaciones) {
        pasajeros = new ABB();
        if (maxEstaciones <= 5) {
            return Retorno.error1("La cantidad de estaciones debe ser 5 o mayor");
        } else {
            this.maxEstaciones = maxEstaciones;
            matrizConexion = new Lista[maxEstaciones][maxEstaciones]; //matriz que en cada posicion tiene un arraylist
            inicializarMatrizConexion(); //TODO: Revisar si dejar en null o no
            return Retorno.ok();
        }
    }
    public void inicializarMatrizConexion(){
        for (int i = 0; i < matrizConexion.length ; i++) {
            for (int j = 0; j < matrizConexion[0].length; j++) {
                Conexion c = new Conexion();
                //matrizConexion[i][j] = c;
            }
        }
    }
    private boolean validarIdentificador(String identificadorPasajero) {
        return (Pattern.matches(identificadorPasajero, "((FR|DE|ES|OT|UK)+([1-9]{1}))+((([.]{1})+([0-9]{3})+([.]{1}))|(([0-9]{2})+([.]{1})))+(([0-9]{3})+([#]{1})+([0-9]{1}))"));//true si identificador es correcto
    }
    private String rectificarIdentificador(String idenfificadorPasajero){
        char[] aux = idenfificadorPasajero.toCharArray();
        String resultado = "";
        for(int i=0; i< aux.length-1; i++){
            if(Character.isDigit(aux[i])){
                resultado += aux[i];
            }
        }
        return resultado;
    }

    @Override
    public Retorno registrarPasajero(String identificadorPasajero, String nombre, int edad) {
        if(identificadorPasajero == null || identificadorPasajero.equals("") || nombre == null || nombre.equals("") || edad == 0 ) {
            if (validarIdentificador(identificadorPasajero)) {
                String identificadorSoloNum = rectificarIdentificador(identificadorPasajero);
                if (pasajeros.existePasajero(identificadorSoloNum)) {
                    return Retorno.error3("Ya existe un pasajero registrado con ese identificador.");
                } else {
                    String nacionalidad = identificadorPasajero.substring(0,2);
                    Pasajero pasajeroNuevo = new Pasajero(identificadorPasajero, identificadorSoloNum, nombre, edad, nacionalidad);
                    if(nacionalidad.equals("FR")) pasajeroFR.agregarAlPrincipio(pasajeroNuevo);
                    if(nacionalidad.equals("DE")) pasajeroDE.agregarAlPrincipio(pasajeroNuevo);
                    if(nacionalidad.equals("UK")) pasajeroUK.agregarAlPrincipio(pasajeroNuevo);
                    if(nacionalidad.equals("ES")) pasajeroES.agregarAlPrincipio(pasajeroNuevo);
                    if(nacionalidad.equals("OT"))pasajeroOT.agregarAlPrincipio(pasajeroNuevo);
                    //TODO: luego método listar
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
    @Override
    public Retorno listarPasajerosPorNacionalidad(Nacionalidad nacionalidad) {
        if (nacionalidad == null){
            return Retorno.error1("La nacionalidad es vacía");
        } else {
            String nac = nacionalidad.getCodigo();
            String resultado = "";
            if (nac.equals("FR")) resultado = pasajeroFR.mostrar();
            if (nac.equals("DE")) resultado = pasajeroDE.mostrar();
            if (nac.equals("UK")) resultado = pasajeroUK.mostrar();
            if (nac.equals("ES")) resultado = pasajeroES.mostrar();
            if (nac.equals("OT")) resultado = pasajeroOT.mostrar();
            return Retorno.ok(resultado);
        }
    }
    @Override
    public Retorno filtrarPasajeros(Consulta consulta) {//le pasamos el AB Consulta
        if (consulta != null) {
            return Retorno.ok(filtrar(pasajeros.getRaiz(), consulta.getRaiz()));
        }
        return Retorno.error1("La consulta es vacía");
    }
//primero recorrer pasajeros, y luego filtrar pasajeros que tiene y si cumplen o no con la consulta. Guardar string y concatenar
    private String filtrar(NodoABB nodo , Consulta.NodoConsulta nodoConsulta){
        if(nodo != null){
            if(filtrar(nodo.getDato(), nodoConsulta)){
                return   filtrar(nodo.getIzq(), nodoConsulta) +
                        nodo.getDato().getIndentificadorPasajero() + "|" +
                         filtrar(nodo.getDer(), nodoConsulta);
            } else {
                return  filtrar(nodo.getIzq(), nodoConsulta) +
                        filtrar(nodo.getDer(), nodoConsulta);
            }
        }
        return "";
    }
    private boolean filtrar(Pasajero p, Consulta.NodoConsulta nodoConsulta){
        if(nodoConsulta.getTipoNodoConsulta().equals(Consulta.TipoNodoConsulta.EdadMayor)){
            return p.getEdad() > nodoConsulta.getValorInt();
        }
        if(nodoConsulta.getTipoNodoConsulta().equals(Consulta.TipoNodoConsulta.And)){
            return filtrar(p, nodoConsulta.getIzq()) && filtrar(p, nodoConsulta.getDer());
        }
        if(nodoConsulta.getTipoNodoConsulta().equals(Consulta.TipoNodoConsulta.Or)){
            return filtrar(p, nodoConsulta.getIzq()) || filtrar(p, nodoConsulta.getDer());
        }
        if(nodoConsulta.getTipoNodoConsulta().equals(Consulta.TipoNodoConsulta.NombreIgual)){
            return p.getNombre().equals(nodoConsulta.getValorString());
        }
        if(nodoConsulta.getTipoNodoConsulta().equals(Consulta.TipoNodoConsulta.Nacionalidad)){
            return p.getNacionalidad().equals(nodoConsulta.getValorNacionalidad());
        }
        return false;
    }
    /*
        @Override
        public Retorno buscarPasajero(String identificador) {
            return Retorno.noImplementada();
        }

    */
private boolean validarCodigoEstacion(String codigo) {
    return (Pattern.matches(codigo, "(([A-Z]{3}))+([0-9]{3}))"));//true si codigo es correcto
}
    @Override
    public Retorno registrarEstacionDeTren(String codigo, String nombre) {
        if(estaciones.largo() == maxEstaciones) {
            return Retorno.error1("Si en el sistema ya hay registrados maxEstaciones.");
        } else if(codigo.equals("") || nombre.equals("") || codigo == null || nombre == null){
            return Retorno.error2("Algún dato es vacío o nulo.");
        } else if(!validarCodigoEstacion(codigo)){
            return Retorno.error3("El código es inválido");
        } else if (existeEstacion(codigo)) {
                    return Retorno.error4("Ya existe una estación con ese código.");
        } else {
            EstacionDeTren e = new EstacionDeTren(codigo,nombre);
            estaciones.agregarAlPrincipio(e);
            return Retorno.ok();
        }
    }

        public boolean existeEstacion (String codigo){
            boolean existe = false;
            for (int i = 0; i < estaciones.largo(); i++) {
                if(estaciones.get(i).getCodigo().equals(codigo)){
                    //  TODO: seria if(estaciones.existe().equals(codigo)){ ?
                    return true;
                }
            }
            return existe;
        }
    private int buscarPosicionEstacion(String codigo){
        for (int i = 0; i < estaciones.largo() ; i++) {
            if(estaciones.get(i).getCodigo().equals(codigo)){
                //  TODO: seria if(estaciones.existe().equals(codigo)){ ?
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
        } else if(codigoEstacionOrigen.equals("") || codigoEstacionOrigen == null || codigoEstacionDestino.equals("") || codigoEstacionDestino == null || estadoDeLaConexion == null ){
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
            return Retorno.error1("Alguno de los parámetros double es menor o igual a 0");
        } else if(codigoEstacionOrigen.equals("") || codigoEstacionOrigen == null || codigoEstacionDestino.equals("") || codigoEstacionDestino == null || estadoDeLaConexion == null ){
            return Retorno.error2("Alguno de los parámetros String o enum es vacío  o null");
        } else if(!validarCodigoEstacion(codigoEstacionOrigen) || !validarCodigoEstacion(codigoEstacionDestino)){
            return Retorno.error3("Alguno de los códigos de estación no es válido");
        }else if(!existeEstacion(codigoEstacionOrigen)){
            return Retorno.error4("No existe la estación de Origen");
        }else if(!existeEstacion(codigoEstacionDestino)){
            return Retorno.error5("No existe la estación de Destino");
        }else if(!validarExistenciaCamino(codigoEstacionOrigen, codigoEstacionDestino)){
            return Retorno.error6("No existe la conexión con identificador entre origen y destino");
        } else {
            agregarConexionAMatriz(codigoEstacionOrigen, codigoEstacionDestino, identificadorConexion, costo,  tiempo,  kilometros, estadoDeLaConexion);
            return Retorno.ok("El camino se actualizó correctamente");
        }
    }

    @Override
    public Retorno listadoEstacionesCantTrasbordos(String codigo, int cantidad) { //usamos DFS no?
        String resultado = llegoConCantidadDeTrasbordos(codigo, cantidad);
            return Retorno.ok(resultado);
        if(cantidad < 0 ) {
            return Retorno.error1("La cantidad es menor a 0");
        } else if(codigo == null) {
            return Retorno.error2("El código es nulo");
        } else if(!validarCodigoEstacion(codigo)) {
            return Retorno.error3("El código de la estación no es válido");
        } else if(!existeEstacion(codigo)) { // es el mismo codigo de estacion de origen o hay que usar otra funcion????
            return Retorno.error4("La estación no está registrada en el sistema");
        }
    }
    private String llegoConCantidadDeTrasbordos(int cantidad, String codigo){
    }

    @Override //TODO: FALTA CASO EXITOSO
    public Retorno viajeCostoMinimoKilometros(String codigoEstacionOrigen, String codigoEstacionDestino) {
        if (codigoEstacionOrigen.equals("") || codigoEstacionOrigen == null || codigoEstacionDestino.equals("") || codigoEstacionDestino == null) {
            return Retorno.error1("Alguno de los códigos es vacío o null");
        } else if (!validarCodigoEstacion(codigoEstacionOrigen) || !validarCodigoEstacion(codigoEstacionDestino)) {
            return Retorno.error2("Alguno de los códigos de estación no es válido");
        } else if (!validarExistenciaCamino(codigoEstacionOrigen,
                codigoEstacionDestino)) {
            return Retorno.error3("No  hay camino entre el origen y el destino");//esta bien???
        } else if (!existeEstacion(codigoEstacionOrigen)) {//esta bien???
            return Retorno.error4("No existe origen");
        } else if (!existeEstacion(codigoEstacionDestino)) {//esta bien???
            return Retorno.error5("No existe destino");
        }
    }

    @Override //TODO: FALTA CASO EXITOSO
    public Retorno viajeCostoMinimoEuros(String codigoEstacionOrigen, String codigoEstacionDestino) {
        if (codigoEstacionOrigen.equals("")|| codigoEstacionOrigen == null || codigoEstacionDestino.equals("") || codigoEstacionDestino == null) {
            return Retorno.error1("Alguno de los códigos es vacío o null");
        } else if (!validarCodigoEstacion(codigoEstacionOrigen) || !validarCodigoEstacion(codigoEstacionDestino)) {
            return Retorno.error2("Alguno de los códigos de estación no es válido");
        } else if (!validarExistenciaCamino(codigoEstacionOrigen,
                codigoEstacionDestino)) {
            return Retorno.error3("No  hay camino entre el origen y el destino");//esta bien???
        } else if (!existeEstacion(codigoEstacionOrigen)) {//esta bien???
            return Retorno.error4("No existe origen");
        } else if (!existeEstacion(codigoEstacionDestino)) {//esta bien???
            return Retorno.error5("No existe destino");
        }
    }

}
