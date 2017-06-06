package konox.libreria1;

import java.util.ArrayList;

/**
 * Created by konox on 07/02/2017.
 */

public class MiPin {
    public double dbLatitud;
    public double dbLongitud;
    public String sNombre;
    public String sDescripcion;
    public String sTipo;
    public String sDificultad;
    public ArrayList<String> imgSpot;

    public MiPin(double dblat,double dblon,String snom){
        this.dbLatitud=dblat;
        this.dbLongitud=dblon;
        this.sNombre=snom;
    }

    public MiPin(double dblat,double dblon,String snom,String sdescripcion,String stipo,String sdificultad, ArrayList<String> imgSpot){
        this.dbLatitud=dblat;
        this.dbLongitud=dblon;
        this.sNombre=snom;
        this.sDescripcion=sdescripcion;
        this.sTipo=stipo;
        this.sDificultad=sdificultad;
        this.imgSpot = imgSpot;
    }

}
