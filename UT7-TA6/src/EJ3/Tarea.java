package EJ3;

public class Tarea {
    private String nombre;
    private int duracion;
    public Tarea(String nombre,int duracion ) {
        this.nombre = nombre;
        this.duracion=duracion;
    }
    public String getNombre() {
        return nombre;
    }
    public int getDuracion() {
        return duracion;
    }
}
