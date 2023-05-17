package interfaz;

public abstract class ABBImplementacion implements ABB {
    private NodoABB raiz;

    public ABBImplementacion(NodoABB raiz) {
        this.raiz = raiz;
    }

    public boolean existePasajero(){
        return true;
    }
}
