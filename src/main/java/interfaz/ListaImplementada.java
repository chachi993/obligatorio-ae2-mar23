package interfaz;

public class ListaImplementada<T> implements Lista<T> {

    private Nodo<T> inicio;
    private int cantidadElementos;

    @Override
    public void agregarAlPrincipio(T dato) {
        this.inicio = new Nodo<>(dato, inicio);
        this.cantidadElementos++;
    }

    //Pre !esVacia()
    @Override
    public T elminarPrincipio() {
        T dato = this.inicio.getDato();
        this.inicio = this.inicio.getSig();
        this.cantidadElementos--;
        return dato;
    }

    @Override
    public void agregarAlFinal(T dato) {
        if (esVacia()) {
            agregarAlPrincipio(dato);
        } else {
            //No es vacía --> inicio != null
            Nodo<T> nuevo = new Nodo<>(dato);
            Nodo<T> fin = this.inicio;
            while (fin.getSig() != null) {
                fin = fin.getSig();
            }
            fin.setSig(nuevo);
            fin = nuevo;
            this.cantidadElementos++;
        }
    }

    @Override
    public boolean esVacia() {
        return this.inicio == null;
    }

    @Override
    public int largo() {
        return cantidadElementos;
    }

    @Override
    public void vaciar() {
        this.inicio = null;
    }

    @Override
    public String mostrar() {
        String retorno = "";
        if(esVacia()) {
           return retorno;
        } else {
            return mostrarRecursivo(inicio, retorno);
        }
    }
    private String mostrarRecursivo(Nodo<T> nodo, String retorno) {
        if (nodo != null) {
            mostrarRecursivo(nodo.getSig(), retorno + nodo.getDato().toString() + " ");
        }
            return retorno;
    }

    @Override
    public T obtener(T dato) {
        Nodo<T> aux = this.inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return aux.getDato();
            }
            aux = aux.getSig();
        }
        return null;
    }

    @Override
    public boolean existe(T dato) {
        return obtener(dato) != null;
    }

    @Override
    public void eliminar(T dato) {
        Nodo<T> aux = this.inicio;
        while (aux != null) {
            if (aux.getSig() != null && aux.getSig().getDato().equals(dato)) {
                if(aux.getSig().getSig() !=null){
                    aux.setSig(aux.getSig().getSig());

                }
            }
            aux = aux.getSig();
        }
    }

    @Override
    public T obtenerFinal() {

        Nodo<T> fin = this.inicio;
        while (fin.getSig() != null) {
            fin = fin.getSig();
        }
        return fin.getDato();
    }

    @Override
    public void mostrarFiltrado(T dato) {
        filtrarRecursivo(this.inicio, dato);
    }

    private void filtrarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo != null) {
            if (nodo.getDato().equals(dato)) {
                System.out.print(nodo.getDato() + " ");
            }
            mostrarRecursivo(nodo.getSig());
        }
    }
    
    @Override
    public T obtenerPrincipio(){
        return inicio.getDato();
    }

    @Override
    public T obtenerSiguiente(T dato){
        Nodo<T> aux = this.inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                if(aux.getSig() != null){
                    return aux.getSig().getDato();
  
                }
            }
            aux = aux.getSig();
        }
        return null;
    }

}
