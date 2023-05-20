package dominio;

public class EstacionDeTren {
    String codigo;
    String nombre;

    public EstacionDeTren() {
        this.codigo = "";
        this.nombre = "";
    }

    public EstacionDeTren(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
