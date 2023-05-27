package dominio;

import interfaz.Nacionalidad;

public class Pasajero {
    private  String identificadorRectificado;
    private final String indentificadorPasajero;
    private  String nombre;
    private int edad;

    private Nacionalidad nacionalidad;

    public Pasajero(String indentificadorPasajero, String identificadorRectificado, String nombre, int edad, String nacionalidad) {
        this.identificadorRectificado = identificadorRectificado;
        this.indentificadorPasajero = indentificadorPasajero;
        this.nombre = nombre;
        this.edad = edad;
        this.nacionalidad = Nacionalidad.fromCodigo(indentificadorPasajero.substring(0,1));
    }

    public Pasajero(String indentificadorPasajero) {
        this.indentificadorPasajero = indentificadorPasajero;
    }
    public String getIndentificadorPasajero() {
        return indentificadorPasajero;
    }

    public String getIndentificadorPasajeroRectificado() {
        return identificadorRectificado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public Nacionalidad getNacionalidad() {return nacionalidad;}
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public int compararPasajero(Pasajero pas){
        if(this.getIndentificadorPasajeroRectificado().compareTo(pas.getIndentificadorPasajeroRectificado()) < 0){
            return -1;
        } else if (this.getIndentificadorPasajeroRectificado().compareTo(pas.getIndentificadorPasajeroRectificado()) > 0) {
            return 1;
        }
        else{
            return 0;
        }
    }
    @Override
    public String toString(){
        return getIndentificadorPasajero() + ';' + getNombre()+';'+ getEdad() +';' + getNacionalidad().toString() + "|" ;
    };
}