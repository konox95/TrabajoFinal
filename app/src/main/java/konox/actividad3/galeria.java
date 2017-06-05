package konox.actividad3;

/**
 * Created by Daniel on 05/06/2017.
 */

public class galeria {
    private String nombre;
    private int foto;

    public galeria(String nombre, int foto) {
        this.nombre = nombre;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
