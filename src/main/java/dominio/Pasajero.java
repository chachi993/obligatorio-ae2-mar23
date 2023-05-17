package dominio;

public class Pasajero {
    private final String indentificadorPasajero;
    private String nombre;
    private int edad;



    public Pasajero(String indentificadorPasajero, String nombre, int edad) {
        this.indentificadorPasajero = indentificadorPasajero;
        this.nombre = nombre;
        this.edad = edad;
    }

    public Pasajero(String indentificadorPasajero) {
        this.indentificadorPasajero = indentificadorPasajero;
    }
    public String getIndentificadorPasajero() {
        return indentificadorPasajero;
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

    public void setEdad(int edad) {
        this.edad = edad;
    }
    public int compararPasajero(Pasajero pas){
        if(this.getIndentificadorPasajero().compareTo(pas.getIndentificadorPasajero()) < 0){
            return -1;
        } else if (this.getIndentificadorPasajero().compareTo(pas.getIndentificadorPasajero()) > 0) {
            return 1;
        }
        else{
            return 0;
        }

    }
}