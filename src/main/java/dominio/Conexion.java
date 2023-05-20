package dominio;

import interfaz.EstadoCamino;

public class Conexion {
    String codigoEstacionOrigen;
    String codigoEstacionDestino;

    static int identificador = 1;
     int identificadorConexion;
    double costo;
    double tiempo;
    double kilometros;
    EstadoCamino estadoDeLaConexion;

    public Conexion() {
        this.codigoEstacionOrigen = "";
        this.codigoEstacionDestino = "";
        this.identificadorConexion = identificador;
        this.costo = 0;
        this.tiempo = 0;
        this.kilometros = 0;
        this.estadoDeLaConexion = null;
        identificador++;
    }

    public String getCodigoEstacionOrigen() {
        return codigoEstacionOrigen;
    }

    public void setCodigoEstacionOrigen(String codigoEstacionOrigen) {
        this.codigoEstacionOrigen = codigoEstacionOrigen;
    }

    public String getCodigoEstacionDestino() {
        return codigoEstacionDestino;
    }

    public void setCodigoEstacionDestino(String codigoEstacionDestino) {
        this.codigoEstacionDestino = codigoEstacionDestino;
    }

    public int getIdentificadorConexion() {
        return identificadorConexion;
    }

    public void setIdentificadorConexion(int identificadorConexion) {
        this.identificadorConexion = identificadorConexion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public double getKilometros() {
        return kilometros;
    }

    public void setKilometros(double kilometros) {
        this.kilometros = kilometros;
    }

    public EstadoCamino getEstadoDeLaConexion() {
        return estadoDeLaConexion;
    }

    public void setEstadoDeLaConexion(EstadoCamino estadoDeLaConexion) {
        this.estadoDeLaConexion = estadoDeLaConexion;
    }
}


