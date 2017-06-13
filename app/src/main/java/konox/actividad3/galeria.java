package konox.actividad3;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Daniel on 05/06/2017.
 */

public class galeria {
    private String nombre;
    private String dificultad;
    private int foto;
    private String android_image_url;

    public galeria(String nombre,String dificultad,int foto) {
        this.nombre = nombre;
        this.dificultad=dificultad;
        this.foto = foto;
    }
    public galeria(String nombre,String dificultad) {
        this.nombre = nombre;
        this.dificultad=dificultad;
    }

    public galeria(String nombre,String dificultad, String android_image_url) {
        this.nombre = nombre;
        this.dificultad=dificultad;
        this.android_image_url=android_image_url;

    }

    public String getAndroid_image_url() {
        return android_image_url;
    }

    public void setAndroid_image_url(String android_image_url) {
        this.android_image_url = android_image_url;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

}
