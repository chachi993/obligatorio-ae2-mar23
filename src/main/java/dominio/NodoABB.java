package dominio;
public class NodoABB {
    private Pasajero dato;
    private NodoABB izq;
    private NodoABB der;

    public NodoABB(Pasajero dato) {
        this.dato = dato;
        this.izq = null;
        this.der = null;
    }

    public Pasajero getDato() {
        return dato;
    }

    public void setDato(Pasajero dato) {
        this.dato = dato;
    }

    public NodoABB getIzq() {
        return izq;
    }

    public void setIzq(NodoABB izq) {
        this.izq = izq;
    }

    public NodoABB getDer() {
        return der;
    }

    public void setDer(NodoABB der) {
        this.der = der;
    }
}
