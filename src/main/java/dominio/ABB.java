package dominio;

public class ABB {
    private NodoABB raiz;

    public NodoABB getRaiz() {
        return raiz;
    }

    public  boolean existePasajero(String identificador){
       Pasajero pasajero = pertence(identificador);
       if(pasajero != null){
           return true;
       }
       else {
           return false;
       }
    }
    public  void insertar(Pasajero dato) {
        if (raiz == null) {
            raiz = new NodoABB(dato);
        } else {
            insertarRec(raiz, dato);
        }
    }

    private void insertarRec(NodoABB nodo, Pasajero dato) {
        if (nodo.getDato().compararPasajero(dato) < 0) {
            if (nodo.getIzq() == null) {
                nodo.setIzq(new NodoABB(dato));
            } else {
                insertarRec(nodo.getIzq(), dato);
            }
        } else if (nodo.getDato().compararPasajero(dato) > 0) {
            if (nodo.getDer() == null) {
                nodo.setDer( new NodoABB(dato));
            } else {
                insertarRec(nodo.getDer(), dato);
            }
        }
    }
    public  Pasajero pertence(String dato) {
        return pertenceRec(raiz, dato);
    }

    private  Pasajero pertenceRec(NodoABB nodo, String dato) {
        if (nodo != null) {
            if (dato == nodo.getDato().getIndentificadorPasajero()) {
                return nodo.getDato();
            } else if (dato.compareTo(nodo.getDato().getIndentificadorPasajero()) < 0 ) {
                return pertenceRec(nodo.getIzq(), dato);
            } else {
                return pertenceRec(nodo.getDer(), dato);
            }
        } else {
            return null;
        }
    }
    public String listarAscendente() {
        return listarAscendente(raiz);
    }

    private String listarAscendente(NodoABB nodo) {
        if (nodo != null)
            return listarAscendente(nodo.getIzq()) + " " + nodo.getDato() + " " + listarAscendente(nodo.getDer());
        else
            return "";
    }
    public String listarDescendente() {
        return listarDescendente(raiz);
    }
    private String listarDescendente(NodoABB nodo) {
        if (nodo != null)
            return listarDescendente(nodo.getDer()) + " " + nodo.getDato() + " " + listarDescendente(nodo.getIzq());
        else
            return "";
    }

}
