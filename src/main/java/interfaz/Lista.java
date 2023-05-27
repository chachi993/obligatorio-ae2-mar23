
package interfaz;

    
    public interface Lista<T> {

    void agregarAlPrincipio(T dato);

    T elminarPrincipio();

    void agregarAlFinal(T dato);
    
    T obtener(T dato);

    boolean existe(T dato);
    
    boolean esVacia();

    int largo();

    void vaciar();

    String mostrar();
    
    void eliminar(T dato);

    public T obtenerFinal();
    
    public void mostrarFiltrado(T dato);
    
    public T obtenerPrincipio();

    public T obtenerSiguiente(T dato);
    
}
